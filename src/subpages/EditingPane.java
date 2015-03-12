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

public class EditingPane extends JDialog {

	private static final Font LABEL_FONT = new Font(Font.SERIF, Font.PLAIN, 20);

	private static final int SPACING = 10;

	private static final Dimension myPreferredSize = new Dimension(
			GUI.SCREEN_SIZE.width / 3, GUI.SCREEN_SIZE.height / 3);
	
	private final EditPage myParent;
	
	private final QA myResponse;

	private String myCategory;

	private String myQuestion;

	private String myAnswer;

	private String myKeywords;

	public EditingPane(final EditPage parent, final QA response, final String category, final String question,
			final String answer, final String keywords) {
		super();
		myCategory = category;
		myQuestion = question;
		myAnswer = answer;
		myKeywords = keywords;
		myParent = parent;
		myResponse = response;
		setLayout(new BorderLayout());
		setTitle("Edit Response");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addContent();
		setPreferredSize(myPreferredSize);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	private void addContent() {
		final JTextArea categoryLabel = createTextElement("Category",
				LABEL_FONT);
		final JTextArea categoryField = createTextArea(myCategory, null);
		categoryField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(final DocumentEvent e) {
				update();
			}

			public void insertUpdate(final DocumentEvent e) {
				update();
			}

			public void removeUpdate(final DocumentEvent e) {
				update();
			}

			private void update() {
				myCategory = categoryField.getText();
			}
		});
		final JTextArea questionLabel = createTextElement("Question",
				LABEL_FONT);
		final JTextArea questionArea = createTextArea(myQuestion, null);
		questionArea.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(final DocumentEvent e) {
				update();
			}

			public void insertUpdate(final DocumentEvent e) {
				update();
			}

			public void removeUpdate(final DocumentEvent e) {
				update();
			}

			private void update() {
				myQuestion = questionArea.getText();
			}
		});
		final JTextArea answerLabel = createTextElement("Answer", LABEL_FONT);
		final JTextArea answerArea = createTextArea(myAnswer, null);
		answerArea.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(final DocumentEvent e) {
				update();
			}

			public void insertUpdate(final DocumentEvent e) {
				update();
			}

			public void removeUpdate(final DocumentEvent e) {
				update();
			}

			private void update() {
				myAnswer = answerArea.getText();
			}
		});
		final JTextArea keywordLabel = createTextElement(
				"Keywords (Separated by Commas)", LABEL_FONT);
		final JTextArea keywordField = createTextArea(myKeywords, null);
		keywordField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(final DocumentEvent e) {
				update();
			}

			public void insertUpdate(final DocumentEvent e) {
				update();
			}

			public void removeUpdate(final DocumentEvent e) {
				update();
			}

			private void update() {
				myKeywords = answerArea.getText();
			}
		});
		final JPanel buttonPanel = new JPanel();
		final JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final QA updated = new QA(myCategory, myQuestion, myAnswer,
						myKeywords);
				if (myResponse == null) {
					myParent.addResponse(updated);
				} else {
					myParent.replace(myResponse, updated);
				}
				dispose();
			}
		});
		final JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);
		final JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
		masterPanel.add(categoryLabel);
		masterPanel.add(categoryField);
		masterPanel.add(Box.createVerticalStrut(SPACING));
		masterPanel.add(questionLabel);
		masterPanel.add(questionArea);
		masterPanel.add(Box.createVerticalStrut(SPACING));
		masterPanel.add(answerLabel);
		masterPanel.add(answerArea);
		masterPanel.add(Box.createVerticalStrut(SPACING));
		masterPanel.add(keywordLabel);
		masterPanel.add(keywordField);
		masterPanel.add(Box.createVerticalStrut(SPACING));
		masterPanel.add(new JSeparator());
		final JScrollPane scroll = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setViewportView(masterPanel);
		add(scroll);
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
}
