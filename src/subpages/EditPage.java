package subpages;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Library;
import model.QA;

/**
 * This is a sub panel of the GUI that allows an admin to edit, add to, or
 * delete from the library database.
 * 
 * @author Robert Ogden rogden33
 */
public class EditPage extends JPanel {

	private final Library myLibrary;

	public EditPage(final Library library) {
		myLibrary = library;
		addContent();
	}

	private void addContent() {
		final JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		for (QA response : myLibrary.getAllResponses()) {
			listPanel.add(new QADisplayPanel(this, response));
		}
		final JScrollPane scroll = new JScrollPane(listPanel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll);
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
