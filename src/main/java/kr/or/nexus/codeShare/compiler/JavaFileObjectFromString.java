package kr.or.nexus.codeShare.compiler;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

// 메모리에 저장된 자바 소스 코드를 컴파일러에서 사용할 수 있도록 변환
public class JavaFileObjectFromString extends SimpleJavaFileObject{

	private String code;

	public JavaFileObjectFromString(String location, String code) throws IOException {
		// location -> 자바 파일의 위치를 URI 형태로 받음
		// 자바 컴파일러가 소스 코드의 위치를 알 수 있도록
		// 부모 클래스인 SimpleJavaFileObject의 생성자를 호출하여, 이 파일 객체가 소스 코드 파일임을 컴파일러에게 알림
		// Kind.SOUREC는 소스 파일임을 나타냄
		super(URI.create(location), Kind.SOURCE);
		// 컴파일할 자바 소스 코드가 저장된 문자열
		this.code = code;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		return code;
	}
}
