import javax.swing.JFrame;
import javax.swing.JOptionPane;

//pop-window thread
public class Notice extends Thread {

	private String display;
	public Notice(String display) {
		this.display=display;
	}
	public void run() {
		JFrame parent = new JFrame();
		JOptionPane.showMessageDialog(parent, display);
	}
}
