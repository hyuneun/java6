package db.pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTest1 {
	private Connection conn;//DB 연결 객체
	private Statement stmt;//sql문을 실행
	private ResultSet rs;//select결과처리
	
	public DBTest1() {
		try {
			//1. Driver loading
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("로딩실패" + e);
		}
		
		try {
			//2. DB와 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","tiger");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.50:1521:orcl","scott","tiger");
			//System.out.println("성공");
		} catch (Exception e) {
			System.out.println("db연결실패" + e);
		}
		
		try {
			//3. sql문 수행 : 자료읽기
			stmt = conn.createStatement();
			
//			rs = stmt.executeQuery("select * from sangdata");//""사용!
//			rs.next();//record 포인터를 이동시킨다
//			boolean b = rs.next();
//			System.out.println(b);//자료가있으면 참 아니면 거짓
//			String code = rs.getString("code");
//			String sang = rs.getString("sang");
//			String su = rs.getString("su");
//			String dan = rs.getString("dan");
//			System.out.println(code +""+ sang +""+ su +""+ dan);
		
			//모든자료 읽기
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
			System.out.println("건수" + count);//<-(권장)서버가 일하게 하지말자
			String sql = "select count(*) as cou from sangdata";
			rs = stmt.executeQuery(sql);
			rs.next();
			System.out.println("건수 " + rs.getString("cou"));
			System.out.println("_______");
			
			rs = stmt.executeQuery("select code as bun,su,sang,dan from sangdata");
			//왠만하면 칼럼명으로 주는것을 권장
			while(rs.next()){
				//String code = rs.getString("bun");//별명을 주면 별명으로 읽어야함
				int code = rs.getInt("bun");//숫자는 스트링이나 인트 둘다 읽을수있다(문자->숫자는 안됨)
				String sang = rs.getString(2);//select 순서로 읽을수있다
				String su = rs.getString(4);
				String dan = rs.getString("dan");
				System.out.println(code +" "+ sang +" "+ su +" "+ dan);
				count++;
			}
				System.out.println("_______1");
				
			rs = stmt.executeQuery("select sawon_no,sawon_name,buser_name,buser_tel,sawon_jik,sawon_gen from sawon full join buser on buser_no=buser_num where sawon_gen = '남'");
			//왠만하면 칼럼명으로 주는것을 권장
			while(rs.next()){
				//String code = rs.getString("bun");//별명을 주면 별명으로 읽어야함
				String sang = rs.getString("sawon_no");//숫자는 스트링이나 인트 둘다 읽을수있다(문자->숫자는 안됨)
				String sang2 = rs.getString("sawon_name");//select 순서로 읽을수있다
				String sang3 = rs.getString("buser_name");
				String sang4 = rs.getString("buser_tel");
				String sang5 = rs.getString("sawon_jik");
				String sang6 = rs.getString("sawon_gen");
				System.out.println(sang+" "+sang2+" "+sang3+" "+sang4+" "+sang5+" "+sang6);
				
			}
			
			
		} catch (Exception e) {
			System.out.println("처리오류 : " + e);
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
