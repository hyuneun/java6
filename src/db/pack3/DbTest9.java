package db.pack3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest9 {
	private Connection conn;//DB 연결 객체
	private Statement stmt;//sql문을 실행
	private ResultSet rs;//select결과처리
	
	
	public DbTest9() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			process();
			
			conn.close();
		} catch (Exception e) {
			System.out.println("로딩실패" + e);
		}
	}
	private void process() {
		try {
			stmt = conn.createStatement();
			boolean b = stmt.execute("select * from sangdata");//셀렉트의 결과가 있으면 트루
			System.out.println(b);
			if(b){
				rs = stmt.getResultSet();
				while(rs.next()){
					System.out.println(rs.getString(1) + rs.getString(2));
				}
			}else{
				System.out.println("업다");
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
