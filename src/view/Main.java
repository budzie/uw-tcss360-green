package view;

import javax.swing.JOptionPane;

import login.AllUsers;
import login.LoginScreen;
import model.Library;

public class Main {

	public static void main(String[] args) {
		// deserialize library with error handling
		Library library = Library.deserialize();
		if (library == null) {
			final int choice = JOptionPane
					.showConfirmDialog(
							null,
							"The Library could not be opened. Do you want to reload the original?",
							"Error", JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				library = Library.createFromOriginal();
			}
		}
		// deserialize AllUsers with error handling
		AllUsers users = AllUsers.deserialize();
		if (users == null) {
			final int choice = JOptionPane
					.showConfirmDialog(
							null,
							"The registered Users could not be opened. Do you want to reload the original set without any saved drafts?",
							"Error", JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				users = AllUsers.createFromOriginal();
			}
		}
		if (users == null || library == null) {
			JOptionPane.showMessageDialog(null, "There was a fatal error.",
					"ERROR", JOptionPane.ERROR_MESSAGE);
		} else {
			// start GUI
			final GUI gui = new GUI(library, users);
			// start login
			final LoginScreen login = new LoginScreen(gui, users, library);
		}
	}
}
