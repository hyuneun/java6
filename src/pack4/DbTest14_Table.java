package pack4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DbTest14_Table extends JFrame{
	String [][] datas = new String[0][4];
	String [] title = {"1","2","3","4"};
	DefaultTableModel model;
	JTable table;
	JLabel lblcount;
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	public DbTest14_Table() {
		setTitle("상품");
		
		layInit();
		accDb();
		
		setBounds(200, 200, 300, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void layInit() {
		model = new DefaultTableModel(datas, title);
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		
		lblcount = new JLabel("�Ǽ� : ");
		add("Center",scroll);
		add("South", lblcount);

	}
	
	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			pstmt = conn.prepareStatement("select * from sangdata");
			rs = pstmt.executeQuery();
			int cou = 0;
			while(rs.next()){
				String code = rs.getString(1);
				String sang = rs.getString(2);
				String su = rs.getString(3);
				String dan = rs.getString(4);
				
				String[] imsi = {code,sang,su,dan};
				model.addRow(imsi);
				cou++;
			}
			lblcount.setText("" + cou);
		} catch (Exception e) {
			System.out.println(e);
		}finally {
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
		new DbTest14_Table();

	}

}

