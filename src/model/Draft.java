package model;

import java.io.File;
import java.io.Serializable;

import fileio.DraftRetriever;

/**
 * @author Sally Budack TCSS360 Winter 2015 
 * @author Robert Ogden
 *
 */
public class Draft implements Serializable {

	private static final long serialVersionUID = -7464859714051139421L;

	private final String myFilePath;

	/**
	 * Draft
	 * 
	 * @param filePath
	 */
	public Draft(final String filePath) {
		myFilePath = filePath;
	}

	/**
	 * getPath
	 * 
	 * @return file path of draft
	 */
	public File getPath() {
		return new File(myFilePath);

	}

	// for testing purposes, draft should open from constructor when inside GUI
	public static void main(String[] args) {
		openDraft();
	}

	/**
	 * openDraft opens draft, new email or any document in its own application
	 */
	public static void openDraft() {
		DraftRetriever dr = new DraftRetriever();
		dr.openDraft();
	}
}
