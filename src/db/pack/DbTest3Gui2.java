package db.pack;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DbTest3Gui2 extends JFrame implements ActionListener {
		JRadioButton  rb1 = new JRadioButton("전체",true);
		JRadioButton  rb2 = new JRadioButton("총무부");
		JRadioButton  rb3 = new JRadioButton("영업부");
		JRadioButton  rb4 = new JRadioButton("전산부");
		JRadioButton  rb5 = new JRadioButton("관리부");
		JRadioButton  rb6 = new JRadioButton("기타");
		ButtonGroup  rg = new ButtonGroup();
		JTextArea txtResult = new JTextArea();
		JTextArea txtResult1 = new JTextArea("    ");
		JTextArea txtResult2 = new JTextArea("    ");
		private Connection conn;
		private Statement stmt;
		private ResultSet rs,rs2;
		JTextField txt1 = new JTextField();
		String mo;
	public DbTest3Gui2() {
		setTitle("고객출력");
		
		laytint();
		accDb();
		
		
		setBounds(200,200,600,600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		private void laytint(){
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		rg.add(rb1);
		rg.add(rb2);
		rg.add(rb3);
		rg.add(rb4);
		rg.add(rb5);
		rg.add(rb6);
		panel.add(rb1);
		panel.add(rb2);
		panel.add(rb3);
		panel.add(rb4);
		panel.add(rb5);
		panel.add(rb6);
		JLabel lbl1 = new JLabel("부서명",JLabel.LEFT);
		JLabel lbl2 = new JLabel("부서전화",JLabel.LEFT);
		panel2.add(lbl1);
		panel2.add(txtResult1);
		panel2.add(lbl2);
		panel2.add(txtResult2);
		panel3.setLayout(new GridLayout(2,1));
		
		panel3.add(panel);
		panel3.add(panel2);
		txtResult.setEditable(false);//작성x오직 출력
		JScrollPane pane = new JScrollPane(txtResult);
		add("North",panel3);
		//add("South",panel2);
		add("Center",pane);
		
		rb1.addActionListener(this);
		rb2.addActionListener(this);
		rb3.addActionListener(this);
		rb4.addActionListener(this);
		rb5.addActionListener(this);
		rb6.addActionListener(this);
		
		}
		private void accDb(){
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (Exception e) {
				System.out.println("에러" + e);
			}
			
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
				stmt = conn.createStatement();
				String sql = "select sawon_no,sawon_name,sawon_jik,sawon_pay,buser_name,buser_tel from sawon left join buser on buser_no=buser_num";
				
				if(e.getSource() == rb1){//전체
					
				}else if(e.getSource() == rb2){//총
					sql += " where buser_num='10'";
					//sql2 += " where buser_name='총무부'";
				}else if(e.getSource() == rb3){//영
					sql += " where buser_num='20'";
					//sql2 += " where buser_name='영업부'";
				}else if(e.getSource() == rb4){//전
					sql += " where buser_num='30'";
					//sql2 += " where buser_name='전산부'";
				}else if(e.getSource() == rb5){//관
					sql += " where buser_num='40'";
					//sql2 += " where buser_name='관리부'";
				}else if(e.getSource() == rb6){//기타
					sql += " where buser_num is null";
					//sql2 += " where not(buser_name='총무부') and not(buser_name='영업부') and not(buser_name='전산부') and not(buser_name='관리부')";
				}
				
				rs = stmt.executeQuery(sql);
				int count = 0;
				txtResult.setText("사번\t직원명\t직급\t연봉\n");
				
				
				while(rs.next()){
					
					if(rs.getInt("sawon_pay") >= 1000 && rs.getInt("sawon_pay") < 2000){
						mo = "1000대";
					}else if(rs.getInt("sawon_pay") >= 2000 && rs.getInt("sawon_pay") < 3000){
						mo = "2000대";
					}else if(rs.getInt("sawon_pay") >= 3000 && rs.getInt("sawon_pay") < 4000){
						mo = "3000대";
					}else if(rs.getInt("sawon_pay") >= 4000 && rs.getInt("sawon_pay") < 5000){
						mo = "4000대";
					}else if(rs.getInt("sawon_pay") >= 5000 && rs.getInt("sawon_pay") < 6000){
						mo = "5000대";
					}else if(rs.getInt("sawon_pay") >= 6000 && rs.getInt("sawon_pay") < 7000){
						mo = "6000대";
					}else if(rs.getInt("sawon_pay") >= 7000 && rs.getInt("sawon_pay") < 8000){
						mo = "7000대";
					}else if(rs.getInt("sawon_pay") >= 8000 && rs.getInt("sawon_pay") < 9000){
						mo = "8000대";
					}else if(rs.getInt("sawon_pay") >= 9000 && rs.getInt("sawon_pay") < 10000){
						mo = "9000대";
					}
					
					String str = rs.getString("sawon_no") + "\t" +
								 rs.getString("sawon_name") + "\t" +
								 rs.getString("sawon_jik") + "\t" +
								 mo + "\n";
					String str2= rs.getString("buser_name");
					String str3= rs.getString("buser_tel");
					if(e.getSource() == rb1){//전체
						str2 = "";
					}else if(e.getSource() == rb6){//기타
						str2 = "";
						
					}
					txtResult.append(str);
					txtResult1.setText(str2);
					txtResult2.setText(str3);
					
					
					count++;
				}
				txtResult.append("인원수 : " + count);
			} catch (Exception e2) {
				System.out.println("actionPerformed err" + e);
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
		new DbTest3Gui2();

	}

}
