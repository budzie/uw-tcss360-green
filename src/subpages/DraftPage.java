package subpages;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Draft;
import model.User;

/**
 * This class is a sub pnlMail of the GUI that constitutes the Drafts Page for
 * the given user. This page contains links to the open drafts for a given User
 * and provides links in order to open them with the default system application.
 * The number of drafts is limited by the max number of drafts, per requirements
 * business rule.
 * 
 * @author Robert Ogden rogden33
 */
public class DraftPage extends JPanel implements ListSelectionListener {

	JLabel title;
	private JInternalFrame internalFrame;
	private JDesktopPane desktopPane;
	private JButton btnLaunchApp;
	private Button btnLaunchMail;
	private JPanel pnlMail;
	private JTextField txtFldMail;
	private JLabel lblEmail;
	private JPanel pnlApp;
	private JTextField txtFldFile;
	private JLabel lblFile;
	private JPanel pnlDraft;

	private JList<Draft> draftList;
	private JPanel draftListpnl;
	private JLabel lblDraftList;
	private JButton btnFile;
	private File myDraftFile;
	// JFileChooser fc = new JFileChooser();
	private List<Draft> userDraftList;
	File file;

	private Desktop desktop;
	private Desktop.Action action = Desktop.Action.OPEN;
	private User myUser;
	private Draft myDraft;
	private JPanel listPanel;
	private JPanel topPanel;
	private JList<String> listbox;
	private JScrollPane scrollPane;
	private JButton addButton;
	private JButton removeButton;
	private Vector<String> listData;
	private JLabel lblNewLabel;
	private JEditorPane dtrpnDraftLimit;
	private JTextField dataField;

