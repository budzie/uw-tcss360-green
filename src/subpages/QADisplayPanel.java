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
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.QA;

public class QADisplayPanel extends JPanel {
	
	private static final Font CATEGORY_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	
	private static final Font LABEL_FONT = new Font(Font.SERIF, Font.PLAIN, 20);
	
	private static final Dimension MAX_ELEMENT_SIZE = new Dimension(300, 1000);

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
				new EditingPane();
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
		leftPanel.add(Box.createVerticalStrut(10));
		leftPanel.add(deleteButton);
		leftPanel.add(Box.createVerticalGlue());
		masterLeftPanel.add(leftPanel);
		add(masterLeftPanel, BorderLayout.WEST);
	}

	private void addCenterPanel() {
		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		final JTextArea category = createTextElement("Category: " + myCategory,
				CATEGORY_FONT);
		final JTextArea questionLabel = createTextElement("Question", new Font(
				Font.SERIF, Font.PLAIN, 20));
		final JTextArea question = createTextElement(myQuestion, null);
		final JTextArea answerLabel = createTextElement("Answer", LABEL_FONT);
		final JTextArea answer = createTextElement(myAnswer, null);
		final JTextArea keywordLabel = createTextElement("Keywords", LABEL_FONT);
		final JTextArea keywords = createTextElement(myKeywords, null);
		centerPanel.add(category);
		centerPanel.add(Box.createVerticalStrut(15));
		centerPanel.add(questionLabel);
		centerPanel.add(question);
		centerPanel.add(Box.createVerticalStrut(15));
		centerPanel.add(answerLabel);
		centerPanel.add(answer);
		centerPanel.add(Box.createVerticalStrut(15));
		centerPanel.add(keywordLabel);
		centerPanel.add(keywords);
		add(centerPanel, BorderLayout.CENTER);
	}
	
	private JTextArea createTextArea(final String text, final Font font) {
		final JTextArea result = new JTextArea(text);
		result.setMaximumSize(MAX_ELEMENT_SIZE);
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

	private class EditingPane extends JDialog {

		public EditingPane() {
			super();
			setTitle("Edit Response");
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			addContent();
			pack();
			setLocationRelativeTo(null);
//			setResizable(false);
			setVisible(true);
		}

		private void addContent() {
			final JTextArea categoryLabel = createTextElement("Category", LABEL_FONT);
			final JTextArea categoryField = createTextArea(myCategory, null);
			categoryField.getDocument().addDocumentListener(
					new DocumentListener() {
						public void changedUpdate(DocumentEvent arg0) {
							update();
						}

						public void insertUpdate(DocumentEvent arg0) {
							update();
						}

						public void removeUpdate(DocumentEvent arg0) {
							update();
						}

						private void update() {
							myCategory = categoryField.getText();
						}
					});
			final JTextArea questionLabel = createTextElement("Question", LABEL_FONT);
			final JTextArea questionArea = createTextArea(myQuestion, null);
			questionArea.getDocument().addDocumentListener(
					new DocumentListener() {
						public void changedUpdate(DocumentEvent arg0) {
							update();
						}

						public void insertUpdate(DocumentEvent arg0) {
							update();
						}

						public void removeUpdate(DocumentEvent arg0) {
							update();
						}

						private void update() {
							myQuestion = questionArea.getText();
						}
					});
			final JTextArea answerLabel = createTextElement("Answer", LABEL_FONT);
			final JTextArea answerArea = createTextArea(myAnswer, null);
			answerArea.getDocument().addDocumentListener(
					new DocumentListener() {
						public void changedUpdate(DocumentEvent arg0) {
							update();
						}

						public void insertUpdate(DocumentEvent arg0) {
							update();
						}

						public void removeUpdate(DocumentEvent arg0) {
							update();
						}

						private void update() {
							myAnswer = answerArea.getText();
						}
					});
			final JTextArea keywordLabel = createTextElement("Keywords (Separated by Commas)", LABEL_FONT);
			final JTextArea keywordField = createTextArea(myKeywords, null);
			keywordField.getDocument().addDocumentListener(
					new DocumentListener() {
						public void changedUpdate(DocumentEvent arg0) {
							update();
						}

						public void insertUpdate(DocumentEvent arg0) {
							update();
						}

						public void removeUpdate(DocumentEvent arg0) {
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
					myParent.replace(myResponse, updated);
					repaintDisplayPanel();
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
			final JPanel masterPanel = new JPanel();
			masterPanel.setLayout(new BoxLayout(masterPanel, BoxLayout.Y_AXIS));
			masterPanel.add(categoryLabel);
			masterPanel.add(categoryField);
			masterPanel.add(Box.createVerticalStrut(10));
			masterPanel.add(questionLabel);
			masterPanel.add(questionArea);
			masterPanel.add(Box.createVerticalStrut(10));
			masterPanel.add(answerLabel);
			masterPanel.add(answerArea);
			masterPanel.add(Box.createVerticalStrut(10));
			masterPanel.add(keywordLabel);
			masterPanel.add(keywordField);
			masterPanel.add(Box.createVerticalStrut(10));
			masterPanel.add(new JSeparator());
			masterPanel.add(buttonPanel);
			System.out.println(masterPanel.getPreferredSize());
			add(masterPanel);
		}

	}

}
