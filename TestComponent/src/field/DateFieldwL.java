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
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import field.util.IconUtil;
import layout.ComponentLayout;

public class DateFieldwL extends JComponent {

	private JLabel label;

	private JLabel lblIWarnicon;

	private Popup popup;
	private PopupFactory popupFactory;
	private JLabel labelPopup;

	private DatePicker datePicker;
	private DatePickerSettings dateSettings;

	public DateFieldwL(String text) {
		setLayout(new ComponentLayout());
		label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(150, 20));
		add(label);

		URL dateImageURL = DateFieldwL.class.getResource("images/calendar.png");
		Image dateExampleImage = Toolkit.getDefaultToolkit().getImage(dateImageURL);
		ImageIcon dateExampleIcon = new ImageIcon(dateExampleImage);

		dateSettings = new DatePickerSettings();
		dateSettings.setAllowEmptyDates(false);
		dateSettings.setAllowKeyboardEditing(false);
		dateSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
		datePicker = new DatePicker(dateSettings);
		datePicker.setDateToToday();

		JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
		datePickerButton.setText("");
		datePickerButton.setIcon(dateExampleIcon);

		add(datePicker);

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

		popupFactory = PopupFactory.getSharedInstance();
		labelPopup = new JLabel("Boş bırakılamaz.");
		labelPopup.setForeground(new Color(255, 0, 0));

	}

	public boolean verify() {

		LocalDate selectedDate = datePicker.getDate();
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

	public LocalDate getDate() {
		return datePicker.getDate();
	}

	public String getStringValue() {
		return datePicker.getText();
	}
}
