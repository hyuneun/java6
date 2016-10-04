package db.pack3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.internal.OracleCallableStatement;

public class DbTest11 {
	Connection conn;
	CallableStatement cstmt;
	OracleCallableStatement ocstmt;
	ResultSet rs;
	
	public DbTest11() {//����Ʈ�� Ŀ���ιޱ�
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			cstmt = conn.prepareCall("{call pro_sel(?)}");
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			
			ocstmt = (OracleCallableStatement)cstmt;
			rs = ocstmt.getCursor(1);
			while(rs.next()){
				System.out.println(rs.getString("sawon_no") + rs.getString("sawon_name"));
			}
		} catch (Exception e) {
			
		}finally {
			try {
				if(rs != null) rs.close();
				if(cstmt != null) cstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
	new DbTest11();

	}

}