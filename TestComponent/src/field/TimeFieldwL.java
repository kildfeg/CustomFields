package field;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import layout.ComponentLayout;

public class TimeFieldwL extends JComponent {

	private JLabel label;

	private JLabel lblIWarnicon;

	private Popup popup;
	private PopupFactory popupFactory;
	private JLabel labelPopup;

	private TimePickerSettings timeSettings;

	private TimePicker timePicker;

	public TimeFieldwL(String text) {
		setLayout(new ComponentLayout());
		label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(150, 20));
		add(label);

		URL timeIconURL = TimeFieldwL.class.getResource("images/watch.png");
		Image timeExampleImage = Toolkit.getDefaultToolkit().getImage(timeIconURL);
		ImageIcon timeExampleIcon = new ImageIcon(timeExampleImage);

		timeSettings = new TimePickerSettings();
		timeSettings.use24HourClockFormat();
		timeSettings.initialTime = LocalTime.of(00, 00);
		timePicker = new TimePicker(timeSettings);
		JButton timePickerButton = timePicker.getComponentToggleTimeMenuButton();
		timePickerButton.setText("");
		timePickerButton.setIcon(timeExampleIcon);

		add(timePicker);

		ImageIcon icon = createImageIcon("images/warn.png", "");
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

		popupFactory = PopupFactory.getSharedInstance();
		labelPopup = new JLabel("Boş bırakılamaz.");
		labelPopup.setForeground(new Color(255, 0, 0));

	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = TimeFieldwL.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public boolean verify() {

		LocalTime selectedDate = timePicker.getTime();
		if (selectedDate == null) {
			setWarnVisible(true);
			return false;
		}
		setWarnVisible(false);
		return true;
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
}
