package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// immutable
public class QA implements Serializable {

	private static final long serialVersionUID = 3846432025191449293L;

	private final String myQuestion;

	private final String myAnswer;
	
	private final List<String> myKeywords;

	private final String myCategory;

	public QA(final String question, final String answer, final List<String> keywords,
			final String category) {
		myQuestion = question;
		myAnswer = answer;
		myKeywords = new ArrayList<String>(keywords);
		myCategory = category;
	}

	/**
	 * @return the myQuestion
	 */
	public String getQuestion() {
		return myQuestion;
	}

	/**
	 * @return the myAnswer
	 */
	public String getAnswer() {
		return myAnswer;
	}
	
	public List<String> getKeywords() {
		return new ArrayList<String>(myKeywords);
	}

	/**
	 * @return the myCategories
	 */
	public String getCategory() {
		return myCategory;
	}

	@Override
	public int hashCode() {
		final String values = myAnswer + myQuestion + myCategory + myKeywords.toString();
		return values.hashCode() + 1;
	}

	@Override
	public boolean equals(final Object other) {
		if (other.getClass() != this.getClass()) {
			return false;
		}
		final QA otherQA = (QA) other;
		boolean result = true;
		result = result && otherQA.myAnswer.equals(myAnswer);
		result = result && otherQA.myQuestion.equals(myQuestion);
		result = result && otherQA.myCategory.equals(myCategory);
		result = result && otherQA.myKeywords.equals(myKeywords);
		return result;
	}

}
