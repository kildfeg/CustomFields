package textfield;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NumberField extends JTextField {

	private int min;
	private int max;

	private String previousValue = null;
	private boolean listenerActive = true;
	private Popup popup;
	private PopupFactory popupFactory;
	private JLabel label;

	/**
	 * 
	 * @param minLentgh
	 * @param maxLentgh
	 */
	public NumberField(int minValue, int maxValue) {
		this.min = minValue;
		this.max = maxValue;

		addListener();

		popupFactory = PopupFactory.getSharedInstance();
		label = new JLabel("En az " + min + " en fazla " + max + " olmalıdır.");
		label.setForeground(new Color(255, 0, 0));
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
						return;
					} else {
						previousValue = text;
						return;
					}
				} catch (NumberFormatException e) {
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

	public boolean verify() {
		validateBorder();
		return verifyValue();
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

	}

	private boolean verifyValue() {
		boolean val = false;

		try {
			val = Integer.parseInt(getText()) >= min && Integer.parseInt(getText()) <= max;

		} catch (NumberFormatException e) {
			val = false;
		}
		Point p = getLocationOnScreen();
		if (val) {
			setBorder(UIManager.getBorder("TextField.border"));
		} else {
			setBorder(new LineBorder(Color.red, 1));
			if (popup == null) {
				popup = popupFactory.getPopup(this, label, p.x + 20, p.y);
				popup.show();
				ActionListener hider = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (popup != null) {
							popup.hide();
							popup = null;
						}
					}
				};
				Timer timer = new Timer(2000, hider);
				timer.start();
			}
		}

		return val;
	}
}
