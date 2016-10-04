package pack4;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DbTest15CRD extends JFrame implements ActionListener {
		JButton btndelete,btninsert,btnexit;
		String [][] datas = new String[0][4];
		String [] title = {"코드","품명","수량","단가"};
		DefaultTableModel model = new DefaultTableModel(datas, title);
		JTable table = new JTable(model);
		JLabel lblcou = new JLabel("건수 :");
		
		
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs;
		
	public DbTest15CRD() {
		btninsert = new JButton("추가");
		btndelete = new JButton("삭제");
		btnexit = new JButton("종료");
		btninsert.addActionListener(this);
		btndelete.addActionListener(this);
		btnexit.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(btninsert);
		panel.add(btndelete);
		panel.add(btnexit);
		add("North",panel);
		
		//table.getColumnModel().getColumn(3).setPreferredWidth(30);//테이블의 열과폭을 조정
		JScrollPane pane = new JScrollPane(table);
		add("Center", pane);
		add("South", lblcou);
		
		setTitle("상품처리");
		setBounds(200,200,300,300);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
					int re = JOptionPane.showConfirmDialog(DbTest15CRD.this, "정말종료?","종료", JOptionPane.OK_CANCEL_OPTION);
				if(re == JOptionPane.OK_OPTION){
					try {
						if (rs != null)
							rs.close();
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (Exception e2) {
						
					}
					System.exit(0);
				}else{
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				}
					
			}
		});
		accDb();
		
		//테이블자료 클릭 시 값 얻기
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table = (JTable)e.getComponent();	//마우스클릭 대상 객체 얻기
				model = (DefaultTableModel)table.getModel();
				
//				System.out.println("행/열번호" + table.getSelectedRow() + "/" + table.getSelectedColumn());
//				System.out.println("열이름 : " + table.getColumnName(table.getSelectedColumn()));
//				System.out.println("선택값 : " + model.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
				System.out.println("선택값 : " + model.getValueAt(table.getSelectedRow(),1));//열번호고정
			}
		});
	}

	private void accDb() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e);
		}

		disData();
	}
	
	private void disData() {
		model.setNumRows(0);
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			String sql = "select * from sangdata order by code desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()){
				String[] imsi = {
					rs.getString(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4)
						
				};
				model.addRow(imsi);
				count++;
			
			}
				lblcou.setText(""+count);
		} catch (Exception e) {
			
		}finally {
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
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btninsert){
			insform ins = new insform(this);
			disData(); //추가된 자료보기
		}else if(e.getSource() == btndelete){
			
		}if(e.getSource() == btnexit){
			int re = JOptionPane.showConfirmDialog(DbTest15CRD.this, "정말종료?","종료", JOptionPane.OK_CANCEL_OPTION);
			if(re == JOptionPane.OK_OPTION){
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (Exception e2) {
					
				}
				System.exit(0);
			}else{
				setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			}
		}

	}

	//추가용 내부클래스
	class insform extends JDialog implements ActionListener{
		JTextField txtsang = new JTextField();
		JTextField txtsu = new JTextField();
		JTextField txtdan = new JTextField();
		JButton btnok = new JButton("등록");
		JButton btncan = new JButton("지움");
		
		
		public insform(Frame frame) {
			super(frame,"상품추가");
			setModal(true);
			
			JPanel pn1 = new JPanel(new GridLayout(4, 2));
			pn1.add(new JLabel("품명"));
			pn1.add(txtsang);
			pn1.add(new JLabel("수량"));
			pn1.add(txtsu);
			pn1.add(new JLabel("단가"));
			pn1.add(txtdan);
			
			pn1.add(btnok);
			pn1.add(btncan);
			
			btnok.addActionListener(this);
			btncan.addActionListener(this);
			
			add("North",new JLabel("_______자료입력_______"));
			add("Center", pn1);
			
			setBounds(220,220,150,150);
			setVisible(true);
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnok){ //상품추가
				//입력자료검사 : 자료유무,숫자입력여부(생략)
				
				//등록가능한 상태
				try {
					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
					//새상품의 코드주기
					int code = 0;
					String sql = "select max(code) from sangdata";
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					if(rs.next()){
						code = rs.getInt(1);//마지막코드를준다
					}
					//System.out.println(code);
					sql = "insert into sangdata values(?,?,?,?)";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, code+1);
					pstmt.setString(2, txtsang.getText().trim());//trim : 앞뒤공백제거
					pstmt.setString(3, txtsu.getText());
					pstmt.setString(4, txtdan.getText());
					if(pstmt.executeUpdate() > 0){
						JOptionPane.showMessageDialog(this, "등록성공");
					}else{
						JOptionPane.showMessageDialog(this, "등록실패");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(this, "등록실패");
				}
			}else{//입력취소
				txtsang.setText(null);
				txtsu.setText(null);
				txtdan.setText(null);
				
				txtsang.requestFocus();
			}
			
		}
	}
	
	public static void main(String[] args) {
		new DbTest15CRD();

	}

}
