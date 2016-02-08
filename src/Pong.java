
public class Pong {

	public static void main(String[] args) {
		PongGUI gui = new PongGUI(960, 720);
		gui.createAndShowGUI();
		gui.run();
		System.out.println("Done");
	}

}
// end class