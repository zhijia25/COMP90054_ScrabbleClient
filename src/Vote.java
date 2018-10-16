import java.rmi.ConnectException;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

// vote thread
public class Vote extends Thread {

	private ServerInterface server;
	private RMIInterface remote;
	private Hostname myName;
	private String vo;
	
	public Vote(ServerInterface server, RMIInterface remote, Hostname myName, String vo) {
		this.server=server;
		this.remote=remote;
		this.myName=myName;
		this.vo=vo;
	}
	public void run() {
		try {
			server.vote(remote, myName.gethostname(), vo);
		}
		catch(NullPointerException e) {
		}
		catch(ConnectException e3) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Server is offline!");			
			System.exit(0);
		} 
		catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
