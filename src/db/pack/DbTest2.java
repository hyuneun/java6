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
			
			//insert : �⺻�� auto commit
			//insert�� �����ϸ� 1��ȯ �ƴϸ� 0 ,�������� ���� ��ȯ �ƴϸ� 0
			//String sql = "insert into sangdata values(4,'���',1,5000)";
			//stmt.executeUpdate(sql);//select�̿��� ������ excuteUpdate �� �Ѵ�
			//Transaction ó�� : �ַ� ������� ��ü������ �۾��� ȿ����
//			conn.setAutoCommit(false);//autocommit ��ü,Ʈ�����ǽ���
//			String sql = "insert into sangdata values(5,'��ȭ',3,95000)";
//			stmt.executeUpdate(sql);
//			//conn.rollback();
//			conn.commit();
//			conn.setAutoCommit(true);//Ʈ�����ǳ�
	
			//update
			String sql2 = "update sangdata set sang='���',su =7 where code=5";
			int re = stmt.executeUpdate(sql2);
			if(re > 0)
				System.out.println("����");
			else
				System.out.println("����");
			
			//delete
			String sql3 = "delete from sangdata where code=4";
			re = stmt.executeUpdate(sql3);
			if(re > 0)
				System.out.println("����");
			else
				System.out.println("����");
			
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
			System.out.println("�Ǽ� : " + cou);
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
