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

@SuppressWarnings("serial")
public class LimittedTextField extends JTextField {

	private int min;
	private int max;

	private String previousValue = null;
	private boolean listenerActive = true;
	private boolean isvalid = false;
	JLabel label;

	private boolean notinit = false;

	/**
	 * 
	 * @param minLentgh
	 * @param maxLentgh
	 */
	public LimittedTextField(int minLentgh, int maxLentgh) {
		this.min = minLentgh;
		this.max = maxLentgh;

		addListener();
		label = new JLabel("Deneme");
		label.setForeground(new Color(255, 0, 0));
		label.setText("En az " + min + " en fazla " + max + " karakter içermelidir.");
	}

	private void addListener() {
		getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (!listenerActive) {
					return;
				}

				String text = getText();
				if (text.length() >= min) {
					previousValue = text;
					isvalid = true;
					return;
				}

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						listenerActive = false;
						setText(previousValue);
						listenerActive = true;
						UIManager.getLookAndFeel().provideErrorFeedback(LimittedTextField.this);
					}
				});

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (!listenerActive) {
					return;
				}
				String text = getText();
				if (text.length() <= max) {
					previousValue = text;
					isvalid = true;
					return;
				}

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						listenerActive = false;
						setText(previousValue);
						listenerActive = true;
						UIManager.getLookAndFeel().provideErrorFeedback(LimittedTextField.this);
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
		boolean val = getText().length() >= min && getText().length() <= max;

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
