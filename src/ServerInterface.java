
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

public interface ServerInterface extends Remote {

	public int checklog(RMIInterface client, String name) throws RemoteException;

	public String getUser(RMIInterface client, String name) throws RemoteException;

	public int getStatus(RMIInterface client, String name) throws RemoteException;

	public void invite(RMIInterface client, String name) throws RemoteException;

	public  void input(RMIInterface client, String name, int i, int j, char c) throws RemoteException;

	public void skip(RMIInterface client, String name) throws RemoteException;

	public void vote(RMIInterface client, String name, String word) throws RemoteException;

	public void close(RMIInterface client, String name) throws RemoteException;

	public void skipInput(RMIInterface client, String name) throws RemoteException;

	public void gameClose(RMIInterface client, String name) throws RemoteException;

	public void timeoutInvite(RMIInterface client) throws RemoteException;
}
