package subpages;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import model.Library;
import model.QA;

/**
 * This is a sub panel of the GUI that allows an admin to edit, add to, or
 * delete from the library database.
 * 
 * @author Robert Ogden rogden33
 */
public class EditPage extends JPanel {

	private static final int VERTICAL_SPACING = 20;

	private final Library myLibrary;

	public EditPage(final Library library) {
		super(new BorderLayout());
		myLibrary = library;
		addContent();
	}

	private void addContent() {
		final JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		for (QA response : myLibrary.getAllResponses()) {
			listPanel.add(new QADisplayPanel(this, response));
			listPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
			listPanel.add(new JSeparator());
			listPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
		}
		final JScrollPane scroll = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setViewportView(listPanel);
		add(scroll, BorderLayout.CENTER);
		final JButton addButton = new JButton("Add Response");
		final EditPage myself = this;
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				new EditingPane(myself, null, "", "", "", "");
			}
		});
		add(addButton, BorderLayout.SOUTH);
		System.out.println("Added content");
	}

	public void addResponse(final QA response) {
		myLibrary.addResponse(response);
		repaint();
	}

	public void deleteResponse(final QA response) {
		myLibrary.removeResponse(response);
		repaint();
	}

	public void replace(final QA old, final QA updated) {
		myLibrary.removeResponse(old);
		myLibrary.addResponse(updated);
		repaint();
	}

	@Override
	public void repaint() {
		if (myLibrary != null) {
			addContent();
		}
		super.repaint();
	}
}
