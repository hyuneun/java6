package db.pack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Prapertist {

	public static void main(String[] args) {
		// �߿������� �ҽ����� ����ϴ� ���� �ƴ϶� ������ ����(***.properties)�� �����ϰ� �д´�.
		//secure coding�� �ٰ��ؼ� �ҽ� �ۼ�
		Properties pro = new  Properties();
		
		//read
		try {
			pro.load(new FileInputStream("c:/work/ex1.properties") );
			System.out.println(pro.getProperty("mes1"));
			System.out.println(pro.getProperty("mes2"));
		} catch (Exception e) {
			System.out.println("����" + e);
		}
		//write
		try {
			pro.setProperty("mes1", "so good");
			pro.setProperty("mes2", "ȫ�浿");
			pro.store(new FileOutputStream("c:/work/ex1.properties"), null);
			System.out.println("���强��");
		} catch (Exception e) {

		}

	}

}
