package db.pack;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DbTest2 {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private Properties pro = new Properties();
		
	
	
	
	public DbTest2() {
		try {
			pro.load(new FileInputStream("/work/sou/java7/src/db/pack/test.properties"));
			
			Class.forName(pro.getProperty("driver"));
			conn = DriverManager.getConnection(
					pro.getProperty("url"),
					pro.getProperty("user"),
					pro.getProperty("password"));
			
			stmt = conn.createStatement();
			
			//insert : 기본은 auto commit
			//insert에 성공하면 1반환 아니면 0 ,나머지는 갯수 반환 아니면 0
			//String sql = "insert into sangdata values(4,'우산',1,5000)";
			//stmt.executeUpdate(sql);//select이외의 실행은 excuteUpdate 로 한다
			//Transaction 처리 : 주로 은행계좌 이체따위의 작업에 효과적
//			conn.setAutoCommit(false);//autocommit 해체,트렌젝션시작
//			String sql = "insert into sangdata values(5,'장화',3,95000)";
//			stmt.executeUpdate(sql);
//			//conn.rollback();
//			conn.commit();
//			conn.setAutoCommit(true);//트렌젝션끝
	
			//update
			String sql2 = "update sangdata set sang='향수',su =7 where code=5";
			int re = stmt.executeUpdate(sql2);
			if(re > 0)
				System.out.println("성공");
			else
				System.out.println("실패");
			
			//delete
			String sql3 = "delete from sangdata where code=4";
			re = stmt.executeUpdate(sql3);
			if(re > 0)
				System.out.println("성공");
			else
				System.out.println("실패");
			
			//select
			rs = stmt.executeQuery("select * from sangdata order by code desc");
			int cou = 0;
			while(rs.next()){
				System.out.println(
						rs.getString("sang") + " " +
						rs.getString("su") + " " +
						rs.getString("dan"));
				cou++;
			}
			System.out.println("건수 : " + cou);
		} catch (Exception e) {
			System.out.println("process err:" + e);
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public static void main(String[] args) {
		new DbTest2();

	}

}
