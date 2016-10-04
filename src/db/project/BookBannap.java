package db.project;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class BookBannap extends JPanel implements ActionListener{
	JTextField txtBbun,txtBjemok,txtBdaeil,txtBdaebun,  txtJemok;
	static JTextField txtBbanil;
	JButton btnBbun,btnChange,btnBannap,btnNew,btnClose;
	DefaultTableModel mod;
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs, rs2;
	String sql;
	
	static JFrame calFrame; 
	public BookBannap(){
		design();
		addListener();
		accDb();
	}
	public void design(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//도서정보 패널========================
		JPanel bookPn=new JPanel(new GridLayout(3,1));
		bookPn.setBorder(new TitledBorder(new TitledBorder("비디오 정보"), "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.LEFT));
		JPanel bPn1=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel bPn2=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel bPn3=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel bPn4=new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		txtBbun=new JTextField("",5);
		txtBjemok=new JTextField("",20);
		txtBdaeil=new JTextField("",10);
		txtBbanil=new JTextField("",10);
		txtBdaebun=new JTextField("",5);
		txtJemok=new JTextField("",25);  //반납되는 도서 제목을 고객메모에서 제거하기 위함
		
		btnBbun=new JButton("확인");
		btnBbun.setMargin(new Insets(0, 3, 0, 3));
		btnChange=new JButton("변경");
		btnChange.setMargin(new Insets(0, 3, 0, 3));

		bPn1.add(new JLabel("번호:"));
		bPn1.add(txtBbun);
		bPn1.add(btnBbun);
		
		bPn2.add(new JLabel("제목:"));
		bPn2.add(txtBjemok);
		txtBjemok.setEditable(false);
		
		bPn3.add(new JLabel("대여일:"));
		bPn3.add(txtBdaeil);	
		txtBdaeil.setEditable(false);
		bPn3.add(new JLabel("      반납일:"));
		bPn3.add(txtBbanil);
		bPn3.add(btnChange);	
		
		bPn4.add(new JLabel("대여자 번호:"));
		bPn4.add(txtBdaebun);
		bPn4.add(txtJemok);   //고객메모란의 대여도서 제목중 반납되는 도서 제목만 제거하기 위해 사용  
		txtJemok.setVisible(true); //숨긴다.
		txtBdaebun.setEditable(false);
		
		JPanel bottomPn=new JPanel();
		bottomPn.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		btnBannap=new JButton("반납 확인");
		btnNew=new JButton("새로 입력");
		btnClose=new JButton(" 닫 기 ");
		
		bottomPn.add(btnBannap);
		bottomPn.add(btnNew);
		JLabel lbl=new JLabel("    "); 
		bottomPn.add(lbl);
		bottomPn.add(btnClose);
		
		bookPn.add(bPn1);  bookPn.add(bPn2); 	bookPn.add(bPn3);	
		
		this.add(bookPn);
		this.add(bPn4);
		this.add(bottomPn);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//도서 목록 테이블 삽입
		String[][]data=new String[0][4];
		String []cols={"번호","제목","대번","이름","대여일"};
		mod=new DefaultTableModel(data,cols){ //테이블 내용 수정 불가
			    public boolean isCellEditable(int rowIndex, int mColIndex) {
				   return false;
				}
			   };
		JTable tab=new JTable(mod);
		tab.getColumnModel().getColumn(0).setPreferredWidth(20);
		tab.getColumnModel().getColumn(1).setPreferredWidth(150);
		tab.getColumnModel().getColumn(2).setPreferredWidth(20);
		tab.getColumnModel().getColumn(3).setPreferredWidth(30);
		tab.setSelectionBackground(Color.green);
		JScrollPane pa=new JScrollPane(tab);
		this.add(pa);
		
		btnBannap.setEnabled(false);  //반납 버튼 비활성화
		btnNew.setEnabled(false);     
	}
	
	private void addListener() {
		btnClose.addActionListener(this);
		btnBbun.addActionListener(this);
		btnChange.addActionListener(this);
		btnBannap.addActionListener(this);
		btnNew.addActionListener(this);
		btnClose.addActionListener(this);
	}
	
	public void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println("드라이버로딩실패 " + e);
		}
		
		daedisplay(); //대여중인도서모두 보이기
	}
	
	public void daedisplay() {
		mod.setNumRows(0);//테이블초기화
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			sql = "select b_bun,b_jemok,c_bun,c_irum,b_daeil from book inner join customer on b_daebun=c_bun order by b_daeil desc, b_bun asc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int count=0;
			while(rs.next()){
				String[] imsi = {rs.getString("b_bun"),
								rs.getString("b_jemok"),
								rs.getString("c_bun"),
								rs.getString("c_irum"),
								rs.getString("b_daeil")
								
				};
				mod.addRow(imsi);
				count++;	
			}
			String[] imsi2 = {null,"전체건수: " + count};
			mod.addRow(imsi2);
			rs.close();
			} catch (Exception e) {
			System.out.println("디스플레이에러" + e);
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnClose){//닫기
			
			BookMain.book_ban.setEnabled(true);
			BookMain.childWinBan.dispose();
		}else if(e.getSource() == btnBbun){//도서번호
			if(txtBbun.getText().equals("")){
				txtBbun.requestFocus();
				JOptionPane.showMessageDialog(this, "도서번호를 입력하세요");
				return;
			}
			sql = "select * from book where b_bun=?";
			processBook(txtBbun.getText().trim());
		}else if(e.getSource() == btnChange){//변경
			BookCal bc = new BookCal();
			calFrame = new JFrame("반납일 변경");
			calFrame.add(bc);
			calFrame.setBounds(300,300,250,200);
			calFrame.setVisible(true);
			
		}else if(e.getSource() == btnBannap){//반납
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
				//customer c_memo만 갱신
				sql = "update customer set c_memo=? where c_bun=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, txtJemok.getText());
				pstmt.setString(2, txtBdaebun.getText());
				pstmt.executeUpdate();
				
				//book
				sql = "update book set b_daeyn='n', b_daebun=0 , b_daeil=null,b_banil=? where b_bun=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, txtBbanil.getText());
				pstmt.setString(2, txtBbun.getText());
				pstmt.executeUpdate();
				
				btnBannap.setEnabled(false);
				btnNew.setEnabled(true);
				
				daedisplay();//갱신후 대여도서 보기
				
				
			} catch (Exception e2) {
				System.out.println("반납오류" + e2);
			}finally {
				try {
					if (rs != null) rs.close();
					if (rs2 != null) rs2.close();
					if (pstmt != null) pstmt.close();
					if (conn != null) conn.close();
					
				} catch (Exception e2) {
					
				}
			}
		}else if(e.getSource() == btnNew){//새로입력
			txtBbun.setText(null);
			txtJemok.setText(null);
			txtBbanil.setText(null);
			txtBdaebun.setText(null);
			txtBjemok.setText(null);
			txtBdaeil.setText(null);
			
			txtBbun.requestFocus();
			btnNew.setEnabled(false);
			
		}
		
		
		
		
		
	}
	
	private void processBook(String findData) {
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, findData);
			rs = pstmt.executeQuery();
			if(rs.next()){
				
				if(rs.getString("b_daeyn").equals("n")){
					JOptionPane.showMessageDialog(this, "대여되있는 도서가 아닙니다");
					txtBbun.setText(null);
					txtJemok.setText(null);
					txtBbanil.setText(null);
					txtBdaebun.setText(null);
					txtBjemok.setText(null);
					txtBdaeil.setText(null);
					rs.close();
					return;
				}

				txtBjemok.setText(rs.getString("b_jemok"));
				txtBdaeil.setText(rs.getString("b_daeil"));
				txtBdaebun.setText(rs.getString("b_daebun"));
				Calendar cal = Calendar.getInstance();
				txtBbanil.setText(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-"
				+ cal.get(Calendar.DATE));
				
				txtBdaeil.setText(rs.getString("b_daeil").substring(0, 10));
				
				
				//반납되는 도서제목을 customer의 메모에서 제거작업준비
				sql = "select * from customer where c_bun=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, txtBdaebun.getText());
				rs = pstmt.executeQuery();
				rs.next();
				txtJemok.setText(rs.getString("c_memo"));
				
				String ban_jemok = txtJemok.getText();
				int start = ban_jemok.indexOf(txtBjemok.getText());
				int end = txtBjemok.getText().length();
				//System.out.println(start+ " " + end);
				txtJemok.setSelectionStart(start);//반전
				txtJemok.setSelectionEnd(start + end + 1);
				txtJemok.requestFocus();
				txtJemok.replaceSelection("");//반전자료를 삭제
				//마지막콤마제거
				try {
					String ss = txtJemok.getText();
					int a = ss.length() - 1;
					String sss = ss.substring(a);
					if(sss.equals(",")) ss = ss.substring(0,a);
					txtJemok.setText(ss);
				} catch (Exception e) {
					
				}
				btnBannap.setEnabled(true);
			}else{
				JOptionPane.showMessageDialog(this, "도서자료가 없네요~");
				txtBbun.setText(null);
				txtBjemok.setText(null);
				txtBdaeil.setText(null);
				

				btnBannap.setEnabled(false);
		
				
			}
		} catch (Exception e) {
			System.out.println("process 오류" + e);
		}finally {
			try {
				if (rs != null) rs.close();
				if (rs2 != null) rs2.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (Exception e2) {
				
			}
		}
		
	}
	
	public static void main(String[] args) {
		BookBannap bookBannap = new BookBannap();
		JFrame frame=new JFrame("반납 창");
		frame.getContentPane().add(bookBannap);
		frame.setResizable(false);
		frame.setBounds(200,200,500,400);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}