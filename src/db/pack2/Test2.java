package db.pack2;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class Test2 extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	JButton button = new JButton("\uCC98\uC74C");
	JButton button_1 = new JButton("\uC774\uC804");
	JButton button_2 = new JButton("\uB2E4\uC74C");
	JButton button_3 = new JButton("\uB9C8\uC9C0\uB9C9");
	JTextArea textArea = new JTextArea();
	private Connection conn;
	private Connection conn2;
	private Statement stmt;
	private Statement stmt2;
	private ResultSet rs;
	private ResultSet rs2;
	int count = 0;

	/**
	 * Launch the application.
	 */
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

	private void display() {
		try {
			textField.setText(rs.getString("sawon_no"));
			textField_1.setText(rs.getString("sawon_name"));
			textField_2.setText(rs.getString("sawon_jik"));
			textField_3.setText(rs.getString("buser_name"));
			textField_4.setText(rs.getString("buser_tel"));

			while (rs2.next()) {

				String str = rs2.getString("gogek_no") + "\t" + rs2.getString("gogek_name") + "\t"
						+ rs2.getString("������") + "\t" + rs2.getString("����") + "\t" + rs2.getString("gogek_tel") + "\n";

				textArea.append(str);
				count++;
				if (count == 0)
					textArea.append("����� �����");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "���÷��̿���");
		}

	}

	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);// Ŀ����
																										// �ű���ְ���,�������Ұ�
			rs = stmt.executeQuery("select sawon_no,sawon_name,sawon_jik,buser_name,buser_tel"
					+ " from sawon left join buser on buser_no=buser_num order by sawon_no");
			rs.next();

			conn2 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			stmt2 = conn2.createStatement();
			String sql = "select gogek_no,gogek_name,"
					+ "case when gogek_jumin like '%-1%' then '��' when gogek_jumin like '%-2%' then '��' end ������,"
					+ "(TO_CHAR(sysdate,'YYYY') - SUBSTR(gogek_jumin,1,2)) - 1900 ����,gogek_tel,gogek_damsano from gogek";
			sql += " where gogek_damsano='" + rs.getString("sawon_no") + "'";

			textArea.setText("����ȣ\t����\t����\t����\t��ȭ\n");
			rs2 = stmt2.executeQuery(sql);

			display();

		} catch (Exception e) {
			System.out.println("����" + e);
		}
	}

	public Test2() {
		setTitle("\uBB38\uC81C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 497, 812);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel Label1 = new JLabel("\uC0AC\uBC88 : ");
		Label1.setBounds(12, 10, 36, 29);
		contentPane.add(Label1);

		textField = new JTextField();
		textField.setBounds(60, 14, 56, 21);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel label = new JLabel("\uC774\uB984 : ");
		label.setBounds(128, 10, 36, 29);
		contentPane.add(label);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(169, 14, 56, 21);
		contentPane.add(textField_1);

		JLabel label_1 = new JLabel("\uC9C1\uAE09 : ");
		label_1.setBounds(232, 10, 36, 29);
		contentPane.add(label_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(270, 14, 56, 21);
		contentPane.add(textField_2);

		JLabel label_2 = new JLabel("\uBD80\uC11C\uBA85 : ");
		label_2.setBounds(12, 49, 48, 29);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("\uBD80\uC11C\uC804\uD654 : ");
		label_3.setBounds(128, 49, 76, 29);
		contentPane.add(label_3);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(60, 53, 56, 21);
		contentPane.add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(203, 53, 76, 21);
		contentPane.add(textField_4);

		button.setBounds(12, 114, 97, 23);
		contentPane.add(button);

		button_1.setBounds(128, 114, 97, 23);
		contentPane.add(button_1);

		button_2.setBounds(241, 114, 97, 23);
		contentPane.add(button_2);

		button_3.setBounds(347, 114, 97, 23);
		contentPane.add(button_3);

		textArea.setBounds(12, 147, 455, 617);
		contentPane.add(textArea);

		button.addActionListener(this);
		button_1.addActionListener(this);
		button_2.addActionListener(this);
		button_3.addActionListener(this);

		accDb();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			conn2 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			stmt2 = conn2.createStatement();
			String sql = "select gogek_no,gogek_name,"
					+ "case when gogek_jumin like '%-1%' then '��' when gogek_jumin like '%-2%' then '��' end ������,"
					+ "(TO_CHAR(sysdate,'YYYY') - SUBSTR(gogek_jumin,1,2)) - 1900 ����,gogek_tel,gogek_damsano from gogek";

			textArea.setText("����ȣ\t����\t����\t����\t��ȭ\n");
			rs2 = stmt2.executeQuery(sql);

			if (e.getSource() == button) {
				rs.first();
				sql += " where gogek_damsano='" + rs.getString("sawon_no") + "'";
				rs2 = stmt2.executeQuery(sql);
				while (rs2.next()) {

					String str = rs2.getString("gogek_no") + "\t" + rs2.getString("gogek_name") + "\t"
							+ rs2.getString("������") + "\t" + rs2.getString("����") + "\t" + rs2.getString("gogek_tel")
							+ "\n";

					textArea.append(str);
					count++;
					if (count == 0)
						textArea.append("����� �����");
				}
			} else if (e.getSource() == button_1) {
				rs.previous();
				sql += " where gogek_damsano='" + rs.getString("sawon_no") + "'";
				rs2 = stmt2.executeQuery(sql);
				while (rs2.next()) {

					String str = rs2.getString("gogek_no") + "\t" + rs2.getString("gogek_name") + "\t"
							+ rs2.getString("������") + "\t" + rs2.getString("����") + "\t" + rs2.getString("gogek_tel")
							+ "\n";

					textArea.append(str);
					count++;
				}
				if (count == 0)
					textArea.append("����� �����");

			} else if (e.getSource() == button_2) {
				rs.next();
				sql += " where gogek_damsano='" + rs.getString("sawon_no") + "'";
				rs2 = stmt2.executeQuery(sql);
				while (rs2.next()) {

					String str = rs2.getString("gogek_no") + "\t" + rs2.getString("gogek_name") + "\t"
							+ rs2.getString("������") + "\t" + rs2.getString("����") + "\t" + rs2.getString("gogek_tel")
							+ "\n";

					textArea.append(str);
					count++;
				}
				if (count == 0)
					textArea.append("����� �����");

			} else if (e.getSource() == button_3) {
				rs.last();
				sql += " where gogek_damsano='" + rs.getString("sawon_no") + "'";
				rs2 = stmt2.executeQuery(sql);

				while (rs2.next()) {

					String str = rs2.getString("gogek_no") + "\t" + rs2.getString("gogek_name") + "\t"
							+ rs2.getString("������") + "\t" + rs2.getString("����") + "\t" + rs2.getString("gogek_tel")
							+ "\n";

					textArea.append(str);
					count++;
				}
				if (count == 0)
					textArea.append("����� �����");

			}

			display();

			textArea.append("\n�ο��� :   " + count);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(this, "����� �����");
		}
	}
}
