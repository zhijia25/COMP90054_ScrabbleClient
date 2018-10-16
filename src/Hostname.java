

//class to store the user himself
public class Hostname {
	private static String hostname;

	Hostname() {
		hostname = "";
	}

	public void sethostname(String hn) {
		hostname = hn;
	}

	public String gethostname() {
		return hostname;
	}

}
