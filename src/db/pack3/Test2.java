package db.pack3;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.omg.CORBA.INTERNAL;

public class Test2 extends JFrame implements ActionListener {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	JTextField txtS, txtN, txtB, txtC;
	JTextArea textArea = new JTextArea();
	JButton btn = new JButton("추가");
	JButton btn2 = new JButton("전체보기");
	JButton btn3 = new JButton("삭제");
	public Test2() {

		layInit();
		setTitle("상품추가");
		setBounds(200, 200, 600, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			String sql2 = "select * from sangdata";
			pstmt = conn.prepareStatement(sql2);// 선처리방식(sql 문장을 미리준다)웹에서주로사용
			rs = pstmt.executeQuery();
			int count2 = 0;
			while (rs.next()) {
				String str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
						+ "\n";

				textArea.append(str);
				count2++;
			}
			textArea.append("건수 : " + count2);
		} catch (Exception d) {
			System.out.println(d + "asd");
		}
	}

	private void layInit() {
		txtS = new JTextField("", 5);
		txtN = new JTextField("", 5);
		txtB = new JTextField("", 5);
		txtC = new JTextField("", 5);

		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new GridLayout(2, 1));
		panel.add(new JLabel("코드"));
		panel.add(txtS);
		panel.add(new JLabel("상품명"));
		panel.add(txtN);
		panel2.add(new JLabel("수량"));
		panel2.add(txtB);
		panel2.add(new JLabel("단가"));
		panel2.add(txtC);
		panel2.add(btn);
		panel2.add(btn2);
		panel2.add(btn3);
		panel3.add(panel);
		panel3.add(panel2);
		textArea.setEditable(false);
		add("North", panel3);
		add("Center", textArea);
		btn.addActionListener(this);
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Test2();
				
			}
		});
		
		btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
				String dsql = "delete from sangdata where code=?";
				pstmt = conn.prepareStatement(dsql);
				pstmt.setInt(1, Integer.parseInt(txtS.getText()));
				int re = pstmt.executeUpdate();
				
				new Test2();
				
				}catch(Exception p){
					
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			// 자료추가
			String isql = "insert into sangdata values(?,?,?,?)";
			pstmt = conn.prepareStatement(isql);

			pstmt.setInt(1, Integer.parseInt(txtS.getText()));
			pstmt.setString(2, txtN.getText());
			pstmt.setInt(3, Integer.parseInt(txtB.getText()));
			pstmt.setInt(4, Integer.parseInt(txtC.getText()));
			int re = pstmt.executeUpdate();
			if (re == 1) {
				JOptionPane.showMessageDialog(this, "값을 추가했습니다");
			} else {
				System.out.println("실패");
				return;
			}
			textArea.setText("코드\t상품명\t수량\t단가\n");

			// 출력

			// sql = "select * from sangdata where code = " + ca; 시큐어코딩 위배
			String sql = "select * from sangdata where code = ?";
			pstmt = conn.prepareStatement(sql);// 선처리방식(sql 문장을 미리준다)
			pstmt.setString(1, txtS.getText());
			rs = pstmt.executeQuery();
			int count = 0;
			while (rs.next()) {
				String str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
						+ "\n";

				textArea.append(str);
				count++;
			}
				textArea.append("건수 : " + count);
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, "같은코드가 존재하거나 잘못된 값입니다");
		} finally {
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
		new Test2();

	}

}
