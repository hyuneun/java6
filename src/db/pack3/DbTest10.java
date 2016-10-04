package db.pack3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

//oracle의 procedure 수행
public class DbTest10 {
	Connection conn;
	CallableStatement cstmt; // 프로시저 처리용

	public DbTest10() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			
			process();
			
			conn.close();
		} catch (Exception e) {
			
		}
	}

	private void process() {
		try{
			cstmt = conn.prepareCall("{call pro_del(?)}");
			cstmt.setInt(1,999);//첫번째는 물음표 순서
			cstmt.executeUpdate();
			System.out.println("삭제성공");
		}catch(Exception e){
			System.out.println(e);
		}
		finally {
			try {
				
			
			if(cstmt != null) cstmt.close();
			} catch (Exception e2) {
				
			}
		}

	}
	public static void main(String[] args) {
		new DbTest10();

	}

}
