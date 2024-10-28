package kr.or.nexus.codeShare.compiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// JavaFileObjectFromString을 확장하여 Java 소스 코드를 파일 시스템에 저장하는 기능
public class JavaFileObjectFromSavedFile extends JavaFileObjectFromString{

	public JavaFileObjectFromSavedFile(String location, String code) throws IOException {
		super(location, code);
		// 경로에서 마지막 슬래시의 인덱스를 찾음
		int lastIdx = location.lastIndexOf("/");
		String packagePath = null;
		String className = location;

		if(lastIdx!=-1){
			// 패키지 경로와 클래스 이름을 분리
			packagePath = location.substring(0, lastIdx);
			className = location.substring(lastIdx+1);

			// 패키지 경로에 해당하는 디렉토리가 존재하지 않으면 생성
			File packageDepth = new File(packagePath);
			if(!packageDepth.exists()){
				packageDepth.mkdirs();
			}

			// 파일을 생성하고 소스 코드를 파일에 작성
			File sourceFile = new File(packageDepth, className);
			if(!sourceFile.exists()){
				sourceFile.createNewFile();
			}

			FileWriter writer = new FileWriter(sourceFile);
			writer.write(code, 0, code.length());
			writer.flush();
			writer.close();
		}else{
			throw new RuntimeException(location + "파일 저장중 예외발생");
		}
	}
}