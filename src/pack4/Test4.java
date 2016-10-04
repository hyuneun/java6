package pack4;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Test4 extends JFrame {
	Connection conn;
	PreparedStatement pstmt, pstmt2, pstmt3;
	ResultSet rs, rs2, rs3;

	String[][] datas = new String[0][4];
	String[] title = { "부서번호", "부서명", "부서전화", "부서위치" };
	DefaultTableModel model = new DefaultTableModel(datas, title);
	JTable table = new JTable(model);
	JTextArea area = new JTextArea();
	String sql2;

	public Test4() {

		JScrollPane scroll = new JScrollPane(table);
		JScrollPane scroll2 = new JScrollPane(area);
		JPanel panel = new JPanel(new GridLayout(2, 0));
		panel.add(scroll);
		panel.add(scroll2);
		add("Center", panel);
		setTitle("상품처리");
		setBounds(200, 200, 350, 700);
		setVisible(true);
		area.setEditable(false);
		disData();
		accDb();

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table = (JTable) e.getComponent(); // 마우스클릭 대상 객체 얻기
				model = (DefaultTableModel) table.getModel();

				try {
					area.setText("");
					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
					String sql3 = "select distinct sawon_name from sawon inner join buser on buser_num=buser_no "
							+ "where buser_name=" + "'" + model.getValueAt(table.getSelectedRow(), 1) + "'";
					pstmt3 = conn.prepareStatement(sql3);
					rs3 = pstmt3.executeQuery();

					while (rs3.next()) {
						String str3 = "직원명 : " + rs3.getString("sawon_name") + "\n" + "관리고객은\n고객명\t고객전화\t주민번호\n";
						area.append(str3);

						sql2 = "select distinct sawon_name,gogek_name,gogek_tel,gogek_jumin,buser_name from sawon full join gogek"
								+ " on gogek_damsano=sawon_no inner join buser on buser_num=buser_no"
								+ " where buser_name=" + "'" + model.getValueAt(table.getSelectedRow(), 1) + "'"
								+ " and sawon_name='" + rs3.getString("sawon_name") + "'";

						pstmt2 = conn.prepareStatement(sql2);
						rs2 = pstmt2.executeQuery();

						while (rs2.next()) {

							if (rs2.getString("gogek_name") == null) {
								area.append("고객이없는사원입니다\n");
							} else {

								String str = rs2.getString("gogek_name") + "\t" + rs2.getString("gogek_tel") + "\t"
										+ rs2.getString("gogek_jumin") + "\n";
								area.append(str);
							}
						}
						rs2 = pstmt2.executeQuery();
						area.append("\n");
					}
				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
		});
	}

	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void disData() {
		model.setNumRows(0);
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			String sql = "select buser_no, buser_name,nvl(buser_tel,'전화없음'),nvl(buser_loc,'없음') "
					+ "from buser group by buser_no, buser_name,buser_tel,buser_loc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String[] imsi = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) };
				model.addRow(imsi);

			}

		} catch (Exception e) {
			System.out.println(e);
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
		new Test4();
	}

}
