package pack4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest9 {
	private Connection conn;  //DB 연결 객체
	private Statement stmt;   //SQL문을 실행
	private ResultSet rs;     //select의 결과 처리
	
	public DbTest9() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			
			process();
			
			conn.close();
		} catch (Exception e) {
			System.out.println("연결 실패:" + e);
		}
	}
	
	private void process(){
		try {
			stmt = conn.createStatement();
			//executeQuery, executeUpdate 두 가지 모두의 경우를 포함한다.
			//즉, DDL, DML, DCL 모두 사용할 수 있다는 것이다.
			//단 return값이 boolean 타입이며 select문일때는 true를, 그 외는 false를 반환한다.
			boolean b = false;
			
			b = stmt.execute("update sangdata set sang='향기젤' where code=11");
			System.out.println("b는 " + b);
			
			int re = stmt.getUpdateCount();
			System.out.println("반환값은 " + re);
			if(re >= 1){
				System.out.println("작업 성공");
			}else{
				System.out.println("작업 실패");
			}
			
			b = stmt.execute("select * from sangdata");
			System.out.println("select 후 b : " + b);
			rs = stmt.getResultSet();//셀렉트의 결과반환
			while(rs.next()){
				System.out.println(rs.getString(1) + " " + rs.getString(2));
			}
		} catch (Exception e) {
			System.out.println("process err:" + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		new DbTest9();
	}
}
