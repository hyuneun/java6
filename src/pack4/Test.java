package pack4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Test extends JFrame implements ItemListener{
	String [][] datas = new String[0][5];
	String [] title = {"사번","이름","연봉","입사년","관리고객수"};
	DefaultTableModel model;
	JTable table;
	JLabel lblcount;
	JCheckBox box2 = new JCheckBox("");
	JComboBox box = new JComboBox();
	
	JPanel panel = new JPanel();
	JPanel panel2 = new JPanel();
	JTextField a = new JTextField("",5);
	JTextField b = new JTextField("",5);
	JTextField c = new JTextField("",5);
	JTextField d = new JTextField("",5);
	
	int count=0;
	Connection conn,conn2,conn3,conn4;
	PreparedStatement pstmt,pstmt2,pstmt3,pstmt4;
	ResultSet rs,rs2,rs3,rs4;
	public Test() {
		setTitle("부서명별 자료보기");
		
		
		layInit();
		
		setBounds(200, 200, 500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void layInit() {
		
		
		
		
		
		model = new DefaultTableModel(datas, title);
		table = new JTable(model); 
		JScrollPane scroll = new JScrollPane(table);
		box.addItem("전체");
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			pstmt = conn.prepareStatement("select buser_name from buser group by buser_name");
			rs = pstmt.executeQuery();
			
			
			while(rs.next()){
				box.addItem(rs.getString("buser_name"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		panel.add(new JLabel("부서명 : "));
		panel.add(box);
		panel.add(new JLabel("연봉별 정렬(내림) : "));
		panel.add(box2);
		panel2.add(new JLabel("직원수"));
		panel2.add(a);
		panel2.add(new JLabel("연봉 => "));
		panel2.add(new JLabel("최대 : "));
		panel2.add(b);
		panel2.add(new JLabel("평균 : "));
		panel2.add(c);
		panel2.add(new JLabel("최소 : "));
		panel2.add(d);
		add("North", panel);
		add("Center",scroll);
		add("South", panel2);
		box.addItemListener(this);
		box2.addItemListener(this);
		
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		try{		
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn2 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			String sql = "select"
					+ " sawon_no,sawon_name,sawon_pay,to_char(sawon_ibsail,'YYYY') ibsail from sawon";
			pstmt2 = conn2.prepareStatement(sql);
			rs2 = pstmt2.executeQuery();
			
			conn3 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			String sql2 = "select max(sawon_pay) mx, min(sawon_pay) mi,  trunc(avg(sawon_pay)) av from sawon";
			pstmt3 = conn3.prepareStatement(sql2);
			rs3 = pstmt3.executeQuery();
			
			conn4 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			pstmt4 = conn4.prepareStatement("select sawon_name,count(gogek_no) co from sawon left join gogek on gogek_damsano=sawon_no group by sawon_name");
			rs4 = pstmt4.executeQuery();
			
			
			
			if(box.getSelectedItem().equals("전체")){
				
				model.setNumRows(0);
				while(rs2.next()){
					
					String aa = rs2.getString("sawon_no");
					String bb = rs2.getString("sawon_name");
					String cc = rs2.getString("sawon_pay");
					String dd = rs2.getString("ibsail");
					rs4.next();
					String ee = rs4.getString("co");
					String[] imsi = {aa,bb,cc,dd,ee};
					
					model.addRow(imsi);
					count++;
				}
				
					a.setText("" + count);
					count = 0;
			}else if(e.getItem().equals("영업부")){
				model.setNumRows(0);
				pstmt2 = conn2.prepareStatement("select"
						+ " sawon_no,sawon_name,sawon_pay,"
						+ "to_char(sawon_ibsail,'YYYY') ibsail from sawon where buser_num=20");
				rs2 = pstmt2.executeQuery();
				
				pstmt4 = conn4.prepareStatement("select sawon_name,count(gogek_no) co from sawon left join gogek on gogek_damsano=sawon_no where buser_num=20 group by sawon_name");
				rs4 = pstmt4.executeQuery();
				while(rs2.next()){
					String aa = rs2.getString("sawon_no");
					String bb = rs2.getString("sawon_name");
					String cc = rs2.getString("sawon_pay");
					String dd = rs2.getString("ibsail");
					rs4.next();
					String ee = rs4.getString("co");
					String[] imsi = {aa,bb,cc,dd,ee};
					model.addRow(imsi);
					count++;
					
				}
				a.setText("" + count);
				count = 0;
				sql2 += " where buser_num=20";
				
				pstmt3 = conn3.prepareStatement(sql2);
				rs3 = pstmt3.executeQuery();
				rs3.next();
				b.setText(rs3.getString(1));
				c.setText(rs3.getString(2));
				d.setText(rs3.getString(3));
			}else if(e.getItem().equals("전산부")){
				model.setNumRows(0);
				pstmt2 = conn2.prepareStatement("select"
						+ " sawon_no,sawon_name,sawon_pay,"
						+ "to_char(sawon_ibsail,'YYYY') ibsail from sawon where buser_num=30");
				rs2 = pstmt2.executeQuery();
				
				pstmt4 = conn4.prepareStatement("select sawon_name,count(gogek_no) co from sawon left join gogek on gogek_damsano=sawon_no where buser_num=30 group by sawon_name");
				rs4 = pstmt4.executeQuery();
				while(rs2.next()){
					String aa = rs2.getString("sawon_no");
					String bb = rs2.getString("sawon_name");
					String cc = rs2.getString("sawon_pay");
					String dd = rs2.getString("ibsail");
					rs4.next();
					String ee = rs4.getString("co");
					String[] imsi = {aa,bb,cc,dd,ee};
					model.addRow(imsi);
					count++;
				}
				a.setText("" + count);
				count = 0;
				sql2 += " where buser_num=30";
				pstmt3 = conn3.prepareStatement(sql2);
				rs3 = pstmt3.executeQuery();
				rs3.next();
				b.setText(rs3.getString("mx"));
				c.setText(rs3.getString("av"));
				d.setText(rs3.getString("mi"));
			}else if(e.getItem().equals("관리부")){
				model.setNumRows(0);
				pstmt2 = conn2.prepareStatement("select"
						+ " sawon_no,sawon_name,sawon_pay,"
						+ "to_char(sawon_ibsail,'YYYY') ibsail from sawon where buser_num=40");
				rs2 = pstmt2.executeQuery();
				
				pstmt4 = conn4.prepareStatement("select sawon_name,count(gogek_no) co from sawon left join gogek on gogek_damsano=sawon_no where buser_num=40 group by sawon_name");
				rs4 = pstmt4.executeQuery();
				while(rs2.next()){
					String aa = rs2.getString("sawon_no");
					String bb = rs2.getString("sawon_name");
					String cc = rs2.getString("sawon_pay");
					String dd = rs2.getString("ibsail");
					rs4.next();
					String ee = rs4.getString("co");
					String[] imsi = {aa,bb,cc,dd,ee};
					model.addRow(imsi);
					count++;
				}
				a.setText("" + count);
				count = 0;
				sql2 += " where buser_num=40";
				pstmt3 = conn3.prepareStatement(sql2);
				rs3 = pstmt3.executeQuery();
				rs3.next();
				b.setText(rs3.getString("mx"));
				c.setText(rs3.getString("av"));
				d.setText(rs3.getString("mi"));
			}else if(e.getItem().equals("총무부")){
				model.setNumRows(0);
				pstmt2 = conn2.prepareStatement("select"
						+ " sawon_no,sawon_name,sawon_pay,"
						+ "to_char(sawon_ibsail,'YYYY') ibsail from sawon where buser_num=10");
				rs2 = pstmt2.executeQuery();
				
				pstmt4 = conn4.prepareStatement("select sawon_name,count(gogek_no) co from sawon left join gogek on gogek_damsano=sawon_no where buser_num=10 group by sawon_name");
				rs4 = pstmt4.executeQuery();
				
				while(rs2.next()){
					String aa = rs2.getString("sawon_no");
					String bb = rs2.getString("sawon_name");
					String cc = rs2.getString("sawon_pay");
					String dd = rs2.getString("ibsail");
					rs4.next();
					String ee = rs4.getString("co");
					String[] imsi = {aa,bb,cc,dd,ee};
					model.addRow(imsi);
					count++;
					
				}
				a.setText("" + count);
				count = 0;
				sql2 += " where buser_num=10";
				pstmt3 = conn3.prepareStatement(sql2);
				rs3 = pstmt3.executeQuery();
				rs3.next();
				b.setText(rs3.getString("mx"));
				c.setText(rs3.getString("av"));
				d.setText(rs3.getString("mi"));
			}
			
			if(box2.isSelected()){
				if(box.getSelectedItem().equals("전체")){
					pstmt2 = conn2.prepareStatement("select"
							+ " sawon_no,sawon_name,sawon_pay,"
							+ "to_char(sawon_ibsail,'YYYY') ibsail from sawon left join buser on buser_no=buser_num"
							+ " order by sawon_pay desc");
					rs2 = pstmt2.executeQuery();
					
					pstmt4 = conn4.prepareStatement("select sawon_name,count(gogek_no) co,sawon_pay from sawon left join gogek on gogek_damsano=sawon_no group by sawon_name,sawon_pay order by sawon_pay");
					rs4 = pstmt4.executeQuery();
					
					model.setNumRows(0);
					while(rs2.next()){
						String aa = rs2.getString("sawon_no");
						String bb = rs2.getString("sawon_name");
						String cc = rs2.getString("sawon_pay");
						String dd = rs2.getString("ibsail");
						rs4.next();
						String ee = rs4.getString("co");
						String[] imsi = {aa,bb,cc,dd,ee};
						model.addRow(imsi);
						
					}
				}else{
				pstmt2 = conn2.prepareStatement("select"
						+ " sawon_no,sawon_name,sawon_pay,"
						+ "to_char(sawon_ibsail,'YYYY') ibsail from sawon left join buser on buser_no=buser_num where buser_name='" + box.getSelectedItem()+ "'"
						+ " order by sawon_pay desc");
				rs2 = pstmt2.executeQuery();
				
				pstmt4 = conn4.prepareStatement("select sawon_name,count(gogek_no) co from sawon left join gogek on gogek_damsano=sawon_no left join buser on buser_no=buser_num"
						+ " where buser_name='" + box.getSelectedItem()+ "'"
						+ " group by sawon_name");
				rs4 = pstmt4.executeQuery();
				
				model.setNumRows(0);
				while(rs2.next()){
					String aa = rs2.getString("sawon_no");
					String bb = rs2.getString("sawon_name");
					String cc = rs2.getString("sawon_pay");
					String dd = rs2.getString("ibsail");
					rs4.next();
					String ee = rs4.getString("co");
					String[] imsi = {aa,bb,cc,dd,ee};
					model.addRow(imsi);
					
				}
			}
			}	
			
			
		}catch(Exception l){
			System.out.println(l);
		}
		
	}
	public static void main(String[] args) {
		new Test();
		
	}

	

}

