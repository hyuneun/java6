package pack4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Test3 extends JFrame{
	String [][] datas = new String[0][0];
	String [] title = {"사번","이름","성별","연봉","세금","실수령액"};
	DefaultTableModel model;
	JTable table;
	JLabel lblcount;
	double e=0;
	int f=0;
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	public Test3() {
		setTitle("직원자료");
		
		layInit();
		accDb();
		
		setBounds(200, 200, 500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void layInit() {
		model = new DefaultTableModel(datas, title);
		table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		
		lblcount = new JLabel("인원수 : ");
		add("Center",scroll);
		add("South", lblcount);

	}
	
	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			pstmt = conn.prepareStatement("select * from sawon");
			rs = pstmt.executeQuery();
			int cou = 0;
			new Test2(this);
			while(rs.next()){
				String a = rs.getString("sawon_no");
				String b = rs.getString("sawon_name");
				String c = rs.getString("sawon_gen");
				String d = rs.getString("sawon_pay");
				if(rs.getInt("sawon_pay") >= 4000){
					e = 0.05 * rs.getInt("sawon_pay");
				}else if(rs.getInt("sawon_pay") >= 3500){
					e = 0.04 * rs.getInt("sawon_pay");
				}else if(rs.getInt("sawon_pay") >= 3000){
					e = 0.03 * rs.getInt("sawon_pay");
				}else if(rs.getInt("sawon_pay") < 3000){
					e = 0.02 * rs.getInt("sawon_pay");
				}
				e = e * 10000; 
				int as = (int) Math.round(e);
				DecimalFormat De = new DecimalFormat("###,###,##0");
				String g = De.format(e);
				
				//String aa = Integer.toString(as);
				f = rs.getInt("sawon_pay") * 10000;
				f = f - as;
				
				//Double.toString(e);
				String bb = De.format(f);	
				
				
				String[] imsi = {a,b,c,d,g,bb};
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
	
	public void exit() {
		System.exit(1);

	}
	
	public static void main(String[] args) {
		new Test3();

	}

}

