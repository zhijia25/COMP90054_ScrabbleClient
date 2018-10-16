

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

//remote interface of client
public interface RMIInterface extends Remote {


	//get the available player in waiting list
	public void sendMessage(String message) throws RemoteException;

	//tell the user that he is invited
	public int invite(String player) throws RemoteException;

	//get the invite result
	public void inviteResult(int result, String name) throws RemoteException;

	//get the input 
	public void input(char[][] table, int round, String name, String names, String score) throws RemoteException;

	//renew the turn
	public void nextTurn(char[][] table, int round, String name, String names, String score) throws RemoteException;

	//get the choices of highlight
	public void twoChoices(String string, String string2) throws RemoteException;

	//get the vote information
	public int vote(String word) throws RemoteException;

	//get the vote result
	public void voteResult(int result) throws RemoteException;
	
	//end the game, close gameGUI
	public void gameOver(String winner) throws RemoteException;
	
	//deal with wrong-turn input
	public void inputError() throws RemoteException;
	
	//use for testing the connection
	public void test() throws RemoteException;

}