package field;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import field.model.ModelCheckBox;
import field.util.IconUtil;
import layout.ComponentLayout;

public class MultiComboBoxwL extends JComponent implements ActionListener {

	private JLabel label;

	private JLabel lblIWarnicon;

	private Popup popup;
	private PopupFactory popupFactory;
	private JLabel labelPopup;

	private JComboBox<ModelCheckBox> comboBox;

	public MultiComboBoxwL(String text, ModelCheckBox[] values) {
		setLayout(new ComponentLayout());
		label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setPreferredSize(new Dimension(150, 20));
		add(label);

		ComboCheckBoxModel myModel = new ComboCheckBoxModel(values);
		comboBox = new JComboBox<ModelCheckBox>(myModel);
		comboBox.setRenderer(new CheckComboRenderer());
		comboBox.addActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		ModelCheckBox store = (ModelCheckBox) cb.getSelectedItem();
		CheckComboRenderer ccr = (CheckComboRenderer) cb.getRenderer();
		store.setSelected(!store.isSelected());
		ccr.checkBox.setSelected(store.isSelected());
	}

	public boolean verify() {

		ModelCheckBox selected = (ModelCheckBox) comboBox.getSelectedItem();
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

	public List<ModelCheckBox> getSelectedValues() {
		List<ModelCheckBox> list = new ArrayList<ModelCheckBox>();

		for (int i = 0; i < comboBox.getItemCount(); i++) {
			ModelCheckBox box = comboBox.getItemAt(i);
			if (box.isSelected()) {
				list.add(box);
			}
		}
		return list;
	}

}

class ComboCheckBoxModel extends DefaultComboBoxModel<ModelCheckBox> {
	public ComboCheckBoxModel(ModelCheckBox[] items) {
		super(items);
	}

	@Override
	public ModelCheckBox getSelectedItem() {
		ModelCheckBox selected = (ModelCheckBox) super.getSelectedItem();
		return selected;
	}
}

class CheckComboRenderer implements ListCellRenderer {
	JLabel label = new JLabel(" ");
	JCheckBox checkBox;

	public CheckComboRenderer() {

		checkBox = new JCheckBox();

	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		ListModel listmodel = list.getModel();
		if (index < 0) {
			label.setText(getDataStringRepresentation(listmodel));
			return label;
		} else {
			ModelCheckBox model = (ModelCheckBox) value;
			checkBox.setText(model.getId());
			checkBox.setSelected(model.isSelected());
			checkBox.setBackground(isSelected ? Color.gray : Color.white);

			return checkBox;
		}
	}

	private String getDataStringRepresentation(ListModel model) {
		List<String> sl = new ArrayList<>();
		for (int i = 0; i < model.getSize(); i++) {
			Object o = model.getElementAt(i);
			if (o instanceof ModelCheckBox && ((ModelCheckBox) o).isSelected()) {
				sl.add(o.toString());
			}
		}
		return sl.stream().collect(Collectors.joining(", "));
	}

}
