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

@SuppressWarnings("serial")
public class LimittedTextField extends JTextField {

	private int min;
	private int max;

	private String previousValue = null;
	private boolean listenerActive = true;

	private Popup popup;
	private PopupFactory popupFactory;
	JLabel jLabel;

	/**
	 * 
	 * @param minLentgh
	 * @param maxLentgh
	 */
	public LimittedTextField(int minLentgh, int maxLentgh) {
		this.min = minLentgh;
		this.max = maxLentgh;

		addListener();

		popupFactory = PopupFactory.getSharedInstance();
		jLabel = new JLabel("En az " + min + " en fazla " + max + " karakter içermelidir.");
		jLabel.setForeground(new Color(255, 0, 0));

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
					validateBorder();
					return;
				}

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						listenerActive = false;
						setText(previousValue);
						listenerActive = true;
						UIManager.getLookAndFeel().provideErrorFeedback(LimittedTextField.this);
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
		boolean val = getText().length() >= min && getText().length() <= max;

		if (val) {
			setBorder(UIManager.getBorder("TextField.border"));
		} else {
			setBorder(new LineBorder(Color.red, 1));
		}
	}

	private boolean verifyValue() {
		boolean val = getText().length() >= min && getText().length() <= max;
		Point p = getLocationOnScreen();

		if (val) {
			setBorder(UIManager.getBorder("TextField.border"));
			if (popup != null) {
				popup.hide();
				popup = null;
			}
		} else {
			setBorder(new LineBorder(Color.red, 1));
			if (popup == null) {
				popup = popupFactory.getPopup(this, jLabel, p.x + 20, p.y);
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
				Timer timer = new Timer(20000, hider);
				timer.start();
			}
		}

		return val;
	}

}
