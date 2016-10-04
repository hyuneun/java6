package db.pack;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DbTest3Gui extends JFrame implements ActionListener {
		JButton btnA = new JButton("전체");
		JButton btnM = new JButton("남자");
		JButton btnF = new JButton("여자");
		JTextArea txtResult = new JTextArea();
		private Connection conn;
		private Statement stmt;
		private ResultSet rs;
	
	public DbTest3Gui() {
		setTitle("고객출력");
		
		laytint();
		accDb();
		
		setBounds(200,200,300,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		private void laytint(){
		JPanel panel = new JPanel();
		panel.add(btnA);
		panel.add(btnM);
		panel.add(btnF);
		
		txtResult.setEditable(false);//작성x오직 출력
		JScrollPane pane = new JScrollPane(txtResult);
		
		add("Center",pane);
		add("North",panel);
		
		btnA.addActionListener(this);
		btnM.addActionListener(this);
		btnF.addActionListener(this);
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
				String sql = "select gogek_no,gogek_name,gogek_jumin from gogek";
				
				if(e.getSource() == btnA){
					
				}else if(e.getSource() == btnM){//남자버튼
					sql += " where gogek_jumin like '%-1%'";
				}else if(e.getSource() == btnF){//여자버튼
					sql += " where gogek_jumin like '%-2%'";
				}
				
				rs = stmt.executeQuery(sql);
				int count = 0;
				txtResult.setText("고객번호\t고객명\t주민번호\n");
				
				while(rs.next()){
					String str = rs.getString("gogek_no") + "\t" +
							rs.getString("gogek_name") + "\t" +
							rs.getString("gogek_jumin") + "\n";
					txtResult.append(str);
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
		new DbTest3Gui();

	}

}
