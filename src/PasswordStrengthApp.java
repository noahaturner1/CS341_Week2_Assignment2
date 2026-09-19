import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * GUI application that validates a password's length and contents, then
 * calculates the longest block of consecutive identical characters.
 */
public class PasswordStrengthApp {

	private JFrame frame;
	private JTextField txt_Password;
	private JLabel lbl_Results;
	private JScrollPane scrollPane;

	/**
	 * Launches the application on the Swing event dispatch thread.
	 *
	 * @param args unused command-line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordStrengthApp window = new PasswordStrengthApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructs the application and initializes the GUI.
	 */
	public PasswordStrengthApp() {
		initialize();
	}

	/**
	 * Builds and lays out all GUI components, including the password field,
	 * instructions label, Go button, and results display.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.GRAY);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Header label
		JLabel lbl_Header = new JLabel("Password Strength App");
		lbl_Header.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Header.setBounds(133, 6, 189, 16);
		frame.getContentPane().add(lbl_Header);
		
		// Instructions describing the password rules and block-strength concept
		JLabel txt_Instructions = new JLabel(
				"<html>Passwords are often characterised as weak or strong <br>"
				+ " based on the length of the largest \"block\" in the sequence<br>"
				+ "of characters. <br>\n<br>\nEnter a password of 8-12 characters "
				+ "and press Go to calculate the largest block in the password and the "
				+ "relative strength of the password.</html>");
		
		txt_Instructions.setForeground(Color.GRAY);
		txt_Instructions.setHorizontalAlignment(SwingConstants.CENTER);
		txt_Instructions.setBounds(6, 23, 438, 111);
		frame.getContentPane().add(txt_Instructions);
		
		// Password input field
		txt_Password = new JTextField();
		txt_Password.setText("Enter password");
		txt_Password.setForeground(Color.LIGHT_GRAY);
		txt_Password.setBounds(16, 159, 416, 26);
		frame.getContentPane().add(txt_Password);
		txt_Password.setColumns(10);
		
		/*
		 * Go button: validates the entered password and, if valid,
		 * computes the longest block of consecutive identical characters.
		 */
		JButton btn_Go = new JButton("Go");
		btn_Go.addActionListener(new ActionListener() {
			/**
			 * Validates the password's length and contents, then displays
			 * either an error message or the longest consecutive-character
			 * block length.
			 *
			 * @param e the button click event
			 */
			public void actionPerformed(ActionEvent e) {

				lbl_Results.setForeground(Color.BLACK);

				String input = txt_Password.getText();

				// Step 1: Validate length (8-12 characters, inclusive) and check for spaces
				boolean invalidLength = input.length() < 8 || input.length() > 12;
				boolean hasSpaces = input.contains(" ");

				if (invalidLength && hasSpaces) {
					lbl_Results.setText("<html>Password must be 8-12 characters and cannot contain spaces.</html>");
				} else if (invalidLength) {
					lbl_Results.setText("<html>Password must be between 8-12 characters, inclusive.</html>");
				} else if (hasSpaces) {
					lbl_Results.setText("<html>Password can't contain spaces.</html>");
				} else {
					// Step 2: Find the longest block of consecutive identical characters
					int longest = 1;
					int current = 1;

					for (int i = 1; i < input.length(); i++) {
						if (input.charAt(i) == input.charAt(i - 1)) {
							current++;
						} else {
							current = 1;
						}
						longest = Math.max(longest, current);
					}

					if (longest < 3) {
						lbl_Results.setText("<html>Longest block: " + longest + ". This is a decent password.</html>");
					} else {
						lbl_Results.setText("<html>Longest block: " + longest + ". <br>You should consider shrinking this block.</html>");					}
				}
			}
		});
		btn_Go.setBounds(156, 123, 117, 29);
		frame.getContentPane().add(btn_Go);

		// Scroll pane housing the results label
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(16, 197, 416, 56);
		frame.getContentPane().add(scrollPane);

		lbl_Results = new JLabel("Results will be displayed here.");
		lbl_Results.setForeground(Color.LIGHT_GRAY);
		scrollPane.setRowHeaderView(lbl_Results);
	}

}