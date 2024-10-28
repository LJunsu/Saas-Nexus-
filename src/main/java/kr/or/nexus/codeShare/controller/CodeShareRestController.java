package kr.or.nexus.codeShare.controller;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.HtmlUtils;

import kr.or.nexus.codeShare.compiler.JavaFileObjectFromSavedFile;
import kr.or.nexus.codeShare.compiler.JavaFileObjectFromString;
import kr.or.nexus.codeShare.javac.javacClass;
import kr.or.nexus.codeShare.service.CodeShareService;
import kr.or.nexus.codeShare.vo.codeShareVO;
import kr.or.nexus.commons.alarmAndLog.EventConstuctor.Event;
import kr.or.nexus.timeline.service.TimeLineService;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("rest/codeShare")
public class CodeShareRestController {

	@Autowired
	private CodeShareService codeShareService;

	@Autowired
	private TimeLineService timeLineService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private HttpServletRequest request;

	@PostMapping("update")
	public Map<String, Object> codeShareUpdate(
		@RequestBody codeShareVO codeShareVO
	) {
		codeShareService.codeShareUpdate(codeShareVO);
		codeShareVO code = codeShareService.codeShareDetail(codeShareVO);
		if(code!=null) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String url = request.getRequestURI();
			Event<codeShareVO, codeShareVO> event =
					new Event<>(methodName, code.getGroupId(), codeShareVO, code, null, "코드쉐어방 생성", url);
			publisher.publishEvent(event);
		}
	    Map<String, Object> response = new HashMap<>();
	    response.put("codeDetail", code);
		return response;

	}

	@PostMapping("create")
	public Map<String, Object> codeShareCreate(
		@RequestBody codeShareVO codeShareVO
		, Authentication authentication
	) {

		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();

		codeShareVO.setMemberId(realUser.getMemberName());

		codeShareService.codeShareInsert(codeShareVO);
		codeShareVO newCodeDetail = codeShareService.codeShareCreate(codeShareVO);


		Map<String, Object> response = new HashMap<>();
		response.put("newCodeDetail", newCodeDetail);

		return response;

	}

	@DeleteMapping("{codeShareId}")
	public Map<String, String> codeShareDelete(
		@PathVariable String codeShareId
	) {

		codeShareVO vo = new codeShareVO();
		vo.setCodeShareId(codeShareId);
		int chk = codeShareService.codeShareDelete(vo);

		Map<String, String> response = new HashMap<>();
		if(chk > 0) {
			response.put("YN", "Y");
		} else {
			response.put("YN", "N");
		}

		return response;

	}



	@Autowired
	WebApplicationContext context;

	@PostMapping("compiling")
	public Map<String, Object> compiling(
		@RequestBody String code
		, Locale locale
	) throws Exception { // json으로 source 받음

		javacClass javacClass = new javacClass();
		String source = javacClass.getCode(code);

		// D:\B_Util\7.Egov\eGovFrameDev-4.0.0-64bit\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Nexus\WEB-INF\shareRepository

		// **************** 소스 저장 위치
		File sourcePath = context.getResource("/WEB-INF/shareRepository/src").getFile(); // 스프링에 해당 파일
		System.out.println("Source path: " + sourcePath.getAbsolutePath());
		if(!sourcePath.exists()){ // 해당 파일이 없다면
			sourcePath.mkdirs(); // 파일 생성
		}

		// **************** 바이트코드 저장 위치
		File targetPath = context.getResource("/WEB-INF/shareRepository/classes").getFile(); // 스프링에 해당 파일
		if(!targetPath.exists()){ // 해당 파일이 없다면
			targetPath.mkdirs(); // 파일 생성
		}

		// ***************** 컴파일러 확보
		// javax.tools 패키지에서 제공하는 JavaCompiler 인터페이스를 동적으로 컴파일하기 위한 객체
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		// ***************** 컴파일 결과 관리 객체 생성
		// Java 컴파일러 API를 사용할 때 컴파일 과정에서 발생하는 진단 정보를 수집하기 위한 객체
		// JavaFileObject -> 진단 정보와 관련된 파일 객체의 타입 / 자바 소스 파일, 클래스 파일 등 자바 파일을 추상화한 인터페이스
		// DiagnosticCollector -> 컴파일러가 실행되는 동안 발생하는 모든 진단 정보를 수집, 저장
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

	    StringBuffer result = new StringBuffer(); // 결과를 담기 위한 StringBuffer
		Map<String, Object> resultMap = new HashMap<String, Object>();

		//******************************** 클래스명 확보
		// 파라미터로 받아온 소스 객체를 문자열로 변환한 값과 result를 인자로 전달
		// success가 true라면 패키지명+클래스명이 result에 담김
		// false라면 오류 메시지가 result에 담김
		boolean success = makeClassDesc(source.toString(), result);

		if(success){ // 패키지명+클래스명이 있다면
			// 패키지명+클래스명을 문자 형태로 저장
			// public class ClassName 이런 형태
			String qualifiedName = result.toString();
			// result를 qualifideName에 담고, result의 내용을 0번부터 result 길이만큼 삭제
			result.delete(0, result.length());

			//******************************** 인메모리 소스
//			JavaFileObjectFromString srcObject =
//					new JavaFileObjectFromString("string:///"
//							+qualifiedName.replaceAll("\\.", "/")+Kind.SOURCE.extension, source);

			//******************************** 저장된 파일 소스
			// 자바 컴파일러에 전달할 소스 코드 객체를 생성
			// 소스 코드는 메모리에 저장되고, 이를 컴파일러가 사용할 수 있도록 JavaFileObject로 래핑
			// = 자바 소스 파일이 디스크에 존재하지 않고, 메모리에 저장된 상태에서 컴파일 할 수 있도록 JavaFileObjectFromString 객체 생성
			JavaFileObjectFromString srcObject =
					new JavaFileObjectFromSavedFile(
							// sourcePath -> 소스 파일이 저장될 디렉터리 경로를 가지고 옴
							sourcePath.getAbsolutePath().replaceAll("\\\\", "/")
							+ "/"
							// qualifiedName -> 패키지명과 클래스명을 결합한 형태의 경로를 나타냄
							// 패키지 구분자인 .을 /로 변환하여 실제 파일 경로로 변환
							+ qualifiedName.replaceAll("\\.", "/")
							// .java 파일 확장자를 추가
							+ Kind.SOURCE.extension
							// 실제 자바 소스가 담긴 문자열 -> 컴파일할 클래스의 코드로 JavaFileObjectFromString에 전달
							, source);

			//******************************** 컴파일 소스 세팅
			// Iterable<? extends JavaFileObject> -> 자바 컴파일러가 컴파일할 수 있는 소스 파일들의 목록을 나타냄
			// JavaFileObject -> 자바 소스 파일을 나타내는 객체로, 파일 시스템에 존재하거나 메모리 상에 존재하는 자바 소스 코드를 컴파일 할 수 있음
			// Arrays.asList(srcObject) ->
			// 		Arrays.asList()는 배열을 리스트로 변환하는 메소드로,
			// 		하나의 srcObject(JavaFileObjectFromString 객체)를 배열로 변환한 후 리스트로 반환
			//	srcOject ->
			//		이전 메모리에 저장된 자바 소스 코드 파일을 나타내는 JavaFileObject 객체
			//		컴파일러에서 사용할 소스 파일
			Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(srcObject);

			//******************************** 컴파일 옵션 세팅
			// 자바 컴파일러에 전달할 옵션을 준비하는 과정
			// 각 옵션은 컴파일러에게 특정 작업을 지시하는 역할

			// Iterable<String> -> 문자열의 컬렉션을 나타냄 - 자바 컴파일러에 전달할 여러 옵션을 포함
			Iterable<String> options =
					// 배열을 리스트로 변환하는 메소드 - 컴파일러에 전달할 옵션들을 리스트 형태로 저장
					Arrays.asList(
						// 컴파일된 클래스 파일을 출력할 디렉토리를 지정하는 옵션
						// 이 옵션 다음에 나오는 경로는 컴파일된 .class 파일이 저장될 위치를 나타냄
						"-d"
						// targetPath -> 컴파일된 클래스 파일들이 저장될 경로
						// getAbsolutePath() ->
						//		해당 경로의 절대 경로를 반환
						//		절대 경로는 파일 시스템에서 컴파일된 클래스 파일의 정확한 저장 위치
						, targetPath.getAbsolutePath()
						// 컴파일 시 필요한 클래스 파일이나 라이브러리 파일들이 위치한 경로를 지정하는 옵션
						, "-classpath"
						// 컴파일 시 사용할 클래스 경로를 설정하는 역할
						// targetPath가 클래스 경로로 지정됨
						// 컴파일된 파일이 저장될 디렉토리와 동시에, 컴파일러가 필요한 클래스 파일을 검색할 때 사용할 경로로 설정
						, targetPath.getAbsolutePath()
					);

			//******************************** 컴파일 작업 생성
			//******************************** System.err 대신 에러 정보 직접 확인
			// StringWriter는 문자 데이터를 메모리에 저장하는 클래스
			// 컴파일 작업 중 발생하는 메시지, 오류 정보를 StringWriter 객체에 기록
			StringWriter writer = new StringWriter();
			// compiler.getTask()는 자바 컴파일러에서 컴파일 작업을 생성하는 메소드
			CompilationTask task =
					compiler.getTask(
						// 컴파일 메시지를 기록할 StringWriter 객체
						writer
						// 기본 파일 매니저 사용
						, null
						// 컴파일 중 발생하는 문제를 처리할 오류 리스너를 기본 처리 방식으로 사용
						, null
						// 컴파일러에게 전달할 옵션 목록
						// -d, -classpath에 전달
						, options
						// 소스 코드 내의 클래슬르 자동으로 컴파일 하도록 설정
						, null
						// 컴파일할 자바 소스 파일
						// JavaFileObject의 목록이며, 메모리 상에 저장된 소스 파일(srcObject)을 포함
						, compilationUnits
					);

			//******************************** 에러 메시지등의 기타 직접 작성시
//			CompilationTask task = compiler.getTask(null, null, diagnostics,
//					options, null, compilationUnits);

			//******************************** 컴파일
			// 자바 컴파일러가 컴파일 작업을 실제로 실행하고, 그 결과를 반환
			// task -> 이전에 생성한 CompilationTask 객체 - 컴파일러가 실행할 작업을 나타냄
			// call() -> 메소드를 호출하면, 자바 컴파일러가 실제 소스 코드를 컴파일
			// 성공 여부 - true(컴파일 성공), false(컴파일 중 오류 발생)
			success = task.call();

			//******************************** 컴파일 결과 탐색
			// 컴파일 과정에서 발생한 진단 메시지를 수집, 이를 결과에 추가
			// Diagnostic 객체는 컴파일 중 발생한 오류, 경고 등의 정보를 담음

			// diagnostics는 DiagnosticCollector<JavaFileObject> 객체
			// diagnostics.getDiagnostics() -> 컴파일 중 발생한 모든 진단 메시지를 반환
			for(Diagnostic diagnostic : diagnostics.getDiagnostics()){
				// getCode() -> 진단 메시지의 코드를 반환
				result.append(diagnostic.getCode() + "\n");
				result.append(
					// getKind() -> 진단 종류를 반환 - ERROR, WARING 등
					diagnostic.getKind()
					// getLineNumber() -> 소스 코드에서 진단이 발생한 라인 번호 반환
					+ " in line number : " + diagnostic.getLineNumber()
					// getPosition() -> 소스 코드에서 진단이 발생한 위치를 반환
					+ " position : " + diagnostic.getPosition() + "\n"
				);
				// getMessage(locale) -> 진단 메시지를 반환 - locale는 메시지를 출력할 언어와 지역을 지정
				result.append(diagnostic.getMessage(locale) + "\n");
			}

			//******************************** 컴파일 성공시 런타임 획득 후 실행
			if (success) { // 컴파일 성공 시
//			    	ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			    	loader.loadClass(qualifiedName).getDeclaredMethod("main", new Class[]{String[].class})
//			    	.invoke(null, new Object[] { null });
				// 웹컨텍스트  클래스패스에서 실행하는 경우, 잦은 리로딩으로 성능 저하
				// 현재 시스템의 임의의 위치에서 실행하는 경우, 정상적인  classpath 를 잡을수 있도록,
				// ProcessBuilder 의 커맨드를 설정할때 옵션 필요.
				// 주의! ProcessBuilder 의 커맨드는 반드시 토큰별로 리스트에 저장할것.

				// 외부 프로세스를 생성하는 클래스 - 자바 명령어를 사용하여 컴파일된 클래스를 실행
				ProcessBuilder builder = new ProcessBuilder();

//				Redirect.INHERIT : 현재 자바 프로세스의 콘솔로 출력할수 있음.
//				Redirect.PIPE : process.getInputStream() 을 통해 출력 데이터 입력 스트림을 확보할수 있음.

				// 프로세스의 표준 출력을 현재 자바 프로세스의 표준 출력으로 리디렉션
				builder.redirectOutput(Redirect.PIPE);
				// 프로세스의 표준 오류 출력을 현재 자바 프로세스의 표준 오류로 리디렉션
				builder.redirectError(Redirect.PIPE);
				// 실행할 명령어와 인수를 리스트 형태로 저장
				List command =
					// java -cp <경로> <클래스명>
					Arrays.asList(
						"java"
						, "-cp"
						, targetPath.getAbsolutePath()
						, qualifiedName
					);

				// command -> 실행할 명령어와 그 인수들을 리스트 형태로 저장한 것
				// ProcessBuilder에 실행할 명령어와 인수를 설정
				// command -> java -cp <경로> <클래스명> 명령어를 포함하고 있음
				// start() -> ProcessBuilder에 의해 설정된 명령어를 사용해 새로운 프로세스를 시작
				// Process 객체를 반환하며, 이 객체는 시작된 프로세스와의 입력, 출력, 오류 스트림에 접근할 수 있게 해줌
				Process process = builder.command(command).start();

				try(
					InputStream in = process.getInputStream(); // 프로세스의 표준 출력을 읽음
					InputStream err = process.getErrorStream(); // 프로세스의 표준 오류 출력을 읽음
				){
					byte[] message = null;
					// process.waitFor() -> 프로세스가 종료될 때까지 대기
					// 종료 코드가 0이 아닌 경우
					if(process.waitFor()!=0){
						// 프로세스가 비정상적으로 종료된 경우에는 success를 false로 설정
						success = false;
					}

					message = new byte[in.available()];
					in.read(message, 0, message.length); // 표준 출력을 읽어옴
					result.append(new String(message, "MS949")); // 읽어온 바이트 배열을 문자열로 변환 - MS949 문자 인코딩

					message = new byte[err.available()];
					err.read(message, 0, message.length); // 오류 출력을 읽어옴
					result.append(new String(message, "MS949")); // 읽어온 바이트 배열을 문자열로 변환 - MS949 문자 인코딩
				}

//				클래스를 삭제하고 싶다면, 주석을 없앨것이나,
//				이전에 컴파일한 클래스와 의존관계를 형성하는 다음 클래스가 컴파일되거나 실행되는 경우가 있다면 주석처리.
//				String classPath = targetPath+"\\"+qualifiedName.replaceAll("\\.", "\\\\")+Kind.CLASS.extension;
//				FileUtils.deleteQuietly(new File(classPath));
			}else{
				writer.flush();
				result.append(writer.toString());
			}
		}

		// 컴파일 성공 여부
	    resultMap.put("success", success);
	    // 컴파일 결과 메시지
//	    resultMap.put("result", HtmlUtils.htmlEscape(result.toString()).replaceAll("\n", "<br />"));
	    resultMap.put("result", HtmlUtils.htmlEscape(result.toString()).replaceAll("\n", "<br/>").replace(" ", "&nbsp;"));
	    System.out.println(resultMap.get("result"));

    	return resultMap;
	}

	/**
	 * 패키지명을 포함한 클래스의 qualifiedName 을 파싱하는 메소드
	 * @param source 원본 소스
	 * @param result 파싱 결과를 담을 객체
	 * @return 파싱 성공시 true, 실패시 false
	 */
	private boolean makeClassDesc(String source, StringBuffer result){
		//*****************************
		// 소스에서 package 단어가 처음 나타는 위치(인덱스)를 찾음(0부터 시작), 만약 없으면 -1 반환
		int packageIdx = source.indexOf("package");
		String packageName = "";
		if(packageIdx != -1){ // package가 있다면
			// packageIdx 이후 처음 나타나는 ;의 인덱스
			// package 선언의 끝을 파악
			int semicolonIdx = source.indexOf(";", packageIdx);
			// 소스에서 packageIdx부터 semicolonIdx까지의 문자열을 잘라냄
			// package kr.or.ddit.compiler 이런 형태를 얻기 위함
			packageName = source.substring(packageIdx, semicolonIdx);
			// 패키지 네임에서 공백을 기준으로 문자열을 나눔
			// package와 kr.or.ddit.compiler의 형태로 두 개의 배열 요소 생성
			// 두 요소 중 두번째인 kr.or.ddit.compiler을 선택
			// 마지막에 .을 붙여 패키지 이름을 반환
			packageName = packageName.split("\\s+")[1].trim()+".";
		}

		//***************************** 패키지명 파싱
		// 소스에서 public 단어가 처음 등장하는 위치(인덱스)를 찾음
		// 보통 자바의 접근 제한자는 클래스 선언 앞에 위치하므로 클래스를 찾기 위한 기준점
		int classIdx = source.indexOf("public");
		// 소스에서 class 단어가 처음 등장하는 위치
		// public이 발견된 경우, 그 인덱스에서 시작해서 class를 찾음
		// public이 없는 경우, 소스 문자열의 시작부터 class를 찾음
		classIdx = source.indexOf("class", classIdx>0?classIdx:0);
		if(classIdx==-1){ // 소스에서 class를 찾지 못했다면
			// result에 클래스를 찾지 못했음을 알리는 메시지 저장
			result.append("class Naminge Exception : class name not found");
			return false;
		}else{ // 소스에서 class를 찾았다면
			// 소스에서 classIdx(클래스의 인덱스) 이후 처음 등장하는 {의 위치(인덱스)
			int braceIdx = source.indexOf("{", classIdx);
			// 클래스부터 { 전까지 그 사이의 문자열을 잘라냄
			// public class ClassName 이런 형태를 얻고, 불필요한 공백 제거
			source = source.substring(classIdx, braceIdx).trim();
			// 공백을 기준으로 소스를 나누어 각각 배열의 요소로 생성한 후 두번째 요소인 class를 건너뛰고, ClassName을 선택
			String className = source.split("\\s+")[1].trim();
			//***************************** 클래스명 파싱
			// 패키지와 클래스명을 합친 문자열을 result에 저장
			result.append(packageName + className);
			return true;
		}
	}
//	@PostMapping("compiling")
//	public String compiling(
//			@RequestBody String code
//			) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
//
//		String clasName = "Test";
//
//		javacClass javacClass = new javacClass();
//		String getCodeText = javacClass.getCode(code);
//
//		String error = javacClass.compile("Test", getCodeText);
//
//		if (!error.trim().equals("")) {
//			System.out.println(error);
//		}
//
//		String folder = System.getProperty("user.dir") + "\\dynamic";
//
//		File file = new File(folder, clasName + ".class");
//		byte[] classByte = classByte = Files.readAllBytes(file.toPath());
//		file.delete();
//
//		FileClassLoader fc = new FileClassLoader();
//		Class dynamic = fc.findClass(classByte, "dynamic." + clasName);
//
//		Object obj =  dynamic.newInstance();
//		Method main = dynamic.getMethod("main", String[].class);
//		String[] params = null;
//		main.invoke(obj, (Object) params);
//
//		return code;
//
//	}
}
