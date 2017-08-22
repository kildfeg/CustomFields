package field;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NumberField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int minValue;
	private int maxValue;

	private String previousValue = null;
	private boolean listenerActive = true;

	private NumberFieldwL parent;

	/**
	 * 
	 * @param minLentgh
	 * @param maxLentgh
	 */
	public NumberField(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;

		addListener();

	}

	private void addListener() {
		getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				updateField();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (!listenerActive) {
					return;
				}
				String text = getText();

				if (text != null && text.length() == 1 && text.equals("-") && minValue < 0) {
					previousValue = text;
					return;
				}
				try {
					int val = Integer.parseInt(text);
					if ((val >= 0 && val <= maxValue) || (val < 0 && val >= minValue)) {
						previousValue = text;
						updateField();
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
						updateField();
					}
				});

			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {

			}
		});

	}

	public boolean verify() {
		return updateField();
	}

	private boolean updateField() {

		boolean val = false;

		try {
			val = Integer.parseInt(getText()) >= minValue && Integer.parseInt(getText()) <= maxValue;
		} catch (NumberFormatException e) {
		}
		if (val) {
			setBorder(UIManager.getBorder("TextField.border"));
			parent.setWarnVisible(false);
		} else {
			setBorder(new LineBorder(Color.red, 1));
			parent.setWarnVisible(true);
		}
		return val;
	}

	public void setParent(NumberFieldwL parent) {
		this.parent = parent;

	}

	public int getValue() {
		int val = 0;
		try {
			val = Integer.parseInt(getText());
		} catch (NumberFormatException e) {

		}
		return val;
	}
}
