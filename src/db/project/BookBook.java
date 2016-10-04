package db.project;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import db.project.BookCustomer.find;

import java.io.*;
import java.sql.*;
import java.util.Calendar;
import java.util.zip.InflaterInputStream;

public class BookBook extends JPanel implements ActionListener{
	JTextField txtBbun,txtBjemok,txtBjang,txtBkuil,txtBdaesu,txtBdaeyn,txtBdaebun,txtBdaeil,txtBbanil;
	JTextArea taBmemo;
	JButton btnInsert,btnUpdate,btnDel,btnFind,btnOption,btnClose;
	JButton btnF,btnP,btnN,btnL,btnin,btnsu;
	JLabel lblRec,lblPic;

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs, rs2;


	String sql, imgPath; //--- 그림 파일 경로 변수
	int iTotal = 0;	// 자료의 갯수
	int iLast = 0; 	// 마지막 레코드 번호
	JPanel picPn;
	File file;  //--- 이미지 로딩하기 위한 변수
	boolean isInsert = false;	// Insert 버튼 눌림 여부
	boolean isUpdate = false;	// Update 버튼 눌림 여부
	
	public BookBook(){
		design();
		addListener();
		accDb();
		
		init();
		display();
	}
	
	public void design(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//도서정보 패널========================
		JPanel bookPn=new JPanel(new GridLayout(6,1));
		bookPn.setBorder(new TitledBorder(new TitledBorder("도서 정보"), "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.LEFT));
		JPanel bPn1=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel bPn2=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel bPn3=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel bPn4=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel bPn5=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel bPn6=new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		txtBbun=new JTextField("",5);
		txtBjemok=new JTextField("",30);
		txtBjang=new JTextField("",10);
		txtBkuil=new JTextField("",10);
		txtBdaesu=new JTextField("",5);
		txtBdaeyn=new JTextField("",5);
		txtBdaebun=new JTextField("",5);
		txtBdaeil=new JTextField("",10);
		txtBbanil=new JTextField("",10);
		taBmemo=new JTextArea(2,30);
		JScrollPane scroll=new JScrollPane(taBmemo);
		taBmemo.setBackground(Color.lightGray);
		
		txtBbun.setEditable(false);
		txtBjemok.setEditable(false);
		txtBjang.setEditable(false);
		txtBkuil.setEditable(false);
		txtBdaesu.setEditable(false);
		txtBdaeyn.setEditable(false);
		txtBdaebun.setEditable(false);
		txtBdaeil.setEditable(false);
		txtBbanil.setEditable(false);
		taBmemo.setEditable(false);
				
		btnInsert=new JButton("신규");
		btnUpdate=new JButton("수정");
		btnDel=new JButton("삭제");
		btnFind=new JButton("검색");
		btnOption=new JButton("옵션");
		btnClose=new JButton("닫기");
		btnF=new JButton(" <<= ");
		btnP=new JButton("  <= ");
		btnN=new JButton(" =>  ");
		btnL=new JButton(" =>> ");
		lblRec=new JLabel(" 0 / 0 ",JLabel.CENTER);
		bPn1.add(new JLabel("번호 :"));
		bPn1.add(txtBbun);
		bPn1.add(new JLabel("                              대여표시 :"));
		bPn1.add(txtBdaeyn);
		
		bPn2.add(new JLabel("제목 :"));
		bPn2.add(txtBjemok);	

		bPn3.add(new JLabel("장르 :"));
		bPn3.add(txtBjang);
		bPn3.add(new JLabel("                구입일 :"));
		bPn3.add(txtBkuil);
		
		bPn4.add(new JLabel("대여횟수 :"));
		bPn4.add(txtBdaesu);
		bPn4.add(new JLabel("                 대여자번호 :"));
		bPn4.add(txtBdaebun);
		
		bPn5.add(new JLabel("대여일 :"));
		bPn5.add(txtBdaeil);
		bPn5.add(new JLabel("            반납일 :"));
		bPn5.add(txtBbanil);

		bPn6.add(new JLabel("메모 :"));
		bPn6.add(scroll);
		
		bookPn.add(bPn1);  bookPn.add(bPn2); 	bookPn.add(bPn3);
		bookPn.add(bPn4);  bookPn.add(bPn5); 	bookPn.add(bPn6);
		this.add(bookPn);
		
		//이미지 패널----------------------------------------------------
		picPn=new JPanel(new BorderLayout());
		lblPic = new JLabel();
		//lblPic.setPreferredSize(new Dimension(1000, 300));
		picPn.add(lblPic);

		this.add(picPn);

		//레코드 이동 패널------------------------------------------------
		JPanel movePn=new JPanel();
		movePn.setBorder(new TitledBorder(new TitledBorder("레코드 이동"), "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.LEFT));
		movePn.add(btnF);
		movePn.add(btnP);
		movePn.add(lblRec);
		movePn.add(btnN);
		movePn.add(btnL);
		
		this.add(movePn);
		
        //명령 버튼 패널--------------------------------------------------
		JPanel bottomPn1=new JPanel();
		bottomPn1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		bottomPn1.add(btnInsert);
		bottomPn1.add(btnUpdate);
		bottomPn1.add(btnDel);
		bottomPn1.add(btnFind);
		bottomPn1.add(btnOption);
		bottomPn1.add(btnClose);
		
		this.add(bottomPn1);
		
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		btnsu=new JButton("그림 수정");

	}
	
	private void addListener() {
		btnClose.addActionListener(this);
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDel.addActionListener(this);
		btnFind.addActionListener(this);
		btnOption.addActionListener(this);
		btnClose.addActionListener(this);
		btnF.addActionListener(this);
		btnP.addActionListener(this);
		btnN.addActionListener(this);
		btnL.addActionListener(this);
		//btnin.addActionListener(this);
		btnsu.addActionListener(this);
	}
	
	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			
		if(e.getSource() == btnClose){
			
			BookMain.book_book.setEnabled(true);
			BookMain.childWinBook.dispose();//메인에있는 대여창닫기
		}else if(e.getSource().equals(btnF)){
			rs.first();
			display();
		}else if(e.getSource().equals(btnP)){
			if(rs.isFirst()) return;
			rs.previous();
			display();
		}else if(e.getSource().equals(btnN)){
			if(rs.isLast()) return;
			rs.next();
			display();
		}else if(e.getSource().equals(btnL)){
			rs.last();
			display();
			
		}else if(e.getSource().equals(btnInsert)){ //신규
			if(isInsert == false){
				btnInsert.setText("확인");
				isInsert = true;
				
				txtBbun.setText(iLast + 1 + "");
				txtBjemok.setEditable(true);
				txtBjang.setEditable(true);
				txtBkuil.setEditable(true);
				taBmemo.setEditable(true);
				taBmemo.setBackground(Color.white);
				
				txtBjemok.setText("");
				txtBjang.setText("");
				taBmemo.setText("");
				imgPath = null; //도서이미지 경로 초기화
				
				Calendar cal = Calendar.getInstance();
				String imsi = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" +
								cal.get(Calendar.DATE);
				txtBkuil.setText(imsi);
				txtBdaeyn.setText("n");
				txtBdaesu.setText("0");
				txtBdaebun.setText(null);
				txtBdaeil.setText(null);
				txtBbanil.setText(null);
				
				//이미지삽입버튼으로 화면변경
				picPn.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
				btnin = new JButton("그림 선택");
				btnin.addActionListener(this);
				lblPic.setVisible(false);
				picPn.add("Center",btnin);
				
				txtBjemok.requestFocus();
			}else{
				btnInsert.setText("신규");
				isInsert = false;
				
				
				txtBjemok.setEditable(false);
				txtBjang.setEditable(false);
				txtBkuil.setEditable(false);
				taBmemo.setEditable(false);
				taBmemo.setBackground(Color.LIGHT_GRAY);
				
				//신규도서 등록작업
				try {
					sql = "insert into book values(?,?,?,?,0,'n',0,null,null,?,?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, txtBbun.getText());
					pstmt.setString(2, txtBjemok.getText());
					pstmt.setString(3, txtBjang.getText());
					pstmt.setString(4, txtBkuil.getText());
					pstmt.setString(5, taBmemo.getText());
					pstmt.setString(6, file.getName());
					pstmt.executeUpdate();
					
					init();
					rs.last();
					display();
				} catch (Exception e2) {
					System.out.println("추가오류" + e2);
				}
			}
			
		}else if(e.getSource().equals(btnUpdate)){	//수정
			 //수정
			if(isUpdate == false){
				btnUpdate.setText("완료");
				isUpdate = true;
				
				txtBjemok.setEditable(true);
				txtBjang.setEditable(true);
				txtBkuil.setEditable(true);
				
				
			}else{
				btnUpdate.setText("수정");
				isUpdate = false;
				
				//수정처리
				try {
					sql = "update book set b_jemok=?,b_jang=?,b_kuipil=? where b_bun=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, txtBjemok.getText());
					pstmt.setString(2, txtBjang.getText());
					pstmt.setString(3, txtBkuil.getText());
					pstmt.setString(4, txtBbun.getText());
					pstmt.executeUpdate();
					
					int clow = rs.getRow();//현재의 레코드포인터를 기억
					init();
					rs.absolute(clow);//레코드의 무조건이동
					display();
					
				} catch (Exception e2) {
					System.out.println("수정오류" + e2);
				}
				
				txtBjemok.setEditable(false);
				txtBjang.setEditable(false);
				txtBkuil.setEditable(false);
			}
		
		}else if(e.getSource().equals(btnDel)){		//삭제
			int re = JOptionPane.showConfirmDialog(this, "정말 삭제할까요?","삭제",JOptionPane.YES_NO_OPTION);
				if(re == JOptionPane.YES_OPTION){
					try {
						if(rs.getRow() == 0){
							JOptionPane.showMessageDialog(this, "삭제할 자료가없어요");
							return;
						}
						
						if(txtBdaeyn.getText().equals("비치중")){
							int crow = rs.getRow();
							sql = "delete from book where b_bun=?";
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, txtBbun.getText());
							pstmt.executeUpdate();
							
							init();
							if(crow == 1){
								
							}else{
								rs.absolute(crow - 1);
							}
							display();
							
						}else{
							JOptionPane.showMessageDialog(this, "빌려간 책입니다");
						}
					} catch (Exception e2) {
						System.out.println("삭제오류");
					}
				}
		
		}else if(e.getSource().equals(btnFind)){	//검색(제목별)
			
			String fb = JOptionPane.showInputDialog(this, "책이름을 입력하세요");
			if(fb == null) return;
			try {
				int crow = rs.getRow(); //검색결과가 없을때에 돌아오기위해
				rs.beforeFirst();
				int cf = 0;
				while(rs.next()){
					String str = rs.getString("b_jemok").replace(" ", "");
					String str2 = rs.getString("b_jemok");
					if(fb.equals(str) || fb.equals(str2)){
						display();
						cf++;
						break;
					}
				}
				if(cf == 0){
					JOptionPane.showMessageDialog(this, "검색결과가없다");
					rs.absolute(crow);
				}
			} catch (Exception e2) {
				System.out.println("검색실패");
			}
		
			
		}else if(e.getSource().equals(btnOption)){		//옵션
			
			//상세자료 처리창 보이기
			JDialog jd = new JDialog(new JFrame(), "상세검색", true);
			jd.add(new find());
			jd.setBounds(300,300,800,500);
			jd.setVisible(true);
		
			
		}else if(e.getSource().equals(btnin)){
			JFileChooser cho = new JFileChooser("C:/work/sou/");
			cho.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = cho.showOpenDialog(this);
			
			if(result == JFileChooser.CANCEL_OPTION){
				file = null;
			}else{
				file = cho.getSelectedFile();
				imgPath = file.getPath().replace("\\", "/");
				//System.out.println(imgPath);
				displayImage();
				
				lblPic.setVisible(true);
				btnin.setVisible(false);
				
			}
					
		}
		
		} catch (Exception e2) {
			// TODO: handle exception
		}
		
	}
	
	
	//상세검색용
		class find extends JPanel implements ActionListener{
			String[][] datas = new String[0][4];
			String[] title = { "번호", "제목", "대여여부", "대여자이름" ,"전화" , "총대여수"};
			DefaultTableModel model = new DefaultTableModel(datas, title);
			JTable table = new JTable(model);
			JRadioButton b1 = new JRadioButton("전체",true);
			JRadioButton b2 = new JRadioButton("대여");
			JRadioButton b3 = new JRadioButton("비치");
			ButtonGroup g = new ButtonGroup();
			 
			
			public find(){
				flayinit();
				faccDb();
				fdisplay();
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int count = 0;
				if(b1.isSelected()){
					fdisplay();
				}else if(b2.isSelected()){
					model.setNumRows(0);
					try {
						conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
						String sql = "select b_bun,b_jemok,b_daeyn,c_irum,c_junhwa,c_daesu from book"
								+ " left join customer on b_daebun=c_bun where b_daeyn='y'";
						pstmt = conn.prepareStatement(sql);
						rs = pstmt.executeQuery();
						
						while (rs.next()) {
							String[] imsi = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) };
							model.addRow(imsi);
							count++;
						}
						String[] imsi3 = {"",""};
						String[] imsi2 = {"","권수 : ","" + count};
						model.addRow(imsi3);
						model.addRow(imsi2);
						
					} catch (Exception e3) {
						System.out.println(e);
					}
				}else if(b3.isSelected()){
					model.setNumRows(0);
					try {
						conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
						String sql = "select b_bun,b_jemok,b_daeyn,c_irum,c_junhwa,c_daesu from book"
								+ " left join customer on b_daebun=c_bun where b_daeyn='n'";
						pstmt = conn.prepareStatement(sql);
						rs = pstmt.executeQuery();
						
						while (rs.next()) {
							String[] imsi = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) };
							model.addRow(imsi);
							count++;
						}
						String[] imsi3 = {"",""};
						String[] imsi2 = {"","권수 : ","" + count};
						model.addRow(imsi3);
						model.addRow(imsi2);
					} catch (Exception e3) {
						System.out.println(e);
					}
				}
					
			
			}
		
		private void flayinit() {
			JButton btn = new JButton("확인");
			setLayout(new BorderLayout());
			//setLayout(new FlowLayout());
			JScrollPane scroll = new JScrollPane(table);
			
			JPanel panel = new JPanel(new GridLayout(2, 0));
			JPanel panel2 = new JPanel();
			JPanel panel3 = new JPanel();
			panel2.add(scroll);
			g.add(b1);
			g.add(b2);
			g.add(b3);
			panel3.add(b1);
			panel3.add(b2);
			panel3.add(b3);
			
			add("Center", panel2);
			add("South", panel3);
			
			b1.addActionListener(this);
			b2.addActionListener(this);
			b3.addActionListener(this);
			
			
		}
		
		private void faccDb() {

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (Exception e) {
				System.out.println(e);
			}

		}
		
		private void fdisplay() {
			model.setNumRows(0);
			int count = 0;
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
				String sql = "select b_bun,b_jemok,b_daeyn,c_irum,c_junhwa,c_daesu from book"
						+ " left join customer on b_daebun=c_bun";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					String[] imsi = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) };
					model.addRow(imsi);
					count++;
				}
				String[] imsi3 = {"",""};
				String[] imsi2 = {"","권수 : ","" + count};
				model.addRow(imsi3);
				model.addRow(imsi2);

			} catch (Exception e) {
				System.out.println(e);
			}

		}
		
		}
	
	
	private void init() {


		try {
			
			sql = "select * from book order by b_bun asc";
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE , ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			rs.last();
			iTotal = rs.getRow();//전체자료수얻기
			iLast = rs.getInt("b_bun"); //마지막고객번호 얻기(추가용)
			//System.out.println(iTotal + " " + iLast);
			rs.first();
		} catch (Exception e) {
			System.out.println("ini에러" + e);
		}

	

	}
	
	private void display() {
		try {
			txtBbun.setText(rs.getString("b_bun"));
			txtBdaeyn.setText(rs.getString("b_daeyn"));
			txtBjemok.setText(rs.getString("b_jemok"));
			txtBjang.setText(rs.getString("b_jang"));
			txtBkuil.setText(rs.getString("b_kuipil"));
			txtBdaesu.setText(rs.getString("b_daesu"));
			txtBdaebun.setText(rs.getString("b_daebun"));
			
			if(rs.getString("b_kuipil") == null){
				txtBdaeil.setText("");
			}else{
				txtBdaeil.setText(rs.getString("b_kuipil").substring(0, 10));
			}
			
			if(rs.getString("b_daeil") == null){
				txtBkuil.setText("");
			}else{
				txtBkuil.setText(rs.getString("b_kuipil").substring(0, 10));
			}
			
			if(rs.getString("b_banil") == null){
				txtBbanil.setText("");
			}else{
				txtBbanil.setText(rs.getString("b_banil").substring(0, 10));
			}
			
			if(rs.getString("b_daeyn").equals("y")){
				txtBdaeyn.setText("대여중");
				txtBdaeyn.setForeground(Color.RED);
			}else{
				txtBdaeyn.setText("비치중");
			txtBdaeyn.setForeground(Color.BLACK);
			}
			
			taBmemo.setText(rs.getString("b_memo"));
			lblRec.setText(rs.getRow() + " / " + iTotal);// 1 / 10
			
			//이미지 출력
			imgPath ="C:/work/sou/java7/src/image/" + rs.getString("b_image");
			//System.out.println(imgPath);
			displayImage();
		} catch (Exception e) {
			System.out.println("dis에러");
		}

	}
	private void displayImage() {
		if(imgPath != null){
			ImageIcon icon = new ImageIcon(imgPath);
			lblPic.setIcon(icon);
			lblPic.setToolTipText("경로는" + imgPath.toLowerCase());//툴팁에 경로표시
			
			if(icon != null){
				lblPic.setText(null);
			}else{
				lblPic.setText("그림없음");
			}
	}
			

	}
	public static void main(String[] args){
		BookBook bookBook =new BookBook();
		
		JFrame frame=new JFrame("도서 창");
		frame.getContentPane().add(bookBook);
		frame.setBounds(440,110,430,700);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
