package login;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.User;

/**
 * @author Sally Budack
 * TCSS342 Fall2014
 *
 */
public class LoadingScreen extends JFrame {
	private final String user;
//	String job;

	
	private final JPanel panel = new JPanel();

	LoadingScreen(final User u) {		
		super("Welcome");
		user = u.getName();
//		job = work;
		
		JLabel identify = new JLabel("Welcome " + user);
//		JLabel access = new JLabel("Access: " + job);	
		JLabel loading = new JLabel("Loading .  .  .  ");	
		setSize(300, 200);
		setLocation(500, 280);
		panel.setLayout(null);

		identify.setBounds(50, 30, 180, 60);
//		access.setBounds(50, 60, 180, 60);
		loading.setBounds(50, 90, 180, 60);
		panel.add(identify);
//		panel.add(access);
		panel.add(loading);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}