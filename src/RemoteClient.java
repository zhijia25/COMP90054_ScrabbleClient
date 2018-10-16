
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//remote class
public class RemoteClient extends UnicastRemoteObject implements RMIInterface {

	private GameGUI gameGUI;
	private Hostname myName;
	private static final long serialVersionUID = 7248281137326966933L;
	private ServerInterface server = null;
	private LoginGUI welcome;
	private WaitingGUI waiting;
	private RemoteClient remote;

	//remote constructor
	protected RemoteClient(Hostname myName, String ip, int port) throws RemoteException {
		this.myName = myName;
		remote = this;
		try {
			Registry registry = LocateRegistry.getRegistry(ip, port);
			server = (ServerInterface) registry.lookup("Scrabble");
		}

		catch (UnknownHostException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Illegal argument!");
			System.exit(0);
		}

		catch (IllegalArgumentException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Illegal argument!");
			System.exit(0);
		}

		catch (ConnectIOException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Illegal argument!");
			System.exit(0);
		} catch (ConnectException e) {
			System.out.println("server logout");
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Sorry, wrong server information or server is offline!");
			System.exit(0);
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gameGUI = new GameGUI(server, remote, myName);
					gameGUI.setVisible(false);
					gameGUI.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							try {
								gameGUI.setVisible(false);
								server.gameClose(remote, myName.gethostname());

							} catch (ConnectException e3) {
								System.out.println("server offline");
								System.exit(0);
							} catch (IOException e1) {
								e1.printStackTrace();
								System.exit(0);
							}
							super.windowClosing(e);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					waiting = new WaitingGUI(server, remote, myName, gameGUI);
					waiting.setVisible(false);
					waiting.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							try {
								waiting.setVisible(false);
								server.gameClose(remote, myName.gethostname());
								server.close(remote, myName.gethostname());

							} catch (ConnectException e3) {

								System.out.println("server offline");
								System.exit(0);
							} catch (IOException e1) {
								e1.printStackTrace();
								System.exit(0);
							}
							super.windowClosing(e);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		});

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					welcome = new LoginGUI(waiting, server, remote, myName);
					welcome.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public void sendMessage(String message) throws RemoteException {

		waiting.setList(message);
	}


	public int invite(String players) throws RemoteException {
		int result = JOptionPane.showConfirmDialog(null, "Do you want to play with " + players, "Invite",
				JOptionPane.YES_NO_OPTION);
		if (result == 0) {
			return 1;
		} else {
			return 2;
		}

	}

	public void inviteResult(int result, String names) throws RemoteException {

		if (result == 1) {
			gameGUI.setUser(myName.gethostname());
			gameGUI.setVisible(true);
		} else if (result == 2) {
			String display = names + " don't want to play the game!";
			Notice notice = new Notice(display);
			notice.start();
		} else if (result == 0) {
			String display = "Someone is playing the game, so you cannot invite!";
			Notice notice = new Notice(display);
			notice.start();
		}else if(result==3) {
			String display = "Too many players!";
			Notice notice = new Notice(display);
			notice.start();
		}
		
	}

	
	public void input(char[][] table, int round, String name, String names, String score) {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {

				if (table[i][j] != '0') {
					gameGUI.setTable(table[i][j], i, j);
					gameGUI.setEnabled(i, j);
				} else {
					gameGUI.setTable(table[i][j], i, j);
				}
			}
		}
		String[] nameArray = names.split(",");
		String[] scoreArray = score.split(",");

		String display = "";

		for (int m = 0; m < nameArray.length; m++) {
			int nameLength = nameArray[m].length();
			int scoreLength = scoreArray[m].length();
			int space = 33 - nameLength - scoreLength;
			String padding = "";

			for (int q = 0; q < space; q++) {
				padding = padding + " ";
			}
			display = display + nameArray[m] + padding + scoreArray[m] + "\n";
		}
		gameGUI.setStatus(display);
		gameGUI.setTurn(name);
		String rd = Integer.toString(round);
		gameGUI.setRound(rd);
	}

	public void nextTurn(char[][] table, int round, String name, String names, String score) {

		System.out.println(name);
		if( myName.gethostname().equals(name)){
			String display="Your turn to play!";
			Notice notice=new Notice(display);
			notice.start();
		}
		
		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (table[i][j] != '0') {
					gameGUI.setTable(table[i][j], i, j);
					gameGUI.setEnabled(i, j);
				} else {
					gameGUI.setTable(table[i][j], i, j);
				}
			}
		}
		String[] nameArray = names.split(",");
		String[] scoreArray = score.split(",");
		String display = "";

		for (int m = 0; m < nameArray.length; m++) {
			int nameLength = nameArray[m].length();
			int scoreLength = scoreArray[m].length();
			int space = 33 - nameLength - scoreLength;
			String padding = "";

			for (int q = 0; q < space; q++) {
				padding = padding + " ";
			}
			display = display + nameArray[m] + padding + scoreArray[m] + "\n";
		}
		gameGUI.setStatus(display);
		gameGUI.setTurn(name);
		String rd = Integer.toString(round);
		gameGUI.setRound(rd);
	}

	public void twoChoices(String string, String string2) throws RemoteException {
		gameGUI.setOption1(string);
		gameGUI.setOption2(string2);
	}

	public int vote(String word) throws RemoteException {
		int result = JOptionPane.showConfirmDialog(null, "DO you think " + word + " is acceptable?",
				"Propose identification", JOptionPane.YES_NO_OPTION);
		System.out.print(result);
		if (result == 0) {
			return 1;
		} else {
			return 2;
		}

	}

	public void voteResult(int result) throws RemoteException {
		if (result == 1) {
			String display = "The choice has been accepted!";
			Notice notice = new Notice(display);
			notice.start();
			gameGUI.reSetInput();
			gameGUI.setOption1("");
			gameGUI.setOption2("");
		}
		if (result == 2) {

			String display = "The choiceis not acceptable!";
			Notice notice = new Notice(display);
			notice.start();
			gameGUI.reSetInput();
			gameGUI.setOption1("");
			gameGUI.setOption2("");
		}
	}

	public ServerInterface getServer() {
		return server;
	}

	public void gameOver(String winner) throws RemoteException {
		String display = "Game over, the winner is\n" + winner;
		Notice notice = new Notice(display);
		notice.start();
		gameGUI.resetTable();
		gameGUI.setVisible(false);
		;
	}

	public void inputError() throws RemoteException {

		String display = "Sorry, not your turn!";
		Notice notice = new Notice(display);
		notice.start();
	}


	@Override
	public void test() throws RemoteException {
		
	}

}
