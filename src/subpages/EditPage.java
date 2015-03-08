package subpages;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
		final List<QA> library = myLibrary.getAllResponses();
		for (int i = 0; i < library.size(); i++) {
			final QADisplayPanel responsePanel = new QADisplayPanel(this,
					library.get(i));
			listPanel.add(responsePanel);
			listPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
			listPanel.add(new JSeparator());
			listPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
		}
		final JScrollPane scroll = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setViewportView(listPanel);
		add(scroll, BorderLayout.CENTER);
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
}
