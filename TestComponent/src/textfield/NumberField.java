package textfield;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NumberField extends JTextField {

	private int min;
	private int max;

	private String previousValue = null;
	private boolean listenerActive = true;
	private boolean isvalid = false;
	JLabel label;

	/**
	 * 
	 * @param minLentgh
	 * @param maxLentgh
	 */
	public NumberField(int minValue, int maxValue) {
		this.min = minValue;
		this.max = maxValue;

		addListener();
		label = new JLabel("Deneme");
		label.setForeground(new Color(255, 0, 0));
		label.setText("En az " + min + " en fazla " + max + " olmalıdır.");
	}

	private void addListener() {
		getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				String text = getText();

				try {
					int val = Integer.parseInt(text);
					if (val >= min) {
						previousValue = text;
						isvalid = true;
						validateBorder();
						return;
					} else {
						previousValue = text;
						isvalid = false;
						validateBorder();
						return;
					}
				} catch (NumberFormatException e) {
					isvalid = false;
				}

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (!listenerActive) {
					return;
				}
				String text = getText();

				try {
					int val = Integer.parseInt(text);
					if (val <= max) {
						previousValue = text;
						isvalid = true;
						validateBorder();
						return;
					}
				} catch (NumberFormatException e) {

				}
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {

						listenerActive = false;
						setText(previousValue);
						listenerActive = true;
						UIManager.getLookAndFeel().provideErrorFeedback(NumberField.this);
						validateBorder();
					}
				});

			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {

			}
		});

	}

	@Override
	public boolean isValid() {
		if (!super.isValid()) {
			return false;
		}
		validateBorder();
		return isvalid;

	}

	private void validateBorder() {
		boolean val = false;

		try {
			val = Integer.parseInt(getText()) >= min && Integer.parseInt(getText()) <= max;

		} catch (NumberFormatException e) {
			val = false;
		}

		if (val) {
			setBorder(UIManager.getBorder("TextField.border"));
		} else {
			setBorder(new LineBorder(Color.red, 1));
		}

		if (label != null) {
			Rectangle rectangle = getBounds();
			label.setBounds(rectangle.x, rectangle.y - 20, rectangle.width, 20);
			label.setVisible(!val);
			getParent().add(label);
			getParent().repaint();
		}
	}
}
