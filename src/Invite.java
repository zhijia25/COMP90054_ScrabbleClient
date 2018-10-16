import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Invite extends Thread {

	private ServerInterface server;
	private RMIInterface client;
	private String str;
	private Timer timer;

	private GameGUI gameGUI;

//invite thread
	public Invite(ServerInterface server, RMIInterface client,String str, GameGUI gameGUI) {
	
		this.server=server;
		this.client=client;
		this.gameGUI=gameGUI;
		this.str =str;
		timer = new Timer();		

	}

	public void run() {
		
		try {
			server.invite(client, str);
		} catch(ConnectException e3) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Server is offline!");			
			System.exit(0);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}		
	}

	public void setInvite(String nameList) {
		str=nameList;
	}
	

}
