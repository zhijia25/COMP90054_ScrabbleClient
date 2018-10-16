

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;

//waiting GUI
public class WaitingGUI extends JFrame {

	private static final long serialVersionUID = 7248281137326966933L;
	private JPanel contentPane;
	private JList<String> playerlist;
	private DefaultListModel<String> listModel;
	private JLabel lblUsername;
	private static Hostname myName;
	private Invite invite;
	private static final String regex1 = "(\\[in game\\])?";

	

	public WaitingGUI(ServerInterface server, RMIInterface client,
			Hostname myName, GameGUI gameGUI) {
		
		this.myName = myName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 330);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setBackground(Color.GRAY);
		separator.setBounds(6, 34, 745, 12);
		contentPane.add(separator);

		JLabel lblPlayer = new JLabel("Player List");
		lblPlayer.setBounds(6, 53, 427, 16);
		contentPane.add(lblPlayer);

		JButton btnInvite = new JButton("Invite");
		btnInvite.setBounds(316, 254, 117, 29);
		contentPane.add(btnInvite);
		btnInvite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String str = myName.gethostname() + ",";
				for (int i = 0; i < playerlist.getSelectedValuesList().size(); i++) {
					str = str + playerlist.getSelectedValuesList().get(i) + ",";
				}
				invite=new Invite(server, client, str,gameGUI);			
				invite.start( );

			}
		});
		

		listModel = new DefaultListModel<>();
		playerlist = new JList<>(listModel);
		playerlist.setBounds(6, 81, 427, 126);
		JScrollPane scrollpane = new JScrollPane(playerlist);
		scrollpane.setBounds(6, 81, 427, 151);
		contentPane.add(scrollpane);
		
		lblUsername = new JLabel("");
		lblUsername.setBounds(29, 15, 427, 15);
		contentPane.add(lblUsername);
		
	}


	//set the display of the username 
	public void setUserName(String user) {
	 lblUsername.setText("Username: "+user+" [online]");
	}
	
	//set the display of available player list
	public void setList(String message) {
		
		listModel=new DefaultListModel<>();
	
		String[] username = message.split(",");		
		int i = 0;	
			while (i < username.length) {
				if (!username[i].matches(myName.gethostname() + regex1)) {
					listModel.addElement(username[i]);
				}
				i++;
			}
			playerlist.setModel(listModel);
	playerlist.paintImmediately(getBounds());
	}
	
}