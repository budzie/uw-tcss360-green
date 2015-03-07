package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import login.AllUsers;
import model.Library;
import model.User;
import subpages.DraftPage;
import subpages.EditPage;
import subpages.LibraryPage;

/**
 * This class is the master GUI for the application that Users mainly interact
 * with. This class is meant to be constructed at the beginning of run time but
 * only set to visible when a user has been passed in, allowing the GUI to be
 * set up for an analyst or administrator as given by the user's status. This
 * GUI uses a CardLayout to switch between the different sub pages. The home
 * screen contains buttons to take the user to each sub page. Each sub page has
 * a top header panel with buttons allowing the user to navigate back to the
 * home page, or to log out and close the application.
 * 
 * @author Robert Ogden rogden33
 */
public class GUI extends JFrame {

	/**
	 * The gap between buttons on the Home screen, just to look prettier.
	 */
	private static final int HOME_GAP = 10;

	/**
	 * The size of the GUI. The GUI is not resizeable.
	 */
	private static final Dimension SIZE = new Dimension(500, 500);

	/**
	 * A list of components that should be enabled only for administrators. As
	 * components are created below, they are added to this list appropriately.
	 * Then, when the User is set, this list is iterated through to disable
	 * these components for Analysts.
	 */
	private final List<Component> myAdminOnlyComponents;

	/**
	 * A list of components that should be enabled only for analysts. As
	 * components are created below, they are added to this list appropriately.
	 * Then, when the User is set, this list is iterated through to disable
	 * these components for Administrators.
	 */
	private final List<Component> myAnalystOnlyComponents;

	/**
	 * A reference to the layout manager for the JFrame's main content panel
	 * referenced below. This reference is kept to allow easy iteration through
	 * cards.
	 */
	private final CardLayout myCardLayout;

	private final Library myLibrary;

	/**
	 * A reference to the main content panel. This panel is added directly to
	 * the JFrame whose layout manager is BorderLayout. This panel's layout
	 * manager is CardLayout which is what is used to move between pages in the
	 * application. This reference is kept to allow easy access for calling
	 * other cards to be shown.
	 */
	private final JPanel myContentPanel;

	/**
	 * A reference to the User currently logged into the application.
	 */
	private User myUser;

	/**
	 * Indicates whether or not this user is an administrator.
	 */
	private boolean myAdminStatus;

