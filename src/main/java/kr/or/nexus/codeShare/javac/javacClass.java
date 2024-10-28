package kr.or.nexus.codeShare.javac;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class javacClass {

	public String execute(String command) {
		StringBuffer output = new StringBuffer();
		Process process = null;
		BufferedReader bufferReader = null;
		Runtime runtime = Runtime.getRuntime();
		String osName = System.getProperty("os.name");

		// 윈도우일 경우
		if (osName.indexOf("Windows") > -1) {
			command = "cmd /c " + command;
		}

		try {
			process = runtime.exec(command);
			Scanner s = new Scanner(process.getInputStream(), "euc-kr");
			while (s.hasNextLine() == true) {
				// 표준출력으로 출력
				output.append(s.nextLine() + System.getProperty("line.separator"));
			}
			s = new Scanner(process.getErrorStream(), "euc-kr");
			while (s.hasNextLine() == true) {
				// 에러 출력
				output.append(s.nextLine() + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			output.append("IOException : " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				process.destroy();
				if (bufferReader != null)
					bufferReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return output.toString();
	}

	public String compile(String clsName, String clsStr) {
		// 클래스 이름과, 자바 파일 내용 포함.
		// 자바 파일 생성.
		File file = wirteClass(clsName, clsStr);

		// 자바 파일 컴파일 명령어
		String command = "javac -source 1.8 -target 1.8 -encoding UTF-8 dynamic/" + clsName + ".java";
		String result = execute(command);
		// 임시 생성된 자바 파일 삭제.
		file.delete();
		return result;
	}

	public File wirteClass(String clsName, String stringClass) {
		String folder = System.getProperty("user.dir") + "\\dynamic";
		// 임의의 패키지명 부여
		return write(folder, clsName + ".java", "package dynamic; " + stringClass);
	}

	private File write(String folderName, String fileName, String result) {
		File folder = new File(folderName);
		if (!folder.exists())
			folder.mkdirs();
		FileWriter fw;
		try {
			File file = new File(folderName, fileName);
			fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(result);
			bw.close();

			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

    public String getCode(String code) {
    	String javaText;

//    	javaText = code.replace("\\n", "\n").replace("\"", "");
    	javaText = code.substring(1, code.length() - 1);
    	javaText = javaText.replace("\\n", "\n");
    	javaText = javaText.replace("\\\"", "\"");
    	System.out.println(javaText);

    	return javaText;
    }

}
