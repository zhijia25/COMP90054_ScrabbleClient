
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.SocketException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Client {

	private static Hostname myName;

	
	public static void main(String[] args) {

		try {
			String ip;
			int port;
			ip = args[0];
			port = Integer.parseInt(args[1]);
			myName = new Hostname();

			//create remote object
			RMIInterface remote = new RemoteClient(myName, ip, port);

		}
		
		
		catch (NumberFormatException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Illegal argument!");
			System.exit(0);
		} 
		catch (ArrayIndexOutOfBoundsException e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Illegal argument!");
			System.exit(0);
		}

		
		catch (Exception e) {
			JFrame parent = new JFrame();
			JOptionPane.showMessageDialog(parent, "Unknown error!");
			e.printStackTrace();
			System.exit(0);
		}

	}
}
