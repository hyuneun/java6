package db.pack2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test extends JFrame implements ActionListener{
	JButton btnF,btnP,btnN,btnL;
	JTextField txtNo,txtName;
	
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	public Test() {
		setTitle("레코드이동");
		
		layInit();
		accDb();
		
		setBounds(200,200,300,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private void layInit() {
		txtNo = new JTextField("",5);
		txtName = new JTextField("",10);
		JPanel panel = new JPanel();
		panel.add(new JLabel("직원자료"));
		panel.add(txtNo);
		panel.add(txtName);
		add("North", panel);
		
		btnF = new JButton("|<<");
		btnP = new JButton("<");
		btnN = new JButton(">");
		btnL = new JButton(">>|");
		JPanel panel2 = new JPanel();
		panel2.add(btnF);panel2.add(btnP);panel2.add(btnN);panel2.add(btnL);
		add("Center", panel2);
		
		btnF.addActionListener(this);
		btnP.addActionListener(this);
		btnN.addActionListener(this);
		btnL.addActionListener(this);
	}
	
	private void accDb(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);//커서를 옮길수있게함,수정은불가
			rs = stmt.executeQuery("select sawon_no,sawon_name,sawon_jik from sawon order by sawon_no");
			
			rs.next();
			
			display();
			
		} catch (Exception e) {
			System.out.println("에러:" + e);
		}
	}
	private void display() {
		try {
			txtNo.setText(rs.getString("sawon_no"));
			txtName.setText(rs.getString("sawon_name"));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "자료가없음");
		}

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource() == btnF) rs.first();
			else if(e.getSource() == btnP) rs.previous();
			else if(e.getSource() == btnN) rs.next();
			else if(e.getSource() == btnL) rs.last();
			
			display();
		} catch (Exception e2) {

		}
		
	}
	
	
	public static void main(String[] args) {
		new Test();

	}

}
