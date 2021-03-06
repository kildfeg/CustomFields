package field;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import field.util.IconUtil;
import layout.ComponentLayout;

public class NumberFieldwL extends JComponent {

	private JLabel label;
	private NumberField numberField;
	private JLabel lblIWarnicon;

	private Popup popup;
	private PopupFactory popupFactory;
	private JLabel labelPopup;

	public NumberFieldwL(String text, int min, int max) {
		setLayout(new ComponentLayout());

		label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(150, 20));
		add(label);

		numberField = new NumberField(min, max);
		numberField.setPreferredSize(new Dimension(150, 20));
		add(numberField);

		ImageIcon icon = IconUtil.createImageIcon(this.getClass(), "images/warn.png", "");
		lblIWarnicon = new JLabel(icon);
		lblIWarnicon.setPreferredSize(new Dimension(20, 20));
		lblIWarnicon.setVisible(false);
		lblIWarnicon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showWarnPopup();
			}
		});
		add(lblIWarnicon);

		numberField.setParent(this);
		popupFactory = PopupFactory.getSharedInstance();
		labelPopup = new JLabel("En az " + min + " en fazla " + max + " olmalıdır.");
		labelPopup.setForeground(new Color(255, 0, 0));

		// setBorder(new LineBorder(new Color(255, 0, 0)));
	}

	public boolean verify() {
		return numberField.verify();
	}

	private void showWarnPopup() {
		if (popup == null) {
			Point p = lblIWarnicon.getLocationOnScreen();
			popup = popupFactory.getPopup(this, labelPopup, p.x + 20, p.y);
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
			Timer timer = new Timer(3000, hider);
			timer.start();
		}

	}

	public void setWarnVisible(boolean b) {
		lblIWarnicon.setVisible(b);

	}

	public int getValue() {
		return numberField.getValue();
	}

}
