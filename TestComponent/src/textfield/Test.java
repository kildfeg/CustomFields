package textfield;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Test extends JPanel {

	protected LimittedTextField textField;
	NumberField numberField;
	protected JTextArea textArea;
	JButton button;
	LimittedTextFieldwL fieldwL;

	NumberFieldwL numberFieldwL;

	public Test() {
		super(new GridLayout(5, 1, 5, 5));

		textField = new LimittedTextField(3, 10);
		textField.setToolTipText("dfdgsdf");
		// textField.setBounds(30, 30, 250, 20);
		add(textField);

		numberField = new NumberField(3, 999);
		// numberField.setBounds(30, 80, 250, 20);
		add(numberField);

		fieldwL = new LimittedTextFieldwL("Test Label", 4, 12);
		add(fieldwL);

		numberFieldwL = new NumberFieldwL("Deneme Text", 0, 12);
		add(numberFieldwL);

		button = new JButton("Doğrula");
		// button.setBounds(30, 110, 250, 50);
		add(button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (textField.verify() && numberField.verify() && fieldwL.verify() && numberFieldwL.verify()) {
					JOptionPane.showMessageDialog(Test.this, "Tüm Alanlar doğrulandı.");
				} else {
					JOptionPane.showMessageDialog(Test.this, "Tüm Alanlar doğrulanaMAdı!!!!!!.");
				}

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
