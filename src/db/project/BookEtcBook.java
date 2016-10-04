package db.project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class BookEtcBook extends JPanel implements ActionListener{

	private JButton btnAllcustomer, btnDae, btnBan;
	private JComboBox btnJang;
	private DefaultTableModel mod;
	private int selectedMode;
	// 1 = all customer, 2 = 대여중인 도서, 3 = 비치된 도서, 0 = default
	int count = 0;
	int cc = 0;
	Connection conn;
	PreparedStatement pstmt,pstmt2;
	ResultSet rs, rs2;
	String sql, sql2;

	public BookEtcBook() {
		accDb();
		design();
		display();
		addListener();
	}

	private void design() {
		this.setLayout(new BorderLayout());
		// 도서정보 패널========================
		this.setBorder(
				new TitledBorder(new TitledBorder("고객 정보"), "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.LEFT));
		JPanel bPn1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		btnAllcustomer = new JButton("전체 도서");
		btnDae = new JButton("대여중");
		btnBan = new JButton("비치중");
		btnJang = new JComboBox();
		btnJang.addItem("장르별");

		bPn1.add(new JLabel("도서 자료 보기"));
		bPn1.add(btnAllcustomer);
		bPn1.add(btnDae);
		bPn1.add(btnBan);
		bPn1.add(btnJang);
		this.add("North", bPn1);

		// 도서 목록 테이블 삽입
		String[][] data = new String[0][4];
		String[] cols = { "번호", "제목", "장르", "대여횟수", "메모" };
		mod = new DefaultTableModel(data, cols);
		JTable tab = new JTable(mod);

		tab.getColumnModel().getColumn(0).setPreferredWidth(7);
		tab.getColumnModel().getColumn(1).setPreferredWidth(25);
		tab.getColumnModel().getColumn(2).setPreferredWidth(7);
		tab.getColumnModel().getColumn(3).setPreferredWidth(7);
		tab.getColumnModel().getColumn(4).setPreferredWidth(50);
		tab.setPreferredScrollableViewportSize(new Dimension(10, 120));
		tab.setSelectionBackground(Color.green);
		JScrollPane scroll = new JScrollPane(tab);
		this.add("Center", scroll);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		btnJang.addActionListener(this);
		}

	private void display() {
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			sql = "select distinct b_jang from book";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				btnJang.addItem(rs.getString("b_jang"));

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

	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("드라이버로딩실패 " + e);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			sql = "select * from book";
			//System.out.println(btnJang.getSelectedItem());
			if (e.getSource() == btnAllcustomer) {
				cc = 1;
			} else if (e.getSource() == btnDae) {
				cc = 2;
				sql += " inner join customer on c_bun=b_daebun";		
			} else if (e.getSource() == btnBan) {
				cc = 3;
				sql += " where b_daeyn='n'";
			}
			
			if(!(btnJang.getSelectedItem().equals("장르별"))){
			if(cc == 1){
				sql = "select * from book";
				sql += " where b_jang='" + btnJang.getSelectedItem() + "'";
			}else if(cc == 2){
				sql = "select * from book inner join customer on c_bun=b_daebun";
				sql += " and b_jang='" + btnJang.getSelectedItem() + "'";
			}else if(cc == 3){
				sql = "select * from book where b_daeyn='n'";
				sql += " and b_jang='" + btnJang.getSelectedItem() + "'";
			}
			}
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			mod.setNumRows(0);
			while (rs.next()) {
				String[] imsi = { rs.getString("b_bun"), rs.getString("b_jemok"), rs.getString("b_jang"),
						rs.getString("b_daesu"), rs.getString("b_memo") };
				mod.addRow(imsi);
				count++;
			}
			
		} catch (Exception e2) {
			System.out.println("오류" + e2);
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
		
	private void addListener() {

		btnAllcustomer.addActionListener(this);
		btnDae.addActionListener(this);
		btnBan.addActionListener(this);
	}

	public static void main(String[] args) {
		BookEtcBook bookEtcBook = new BookEtcBook();
		JFrame frame = new JFrame();
		frame.getContentPane().add(bookEtcBook);
		frame.setBounds(200, 200, 580, 400);
		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
