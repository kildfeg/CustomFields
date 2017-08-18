package textfield;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class NumberFieldwL extends JComponent {

	private JLabel label;
	private NumberField numberField;

	public NumberFieldwL(String text, int min, int max) {
		setLayout(new FlowLayout());
		label = new JLabel(text);
		numberField = new NumberField(min, max);
		numberField.setPreferredSize(new Dimension(200, 20));
		add(label);
		add(numberField);
	}

	public boolean verify() {
		return numberField.verify();
	}
}
