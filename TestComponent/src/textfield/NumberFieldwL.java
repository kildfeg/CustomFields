package textfield;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import layout.ComponentLayout;

public class NumberFieldwL extends JComponent {

	private JLabel label;
	private NumberField numberField;

	public NumberFieldwL(String text, int min, int max) {
		// setLayout(new FlowLayout());
		setLayout(new ComponentLayout());

		label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(150, 20));
		label.setBorder(new LineBorder(new Color(0, 255, 0)));

		numberField = new NumberField(min, max);
		numberField.setPreferredSize(new Dimension(150, 20));
		numberField.setBorder(new LineBorder(new Color(0, 0, 255)));

		add(label);
		add(numberField);

		setBorder(new LineBorder(new Color(255, 0, 0)));
	}

	public boolean verify() {
		return numberField.verify();
	}

}
