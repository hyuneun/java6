package db.pack3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest8_Pulling {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBConnectionMgr mgr;

	public DbTest8_Pulling() {
		try {
			mgr = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		accDb();
	}
	
	private void accDb() {
		//System.out.println("success");
		try {
			conn = mgr.getConnection();
			String sql = "select * from sangdata";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				System.out.println(rs.getString(1) + rs.getString(2));
			}
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			mgr.freeConnection(conn, stmt, rs);
		}

	}
	
	public static void main(String[] args) {
		new DbTest8_Pulling();
	}

}
