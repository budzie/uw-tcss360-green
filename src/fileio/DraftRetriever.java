package fileio;
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


/*
 * DraftRetriever.java
 * open existing files to edit or print
 * open new email
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;


/**
 * @author Oracle
 * @author edited by Sally Budack
 * TCSS360 Winter 2015
 * open email by contact info or browsing
 * open existing file.
 */
public class DraftRetriever extends JFrame {
	
	File myDraftFile;
    // GUI components
    JButton btnLaunchApplication = new JButton("Launch Application");
    JButton btnLaunchEmail = new JButton();
    JRadioButton rbEdit = new JRadioButton("Edit");
    JRadioButton rbOpen = new JRadioButton("Open", true);
    JRadioButton rbPrint = new JRadioButton("Print");
    JTextField txtMailTo = new JTextField();
    JTextField txtFile = new JTextField();
    ButtonGroup bgAppAction = new ButtonGroup();
    JLabel lblMailRecipient = new JLabel("E-mail:");
    JLabel lblFile = new JLabel("File:");
    JButton btnFile = new JButton("Browse");
    JLabel emptyLabel = new JLabel(" ");
    JPanel conLeft = new JPanel();
    JPanel conCenter = new JPanel();
    JPanel conRight = new JPanel();
    JFileChooser fc = new JFileChooser();
    File file;
    
