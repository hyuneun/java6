package db.pack;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Test2 extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	JTextArea txtbox = new JTextArea();
	ButtonGroup  rg = new ButtonGroup();
	JRadioButton b1 = new JRadioButton("\uCD08\uAE30",true);
	JRadioButton b2 = new JRadioButton("\uC624\uB984");
	JRadioButton b3 = new JRadioButton("\uB0B4\uB9BC");
	JButton button1 = new JButton("\uD655\uC778");
	int countg = 0;
	private final JButton button2 = new JButton("\uCD08\uAE30\uD654");
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test2 frame = new Test2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	private void accDb(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("에러" + e);
		}
	
	}
	
	public Test2() {
		
		setTitle("\uC9C1\uAE09\uBCC4\uAC80\uC0C9");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\uC9C1\uAE09\uC785\uB825 : ");
		label.setBounds(12, 10, 83, 35);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(74, 17, 116, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		
		
		button1.setBounds(202, 10, 97, 23);
		contentPane.add(button1);
		
		
		b1.setBounds(311, 16, 55, 23);
		contentPane.add(b1);
		
		
		b2.setBounds(370, 16, 55, 23);
		contentPane.add(b2);
		
		
		b3.setBounds(425, 16, 61, 23);
		contentPane.add(b3);
		
		
		
		txtbox.setEditable(false);
		txtbox.setBounds(12, 56, 473, 345);
		contentPane.add(txtbox);
		
		rg.add(b1);
		rg.add(b2);
		rg.add(b3);
		button2.setBounds(202, 33, 97, 23);
		
		contentPane.add(button2);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		button1.addActionListener(this);
		button2.addActionListener(new ActionListener() {	//초기화버튼
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txtbox.setText("");
				
			}
		});
		accDb();
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			stmt = conn.createStatement();
			String sql = "select sawon_no,sawon_name,sawon_jik,sawon_gen from sawon";
		
			
			int count = 0;
			txtbox.setText("사번\t직원명\t직급\t성별\n");
			
//			if(textField.getText().equals("대리")){
//				sql += " where sawon_jik='대리'";
//			}else if(textField.getText().equals("사원")){
//				sql += " where sawon_jik='사원'";
//			}else if(textField.getText().equals("부장")){
//				sql += " where sawon_jik='부장'";
//			}else if(textField.getText().equals("과장")){
//				sql += " where sawon_jik='과장'";
//			}else if(textField.getText().equals("전무")){
//				sql += " where sawon_jik='전무'";
//			}else if(textField.getText().equals("")){
//				
//			}else{
//				return;
//			}
			
			if(textField.getText().equals("")){
				
			}else{
				sql += " where sawon_jik='" + textField.getText() + "'";
			}
			
			
			if(e.getSource() == b1){//전체
				if(countg == 0){
					return;
				}else{
					
				}
			}else if(e.getSource() == b2){//오름
				if(countg == 0){
					return;
				}else{
					sql += " order by sawon_name";
				}
			}else if(e.getSource() == b3){//내림
				if(countg == 0){
					return;
				}else{
					sql += " order by sawon_name desc";
				}
			}
			
			
			rs = stmt.executeQuery(sql);
			int countM = 0;
			int countW = 0;
			
			while(rs.next()){
				
			if(rs.getString("sawon_gen").equals("남")){
				countM++;
			}else if(rs.getString("sawon_gen").equals("여")){
				countW++;
			}
			String str = rs.getString("sawon_no") + "\t" +
						 rs.getString("sawon_name") + "\t" +
						 rs.getString("sawon_jik") + "\t" +
						 rs.getString("sawon_gen") + "\n";
						 
				
			txtbox.append(str);
				count++;
			}
			
			txtbox.append("\n인원수 :   " + count + "   남:" + countM + "   여:" + countW);
			
		
		
		}catch (Exception e2) {
			System.out.println(e2);
		}
//		finally {
//			try {
//				
//				if(rs != null) rs.close();
//				if(stmt != null) stmt.close();
//				if(conn != null) conn.close();
//			} catch (Exception e2) {
//				// TODO: handle exception
//			}
//		}
		
	}
}
