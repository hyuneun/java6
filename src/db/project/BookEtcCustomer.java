package db.project;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class BookEtcCustomer extends JPanel implements ActionListener{
	private JButton btnCall,btnCdae,btnCjuyo;
	private DefaultTableModel mod;
	private Boolean juyomode = false; 
	//주요 모드 = 대여횟수 3회 이상.. 고객..
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	String sql;
	
	
	public BookEtcCustomer(){
		design();
		addListener();
		accDb();
	}
	public void design(){
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder(new TitledBorder("고객 정보"), "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.LEFT));
		JPanel cPn1=new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		btnCall=new JButton("전체고객");
		btnCdae=new JButton("대여중인 고객");
		btnCjuyo=new JButton("주요 고객");
	
		cPn1.add(new JLabel("고객 자료 보기"));
		cPn1.add(btnCall);		
		cPn1.add(btnCdae);
		cPn1.add(btnCjuyo);
		this.add("North",cPn1);
		
		//비디오 목록 테이블 삽입
		String[][]data=new String[0][4];
		String []cols={"번호","이름","전화","대여횟수","메모"};
		mod=new DefaultTableModel(data,cols);
		JTable tab=new JTable(mod);
		tab.getColumnModel().getColumn(0).setPreferredWidth(20);
		tab.getColumnModel().getColumn(1).setPreferredWidth(26);
		tab.getColumnModel().getColumn(3).setPreferredWidth(20);
		tab.getColumnModel().getColumn(4).setPreferredWidth(50);
		tab.setPreferredScrollableViewportSize(new Dimension(10,120)); 

		tab.setSelectionBackground(Color.green);
		JScrollPane scroll=new JScrollPane(tab);
		this.add("Center",scroll);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			sql = "select * from customer";
		if(e.getSource() == btnCall){
			mod.setNumRows(0);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String[] imsi = {rs.getString("c_bun") , rs.getString("c_irum") ,rs.getString("c_junhwa"),
						rs.getString("c_daesu") , rs.getString("c_memo")};
				mod.addRow(imsi);
				
			}
			
		}else if(e.getSource() == btnCdae){
			mod.setNumRows(0);
			sql += " inner join book on c_bun=b_daebun";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String[] imsi = {rs.getString("c_bun") , rs.getString("c_irum") ,rs.getString("c_junhwa"),
						rs.getString("c_daesu") , rs.getString("c_memo")};
					mod.addRow(imsi);
			}
			
		}else if(e.getSource() == btnCjuyo){ //3회이상 대여고객
			mod.setNumRows(0);
			sql += " where c_daesu >= 3";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String[] imsi = {rs.getString("c_bun") , rs.getString("c_irum") ,rs.getString("c_junhwa"),
						rs.getString("c_daesu") , rs.getString("c_memo")};
					mod.addRow(imsi);
			}
			
		}
		
		} catch (Exception e2) {
			System.out.println("오류" + e2);
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
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
	
	private void addListener() {
		btnCall.addActionListener(this);
		btnCdae.addActionListener(this);
		btnCjuyo.addActionListener(this);
	}
	public static void main(String[] args) {
		BookEtcCustomer bookEtcCustomer =new BookEtcCustomer();
		JFrame frame=new JFrame();
		frame.getContentPane().add(bookEtcCustomer);
		frame.setBounds(200,200,580,400);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
