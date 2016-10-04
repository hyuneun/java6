package db.pack3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Test extends JFrame implements ActionListener{
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBConnectionMgr mgr;
	
	JButton btn = new JButton("확인");
	JTextField txtS,txtN,txtB;
	JTextArea textArea = new JTextArea();
	String sql="";
	
	
	public Test() {
		try {
			mgr = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
		accDb();
		layInit();
		
		setTitle("pooling test");
		setBounds(200,200,500,500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void accDb() {
		//System.out.println("success");
		try {
			
			
			
			
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	private void layInit() {
		txtS = new JTextField("",5);
		txtN = new JTextField("",5);
		txtB = new JTextField("",5);
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("고객명 : "));
		panel.add(txtS);
		panel.add(new JLabel("주민번호"));
		panel.add(txtN);
		panel.add(new JLabel("-"));
		panel.add(txtB);
		panel.add(btn);
		textArea.setEditable(false);
		add("North",panel);
		add("Center",textArea);
		btn.addActionListener(this);
	}
	
	public static void main(String[] args) {
		new Test();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			if(txtS.getText().equals("") || txtN.getText().equals("") || txtB.getText().equals("")){
				return;
			}else{
				conn = mgr.getConnection();
				sql = "select distinct gogek_name , gogek_jumin , sawon_no , sawon_name , buser_name , buser_tel , sawon_jik"
						+ " from sawon inner join buser on buser_num=buser_no inner join gogek on gogek_damsano=sawon_no";
				stmt = conn.createStatement();
				
				sql += " where gogek_name='" + txtS.getText() + "'";
				sql += " and gogek_jumin='" + txtN.getText() + "-" + txtB.getText() + "'";
			}
			
			rs = stmt.executeQuery(sql);
			textArea.setText("담당직원\n사번\t이름\t부서명\t전화\t직급\n");
			
			while(rs.next()){
				String str = rs.getString("sawon_no") + "\t" +
						 rs.getString("sawon_name") + "\t" +
						 rs.getString("buser_name") + "\t" +
						 rs.getString("buser_tel") + "\t" +
						 rs.getString("sawon_jik") + "\n";				
				textArea.append(str);
				
				
			}
		} catch (Exception e1) {
			System.out.println(e1);
		}finally{
			mgr.freeConnection(conn, stmt, rs);
		}
		
	}

}
