package db.pack3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest9 {
	private Connection conn;//DB ���� ��ü
	private Statement stmt;//sql���� ����
	private ResultSet rs;//select���ó��
	
	
	public DbTest9() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			process();
			
			conn.close();
		} catch (Exception e) {
			System.out.println("�ε�����" + e);
		}
	}
	private void process() {
		try {
			stmt = conn.createStatement();
			boolean b = stmt.execute("select * from sangdata");//����Ʈ�� ����� ������ Ʈ��
			System.out.println(b);
			if(b){
				rs = stmt.getResultSet();
				while(rs.next()){
					System.out.println(rs.getString(1) + rs.getString(2));
				}
			}else{
				System.out.println("����");
			}
		} catch (Exception e) {
			
		}finally {
			try {
				
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			} catch (Exception e2) {
				
			}
		}

	}
	public static void main(String[] args) {
		new DbTest9();

	}

}
