package layout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import field.LimittedTextFieldwL;
import field.NumberFieldwL;

public class Main extends JPanel {

	private JButton button;
	private NumberFieldwL nfl;
	private LimittedTextFieldwL ltf;

	public Main() {
		super(new FormLayout());

		nfl = new NumberFieldwL("NumberField : ", -10, 255);
		add(nfl);

		ltf = new LimittedTextFieldwL("TextField : ", 10, 25);
		add(ltf);

		button = new JButton("Doğrula");
		add(button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (nfl.verify() && ltf.verify()) {
					JOptionPane.showMessageDialog(Main.this, "Tüm Alanlar doğrulandı.");
				} else {
					JOptionPane.showMessageDialog(Main.this, "Tüm Alanlar doğrulanaMAdı!!!!!!.");
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
		frame.setSize(400, 200);
		// Display the window.
		// frame.pack();
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