    private Desktop desktop;
    private Desktop.Action action = Desktop.Action.OPEN;
    
    
    /**
     * Creates new form DraftRetriever
     */
    public DraftRetriever() {
        // initialize all gui components
        initComponents();
        // disable buttons that launch browser, email client,
        // disable buttons that open, edit, print files
        disableActions();
        // before any Desktop APIs are used, first check whether the API is
        // supported by this particular VM on this particular host
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            // now enable buttons for actions that are supported.
            enableSupportedActions();
        }
        loadFrameIcon();
        setResizable(false);
    }
    
    /**
     * 
     * openDraft
     * this method launches the GUI
     * The GUI has options to open a new email to create a draft or 
     * open any existing document with its designated application to
     * edit or print. 
     */
    public void openDraft() {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
        	ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DraftRetriever().setVisible(true);
            }
        });
    }
    
    /** Create and show components
     */
    private void initComponents() {
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Draft Retriever");
        
        txtMailTo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onLaunchMail(null);
            }
        });
        
        btnLaunchEmail.setText("Launch Mail");
        btnLaunchEmail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onLaunchMail(evt);
            }
        });
        
        
        txtFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onLaunchDefaultApplication(null);
            }
        });
        
        rbOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onOpenAction(evt);
            }
        });
        
        
        rbEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onEditAction(evt);
            }
        });
        
        
        rbPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onPrintAction(evt);
            }
        });
        
        btnLaunchApplication.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onLaunchDefaultApplication(evt);
            }
        });
        
        btnFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onChooseFile(evt);
            }
        });
        
        Container conFrame = this.getContentPane();
        
        bgAppAction.add(rbOpen);
        bgAppAction.add(rbEdit);
        bgAppAction.add(rbPrint);
        
        // Components layouting
        
        GroupLayout layout = new GroupLayout(conFrame);
        conFrame.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        GroupLayout.SequentialGroup majorHGroup = layout.createSequentialGroup();
        
        // Horizontal group
        
        GroupLayout.ParallelGroup lblHGroup =
                layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        lblHGroup.addComponent(lblMailRecipient, GroupLayout.Alignment.TRAILING);
        lblHGroup.addComponent(lblFile, GroupLayout.Alignment.TRAILING);
        
        GroupLayout.ParallelGroup txtFieldsHGroup =
                layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        txtFieldsHGroup.addComponent(txtMailTo);
        GroupLayout.SequentialGroup rbHGroup = layout.createSequentialGroup();
        rbHGroup.addComponent(rbOpen);
        rbHGroup.addComponent(rbEdit);
        rbHGroup.addComponent(rbPrint);
        txtFieldsHGroup.addGroup(rbHGroup);
        GroupLayout.SequentialGroup fileHGroup = layout.createSequentialGroup();
        fileHGroup.addComponent(txtFile);
        fileHGroup.addComponent(btnFile);
        txtFieldsHGroup.addGroup(fileHGroup);
        
        GroupLayout.ParallelGroup btnHGroup =
                layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        btnHGroup.addComponent(btnLaunchEmail);
        btnHGroup.addComponent(btnLaunchApplication);
        
        majorHGroup.addGroup(lblHGroup);
        majorHGroup.addGroup(txtFieldsHGroup);
        majorHGroup.addGroup(btnHGroup);
        
        layout.setHorizontalGroup(majorHGroup);
        
        // Vertical group
        
        GroupLayout.SequentialGroup majorVGroup = layout.createSequentialGroup();
        
        GroupLayout.ParallelGroup uriVGroup =
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        
        GroupLayout.ParallelGroup mailVGroup =
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        mailVGroup.addComponent(lblMailRecipient);
        mailVGroup.addComponent(txtMailTo);
        mailVGroup.addComponent(btnLaunchEmail);
        
        GroupLayout.ParallelGroup rbVGroup =
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        rbVGroup.addComponent(rbOpen);
        rbVGroup.addComponent(rbEdit);
        rbVGroup.addComponent(rbPrint);
        
        GroupLayout.ParallelGroup fileVGroup =
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        fileVGroup.addComponent(lblFile);
        fileVGroup.addComponent(btnLaunchApplication);
        fileVGroup.addComponent(txtFile);
        fileVGroup.addComponent(btnFile);
        
        majorVGroup.addGroup(uriVGroup);
        majorVGroup.addGroup(mailVGroup);
        majorVGroup.addGroup(rbVGroup);
        majorVGroup.addGroup(fileVGroup);
        
        layout.setVerticalGroup(majorVGroup);
        
        pack();
    }
    
    /**
     * Load the "desktop" icon into our frame window.
     */
    private void loadFrameIcon() {
        URL imgUrl = null;
        ImageIcon imgIcon = null;
        
        imgUrl = DraftRetriever.class.getResource("images/desk32.gif");
        imgIcon = new ImageIcon(imgUrl);
        Image img = imgIcon.getImage();
        this.setIconImage(img);
    }
    
    /**
     * Set the Desktop.Action to PRINT before invoking
     * the default application.
     */
    private void onPrintAction(ActionEvent evt) {
        action = Desktop.Action.PRINT;
    }
    
    /**
     * Set the Desktop.Action to EDIT before invoking
     * the default application.
     */
    private void onEditAction(ActionEvent evt) {
        action = Desktop.Action.EDIT;
    }
    
    /**
     * Set the Desktop.Action to OPEN before invoking
     * the default application.
     */
    private void onOpenAction(ActionEvent evt) {
        action = Desktop.Action.OPEN;
    }
    
    /**
     * Launch the default application associated with a specific
     * filename using the preset Desktop.Action.
     *
     */
    private void onLaunchDefaultApplication(ActionEvent evt) {
    	
        String fileName = txtFile.getText();
         file = new File(fileName);
        
        try {
            switch(action) {
                case OPEN:
                    desktop.open(file);
                    break;
                case EDIT:
                    desktop.edit(file);
                    break;
                case PRINT:
                    desktop.print(file);
                    break;
			default:
				break;
            }
        } catch (IOException ioe) {        	
            ioe.printStackTrace();
            System.out.println("Cannot perform the given operation to the " + file + " file");
        }
    }
    
    
    /**
     * Launch the default email client using the "mailto"
     * protocol and the text supplied by the user.
     *
     */
    private void onLaunchMail(ActionEvent evt) {
        String mailTo = txtMailTo.getText();
        URI uriMailTo = null;
        try {
            if (mailTo.length() > 0) {
                uriMailTo = new URI("mailto", mailTo, null);
                desktop.mail(uriMailTo);
            } else {
                desktop.mail();
            }
        } catch(IOException ioe) {        	
            ioe.printStackTrace();
        } catch(URISyntaxException use) {
            use.printStackTrace();
        }
    }
    
    /**
     * Launch the default browser with the text provided by the
     * user.
     *
     */   
    private void onChooseFile(ActionEvent evt) {
        if (evt.getSource() == btnFile) {
            int returnVal = fc.showOpenDialog(DraftRetriever.this);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                file = fc.getSelectedFile();
                txtFile.setText(file.getAbsolutePath());
                
            }
        }
    }
    
    /**
     * Enable actions that are supported on this host.
     * The actions are: open email client, and
     * open, edit, and print files using their associated application
     */
    
    private void enableSupportedActions() {
        if (desktop.isSupported(Desktop.Action.MAIL)) {
            txtMailTo.setEnabled(true);
            btnLaunchEmail.setEnabled(true);
        }
        if (desktop.isSupported(Desktop.Action.OPEN)) {
            rbOpen.setEnabled(true);
        }
        if (desktop.isSupported(Desktop.Action.EDIT)) {
            rbEdit.setEnabled(true);
        }
        if (desktop.isSupported(Desktop.Action.PRINT)) {
            rbPrint.setEnabled(true);
        }
        if (rbEdit.isEnabled() || rbOpen.isEnabled() || rbPrint.isEnabled()) {
            txtFile.setEnabled(true);
            btnLaunchApplication.setEnabled(true);
            btnFile.setEnabled(true);
        }
    }
    
    /**
     * Disable all graphical components until we know
     * whether their functionality is supported.
     */
    private void disableActions() {
        
        txtMailTo.setEnabled(false);
        btnLaunchEmail.setEnabled(false);
        
        rbEdit.setEnabled(false);
        rbOpen.setEnabled(false);
        rbPrint.setEnabled(false);
        
        txtFile.setEnabled(false);
        btnLaunchApplication.setEnabled(false);
        btnFile.setEnabled(false);
    }
}

