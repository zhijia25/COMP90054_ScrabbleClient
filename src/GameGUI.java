
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.awt.Color;
import javax.swing.JTextArea;

public class GameGUI extends JFrame {

	private static final long serialVersionUID = 7248281137326966933L;
	private JPanel contentPane;
	private JTextArea playerStatus;
	private JTextPane option1;
	private JTextPane option2;
	private ArrayList<ArrayList<JButton>> table;
	private ArrayList<JButton> subTable;
	private JLabel lblUsername;
	private JTextPane input;
	private JTextPane turn;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	
	//gameGUI
	public GameGUI(ServerInterface server, RMIInterface remote, Hostname myName) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1147, 769);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		input = new JTextPane();
		input.setBounds(7, 412, 128, 93);
		contentPane.add(input);

		playerStatus = new JTextArea();
		playerStatus.setFont(new Font("Monospaced", Font.BOLD, 16));
		playerStatus.setEditable(false);
		playerStatus.setBounds(779, 98, 307, 602);
		contentPane.add(playerStatus);
		playerStatus.setColumns(10);
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(779, 98, 307, 623);
		this.getContentPane().add(scrollPane1);
		scrollPane1.setViewportView(playerStatus);
		playerStatus.setLineWrap(true);

		

		option1 = new JTextPane();
		option1.setEditable(false);
		option1.setBounds(41, 610, 350, 40);
		contentPane.add(option1);

		option2 = new JTextPane();
		option2.setEditable(false);
		option2.setBounds(41, 660, 350, 40);
		contentPane.add(option2);

		JLabel lblPossibleOutput = new JLabel("Possible output(enter the corresponding button to highlight)");
		lblPossibleOutput.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPossibleOutput.setBounds(29, 568, 515, 43);
		contentPane.add(lblPossibleOutput);

		JButton btnNo_1 = new JButton("No. 1");
		btnNo_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNo_1.setBounds(435, 610, 97, 40);
		contentPane.add(btnNo_1);
		btnNo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String vo = option1.getText();
				if (vo.equals("")) {
					JFrame parent = new JFrame();
					JOptionPane.showMessageDialog(parent, "please enter one letter !");
				} else {

					int result = JOptionPane.showConfirmDialog(null, "Are you sure to highlight  " + vo + "?",
							"Skip Notification", JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						Vote vt = new Vote(server, remote, myName, vo);
						vt.start();

					}

				}

			}
		});
		JButton btnNo_2 = new JButton("No. 2");
		btnNo_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNo_2.setBounds(435, 660, 97, 40);
		contentPane.add(btnNo_2);
		btnNo_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String vo = option2.getText();
				if (vo.equals("")) {
					JFrame parent = new JFrame();
					JOptionPane.showMessageDialog(parent, "please enter one letter !");
				} else {
					int result = JOptionPane.showConfirmDialog(null, "Are you sure to highlight  " + vo + "?",
							"Skip Notification", JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						Vote vt = new Vote(server, remote, myName, vo);
						vt.start();

					}
				}

			}
		});
		
		JButton skipInput = new JButton("skip");
		skipInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		skipInput.setBounds(10, 515, 97, 43);
		contentPane.add(skipInput);
		skipInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					int result = JOptionPane.showConfirmDialog(null, "Are you sure to skip? ", "Skip Notification",
							JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						try {
						
							input.setText("");
							server.skipInput(remote, myName.gethostname());
						} catch (ConnectException e3) {
							JFrame parent = new JFrame();
							JOptionPane.showMessageDialog(parent, "Server is offline!");
							System.exit(0);
						}

					}

				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
		});

		JButton skipHighlight = new JButton("skip submit");
		skipHighlight.setFont(new Font("Tahoma", Font.BOLD, 12));
		skipHighlight.setBounds(603, 610, 109, 93);
		contentPane.add(skipHighlight);
		skipHighlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					String vo1 = option1.getText();
					String vo2 = option2.getText();
					if (!vo1.equals("") && !vo2.equals("")) {
						int result = JOptionPane.showConfirmDialog(null, "Are you sure to skip? ", "Skip Notification",
								JOptionPane.YES_NO_OPTION);
						if (result == 0) {
							try {
								option1.setText("");
								option2.setText("");
								server.skip(remote, myName.gethostname());
							} catch (ConnectException e3) {
								JFrame parent = new JFrame();
								JOptionPane.showMessageDialog(parent, "Server is offline!");
								System.exit(0);
							}
						}
					}

					else {
						JFrame parent = new JFrame();
						JOptionPane.showMessageDialog(parent, "please enter one letter !");
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
		});

		JLabel lblScrabbleGame = new JLabel("Scrabble Game");
		lblScrabbleGame.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblScrabbleGame.setBounds(11, -31, 189, 104);
		contentPane.add(lblScrabbleGame);

		JLabel lblPlayerStatus = new JLabel("Player Status");
		lblPlayerStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPlayerStatus.setBounds(760, 50, 134, 38);
		contentPane.add(lblPlayerStatus);

		JLabel lblInputonlyOneWord = new JLabel("Input(only one word)");
		lblInputonlyOneWord.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblInputonlyOneWord.setBounds(0, 366, 200, 63);
		contentPane.add(lblInputonlyOneWord);

		lblUsername = new JLabel("");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUsername.setBounds(286, 0, 350, 56);
		contentPane.add(lblUsername);

		turn = new JTextPane();
		turn.setEditable(false);
		turn.setBounds(7, 86, 128, 56);
		contentPane.add(turn);

		JLabel lblsTrun = new JLabel("Trun:");
		lblsTrun.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblsTrun.setBounds(0, 54, 97, 31);
		contentPane.add(lblsTrun);

		JLabel lblRound = new JLabel("Round:");
		lblRound.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRound.setBounds(0, 229, 58, 15);
		contentPane.add(lblRound);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(7, 254, 128, 67);
		contentPane.add(textPane);

		//create the game table
		table = new ArrayList<ArrayList<JButton>>(20);
		for (int i = 0; i < 20; i++) {
			subTable = new ArrayList<JButton>(20);
			for (int j = 0; j < 20; j++) {
				JButton Bu = new JButton("");
				Bu.setFont(new Font("Tahoma", Font.BOLD, 16));
				Bu.setBounds(230 + 25 * i, 50 + 25 * j, 25, 25);
				Bu.setBorder(BorderFactory.createEtchedBorder());
				contentPane.add(Bu);
				subTable.add(j, Bu);

				int column = i;
				int row = j;
				Bu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						String in = input.getText();
						if (in.matches("[A-Za-z]") && input.getText().length() == 1) {

							String inUp=in.toUpperCase();
							char input = inUp.charAt(0);
							//create a input-sending thread
							Input inp = new Input(server, remote, myName, column, row, input);
							inp.start();

						} else {
							JFrame parent = new JFrame();
							JOptionPane.showMessageDialog(parent, "please enter one letter !");
							reSetInput();
						}
					}
				});

			}
			table.add(subTable);
		}
	}

	//set the available highlight option
	public void setOption1(String string) {
		option1.setText(string);
	}

	//set the available highlight option
	public void setOption2(String string) {
		option2.setText(string);
	}

	//print the input on the game table
	public void setTable(char input, int i, int j) {
		if (String.valueOf(input).equals("0")) {
			table.get(i).get(j).setText("");
		} else {
			table.get(i).get(j).setText(String.valueOf(input));
		}

	}

	//set the game table enable/not enable when inputting
	public void setEnabled(int i, int j) {
		table.get(i).get(j).setEnabled(false);
	}

	//set the player statistics
	public void setStatus(String str) {
		playerStatus.setText(str);
	}

	//set the username in Game GUI
	public void setUser(String user) {
		lblUsername.setText("Player Name: " + user);
	}

	//clear the input area
	public void reSetInput() {
		input.setText("");
	}

	//set the display of the trun
	public void setTurn(String str) {
		turn.setText(str);
	}

	//set the display of round
	public void setRound(String str) {
		textPane.setText(str);
	}

	//reset the game table
	public void resetTable() {
		for (int i = 0; i < table.size(); i++) {
			for (int j = 0; j < subTable.size(); j++) {
				table.get(i).get(j).setEnabled(true);
				table.get(i).get(j).setText("");
			}
		}
	}

}
