package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import field.ComboBoxwL;
import field.DateFieldwL;
import field.DateTimeFieldwL;
import field.LimittedTextAreawL;
import field.LimittedTextFieldwL;
import field.MultiComboBoxwL;
import field.NumberFieldwL;
import field.TimeFieldwL;
import field.model.ModelCheckBox;
import layout.FormLayout;

public class Main extends JPanel {

	private JButton button;
	private NumberFieldwL nfl;
	private LimittedTextFieldwL ltf;
	private LimittedTextFieldwL ltf2;
	private LimittedTextFieldwL ltf3;
	private LimittedTextAreawL lta;

	public Main() {
		super(new FormLayout(2, 2));

		nfl = new NumberFieldwL("NumberField : ", -10, 255);
		add(nfl);

		ltf = new LimittedTextFieldwL("TextField : ", 10, 25);
		add(ltf);

		ltf2 = new LimittedTextFieldwL("TextField : ", 2, 10);
		add(ltf2);

		ltf3 = new LimittedTextFieldwL("TextField : ", 3, 11);
		add(ltf3);

		lta = new LimittedTextAreawL("TextArea : ", 3, 150);
		add(lta);

		DateFieldwL datefieldwL = new DateFieldwL("Date Field : ");
		add(datefieldwL);

		TimeFieldwL fieltwL = new TimeFieldwL("Time Field : ");
		add(fieltwL);

		DateTimeFieldwL dateTimeFieldwL = new DateTimeFieldwL("Date Time Field : ");
		add(dateTimeFieldwL);

		ComboBoxwL comboBoxwL = new ComboBoxwL("String combo : ", new String[] { "abc", "def", "xyz" });
		add(comboBoxwL);

		ModelCheckBox[] values = new ModelCheckBox[4];

		values[0] = new ModelCheckBox("north", "north", true);
		values[1] = new ModelCheckBox("west", "west", false);
		values[2] = new ModelCheckBox("south", "south", true);
		values[3] = new ModelCheckBox("east", "east", false);

		MultiComboBoxwL multiComboBoxwL = new MultiComboBoxwL("Multi : ", values);
		add(multiComboBoxwL);

		button = new JButton("Doğrula");
		add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (multiComboBoxwL.verify() && comboBoxwL.verify() && dateTimeFieldwL.verify() && fieltwL.verify()
						&& datefieldwL.verify() && nfl.verify() && ltf.verify() && ltf2.verify() && ltf3.verify()
						&& lta.verify()) {
					// JOptionPane.showMessageDialog(Main.this, "Tüm Alanlar
					// doğrulandı.");

					System.out.println("Date : " + datefieldwL.getStringValue());
					System.out.println("Time : " + fieltwL.getStringValue());
					System.out.println("Date Time : " + dateTimeFieldwL.getStringValue());
					System.out.println("Number : " + nfl.getValue());
					System.out.println("TextArea : " + lta.getValue());

					System.out.println("TextField : " + ltf2.getValue());
					System.out.println("TextField : " + ltf3.getValue());
					System.out.println("Combo  : " + comboBoxwL.getStringValue());

					for (ModelCheckBox modelCheckBox : multiComboBoxwL.getSelectedValues()) {
						System.out.println("Multi  : " + modelCheckBox.getValue());
					}

				} else {
					// JOptionPane.showMessageDialog(Main.this, "Tüm Alanlar
					// doğrulanaMAdı!!!!!!.");
				}

			}
		});

	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("TextDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add contents to the window.
		frame.add(new Main());
		frame.setSize(400, 600);
		// Display the window.
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
