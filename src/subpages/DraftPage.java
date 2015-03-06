package subpages;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is a sub panel of the GUI that constitutes the Drafts Page for the
 * given user. This page contains links to the open drafts for a given User and
 * provides links in order to open them with the default system application. The
 * number of drafts is limited by the max number of drafts, per requirements
 * business rule.
 * 
 * @author Robert Ogden rogden33
 */
public class DraftPage extends JPanel {

	public DraftPage() {
		add(new JLabel("drafts page"));
	}
}
