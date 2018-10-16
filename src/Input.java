import java.rmi.ConnectException;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Input extends Thread {

	private ServerInterface server; 
	private RMIInterface remote; 
	private Hostname myName;
	private int column;
	private int row;
	private char input;
	
	//the thread to send input to server
	public Input(ServerInterface server, RMIInterface remote, Hostname myName, int column,int row, char input) {
		this.server=server;
		this.remote=remote;
		this.myName=myName;
		this.column=column;
		this.row=row;
		this.input=input;
	}
	public void run() {
		try {			
			server.input(remote, myName.gethostname(), column, row, input);
			
		} catch(ConnectException e3) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Server is offline!");			
			System.exit(0);
		}  catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
