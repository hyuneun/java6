package db.project;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class BookCustomer extends JPanel implements ActionListener{
	JTextField txtCbun,txtCirum,txtCjunhwa,txtCjuso,txtCdaesu;
	JTextArea taCmemo;
	JButton btnInsert,btnOk,btnUpdate,btnDel,btnFind,btnOption,btnClose;
	JButton btnF,btnP,btnN,btnL;
	JLabel lblRec;
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs, rs2;
	String sql;
	
	int iTotal = 0;	// 자료의 갯수
	int iLast = 0; 	// 마지막 레코드 번호
	boolean isInsert = false;	// Insert 버튼 눌림 여부
	boolean isUpdate = false;	// Update 버튼 눌림 여부
	
	// 생성자
	public BookCustomer(){
		design();
		addListener();
		accDb();
		
		init();
		display();
	}
	
	public void design(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		//고객정보 패널========================
		JPanel customerPn=new JPanel(new GridLayout(4,1));
		customerPn.setBorder(new TitledBorder(new TitledBorder("고객 정보"), "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.LEFT));
		JPanel cPn1=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel cPn2=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel cPn3=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel cPn4=new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		txtCbun=new JTextField("",5);
		txtCirum=new JTextField("",10);
		txtCjunhwa=new JTextField("",15);
		txtCjuso=new JTextField("",28);
		txtCdaesu=new JTextField("",5);
		taCmemo=new JTextArea(2,28);
		JScrollPane scroll=new JScrollPane(taCmemo);
		taCmemo.setBackground(Color.lightGray);
		
		txtCbun.setEditable(false);
		txtCirum.setEditable(false);
		txtCjunhwa.setEditable(false);
		txtCjuso.setEditable(false);
		txtCdaesu.setEditable(false);
		taCmemo.setEditable(false);
		
		btnInsert=new JButton("신규");
		btnOk=new JButton("확인");
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
		cPn1.add(new JLabel("번호 :"));
		cPn1.add(txtCbun);
		cPn1.add(new JLabel("       이름 :"));
		cPn1.add(txtCirum);
		
		cPn2.add(new JLabel("전화 :"));
		cPn2.add(txtCjunhwa);	
		cPn2.add(new JLabel("      대여횟수 :"));
		cPn2.add(txtCdaesu);
		
		cPn3.add(new JLabel("주소 :"));
		cPn3.add(txtCjuso);
		
		cPn4.add(new JLabel("메모 :"));
		cPn4.add(scroll);

		customerPn.add(cPn1);  customerPn.add(cPn2); 	customerPn.add(cPn3);	customerPn.add(cPn4);
		this.add(customerPn);
		
		btnOk.setEnabled(false);
		
		//레코드 이동 패널========================
		JPanel movePn=new JPanel();
		movePn.setBorder(new TitledBorder(new TitledBorder("레코드 이동"), "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.LEFT));
		movePn.add(btnF);
		movePn.add(btnP);
		movePn.add(lblRec);
		movePn.add(btnN);
		movePn.add(btnL);
		
		this.add(movePn);
		
        //명령 버튼 패널========================
		JPanel bottomPn1=new JPanel();
		bottomPn1.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		bottomPn1.add(btnInsert);
		bottomPn1.add(btnOk);
		JLabel lbl1=new JLabel("    "); 
		bottomPn1.add(lbl1);
		bottomPn1.add(btnUpdate);
		bottomPn1.add(btnDel);
		
		JPanel bottomPn2=new JPanel();
		bottomPn2.add(btnFind);
		bottomPn2.add(btnOption);
		JLabel lbl2=new JLabel("                          "); 
		bottomPn2.add(lbl2);
		bottomPn2.add(btnClose);
		
		this.add(bottomPn1);
		this.add(bottomPn2);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	private void addListener() {
		btnClose.addActionListener(this);
		btnInsert.addActionListener(this);
		btnOk.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDel.addActionListener(this);
		btnFind.addActionListener(this);
		btnOption.addActionListener(this);
		btnClose.addActionListener(this);
		btnF.addActionListener(this);
		btnP.addActionListener(this);
		btnN.addActionListener(this);
		btnL.addActionListener(this);
	}
	
	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//레코드가 움직여야하기때문에 conn을 계속 살려둠
		} catch (Exception e) {
			System.out.println("드라이버로딩실패 " + e);
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		if(e.getSource() == btnClose){
			
			BookMain.book_customer.setEnabled(true);
			BookMain.childWinCustomer.dispose();//메인에있는 대여창닫기
		}else if(e.getSource() == btnInsert){	//신규
			if(isInsert == false){
				btnInsert.setText("취소");
				btnOk.setEnabled(true);
				isInsert = true;
				
				txtCirum.setEditable(true);
				txtCjunhwa.setEditable(true);
				txtCjuso.setEditable(true);
				txtCbun.setText(String.valueOf(iLast + 1));//신규고객번호
				
				txtCirum.setText(null);
				txtCjunhwa.setText(null);
				txtCjuso.setText(null);
				txtCdaesu.setText(null);
				taCmemo.setText(null);
				txtCirum.requestFocus();
			}else{
				btnInsert.setText("신규");
				btnOk.setEnabled(false);
				isInsert = false;
				
				txtCirum.setEditable(false);
				txtCjunhwa.setEditable(false);
				txtCjuso.setEditable(false);
				
				display();
			}
		}else if(e.getSource() == btnOk){	//확인
			try {
				//입력자료검사필요(생략)
				sql = "insert into customer values(?,?,?,?,0,'')";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, txtCbun.getText());
				pstmt.setString(2, txtCirum.getText());
				pstmt.setString(3, txtCjunhwa.getText());
				pstmt.setString(4, txtCjuso.getText());
				pstmt.executeQuery(); //등록수행
				
				init();//전체건수출력
				rs.last();//추가후 마지막으로 레코드이동
				display();
				
				txtCirum.setEditable(false);
				txtCjunhwa.setEditable(false);
				txtCjuso.setEditable(false);
				btnInsert.setText("신규");
				btnOk.setEnabled(false);
				
			} catch (Exception e2) {
				System.err.println("신규확인에러" + e2);
			}
		}else if(e.getSource() == btnUpdate){ //수정
			if(isUpdate == false){
				btnUpdate.setText("완료");
				isUpdate = true;
				
				txtCirum.setEditable(true);
				txtCjunhwa.setEditable(true);
				txtCjuso.setEditable(true);
				
				
			}else{
				btnUpdate.setText("수정");
				isUpdate = false;
				
				//수정처리
				try {
					sql = "update customer set c_irum=?,c_junhwa=?,c_juso=? where c_bun=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, txtCirum.getText());
					pstmt.setString(2, txtCjunhwa.getText());
					pstmt.setString(3, txtCjuso.getText());
					pstmt.setString(4, txtCbun.getText());
					pstmt.executeUpdate();
					
					int clow = rs.getRow();//현재의 레코드포인터를 기억
					init();
					rs.absolute(clow);//레코드의 무조건이동
					display();
					
				} catch (Exception e2) {
					System.out.println("수정오류" + e2);
				}
				
				txtCirum.setEditable(false);
				txtCjunhwa.setEditable(false);
				txtCjuso.setEditable(false);
			}
		}else if(e.getSource() == btnDel){	//삭제
			int re = JOptionPane.showConfirmDialog(this, "정말 삭제할까요?","삭제",JOptionPane.YES_NO_OPTION);
				if(re == JOptionPane.YES_OPTION){
					try {
						if(rs.getRow() == 0){
							JOptionPane.showMessageDialog(this, "삭제할 자료가없어요");
							return;
						}
						//현재대여중인고객은 삭제불가
						if(taCmemo.getText().equals("")){
							int crow = rs.getRow();
							sql = "delete from customer where c_bun=?";
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, txtCbun.getText());
							pstmt.executeUpdate();
							
							init();
							if(crow == 1){
								
							}else{
								rs.absolute(crow - 1);
							}
							display();
							
						}else{
							JOptionPane.showMessageDialog(this, "반납할 도서가 있습니다");
						}
					} catch (Exception e2) {
						System.out.println("삭제오류");
					}
				}
		}else if(e.getSource() == btnFind){		//검색
			//고객번호로검색
			String fb = JOptionPane.showInputDialog(this, "몇번 고객을 검색할까여?");
			if(fb == null) return;
			try {
				int crow = rs.getRow(); //검색결과가 없을때에 돌아오기위해
				rs.beforeFirst();
				int cf = 0;
				while(rs.next()){
					String str = rs.getString("c_bun");
					if(fb.equals(str)){
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
				System.out.println("번호검색실패");
			}
		}else if(e.getSource() == btnOption){	//옵션
			//상세자료 처리창 보이기
			JDialog jd = new JDialog(new JFrame(), "상세검색", true);
			jd.add(new find());
			jd.setBounds(300,300,500,500);
			jd.setVisible(true);
		}
		else if(e.getSource() == btnF){	//처음
				rs.first();
				display();
		}else if(e.getSource() == btnP){	//이전
			if(rs.isFirst()) return;
			rs.previous();
			display();
		}else if(e.getSource() == btnN){	//다음
			if(rs.isLast()) return;
			rs.next();
			display();
		}else if(e.getSource() == btnL){	//마지막
			rs.last();
			display();
		}
		}catch (SQLException e1) {
			System.out.println(e1);
		}
		
	}
	
	//상세검색용
	class find extends JPanel{
		String[][] datas = new String[0][4];
		String[] title = { "고객번호", "고객명", "고객전화", "대여수" };
		DefaultTableModel model = new DefaultTableModel(datas, title);
		JTable table = new JTable(model);
		JTextField ff = new JTextField("",5);
		public find(){
			flayinit();
			faccDb();
			fdisplay();
		}
			
	
	private void flayinit() {
		JButton btn = new JButton("확인");
		setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(table);
		
		JPanel panel = new JPanel(new GridLayout(2, 0));
		JPanel panel2 = new JPanel();
		panel.add(scroll);
		panel2.add(new JLabel("이름별검색 : "));
		panel2.add(ff);
		panel2.add(btn);
		add("Center", panel);
		add("South", panel2);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(ff.getText().equals("")){
					
					fdisplay();
				}else{
					boolean b = ff.getText().matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
					if(b == true){
						
					int count = 0;

					model.setNumRows(0);
					try {
						conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
						String sql = "select * from customer where c_irum=?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, ff.getText());
						rs = pstmt.executeQuery();
						while (rs.next()) {
							String[] imsi = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5) };
							model.addRow(imsi);
							count++;
						}

					} catch (Exception e2) {
						System.out.println(e2);
					}
					
					if(count == 0){
						JOptionPane.showMessageDialog(find.this, "도서자료가 없네요~");
					}
					}else{
						JOptionPane.showMessageDialog(find.this, "한글을 입력하세요");
					}
				}
				
			}
		});
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
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			String sql = "select * from customer";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String[] imsi = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5) };
				model.addRow(imsi);

			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	}
	private void init() {
		try {
			sql = "select * from customer order by c_bun asc";
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE , ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			rs.last();
			iTotal = rs.getRow();//전체자료수얻기
			iLast = rs.getInt("c_bun"); //마지막고객번호 얻기(추가용)
			//System.out.println(iTotal + " " + iLast);
			rs.first();
			
		} catch (Exception e) {
			System.out.println("ini에러" + e);
		}

	}
	
	private void display() {
		try {
			txtCbun.setText(rs.getString("c_bun"));
			txtCirum.setText(rs.getString("c_irum"));
			txtCjunhwa.setText(rs.getString("c_junhwa"));
			txtCdaesu.setText(rs.getString("c_daesu"));
			txtCjuso.setText(rs.getString("c_juso"));
			taCmemo.setText(rs.getString("c_memo"));
			
			lblRec.setText(rs.getRow() + " / " + iTotal);// 1 / 10
			
		} catch (Exception e) {
			System.out.println("display에러");
		}

	}
	
	public static void main(String[] args) {
		BookCustomer bookCustomer=new BookCustomer();
		JFrame frame=new JFrame("고객 창");
		frame.getContentPane().add(bookCustomer);
		frame.setBounds(200,200,430,450);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}