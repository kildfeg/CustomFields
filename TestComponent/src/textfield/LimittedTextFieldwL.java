package textfield;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import layout.ComponentLayout;

public class LimittedTextFieldwL extends JComponent {

	private JLabel label;
	private LimittedTextField limittedTextField;

	public LimittedTextFieldwL(String text, int min, int max) {
		// setLayout(new FlowLayout());
		setLayout(new ComponentLayout());
		label = new JLabel(text);
		limittedTextField = new LimittedTextField(min, max);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(150, 20));
		limittedTextField.setPreferredSize(new Dimension(200, 20));
		add(label);
		add(limittedTextField);
	}

	public boolean verify() {
		return limittedTextField.verify();
	}
}
