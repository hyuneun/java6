package db.pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTest1 {
	private Connection conn;//DB ���� ��ü
	private Statement stmt;//sql���� ����
	private ResultSet rs;//select���ó��
	
	public DBTest1() {
		try {
			//1. Driver loading
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("�ε�����" + e);
		}
		
		try {
			//2. DB�� ����
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","tiger");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.50:1521:orcl","scott","tiger");
			//System.out.println("����");
		} catch (Exception e) {
			System.out.println("db�������" + e);
		}
		
		try {
			//3. sql�� ���� : �ڷ��б�
			stmt = conn.createStatement();
			
//			rs = stmt.executeQuery("select * from sangdata");//""���!
//			rs.next();//record �����͸� �̵���Ų��
//			boolean b = rs.next();
//			System.out.println(b);//�ڷᰡ������ �� �ƴϸ� ����
//			String code = rs.getString("code");
//			String sang = rs.getString("sang");
//			String su = rs.getString("su");
//			String dan = rs.getString("dan");
//			System.out.println(code +""+ sang +""+ su +""+ dan);
		
			//����ڷ� �б�
			rs = stmt.executeQuery("select * from sangdata");
			int count = 0;
			while(rs.next()){
				String code = rs.getString("code");
				String sang = rs.getString("sang");
				String su = rs.getString("su");
				String dan = rs.getString("dan");
				System.out.println(code +" "+ sang +" "+ su +" "+ dan);
				count++;
			}
			System.out.println("�Ǽ�" + count);//<-(����)������ ���ϰ� ��������
			String sql = "select count(*) as cou from sangdata";
			rs = stmt.executeQuery(sql);
			rs.next();
			System.out.println("�Ǽ� " + rs.getString("cou"));
			System.out.println("_______");
			
			rs = stmt.executeQuery("select code as bun,su,sang,dan from sangdata");
			//�ظ��ϸ� Į�������� �ִ°��� ����
			while(rs.next()){
				//String code = rs.getString("bun");//������ �ָ� �������� �о����
				int code = rs.getInt("bun");//���ڴ� ��Ʈ���̳� ��Ʈ �Ѵ� �������ִ�(����->���ڴ� �ȵ�)
				String sang = rs.getString(2);//select ������ �������ִ�
				String su = rs.getString(4);
				String dan = rs.getString("dan");
				System.out.println(code +" "+ sang +" "+ su +" "+ dan);
				count++;
			}
				System.out.println("_______1");
				
			rs = stmt.executeQuery("select sawon_no,sawon_name,buser_name,buser_tel,sawon_jik,sawon_gen from sawon full join buser on buser_no=buser_num where sawon_gen = '��'");
			//�ظ��ϸ� Į�������� �ִ°��� ����
			while(rs.next()){
				//String code = rs.getString("bun");//������ �ָ� �������� �о����
				String sang = rs.getString("sawon_no");//���ڴ� ��Ʈ���̳� ��Ʈ �Ѵ� �������ִ�(����->���ڴ� �ȵ�)
				String sang2 = rs.getString("sawon_name");//select ������ �������ִ�
				String sang3 = rs.getString("buser_name");
				String sang4 = rs.getString("buser_tel");
				String sang5 = rs.getString("sawon_jik");
				String sang6 = rs.getString("sawon_gen");
				System.out.println(sang+" "+sang2+" "+sang3+" "+sang4+" "+sang5+" "+sang6);
				
			}
			
			
		} catch (Exception e) {
			System.out.println("ó������ : " + e);
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {

			}
		}
		
	}
	
	public static void main(String[] args) {
		new DBTest1();

	}

}
