package textfield;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Test extends JPanel {

	protected LimittedTextField textField;
	NumberField numberField;
	protected JTextArea textArea;
	JButton button;

	public Test() {
		super(null);

		textField = new LimittedTextField(3, 10);
		textField.setBounds(30, 30, 250, 20);
		add(textField);

		numberField = new NumberField(3, 999);
		numberField.setBounds(30, 80, 250, 20);
		add(numberField);

		button = new JButton("Doğrula");
		button.setBounds(30, 110, 250, 50);
		add(button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textField.isValid();
				numberField.isValid();

			}
		});

	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("TextDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add contents to the window.
		frame.add(new Test());
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
