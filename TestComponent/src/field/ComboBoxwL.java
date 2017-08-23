package field;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import field.util.IconUtil;
import layout.ComponentLayout;

public class ComboBoxwL extends JComponent {

	private JLabel label;

	private JLabel lblIWarnicon;

	private Popup popup;
	private PopupFactory popupFactory;
	private JLabel labelPopup;

	private JComboBox<Model> comboBox;

	public ComboBoxwL(String text, Model[] values) {
		setLayout(new ComponentLayout());
		label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(150, 20));
		add(label);

		ComboBoxModel myModel = new ComboBoxModel(values);
		comboBox = new JComboBox<Model>(myModel);
		add(comboBox);

		initWarn();
	}

	private void initWarn() {
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

	public ComboBoxwL(String text, String[] strings) {

		Model[] values = new Model[strings.length];

		for (int i = 0; i < strings.length; i++) {

			String str = strings[i];
			values[i] = new Model(str, str);

		}

		setLayout(new ComponentLayout());
		label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(150, 20));
		add(label);

		ComboBoxModel myModel = new ComboBoxModel(values);
		comboBox = new JComboBox<Model>(myModel);
		add(comboBox);
		initWarn();

	}

	public boolean verify() {

		Model selected = (Model) comboBox.getSelectedItem();
		if (selected == null) {
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

	public String getStringValue() {
		return ((Model) comboBox.getSelectedItem()).getValue();
	}

	public Model getSelectedModel() {
		return (Model) comboBox.getSelectedItem();
	}

}

class ComboBoxModel extends DefaultComboBoxModel<Model> {
	public ComboBoxModel(Model[] items) {
		super(items);
	}

	@Override
	public Model getSelectedItem() {
		Model selected = (Model) super.getSelectedItem();
		return selected;
	}
}

class Model {
	String id;
	String value;

	public Model(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return getValue();
	}
}
