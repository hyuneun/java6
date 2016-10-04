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
	JButton btn = new JButton("�߰�");
	JButton btn2 = new JButton("��ü����");
	JButton btn3 = new JButton("����");
	public Test2() {

		layInit();
		setTitle("��ǰ�߰�");
		setBounds(200, 200, 600, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			String sql2 = "select * from sangdata";
			pstmt = conn.prepareStatement(sql2);// ��ó�����(sql ������ �̸��ش�)�������ַλ��
			rs = pstmt.executeQuery();
			int count2 = 0;
			while (rs.next()) {
				String str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
						+ "\n";

				textArea.append(str);
				count2++;
			}
			textArea.append("�Ǽ� : " + count2);
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
		panel.add(new JLabel("�ڵ�"));
		panel.add(txtS);
		panel.add(new JLabel("��ǰ��"));
		panel.add(txtN);
		panel2.add(new JLabel("����"));
		panel2.add(txtB);
		panel2.add(new JLabel("�ܰ�"));
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

			// �ڷ��߰�
			String isql = "insert into sangdata values(?,?,?,?)";
			pstmt = conn.prepareStatement(isql);

			pstmt.setInt(1, Integer.parseInt(txtS.getText()));
			pstmt.setString(2, txtN.getText());
			pstmt.setInt(3, Integer.parseInt(txtB.getText()));
			pstmt.setInt(4, Integer.parseInt(txtC.getText()));
			int re = pstmt.executeUpdate();
			if (re == 1) {
				JOptionPane.showMessageDialog(this, "���� �߰��߽��ϴ�");
			} else {
				System.out.println("����");
				return;
			}
			textArea.setText("�ڵ�\t��ǰ��\t����\t�ܰ�\n");

			// ���

			// sql = "select * from sangdata where code = " + ca; ��ť���ڵ� ����
			String sql = "select * from sangdata where code = ?";
			pstmt = conn.prepareStatement(sql);// ��ó�����(sql ������ �̸��ش�)
			pstmt.setString(1, txtS.getText());
			rs = pstmt.executeQuery();
			int count = 0;
			while (rs.next()) {
				String str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
						+ "\n";

				textArea.append(str);
				count++;
			}
				textArea.append("�Ǽ� : " + count);
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, "�����ڵ尡 �����ϰų� �߸��� ���Դϴ�");
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
