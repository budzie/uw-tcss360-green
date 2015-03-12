package subpages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.QA;
import view.GUI;

public class QADisplayPanel extends JPanel {

	private static final Font CATEGORY_FONT = new Font(Font.SANS_SERIF,
			Font.BOLD, 20);

	private static final Font LABEL_FONT = new Font(Font.SERIF, Font.PLAIN, 20);

	private static final int SPACING = 10;

	private final EditPage myParent;

	private final QA myResponse;

	private String myCategory;

	private String myQuestion;

	private String myAnswer;

	private String myKeywords;

	public QADisplayPanel(final EditPage parent, final QA response) {
		super(new BorderLayout());
		myParent = parent;
		myResponse = response;
		myCategory = myResponse.getCategory();
		myQuestion = myResponse.getQuestion();
		myAnswer = myResponse.getAnswer();
		myKeywords = myResponse.getKeywordString();
		addLeftPanel();
		addCenterPanel();
	}

	private void addLeftPanel() {
		final JPanel masterLeftPanel = new JPanel();
		final JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		final JButton editButton = new JButton("Edit");
		editButton.setAlignmentX(CENTER_ALIGNMENT);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				new EditingPane(myParent, myResponse, myCategory, myQuestion,
						myAnswer, myKeywords);
			}
		});
		final JButton deleteButton = new JButton("Delete");
		deleteButton.setAlignmentX(CENTER_ALIGNMENT);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				myParent.deleteResponse(myResponse);
			}
		});
		leftPanel.add(Box.createVerticalGlue());
		leftPanel.add(editButton);
		leftPanel.add(Box.createVerticalStrut(SPACING));
		leftPanel.add(deleteButton);
		leftPanel.add(Box.createVerticalGlue());
		masterLeftPanel.add(Box.createHorizontalStrut(SPACING));
		masterLeftPanel.add(leftPanel);
		masterLeftPanel.add(Box.createHorizontalStrut(SPACING));
		add(masterLeftPanel, BorderLayout.WEST);
	}

	private void addCenterPanel() {
		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		final JTextArea category = createTextElement("Category: " + myCategory,
				CATEGORY_FONT);
		final JTextArea questionLabel = createTextElement("Question",
				LABEL_FONT);
		final JTextArea question = createTextElement(myQuestion, null);
		final JTextArea answerLabel = createTextElement("Answer", LABEL_FONT);
		final JTextArea answer = createTextElement(myAnswer, null);
		final JTextArea keywordLabel = createTextElement("Keywords", LABEL_FONT);
		final JTextArea keywords = createTextElement(myKeywords, null);
		centerPanel.add(category);
		centerPanel.add(Box.createVerticalStrut(SPACING));
		centerPanel.add(questionLabel);
		centerPanel.add(question);
		centerPanel.add(Box.createVerticalStrut(SPACING));
		centerPanel.add(answerLabel);
		centerPanel.add(answer);
		centerPanel.add(Box.createVerticalStrut(SPACING));
		centerPanel.add(keywordLabel);
		centerPanel.add(keywords);
		add(centerPanel, BorderLayout.CENTER);
	}

	private JTextArea createTextArea(final String text, final Font font) {
		final JTextArea result = new JTextArea(text);
		result.setWrapStyleWord(true);
		result.setLineWrap(true);
		if (font == null) {
			result.setFont(UIManager.getFont("Label.font"));
		} else {
			result.setFont(font);
		}
		return result;
	}

	private JTextArea createTextElement(final String text, final Font font) {
		final JTextArea result = createTextArea(text, font);
		result.setOpaque(false);
		result.setEditable(false);
		result.setFocusable(false);
		result.setBackground(UIManager.getColor("Label.background"));
		result.setBorder(UIManager.getBorder("Label.border"));
		return result;
	}

	private void repaintDisplayPanel() {
		repaint();
	}

}
