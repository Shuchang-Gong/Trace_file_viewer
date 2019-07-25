import javax.swing.SwingUtilities;

public class assign2 implements Runnable {

	public void run() {
		FileDisplayer fd = new FileDisplayer();
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new assign2());
	}

}