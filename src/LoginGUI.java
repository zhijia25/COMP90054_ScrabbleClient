
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Random;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 7248281137326966933L;
	private JPanel contentPane;
	private ArrayList<String> users;
	

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	//login GUI
	public LoginGUI(WaitingGUI wait, ServerInterface server, RMIInterface remote,
			Hostname myName) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 207);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWelcomeTo = new JLabel("Welcome to the Scrabble Game !");
		lblWelcomeTo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblWelcomeTo.setBounds(89, 10, 379, 38);
		contentPane.add(lblWelcomeTo);

		JLabel lblUsername = new JLabel("username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(10, 72, 86, 45);
		contentPane.add(lblUsername);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(114, 84, 252, 21);
		contentPane.add(textPane);

		JButton log = new JButton("Log In");
		log.setFont(new Font("Tahoma", Font.BOLD, 14));
		log.setBounds(48, 127, 112, 33);
		contentPane.add(log);
		log.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String name = textPane.getText();
					myName.sethostname(name);
					int result = server.checklog(remote, name);
						if (result == 1) {
							String display="Successfully log in !";
							//create pop-window thread
							Notice notice=new Notice(display);
							notice.start();
							wait.setUserName(name);
							wait.setVisible(true);
							dispose();
						} else if(result==2) {
							JFrame parent = new JFrame();
							JOptionPane.showMessageDialog(parent,
									"Sorry, this name has been registered, please change another one");
							textPane.setText("");
						}else {
							JFrame parent = new JFrame();
							JOptionPane.showMessageDialog(parent,
									"Sorry, you are only allowed to make name with letter and number at most 8 characters");
							textPane.setText("");
						}
						
					
				}catch(ConnectException e3) {
					JFrame parent = new JFrame();
					JOptionPane.showMessageDialog(parent, "Server is offline!");			
					System.exit(0);
				}  catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		JButton enter = new JButton("Enter");
		enter.setFont(new Font("Tahoma", Font.BOLD, 14));
		enter.setBounds(254, 128, 112, 32);
		contentPane.add(enter);
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					String name = getRandomString();
					JFrame parent = new JFrame();
					JOptionPane.showMessageDialog(parent, "your name is " + name);
					myName.sethostname(name);
					int result = server.checklog(remote, name);
					
					if (result == 1) {
						JFrame parent1 = new JFrame();

						JOptionPane.showMessageDialog(parent1, "Successfully log in !");

						dispose();
						wait.setUserName(name);
						wait.setVisible(true);
					} else {
						JFrame parent2 = new JFrame();
						JOptionPane.showMessageDialog(parent2,
								"Sorry, this name has been registered, please change another one");

					}
				}catch(ConnectException e3) {
					JFrame parent = new JFrame();
					JOptionPane.showMessageDialog(parent, "Server is offline!");			
					System.exit(0);
				}  
				catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	//create random name with 8 characters
	public String getRandomString() {
		String source = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; ++i) {
			int number = random.nextInt(62);
			sb.append(source.charAt(number));
		}
		return sb.toString();
	}

}
