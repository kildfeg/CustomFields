package field;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import layout.ComponentLayout;

public class DateFieldwL extends JComponent {

	private JLabel label;

	private JLabel lblIWarnicon;

	private Popup popup;
	private PopupFactory popupFactory;
	private JLabel labelPopup;

	private JDatePickerImpl datePicker;

	public DateFieldwL(String text) {
		setLayout(new ComponentLayout());
		label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(150, 20));
		add(label);

		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		add(datePicker);

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
		java.net.URL imgURL = DateFieldwL.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public boolean verify() {

		Date selectedDate = (Date) datePicker.getModel().getValue();
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

class DateLabelFormatter extends AbstractFormatter {

	private String datePattern = "yyyy-MM-dd";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	@Override
	public Object stringToValue(String text) throws ParseException {
		return dateFormatter.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value != null) {
			Calendar cal = (Calendar) value;
			return dateFormatter.format(cal.getTime());
		}

		return "";
	}

}