	/**
	 * The sole constructor. This constructor is responsible for initializing
	 * all final fields, as well as setting up the basic properties of the
	 * JFrame.
	 */
	public GUI(final Library library, final AllUsers users) {
		super("Global Logistics | Analyst Response Library");
		myAdminOnlyComponents = new ArrayList<Component>();
		myAnalystOnlyComponents = new ArrayList<Component>();
		myCardLayout = new CardLayout();
		myLibrary = library;
		myContentPanel = addContent();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
//		setResizable(false);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(final WindowEvent event) {
				try {
					users.serialize();
					myLibrary.serialize();
				} catch (IOException e) {
				}
			}
		});
	}

	/**
	 * This method sets the user that is logged into this application. This
	 * method is called by the login screen and sets the needed components to be
	 * enabled or disabled based on the user's privilege level. Finally, this
	 * sets the GUI as visible and disables the loading screen.
	 * 
	 * @param user
	 *            the user logged into the application
	 */
	public void setUser(final User user, final JFrame loadingScreen) {
		myUser = user;
		myAdminStatus = myUser.isAdmin();
		add(createTopBar(), BorderLayout.NORTH);
		if (myAdminStatus) {
			for (Component c : myAnalystOnlyComponents) {
				c.setEnabled(false);
			}
		} else {
			for (Component c : myAdminOnlyComponents) {
				c.setEnabled(false);
			}
		}
		setVisible(true);
		loadingScreen.dispose();
	}

	/**
	 * This method creates all of the content in the GUI and sets up the card
	 * layout. A reference to the content master panel is returned to the
	 * constructor so that it can be referenced as a field. Finally, this method
	 * sets the Home page to be shown first when the GUI becomes visible
	 * 
	 * @return a reference to the master content panel
	 */
	private JPanel addContent() {
		// create all needed panels
		final JPanel masterPanel = new JPanel(myCardLayout);
		// add sub panels to master panel
		masterPanel.add(createHomePage(), "Home");
		masterPanel.add(createLibraryPage(), "Library");
		masterPanel.add(createEditPage(), "Edit");
		masterPanel.add(createDraftsPage(), "Drafts");
		add(masterPanel, BorderLayout.CENTER);
		// show home page at application start
		myCardLayout.show(masterPanel, "Home");
		return masterPanel;
	}

	/**
	 * This method creates the home page with its several buttons to navigate
	 * between pages. Each button is given an action listener to bring up its
	 * associated page. In the case of the update button, a call is passed to
	 * the model to update the database with an online updated copy when
	 * connected to the network.
	 * 
	 * @return the Home page
	 */
	private JPanel createHomePage() {
		final JPanel panel = new JPanel(
				new GridLayout(2, 2, HOME_GAP, HOME_GAP));
		// library button
		final JButton libraryButton = new JButton("Library");
		libraryButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				myCardLayout.show(myContentPanel, "Library");
			}
		});
		// edit button
		final JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				myCardLayout.show(myContentPanel, "Edit");
			}
		});
		myAdminOnlyComponents.add(editButton);
		// drafts button
		final JButton draftsButton = new JButton("Drafts");
		draftsButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				myCardLayout.show(myContentPanel, "Drafts");
			}
		});
		myAnalystOnlyComponents.add(draftsButton);
		// update button
		final JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				// call to model
			}
		});
		// add components
		panel.add(libraryButton);
		panel.add(editButton);
		panel.add(draftsButton);
		panel.add(updateButton);
		return panel;
	}

	/**
	 * This method creates the library page, implemented in an outside class,
	 * along with the top common bar to navigate home or log out. The panel is
	 * laid out as a BorderLayout to allow the navigation bar to sit in the
	 * NORTH region.
	 * 
	 * @return a reference to the library page.
	 */
	private JPanel createLibraryPage() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(new LibraryPage());
		return panel;
	}

	/**
	 * This method creates the edit page, implemented in an outside class, along
	 * with the top common bar to navigate home or log out. The panel is laid
	 * out as a BorderLayout to allow the navigation bar to sit in the NORTH
	 * region.
	 * 
	 * @return a reference to the edit page.
	 */
	private JPanel createEditPage() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(new EditPage(myLibrary));
		return panel;
	}

	/**
	 * This method creates the drafts page, implemented in an outside class,
	 * along with the top common bar to navigate home or log out. The panel is
	 * laid out as a BorderLayout to allow the navigation bar to sit in the
	 * NORTH region.
	 * 
	 * @return a reference to the drafts page.
	 */
	private JPanel createDraftsPage() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(new DraftPage());
		return panel;
	}

	/**
	 * This method create the top bar which is shared by the various sub pages
	 * in order to navigate home or log out. This small panel is meant to be
	 * placed in the NORTH region of the sub panel pages.
	 * 
	 * @return the common top navigation bar
	 */
	private JPanel createTopBar() {
		final JPanel panel = new JPanel(new BorderLayout());
		// go home button
		final JButton homeButton = new JButton("Go Home");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				myCardLayout.show(myContentPanel, "Home");
			}
		});
		// logout and exit button
		final JButton exitButton = new JButton("Exit");
		final GUI thisGUI = this;
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				dispatchEvent(new WindowEvent(thisGUI, WindowEvent.WINDOW_CLOSING));
			}
		});
		// user name label
		final JLabel welcomeLabel = new JLabel("Welcome, " + myUser.getName());
		welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
		// add components
		panel.add(homeButton, BorderLayout.WEST);
		panel.add(exitButton, BorderLayout.EAST);
		panel.add(welcomeLabel, BorderLayout.CENTER);
		return panel;
	}

}