	/**
	 * DraftPage
	 */
	public DraftPage() {
		super();
		// initialize all gui components
		initComponents();
		// disable buttons that launch browser, email client,
		// disable buttons that open files
		disableActions();
		// before any Desktop APIs are used, first check whether the API is
		// supported by this particular VM on this particular host
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
			// now enable buttons for actions that are supported.
			enableSupportedActions();
		}
		// loadFrameIcon();
		setVisible(true);

	}

	private void addContent() {
		add(getInternalFrame());
		add(getDesktopPane());
		title = new JLabel("Drafts Page");
		this.title.setHorizontalAlignment(SwingConstants.CENTER);
		this.title.setBounds(211, 34, 87, 24);
		add(title);
	}

	private JInternalFrame getInternalFrame() {
		if (internalFrame == null) {
			internalFrame = new JInternalFrame("Draft Retriever");
			internalFrame.setBounds(42, 69, 398, 206);
			internalFrame.setNormalBounds(new Rectangle(20, 20, 150, 150));
			internalFrame.getContentPane().add(getPanel(), BorderLayout.NORTH);
			internalFrame.getContentPane().add(getPnlApp(), BorderLayout.SOUTH);
			// internalFrame.getContentPane().add(getPnlDraft(),
			// BorderLayout.WEST);
			internalFrame.getContentPane().add(getListPanel(),
					BorderLayout.CENTER);

			internalFrame.setVisible(true);
		}
		return internalFrame;
	}

	private JDesktopPane getDesktopPane() {
		if (desktopPane == null) {
			desktopPane = new JDesktopPane();
			desktopPane.setBounds(0, 0, 1, 1);
		}
		return desktopPane;
	}

	private JButton getBtnLaunchApp() {
		if (btnLaunchApp == null) {
			btnLaunchApp = new JButton("Launch Application");
			btnLaunchApp.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return btnLaunchApp;
	}

	private Button getBtnLaunchMail() {
		if (btnLaunchMail == null) {
			btnLaunchMail = new Button("Launch E-Mail");
		}
		return btnLaunchMail;
	}

	private JPanel getPanel() {
		if (pnlMail == null) {
			pnlMail = new JPanel();
			pnlMail.add(getLblEmail());
			pnlMail.add(getTxtFldMail());
			pnlMail.add(getBtnLaunchMail());
		}
		return pnlMail;
	}

	private JTextField getTxtFldMail() {
		if (txtFldMail == null) {
			txtFldMail = new JTextField();
			txtFldMail.setColumns(10);
		}
		return txtFldMail;
	}

	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("Enter E-Mail");
		}
		return lblEmail;
	}

	private JPanel getPnlApp() {
		if (pnlApp == null) {
			pnlApp = new JPanel();
			pnlApp.add(getLblFile());
			pnlApp.add(getTxtFldFile());
			pnlApp.add(getBtnFile());
			pnlApp.add(getBtnLaunchApp());
		}
		return pnlApp;
	}

	private JTextField getTxtFldFile() {
		if (txtFldFile == null) {
			txtFldFile = new JTextField();
			txtFldFile.setColumns(10);
		}
		return txtFldFile;
	}

	private JLabel getLblFile() {
		if (lblFile == null) {
			lblFile = new JLabel("File:");
		}
		return lblFile;
	}

	private JPanel getPnlDraft() {
		if (pnlDraft == null) {
			pnlDraft = new JPanel();
			pnlDraft.add(draftList);
		}
		return pnlDraft;
	}

	public void setUser(final User user) {
		myUser = user;
	}

	public User getUser() {
		return myUser;
	}

	public JPanel getListPanel() {
		// Set the frame characteristics
		listPanel = new JPanel();
		this.listPanel.setLayout(new GridLayout(0, 2, 0, 0));
		// Create a panel to hold all other components
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		listPanel.add(topPanel);
		// Create some items to add to the list
		listData = getList();
		// Create a new listbox control
		listbox = new JList<String>(listData);
		this.listbox.setVisibleRowCount(5);
		this.listbox.setPreferredSize(new Dimension(150, 100));
		this.listbox.setName("Active Drafts");
		this.listbox.setBounds(new Rectangle(0, 0, 75, 25));
		this.listbox.setBackground(UIManager.getColor("Button.background"));
		listPanel.add(this.listbox);
		listbox.setFixedCellWidth(100);
		listbox.setFixedCellHeight(25);
		// Create the data model for this example
		listData = new Vector<String>();

		// Create a new listbox control

		listbox.addListSelectionListener(this);

		CreateDataEntryPanel();
		return listPanel;
	}

	public void CreateDataEntryPanel() {
		// Create a panel to hold all other components
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new BorderLayout());
		topPanel.add(dataPanel, BorderLayout.SOUTH);

		// Create some function buttons
		addButton = new JButton("Add");
		dataPanel.add(addButton, BorderLayout.WEST);
		addButton.addActionListener((ActionListener) new DraftPage());
		removeButton = new JButton("Delete");
		dataPanel.add(removeButton, BorderLayout.EAST);
		removeButton.addActionListener((ActionListener) new DraftPage());
		this.topPanel.add(getLblNewLabel(), BorderLayout.NORTH);
		this.topPanel.add(getDtrpnDraftLimit(), BorderLayout.CENTER);
	}

	// Handler for list selection changes
	public void valueChanged(ListSelectionEvent event) {
		// See if this is a listbox selection and the
		// event stream has settled
		if (event.getSource() == listbox && !event.getValueIsAdjusting()) {
			// Get the current selection and place it in the
			// edit field
			String stringValue = (String) listbox.getSelectedValue();
			if (stringValue != null)
				dataField.setText(stringValue);
		}
	}

	// Handler for button presses
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == addButton) {
			// Get the text field value
			String stringValue = dataField.getText();
			dataField.setText("");

			// Add this item to the list and refresh
			if (stringValue != null) {
				listData.addElement(stringValue);
				listbox.setListData(listData);
				scrollPane.revalidate();
				scrollPane.repaint();
			}
		}

		if (event.getSource() == removeButton) {
			// Get the current selection
			int selection = listbox.getSelectedIndex();
			if (selection >= 0) {
				// Add this item to the list and refresh
				listData.removeElementAt(selection);
				listbox.setListData(listData);
				scrollPane.revalidate();
				scrollPane.repaint();

				// As a nice touch, select the next item
				if (selection >= listData.size())
					selection = listData.size() - 1;
				listbox.setSelectedIndex(selection);
			}
		}
	}

	private Vector<String> getList() {
		listData = new Vector<String>();
		for (int i = 0; i < userDraftList.size(); i++) {
			listData.add(userDraftList.get(i).getMyFilePath());
		}
		if (listData.size() == 0) {
			listData.add(0, "You have not saved any drafts yet");
		}
		return listData;
	}

	private JLabel getLblDraftList() {
		if (lblDraftList == null) {
			lblDraftList = new JLabel("Draft List");
		}
		return lblDraftList;
	}

	private JButton getBtnFile() {
		if (btnFile == null) {
			btnFile = new JButton("Browse");
		}
		return btnFile;
	}

	/**
	 * Create and show components
	 */
	private void initComponents() {

		setLayout(null);
		addContent();

		txtFldMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onLaunchMail(null);
			}
		});

		btnLaunchMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				onLaunchMail(evt);
			}
		});

		txtFldFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onLaunchDefaultApplication(null);
			}
		});

		btnLaunchApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				onLaunchDefaultApplication(evt);
			}
		});

		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				onChooseFile(evt);
			}
		});
	}

	/**
	 * Launch the default application associated with a specific filename using
	 * the preset Desktop.Action.
	 *
	 */
	private void onLaunchDefaultApplication(ActionEvent evt) {
		action = Desktop.Action.OPEN;
		String fileName = txtFldFile.getText();
		file = new File(fileName);

		try {
			desktop.open(file);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("Cannot perform the given operation to the "
					+ file + " file");
		}
		myDraft.setMyFilePath(fileName);
	}

	/**
	 * Launch the default email client using the "mailto" protocol and the text
	 * supplied by the user.
	 *
	 */
	private void onLaunchMail(ActionEvent evt) {
		String mailTo = txtFldMail.getText();
		URI uriMailTo = null;
		try {
			if (mailTo.length() > 0) {
				uriMailTo = new URI("mailto", mailTo, null);
				desktop.mail(uriMailTo);
			} else {
				desktop.mail();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (URISyntaxException use) {
			use.printStackTrace();
		}
	}

	/**
	 * Launch the default browser with the text provided by the user.
	 *
	 */
	private File onChooseFile(ActionEvent evt) {
		// if (evt.getSource() == btnFile) {
		// int returnVal = fc.showOpenDialog(DraftPage.this);
		// if (returnVal == JFileChooser.APPROVE_OPTION){
		// file = fc.getSelectedFile();
		// txtFldFile.setText(file.getAbsolutePath());
		//
		// }
		// }
		return file;
	}

	/**
	 * Enable actions that are supported on this host. The actions are: open
	 * email client, and open files using their associated application
	 */

	private void enableSupportedActions() {
		if (desktop.isSupported(Desktop.Action.MAIL)) {
			txtFldMail.setEnabled(true);
			btnLaunchMail.setEnabled(true);
		}
		if (desktop.isSupported(Desktop.Action.OPEN)) {
			txtFldFile.setEnabled(true);
			btnLaunchApp.setEnabled(true);
			btnFile.setEnabled(true);
		}
	}

	/**
	 * Disable all graphical components until we know whether their
	 * functionality is supported.
	 */
	private void disableActions() {

		txtFldMail.setEnabled(false);
		btnLaunchMail.setEnabled(false);
		txtFldFile.setEnabled(false);
		btnLaunchApp.setEnabled(false);
		btnFile.setEnabled(false);
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Active Drafts");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabel;
	}

	private JEditorPane getDtrpnDraftLimit() {
		dtrpnDraftLimit = new JEditorPane();
		dtrpnDraftLimit.setBackground(UIManager.getColor("Button.background"));
		dtrpnDraftLimit.setBackground(UIManager.getColor("CheckBox.light"));
		dtrpnDraftLimit.setEditable(false);
		dtrpnDraftLimit
				.setText("Maximum Drafts permitted is 3. When you complete a draft, delete it so you may add more.");
		return dtrpnDraftLimit;
	}
}
