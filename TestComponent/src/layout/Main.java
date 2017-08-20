package layout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import textfield.LimittedTextFieldwL;
import textfield.NumberFieldwL;

public class Main extends JPanel {

	public Main() {
		super(new FormLayout());
		add(new NumberFieldwL("textx", 10, 25));
		add(new NumberFieldwL("textx", 10, 25));
		add(new NumberFieldwL("textx", 10, 25));
		add(new LimittedTextFieldwL("textx", 10, 25));
		add(new LimittedTextFieldwL("textx", 10, 25));
		add(new JLabel("Deneme"));
		add(new JLabel("Deneme2"));
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("TextDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add contents to the window.
		frame.add(new Main());
		frame.setSize(400, 200);
		// Display the window.
		// frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
