package field;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class LimittedTextArea extends JTextArea {

	private int min;
	private int max;

	private String previousValue = null;
	private boolean listenerActive = true;
	private LimittedTextAreawL parent;

	/**
	 * 
	 * @param minLentgh
	 * @param maxLentgh
	 */
	public LimittedTextArea(int minLentgh, int maxLentgh) {
		this.min = minLentgh;
		this.max = maxLentgh;

		addListener();

	}

	private void addListener() {
		getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (!listenerActive) {
					return;
				}
				String text = getText();
				if (text.length() <= max) {
					previousValue = text;
					updateField();
					return;
				}

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						listenerActive = false;
						setText(previousValue);
						listenerActive = true;
						UIManager.getLookAndFeel().provideErrorFeedback(LimittedTextArea.this);
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
		boolean val = getText().length() >= min && getText().length() <= max;
		if (val) {
			setBorder(UIManager.getBorder("TextField.border"));
			parent.setWarnVisible(false);
		} else {
			setBorder(new LineBorder(Color.red, 1));
			parent.setWarnVisible(true);
		}
		return val;
	}

	public void setParent(LimittedTextAreawL parent) {
		this.parent = parent;
	}

}
