package pack4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class MdiTest extends JFrame implements ActionListener {
	JMenuItem munnew,munexit;
	JInternalFrame childwin;
	JDesktopPane desk = new JDesktopPane();
	
	public MdiTest() {
		setTitle("MDI TEST");
		
		JMenuBar mbar = new JMenuBar();
		JMenu munfile = new JMenu("파일");
		munnew = new JMenuItem("새창");
		munexit = new JMenuItem("종료");
		munfile.add(munnew);
		munfile.add(munexit);
		mbar.add(munfile);
		setJMenuBar(mbar);
		
		munnew.addActionListener(this);
		munexit.addActionListener(this);
		
		getContentPane().setBackground(Color.gray);
		setBounds(200,200,300,300);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("새창")){
			getContentPane().add(desk);
			createListen();
			desk.add(childwin);
			childwin.setLocation(10, 10);
			childwin.show();
		}else if(e.getActionCommand().equals("종료")){
			System.exit(0);
		}
		
	}
	
	private void createListen(){
		childwin = new JInternalFrame("자식창:", true, true, true);
		childwin.getContentPane().setLayout(new BorderLayout());
		childwin.setSize(300, 200);
		childwin.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {
		new MdiTest();

	}

}
