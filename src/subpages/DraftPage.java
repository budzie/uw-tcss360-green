package subpages;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
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
	// button to launch applications
	private JButton btnLaunchApp;
	// button to launch email
	private Button btnLaunchMail;
	private JPanel pnlMail;
	private JTextField txtFldMail;
	private JLabel lblEmail;
	private JPanel pnlApp;
	private JTextField txtFile;
	private JLabel lblFile;
	// button to browse for files
	private JButton btnFile;
	// choose file
	private JFileChooser fc = new JFileChooser();
	File file;
	private Desktop desktop;
	// personalize drafts to individual user
	private User myUser;
	private Draft myDraft;
	private JPanel listPanel;
	private JPanel topPanel;
	// display of draft list
	private JList<String> listbox;
	// button to add drafts to list
	private JButton addButton;
	// button to remove drafts from list
	private JButton removeButton;
	private Vector<String> listData;
	private ArrayList<String> fileDisplayName;
	// panel for draft info
	private JEditorPane paneDraftLimit;
	private Action action;
	private JPanel panelDraftRadioBtn;
	private JPanel pnlDraftByName;
	private JPanel panelDraftFiletxt;
	private JTextField txtDraft1;
	private JTextField txtDraft2;
	private JTextField txtDraft3;
	private JRadioButton rdbtnDraft1;
	private JRadioButton rdbtnDraft2;
	private JRadioButton rdbtnDraft3;
	private JButton OpenButton;
	private JPanel panelManageDrafts;
	private JPanel panelDraftButtons;
	private ButtonGroup btnGroup;
	private String draft;
	private JLabel lblDraft1;
	private JLabel lblDraft2;
	private JLabel lblDraft3;
	private String draftName;


	/**
	 * DraftPage
	 * constructor
	 */
	public DraftPage() {
		super();
		// initialize all gui components
		initComponents();
		// disable button that launches email client & opens files		
		disableActions();
		// before Desktop APIs are used, check if API is supported
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
			// now enable buttons for actions that are supported.
			enableSupportedActions();
		}		
		setVisible(true);
	}
	////////////////////////    internal frame
	/**
	 * 
	 * addContent
	 * adds an internal frame that will contain the ability to open files
	 * and emails, drafts can be saved to readily access
	 */
	private void addContent() {
		add(getInternalFrame());// rather than a JFrame
		add(getDesktopPane());
		title = new JLabel("Drafts Page");
		this.title.setHorizontalAlignment(SwingConstants.CENTER);
		this.title.setBounds(294, 4, 87, 24);
		add(title);
	}

	/** 
	 * 
	 * getInternalFrame
	 * @return internal frame formatted
	 */
	private JInternalFrame getInternalFrame() {
		if (internalFrame == null) {
			internalFrame = new JInternalFrame("Draft Retriever");
			internalFrame.setBounds(10, 39, 641, 250);
			internalFrame.setNormalBounds(new Rectangle(20, 20, 250, 150));
			internalFrame.getContentPane().add(getPanel(), BorderLayout.NORTH);
			internalFrame.getContentPane().add(getPnlApp(), BorderLayout.SOUTH);
			internalFrame.getContentPane().add(getListPanel(),
					BorderLayout.CENTER);
			this.listPanel.add(getPanelManageDrafts());
			internalFrame.setVisible(true);
		}
		return internalFrame;
	}

	/** 
	 * 
	 * getDesktopPane
	 * used to open/launch applications
	 * @return desktop pane
	 */
	private JDesktopPane getDesktopPane() {
		if (desktopPane == null) {
			desktopPane = new JDesktopPane();
			desktopPane.setBounds(0, 0, 1, 1);
		}
		return desktopPane;
	}

	/**
	 * 
	 * getBtnLaunchApp
	 * button to launch application
	 * @return
	 */
	private JButton getBtnLaunchApp() {
		if (btnLaunchApp == null) {
			btnLaunchApp = new JButton("Launch Application");
			btnLaunchApp.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return btnLaunchApp;
	}

	/**
	 * 
	 * getBtnLaunchMail
	 * button to launch email
	 * @return
	 */
	private Button getBtnLaunchMail() {
		if (btnLaunchMail == null) {
			btnLaunchMail = new Button("Launch E-Mail");
		}
		return btnLaunchMail;
	}

	/**
	 * 
	 * getPanel
	 * panel containing a label, textfield and button to manage email
	 * @return
	 */
	private JPanel getPanel() {
		if (pnlMail == null) {
			pnlMail = new JPanel();
			pnlMail.add(getLblEmail());
			pnlMail.add(getTxtFldMail());
			pnlMail.add(getBtnLaunchMail());
		}
		return pnlMail;
	}

	/**
	 * 
	 * getTxtFldMail
	 * email textfield that holds the recipient's email address
	 * @return
	 */
	private JTextField getTxtFldMail() {
		if (txtFldMail == null) {
			txtFldMail = new JTextField();
			txtFldMail.setColumns(10);
		}
		return txtFldMail;
	}

	/**
	 * 
	 * getLblEmail
	 * label identifying email apnel
	 * @return
	 */
	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("Enter E-Mail");
		}
		return lblEmail;
	}

	/**
	 * 
	 * getPnlApp
	 * panel that manages launching documents in their own application
	 * @return
	 */
	private JPanel getPnlApp() {
		if (pnlApp == null) {
			pnlApp = new JPanel();
			pnlApp.add(getLblFile());
			pnlApp.add(getTxtFile());
			pnlApp.add(getBtnFile());
			pnlApp.add(getBtnLaunchApp());
		}
		return pnlApp;
	}

	/** 
	 * 
	 * getTxtFldFile
	 * textfield that holds the path to a document file
	 * @return JTextField
	 */
	private JTextField getTxtFile() {
		if (txtFile == null) {
			txtFile = new JTextField();
			txtFile.setColumns(10);
		}
		return txtFile;
	}

	/**
	 * 
	 * getLblFile
	 * label identifying File
	 * @return label identifying File 
	 */
	private JLabel getLblFile() {
		if (lblFile == null) {
			lblFile = new JLabel("File:");
		}
		return lblFile;
	}

	/**
	 * 
	 * getBtnFile
	 * @return button to browse for file 
	 */
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

		txtFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onLaunchDefaultApplication(e);
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
		if(txtFile.getText().length() > 1){
			action = Desktop.Action.OPEN;

			String fileName = txtFile.getText();
			file = new File(fileName);

			try {
				desktop.open(file);
			} catch (IOException ioe) { ;
			ioe.printStackTrace();
			System.out.println("Cannot perform the given operation to the " + file + " file");
			}
		}
		txtFile.setText(" ");
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
		txtFldMail.setText(" ");
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
				int dot = file.getName().indexOf('.');
				draftName = file.getName().substring(0, dot);
				txtFile.setText(file.getAbsolutePath());
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
			txtFile.setEnabled(true);
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
		txtFile.setEnabled(false);
		btnLaunchApp.setEnabled(false);
		btnFile.setEnabled(false);
	}

	////////////////////////list panel

	/**
	 * 
	 * getListPanel
	 * panel that manages draft list
	 * @return
	 */
	private JPanel getListPanel() {
		// Set the frame characteristics
		listPanel = new JPanel();
		this.listPanel.setLayout(new BorderLayout(0, 0));
		// Create a panel to hold all other components
		topPanel = new JPanel();
		this.topPanel.setMaximumSize(new Dimension(150, 300));
		this.topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		topPanel.setLayout(new BorderLayout());
		listPanel.add(topPanel, BorderLayout.WEST);
		// Create some items to add to the list
		listData = getList();
		// Create the data model for this example
		listData = new Vector<String>();
		fileDisplayName = new ArrayList<String>();
		CreateDataEntryPanel();
		return listPanel;
	}

	/**
	 * 
	 * CreateDataEntryPanel
	 * @return 
	 */
	private JEditorPane CreateDataEntryPanel() {
		// describes limits & how to use panel
		paneDraftLimit = new JEditorPane();
		this.topPanel.add(this.paneDraftLimit, BorderLayout.NORTH);
		this.paneDraftLimit.setSize(getPreferredSize());
		paneDraftLimit.setBackground(UIManager.getColor("Button.background"));
		paneDraftLimit.setBackground(UIManager.getColor("CheckBox.light"));
		paneDraftLimit.setEditable(false);
		paneDraftLimit
		.setText("Maximum Drafts permitted is 3.\r\nTo add a draft, Browse "
				+ "files, select\r\ndraft, click add. When you \r\ncomplete a"
				+ " draft, delete it so you\r\nmay add more. To open a draft "
				+ "in\r\nthe list, click on it, then click Open.");

		// Create a panel to hold all other components
		panelDraftButtons = new JPanel();
		topPanel.add(panelDraftButtons, BorderLayout.SOUTH);
		panelDraftButtons.setLayout(new FlowLayout());
		// Create function buttons
		// add draft to list and display
		addButton = new JButton("Add");
		panelDraftButtons.add(addButton);
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == addButton) {
					// Get the text field value
					String stringValue = txtFile.getText();	
					txtFile.setText(" ");
					// Add this item to the list and refresh
					if (stringValue != null && stringValue.length() > 1 
							&& listData.size() < 3) {
						listData.addElement(stringValue);
						fileDisplayName.add(draftName);
						//	listbox.setListData(listData);
						displayDraft();	
						panelManageDrafts.repaint();
					}
				}
			}
		});


				

		// remove draft from list
		removeButton = new JButton("Delete");
		panelDraftButtons.add(removeButton);

		removeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {

				if (event.getSource() == removeButton) {
					if(rdbtnDraft1.isSelected()){
						listData.remove(txtDraft1.getText());
						txtDraft1.setText(" ");
						lblDraft1.setText("Draft 1");
					}else if(rdbtnDraft2.isSelected()){
						listData.remove(txtDraft2.getText());
						txtDraft2.setText(" ");
						lblDraft2.setText("Draft 2");
					}else if(rdbtnDraft3.isSelected()){
						listData.remove(txtDraft3.getText());
						txtDraft3.setText(" ");
						lblDraft3.setText("Draft 3");
					}
					displayDraft();	
					panelManageDrafts.repaint();
				}
			}
		});
		// open draft in its own application
		panelDraftButtons.add(getOpenButton());
		return paneDraftLimit;
	}

	// display draft names
	private void displayDraft() {				
		if(listData.size() == 1){
			txtDraft1.setText(listData.get(0));
			lblDraft1.setText(fileDisplayName.get(0));
			txtDraft2.setText(" ");
			lblDraft2.setText("Draft 2");
			txtDraft3.setText(" ");
			lblDraft3.setText("Draft 3");
			rdbtnDraft1.setSelected(true); 
		}
		if(listData.size() == 2){
			txtDraft1.setText(listData.get(0));
			lblDraft1.setText(fileDisplayName.get(0));
			txtDraft2.setText(listData.get(1));
			lblDraft2.setText(fileDisplayName.get(1));
			txtDraft3.setText(" ");
			lblDraft3.setText("Draft 3");
			rdbtnDraft2.setSelected(true); 
		}
		if(listData.size() == 3){
			txtDraft1.setText(listData.get(0));
			lblDraft1.setText(fileDisplayName.get(0));
			txtDraft2.setText(listData.get(1));
			lblDraft2.setText(fileDisplayName.get(1));
			txtDraft3.setText(listData.get(2));
			lblDraft3.setText(fileDisplayName.get(2));
			rdbtnDraft3.setSelected(true); 
		}
	}

	// Handler for list selection changes
	public void valueChanged(ListSelectionEvent event) {
		// See if this is a listbox selection and the
		// event stream has settled
		if (event.getSource() == listbox && !event.getValueIsAdjusting()) {
			listbox.getSelectedValue();
		}
	}

	// save drafts fro this user
	private Vector<String> getList() {
		listData = new Vector<String>();
		if (listData.size() == 0) {
			listData.add(0, "You have not saved any drafts yet");
		}else{
			for (int i = 0; i < myUser.getDrafts().size(); i++) {
				listData.add(myUser.getDrafts().get(i).getMyFilePath());
			}
		}
		return listData;
	}



	//////////////////////// draft panels


	/**
	 * 
	 * getPnlDraftspacer
	 * @return panel used for spacing
	 */
	private JPanel getPnlDraftByName() {
		if (pnlDraftByName == null) {
			pnlDraftByName = new JPanel();
			pnlDraftByName.setLocation(179, 0);
			pnlDraftByName.setSize(new Dimension(201, 155));
			GroupLayout gl_pnlDraftByName = new GroupLayout(pnlDraftByName);
			gl_pnlDraftByName.setHorizontalGroup(
					gl_pnlDraftByName.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlDraftByName.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlDraftByName.createParallelGroup(Alignment.LEADING)
									.addComponent(getLblDraft2(), GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
									.addComponent(getLblDraft3(), Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
									.addComponent(getLblDraft1(), GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
									.addContainerGap())
					);
			gl_pnlDraftByName.setVerticalGroup(
					gl_pnlDraftByName.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlDraftByName.createSequentialGroup()
							.addGap(33)
							.addComponent(getLblDraft1())
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(getLblDraft2())
							.addGap(11)
							.addComponent(getLblDraft3())
							.addContainerGap(58, Short.MAX_VALUE))
					);
			pnlDraftByName.setLayout(gl_pnlDraftByName);
		}
		return pnlDraftByName;
	}

	/**
	 * 
	 * getPanelDraftFiletxt
	 * @return panel with textFields for draft path locations 
	 */
	private JPanel getPanelDraftFiletxt() {
		if (panelDraftFiletxt == null) {
			panelDraftFiletxt = new JPanel();
			panelDraftFiletxt.setLocation(386, 0);
			panelDraftFiletxt.setSize(new Dimension(46, 155));
			panelDraftFiletxt.setLayout(null);
			panelDraftFiletxt.add(getTxtDraft3());
			panelDraftFiletxt.add(getTxtDraft2());
			panelDraftFiletxt.add(getTxtDraft1());
		}

		return panelDraftFiletxt;
	}

	/**
	 * 
	 * getTxtDraft1
	 * @return textfield for draft1
	 */
	private JTextField getTxtDraft1() {
		if (txtDraft1 == null) {
			txtDraft1 = new JTextField();
			txtDraft1.setBounds(53, 31, 168, 20);
			txtDraft1.setHorizontalAlignment(SwingConstants.RIGHT);
			txtDraft1.setEditable(false);
			txtDraft1.setColumns(10);
		}
		return txtDraft1;
	}

	/**
	 * 
	 * getTxtDraft2
	 * @return textfield for draft2
	 */
	private JTextField getTxtDraft2() {
		if (txtDraft2 == null) {
			txtDraft2 = new JTextField();
			txtDraft2.setBounds(53, 57, 168, 20);
			txtDraft2.setHorizontalAlignment(SwingConstants.RIGHT);
			txtDraft2.setEditable(false);
			txtDraft2.setColumns(10);
		}
		return txtDraft2;
	}

	/**
	 * 
	 * getTxtDraft3
	 * @return textfield for draft3
	 */
	private JTextField getTxtDraft3() {
		if (txtDraft3 == null) {
			txtDraft3 = new JTextField();
			txtDraft3.setBounds(53, 83, 168, 20);
			txtDraft3.setHorizontalAlignment(SwingConstants.RIGHT);
			txtDraft3.setEditable(false);
			txtDraft3.setColumns(10);
		}
		return txtDraft3;
	}

	/** 
	 * 
	 * getOpenButton
	 * @return button to open a draft from the list
	 */
	private JButton getOpenButton() {
		if (OpenButton == null) {
			OpenButton = new JButton("Open");
		}
		
		OpenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				onOpenDraft(evt);
			}
			private void onOpenDraft(ActionEvent evt) {						
				action = Desktop.Action.OPEN;						
				try {
					if(draft != null && listData.contains(draft)){
						if(rdbtnDraft1.isSelected()){
							draft = txtDraft1.getText();
						}else if(rdbtnDraft2.isSelected()){
							draft = txtDraft2.getText();
						}else if(rdbtnDraft3.isSelected()){
							draft = txtDraft3.getText();
						}
						file = new File(draft);
					}
					desktop.open(file);
				} catch (IOException ioe) {        	
					ioe.printStackTrace();
					System.out.println("Cannot perform the given operation to the " + file + " file");
				}
				panelManageDrafts.repaint();
			}
		});
		return OpenButton;
	}

	/**
	 * 
	 * getPanelManageDrafts
	 * @return panel with list of drafts, radiobuttons and textfields
	 */
	private JPanel getPanelManageDrafts() {
		if (panelManageDrafts == null) {
			panelManageDrafts = new JPanel();
			panelManageDrafts.setLayout(null);

			panelManageDrafts.add(getPanelDraftRadioBtn());
			panelManageDrafts.add(getPnlDraftByName());
			panelManageDrafts.add(getPanelDraftFiletxt());
		}
		return panelManageDrafts;
	}
	private JLabel getLblDraft1() {
		if (lblDraft1 == null) {
			lblDraft1 = new JLabel("Draft 1 ");
		}
		return lblDraft1;
	}
	private JLabel getLblDraft2() {
		if (lblDraft2 == null) {
			lblDraft2 = new JLabel("Draft 2 ");
		}
		return lblDraft2;
	}
	private JLabel getLblDraft3() {
		if (lblDraft3 == null) {
			lblDraft3 = new JLabel("Draft 3 ");
		}
		return lblDraft3;
	}
	private JPanel getPanelDraftRadioBtn() {
		if (panelDraftRadioBtn == null) {
			panelDraftRadioBtn = new JPanel();
			panelDraftRadioBtn.setBounds(10, 11, 97, 133);
			GroupLayout gl_panelDraftRadioBtn = new GroupLayout(panelDraftRadioBtn);
			gl_panelDraftRadioBtn.setHorizontalGroup(
					gl_panelDraftRadioBtn.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelDraftRadioBtn.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_panelDraftRadioBtn.createParallelGroup(Alignment.LEADING)
									.addComponent(getrdbtnDraft1(), GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
									.addComponent(getrdbtnDraft2(), GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
									.addComponent(getrdbtnDraft3(), GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)))
					);
			gl_panelDraftRadioBtn.setVerticalGroup(
					gl_panelDraftRadioBtn.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelDraftRadioBtn.createSequentialGroup()
							.addGap(21)
							.addComponent(getrdbtnDraft1())
							.addGap(3)
							.addComponent(getrdbtnDraft2())
							.addGap(3)
							.addComponent(getrdbtnDraft3()))
					);
			btnGroup = new ButtonGroup();
			rdbtnDraft1.setSelected(true); 
			btnGroup.add(rdbtnDraft1);
			btnGroup.add(rdbtnDraft2);
			btnGroup.add(rdbtnDraft3);
			panelDraftRadioBtn.setLayout(gl_panelDraftRadioBtn);
		}


		return panelDraftRadioBtn;
	}

	private JRadioButton getrdbtnDraft1() {
		if (rdbtnDraft1 == null) {
			rdbtnDraft1 = new JRadioButton("Draft 1");
		}
		return rdbtnDraft1;
	}
	private JRadioButton getrdbtnDraft2() {
		if (rdbtnDraft2 == null) {
			rdbtnDraft2 = new JRadioButton("Draft 2");
		}
		return rdbtnDraft2;
	}

	/**
	 * 
	 * getrdbtnDraft3
	 * @return radio button 3 which if selected will allow user to open
	 * 
	 *  or delete draft 3
	 */
	private JRadioButton getrdbtnDraft3() {
		if (rdbtnDraft3 == null) {
			rdbtnDraft3 = new JRadioButton("Draft 3");
		}
		return rdbtnDraft3;
	}
	/**
	 * 
	 * getRadioButtonToggle
	 * @return button group where only 1 button can be selected
	 */
	private ButtonGroup getRadioButtonToggle() {	  
		// Create the button group to keep only one selected.
		btnGroup = new ButtonGroup();
		rdbtnDraft1.setSelected(true); 
		btnGroup.add(rdbtnDraft1);
		btnGroup.add(rdbtnDraft2);
		btnGroup.add(rdbtnDraft3);
		return btnGroup;
	}
}