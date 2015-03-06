package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Library implements Serializable {

	private static final String KEYWORD_DELIMITER = ",";

	private static final String ORIGINAL_FILE_DELIMITER = "\t";

	private static final long serialVersionUID = 383391927594755642L;

	private static final String LIBRARY_SAVE_FILE = "library.ser";

	private static final String LIBRARY_ORIGINAL = "answers.dat";

	private final List<QA> myLibrary;
	
	private boolean myIsAdminFlag;

	private Library(final List<QA> library) {
		myLibrary = new ArrayList<QA>(library);
	}
	
	public void setUser(final User user) {
		myIsAdminFlag = user.isAdmin();
	}

	public List<QA> searchByKeyword(final String keyword) {
		final List<QA> result = new ArrayList<QA>();
		for (QA response : myLibrary) {
			if (response.getKeywords().toString().toLowerCase().contains(keyword.toLowerCase())) {
				result.add(response);
			}
		}
		return result;
	}

	public List<QA> searchByQuestion(final String question) {
		final List<QA> result = new ArrayList<QA>();
		for (QA response : myLibrary) {
			if (response.getQuestion().toLowerCase().contains(question.toLowerCase())) {
				result.add(response);
			}
		}
		return result;
	}

	public List<QA> searchByAnswer(final String answer) {
		final List<QA> result = new ArrayList<QA>();
		for (QA response : myLibrary) {
			if (response.getAnswer().toLowerCase().contains(answer.toLowerCase())) {
				result.add(response);
			}
		}
		return result;
	}

	public List<QA> browseByCategory(final String cat) {
		final List<QA> result = new ArrayList<QA>();
		for (QA response : myLibrary) {
			if (response.getCategory().toLowerCase().contains(cat.toLowerCase())) {
				result.add(response);
			}
		}
		return result;
	}

	public Set<String> getAvailableCatergories() {
		final Set<String> result = new HashSet<String>();
		for (QA response : myLibrary) {
			result.add(response.getCategory());
		}
		return result;
	}

	public void addResponse(final QA response) throws IllegalStateException {
		if (!myIsAdminFlag) {
			throw new IllegalStateException("User is not admin");
		}
		myLibrary.add(response);
	}

	public void removeResponse(final QA response) throws IllegalStateException {
		if (!myIsAdminFlag) {
			throw new IllegalStateException("User is not admin");
		}
		myLibrary.remove(response);
	}

	public void serialize() throws IOException {
		final FileOutputStream fileOut = new FileOutputStream(LIBRARY_SAVE_FILE);
		final ObjectOutputStream out = new ObjectOutputStream(fileOut);
		final Library serializeMe = new Library(myLibrary);
		out.writeObject(serializeMe);
		out.close();
		fileOut.close();
	}

	public static Library deserialize() {
		try {
			final FileInputStream fileIn = new FileInputStream(
					LIBRARY_SAVE_FILE);
			final ObjectInputStream objIn = new ObjectInputStream(fileIn);
			final Library lib = (Library) objIn.readObject();
			objIn.close();
			fileIn.close();
			return lib;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Library createFromOriginal() {
		final List<QA> library = new ArrayList<QA>();
		try {
			final Scanner in = new Scanner(new File(LIBRARY_ORIGINAL));
			while (in.hasNextLine()) {
				final String[] line = in.nextLine().trim()
						.split(ORIGINAL_FILE_DELIMITER);
				if (line.length < 4) {
					in.close();
					System.err.println("Library File Improperly Formatted!");
					return null;
				}
				final List<String> keywords = new ArrayList<String>();
				for (String key : line[1].split(KEYWORD_DELIMITER)) {
					keywords.add(key);
				}
				final String category = line[0];
				final String question = line[2];
				final String answer = line[3];
				final QA response = new QA(question, answer, keywords, category);
				library.add(response);
			}
			in.close();
			return new Library(library);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
