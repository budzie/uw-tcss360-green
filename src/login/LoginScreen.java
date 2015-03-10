package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Library;
import model.User;
import view.GUI;

/**
 * @author Sally Budack
 * @author Robert Ogden
 *
 */
public class LoginScreen extends JFrame {

	private final JLabel enterID = new JLabel("Please enter your user ID");
	private final JLabel enterPin = new JLabel("Please enter your pin number");
	private final JButton login = new JButton("LoginScreen");
	private final JButton cancel = new JButton("Cancel");
	private final JPanel panel = new JPanel();
	private final JTextField userName = new JTextField(15);
	private final JTextField pinNum = new JTextField(4);

	private AllUsers myUsers;

	private final GUI myGUI;
	
	private final Library myLibrary;

	/**
	 * LoginScreen
	 * @param gui
	 * @param users
	 * @param library
	 */
	public LoginScreen(final GUI gui, final AllUsers users, final Library library) {
		super("LoginScreen Authentification");
		myGUI = gui;
		myUsers = users;
		myLibrary = library;
		setSize(300, 200);
		setLocation(500, 280);
		panel.setLayout(null);
		enterID.setBounds(50, 10, 170, 20);
		enterPin.setBounds(50, 45, 170, 20);
		userName.setBounds(70, 30, 130, 20);
		pinNum.setBounds(100, 65, 35, 20);
		login.setBounds(50, 100, 80, 20);
		cancel.setBounds(140, 100, 80, 20);
		panel.add(enterID);
		panel.add(enterPin);
		panel.add(cancel);
		panel.add(login);
		panel.add(userName);
		panel.add(pinNum);
		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addActionListeners();
	}

	/**
	 * errorMessage
	 */
	public void errorMessage() {
		JOptionPane.showMessageDialog(this, "Wrong Password / Username");
		userName.setText("");
		pinNum.setText("");
		userName.requestFocus();
	}

	/**
	 * actionlogin
	 */
	public void addActionListeners() {
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				validateUser();
			}
		});

		pinNum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					validateUser();
				}
			}
		});

		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// System.exit(ABORT);
				// (Robert) this is a much nicer and cleaner way of closing
				dispose();
			}
		});
	}

	/**
	 * 
	 * validateUser
	 */
	private void validateUser() {
		// (Robert) Using the verification is AllUsers instead so that we can
		// serialize everything

		// Analyst staff = new Analyst();
		// ValidateUser validate = new ValidateUser();
		final String person = userName.getText();
		final String pWord = pinNum.getText();
		// if (pWord.length() != 4) {
		// errorMessage();
		// return;
		// }
		try {
			final int pin = Integer.parseInt(pWord);
			final User validUser = myUsers.validateUser(person, pin);
			if (validUser == null) {
				throw new BadUserException();
			}
			final LoadingScreen loading = new LoadingScreen(validUser);
			loading.setVisible(true);
			myGUI.setUser(validUser, loading);
			myLibrary.setUser(validUser);
			dispose();
		} catch (BadUserException e) {
			errorMessage();
		}
		// if (pWord.length() == 4) {
		// pin = pWord;
		// }

		// staff.pinNum = pin;
		// staff.userID = person;

		// try {
		// if (validate.isValid(person) && validate.isValid(pin)) {
		// String displayName = validate.getName(staff.userID);
		// String job = validate.getRole(staff.userID);
		// NewFrame regFace = new NewFrame(displayName, job);
		// regFace.setVisible(true);
		// dispose();
		// } else {
		//
		// errorMessage();
		// }
		// } catch (FileNotFoundException e) {
		//
		// e.printStackTrace();
		// }

	}
	
	private class BadUserException extends Exception {}
}
