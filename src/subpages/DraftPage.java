package subpages;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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
 * @author Sally Budack
 * @author Robert Ogden rogden33
 */
public class DraftPage extends JPanel implements ListSelectionListener {
	// components
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
	private JButton btnFile;
		private JFileChooser fc = new JFileChooser();
	private List<Draft> userDraftList;
	File file;

	private Desktop desktop;
	private User myUser;
	private Draft myDraft;
	private JPanel listPanel;
	private JPanel topPanel;
	private JList<String> listbox;
	private JButton addButton;
	private JButton removeButton;
	private Vector<String> listData;
	private JLabel lblNewLabel;
	private JEditorPane paneDraftLimit;
	private Action action;

	/**
	 * DraftPage
	 * constructor
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
		add(getInternalFrame());// rather than a JFrame
		add(getDesktopPane());
		title = new JLabel("Drafts Page");
		this.title.setHorizontalAlignment(SwingConstants.CENTER);
		this.title.setBounds(300, 20, 87, 24);
		add(title);
	}

	private JInternalFrame getInternalFrame() {
		if (internalFrame == null) {
			internalFrame = new JInternalFrame("Draft Retriever");
			internalFrame.setBounds(42, 50, 600, 250);
			internalFrame.setNormalBounds(new Rectangle(20, 20, 250, 150));
			internalFrame.getContentPane().add(getPanel(), BorderLayout.NORTH);
			internalFrame.getContentPane().add(getPnlApp(), BorderLayout.SOUTH);
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

	/**
	 * setUser
	 * @param user
	 */
	public void setUser(final User user) {
		myUser = user;
	}

	/**
	 * getUser
	 * @return user logged in
	 */
	public User getUser() {
		return myUser;
	}

	private JPanel getListPanel() {
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
		listbox.setVisibleRowCount(-1);
		listbox.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

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
		listbox.addListSelectionListener(new ListSelectionListener() {
//			int selection = listbox.getSelectedIndex();
			public void valueChanged(ListSelectionEvent e) {			     
				int selectedRow = e.getFirstIndex(); 
				int idx = e.getFirstIndex();
				int idx2 = e.getLastIndex();  //idx and idx2 should be the same if you set Single Cell
				if(idx == idx2){
					// get the string
					Object workingCopy = listbox.getModel().getElementAt(selectedRow); 					
//					String workingCopy = (String)listbox.getSelectedValue();
					if (selectedRow >= 0) {
						txtFldFile.setText(" ");
						txtFldFile.setText((String) workingCopy);						
					}			
				}
			}
		});


		CreateDataEntryPanel();
		return listPanel;
	}

	private void CreateDataEntryPanel() {
		// Create a panel to hold all other components
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new BorderLayout());
		topPanel.add(dataPanel, BorderLayout.SOUTH);
		dataPanel.setLayout(new FlowLayout());
		// Create some function buttons
		addButton = new JButton("Add");
		dataPanel.add(addButton);
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == addButton) {
					// Get the text field value
					String stringValue = txtFldFile.getText();
					//				dataField.setText("");
					// Add this item to the list and refresh
					if (stringValue != null) {

						listData.addElement(stringValue);
						listbox.setListData(listData);
						//						scrollPane.revalidate();
						//						scrollPane.repaint();
					}
				}

			}

		});

		removeButton = new JButton("Delete");
		dataPanel.add(removeButton);
		removeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == removeButton) {
					// Get the current selection
					int selection = listbox.getSelectedIndex();
					if (selection >= 0) {
						// Add this item to the list and refresh
						listData.removeElementAt(selection);
						listbox.setListData(listData);
						//					scrollPane.revalidate();
						//						scrollPane.repaint();

						// As a nice touch, select the next item
						if (selection >= listData.size())
							selection = listData.size() - 1;
						listbox.setSelectedIndex(selection);
					}
				}

			}

		});
		this.topPanel.add(getLblNewLabel(), BorderLayout.NORTH);
		this.topPanel.add(getPaneDraftLimit(), BorderLayout.CENTER);
	}

	// Handler for list selection changes
	public void valueChanged(ListSelectionEvent event) {
		// See if this is a listbox selection and the
		// event stream has settled
		if (event.getSource() == listbox && !event.getValueIsAdjusting()) {
			listbox.getSelectedValue();
		}
	}


	private Vector<String> getList() {
		listData = new Vector<String>();
		if (listData.size() == 0) {
			listData.add(0, "You have not saved any drafts yet");
		}else{
			for (int i = 0; i < userDraftList.size(); i++) {
				listData.add(userDraftList.get(i).getMyFilePath());
			}
		}
		return listData;
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
	 * Launch the default application associated with a specific
	 * filename using the preset Desktop.Action.
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
			System.out.println("Cannot perform the given operation to the " + file + " file");
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
				if (evt.getSource() == btnFile) {
					int returnVal = fc.showOpenDialog(DraftPage.this);
					if (returnVal == JFileChooser.APPROVE_OPTION){
						file = fc.getSelectedFile();
						txtFldFile.setText(file.getAbsolutePath());
		
					}
				}
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

	private JEditorPane getPaneDraftLimit() {
		paneDraftLimit = new JEditorPane();
		paneDraftLimit.setBackground(UIManager.getColor("Button.background"));
		paneDraftLimit.setBackground(UIManager.getColor("CheckBox.light"));
		paneDraftLimit.setEditable(false);
		paneDraftLimit
		.setText("Maximum Drafts permitted is 3. \nWhen you complete a draft, \ndelete it so you may add more."
				+ "\nClick on the draft in the list you want to open,\n then Launch Application ");
		return paneDraftLimit;
	}
}
