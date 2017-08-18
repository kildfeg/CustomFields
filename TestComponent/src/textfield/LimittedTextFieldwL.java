package textfield;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class LimittedTextFieldwL extends JComponent {

	private JLabel label;
	private LimittedTextField limittedTextField;

	public LimittedTextFieldwL(String text, int min, int max) {
		setLayout(new FlowLayout());
		label = new JLabel(text);
		limittedTextField = new LimittedTextField(min, max);
		limittedTextField.setPreferredSize(new Dimension(200, 20));
		add(label);
		add(limittedTextField);
	}

	public boolean verify() {
		return limittedTextField.verify();
	}
}
