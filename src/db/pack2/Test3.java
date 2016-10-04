package db.pack2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Test3 extends JFrame implements ActionListener {
	JButton btn = new JButton("濡쒓렇�씤");
	JTextField txtS, txtN, txtB, txtT, txtJ;
	JTextArea textArea = new JTextArea();
	String sql = "";
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	int count = 0;

	public Test3() {

		setTitle("濡쒓렇�씤�뿰�뒿");

		accDb();
		layInit();

		setBounds(200, 200, 500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void layInit() {
		txtS = new JTextField("", 5);
		txtN = new JTextField("", 5);
		txtB = new JTextField("", 5);
		txtT = new JTextField("", 10);
		txtJ = new JTextField("", 5);
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new GridLayout(2, 1));
		panel.add(new JLabel("�궗踰�:"));
		panel.add(txtS);
		panel.add(new JLabel("�씠由�:"));
		panel.add(txtN);
		panel.add(btn);
		panel2.add(new JLabel("遺��꽌硫�:"));
		panel2.add(txtB);
		panel2.add(new JLabel("遺��꽌�쟾�솕:"));
		panel2.add(txtT);
		panel2.add(new JLabel("吏곴툒:"));
		panel2.add(txtJ);
		panel3.add(panel);
		panel3.add(panel2);
		add("North", panel3);
		add("Center", textArea);
		btn.addActionListener(this);

		textArea.setEditable(false);
		txtB.setEditable(false);
		txtT.setEditable(false);
		txtJ.setEditable(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (btn.getText().equals("濡쒓렇�씤")) {

			try {

				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql = "select distinct sawon_no,sawon_name,buser_name,buser_tel,sawon_jik,"
						+ "gogek_name,gogek_tel,gogek_jumin" + " from sawon left join buser on buser_no=buser_num"
						+ " left join gogek on gogek_damsano=sawon_no";

				if (txtS.getText().equals("") || txtN.getText().equals("")) {
					return;
				} else {
					sql += " where sawon_no='" + txtS.getText() + "'";
					sql += " and sawon_name='" + txtN.getText() + "'";
				}

				rs = stmt.executeQuery(sql);
				rs.next();
				txtN.setText(rs.getString("sawon_name"));
				txtB.setText(rs.getString("buser_name"));
				txtT.setText(rs.getString("buser_tel"));
				txtJ.setText(rs.getString("sawon_jik"));
				rs.previous();
				textArea.append("怨좉컼紐�\t怨좉컼�쟾�솕\t二쇰�쇰쾲�샇\n");
				while (rs.next()) {
					String str = rs.getString("gogek_name") + "\t" + rs.getString("gogek_tel") + "\t"
							+ rs.getString("gogek_jumin") + "\n";
					textArea.append(str);

				}

				btn.setText("濡쒓렇�븘�썐");
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(this, "濡쒓렇�씤�떎�뙣");

			}

		} else {
			btn.setText("濡쒓렇�씤");
			txtS.setText("");
			txtN.setText("");
			txtB.setText("");
			txtT.setText("");
			txtJ.setText("");
			textArea.setText("");

		}

	}

	public static void main(String[] args) {
		new Test3();

	}

}
