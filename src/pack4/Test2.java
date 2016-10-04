package pack4;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test2 extends JDialog implements ActionListener{
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	String sql = "";
	int count = 0;
	JPanel panel = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	JPanel panel4 = new JPanel(new GridLayout(3, 1));
	
	JLabel lb = new JLabel("사번");
	JTextField t1 = new JTextField("",5);
	JLabel lb2 = new JLabel("이름");
	JTextField t2 = new JTextField("",5);
	
	JButton jb = new JButton("확인");
	
	public Test2(JFrame frame) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		setTitle("로그인");
		setModal(true);
		
		
		jb.addActionListener(this);
		
		panel.add(lb);
		panel.add(t1);
		panel2.add(lb2);
		panel2.add(t2);
		panel3.add(jb);
		panel4.add(panel);
		panel4.add(panel2);
		panel4.add(panel3);
		
		
		add("South", panel4);

		setBounds(200,200,200,200);
		setVisible(true);
		try {
			

			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (t1.getText().equals("") || t2.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "입력좀");
			return;
		} else {
			try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql = "select distinct sawon_no,sawon_name,buser_name,buser_tel,sawon_jik,"
					+ "gogek_name,gogek_tel,gogek_jumin" + " from sawon left join buser on buser_no=buser_num"
					+ " left join gogek on gogek_damsano=sawon_no";
			
			sql += " where sawon_no='" + t1.getText() + "'";
			sql += " and sawon_name='" + t2.getText() + "'";
			
			}catch(Exception ee){
				System.out.println(ee);
			}
			
		}
		
		try {
		rs = stmt.executeQuery(sql);
		rs.next();
			if(rs.getString("sawon_name").equals("")){
				
				count++;
			}else{
				JOptionPane.showMessageDialog(this, "로그인성공");
				dispose();
			}
		} catch (Exception e2) {
			count++;
			
		}
//		if(count == 1){
//			JOptionPane.showMessageDialog(this, "로그인실패 : " + count + "\n한번더틀리면 종료합니다");
//		}
		if(count == 2){
			//Test tt = new Test();
			JOptionPane.showMessageDialog(this, "로그인실패\n프로그램을 종료합니다");
			System.exit(0);
			
			//tt.exit();
		}
		
	}
}
