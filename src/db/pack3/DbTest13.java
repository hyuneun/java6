package db.pack3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbTest13 {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	public DbTest13() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");

			// //자료추가
			// String isql = "insert into sangdata values(?,?,?,?)";
			// pstmt = conn.prepareStatement(isql);
			// pstmt.setString(1, "10");
			// pstmt.setString(2, "안경");
			// pstmt.setInt(3, 15);
			// pstmt.setString(4, "56000");
			// int re = pstmt.executeUpdate();
			// if(re == 1){
			// System.out.println("성공");
			// }else
			// System.out.println("실패");

			// 자료수정
//			String usql = "update sangdata set sang=?,su=? where code=?";
//			pstmt = conn.prepareStatement(usql);
//			pstmt.setString(1, "물안경");
//			pstmt.setInt(2, 13);
//			pstmt.setInt(3, 10);
//			int re = pstmt.executeUpdate(); 
//			if (re >= 1) {
//				System.out.println("성공");
//			} else
//				System.out.println("실패");
			
			//자료삭제
			String dsql = "delete from sangdata where code=?";
			pstmt = conn.prepareStatement(dsql);
			pstmt.setInt(1, 10);
			int re = pstmt.executeUpdate(); 
			
			if (re >= 1) {
				System.out.println("성공");
			} else
				System.out.println("실패");
			
			
			
			// 전체자료읽기

			String sql = "select * from sangdata";
			pstmt = conn.prepareStatement(sql);// 선처리방식(sql 문장을 미리준다)
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("code") + rs.getString(2) + rs.getString(3) + rs.getString(4));
			}
			System.out.println();
			// 부분자료읽기
			String ca = "1";
			// sql = "select * from sangdata where code = " + ca; 시큐어코딩 위배
			sql = "select * from sangdata where code = ?";
			pstmt = conn.prepareStatement(sql);// 선처리방식(sql 문장을 미리준다)
			pstmt.setString(1, ca);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("code") + rs.getString(2) + rs.getString(3) + rs.getString(4));
			}

		} catch (Exception e) {
			System.out.println("로딩실패" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {

			}
		}
	}

	public static void main(String[] args) {
		new DbTest13();

	}

}
