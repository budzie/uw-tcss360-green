package subpages;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.QA;

public class QADisplayPanel extends JPanel {

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
		final JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		final JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				new EditingPane();
			}
		});
		final JButton deleteButton = new JButton("Delete");
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
		add(leftPanel, BorderLayout.WEST);
	}

	private void addCenterPanel() {
		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		final JLabel category = new JLabel("Category: "
				+ myCategory);
		category.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		category.setHorizontalAlignment(JLabel.CENTER);
		final JLabel questionLabel = new JLabel("Question");
		questionLabel.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		questionLabel.setHorizontalAlignment(JLabel.CENTER);
		final JLabel question = new JLabel(myQuestion);
		final JLabel answerLabel = new JLabel("Answer");
		answerLabel.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		answerLabel.setHorizontalAlignment(JLabel.CENTER);
		final JLabel answer = new JLabel(myAnswer);
		final JLabel keywordLabel = new JLabel("Keywords");
		keywordLabel.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		keywordLabel.setHorizontalAlignment(JLabel.CENTER);
		final JLabel keywords = new JLabel(myKeywords);
		centerPanel.add(category);
		centerPanel.add(questionLabel);
		centerPanel.add(question);
		centerPanel.add(answerLabel);
		centerPanel.add(answer);
		centerPanel.add(keywordLabel);
		centerPanel.add(keywords);
		add(centerPanel, BorderLayout.CENTER);
	}
	
	private void repaintDisplayPanel() {
		repaint();
	}

	private class EditingPane extends JDialog {

		public EditingPane() {
			super();
			setTitle("Edit Response");
			setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
			addContent();
			pack();
			setResizable(false);
		}

		private void addContent() {
			final JLabel categoryLabel = new JLabel("Question");
			categoryLabel.setFont(new Font(Font.SERIF, Font.BOLD, 12));
			categoryLabel.setHorizontalAlignment(JLabel.CENTER);
			final JTextField categoryField = new JTextField(myCategory);
			categoryField.getDocument().addDocumentListener(new DocumentListener() {
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
			final JLabel questionLabel = new JLabel("Question");
			questionLabel.setFont(new Font(Font.SERIF, Font.BOLD, 12));
			questionLabel.setHorizontalAlignment(JLabel.CENTER);
			final JTextArea questionArea = new JTextArea(
					myQuestion);
			questionArea.getDocument().addDocumentListener(new DocumentListener() {
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
			final JScrollPane questionPane = new JScrollPane(questionArea,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			final JLabel answerLabel = new JLabel("Answer");
			answerLabel.setFont(new Font(Font.SERIF, Font.BOLD, 12));
			answerLabel.setHorizontalAlignment(JLabel.CENTER);
			final JTextArea answerArea = new JTextArea(myAnswer);
			answerArea.getDocument().addDocumentListener(new DocumentListener() {
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
			final JScrollPane answerPane = new JScrollPane(answerArea,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			final JLabel keywordLabel = new JLabel("Keywords (Separated by Commas)");
			keywordLabel.setFont(new Font(Font.SERIF, Font.BOLD, 12));
			keywordLabel.setHorizontalAlignment(JLabel.CENTER);
			final JTextField keywordField = new JTextField(myKeywords);
			keywordField.getDocument().addDocumentListener(new DocumentListener() {
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
					final QA updated = new QA(myCategory, myQuestion, myAnswer, myKeywords);
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
			masterPanel.add(questionLabel);
			masterPanel.add(questionPane);
			masterPanel.add(answerLabel);
			masterPanel.add(answerPane);
			masterPanel.add(keywordLabel);
			masterPanel.add(keywordField);
			masterPanel.add(buttonPanel);
			add(masterPanel);
		}

	}

}
