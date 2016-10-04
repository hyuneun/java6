package db.pack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Prapertist {

	public static void main(String[] args) {
		// 중요정보는 소스내에 기술하는 것이 아니라 별도의 파일(***.properties)에 저장하고 읽는다.
		//secure coding에 근거해서 소스 작성
		Properties pro = new  Properties();
		
		//read
		try {
			pro.load(new FileInputStream("c:/work/ex1.properties") );
			System.out.println(pro.getProperty("mes1"));
			System.out.println(pro.getProperty("mes2"));
		} catch (Exception e) {
			System.out.println("에러" + e);
		}
		//write
		try {
			pro.setProperty("mes1", "so good");
			pro.setProperty("mes2", "홍길동");
			pro.store(new FileOutputStream("c:/work/ex1.properties"), null);
			System.out.println("저장성공");
		} catch (Exception e) {

		}

	}

}
