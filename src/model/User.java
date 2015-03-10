package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Robert Ogden
 * @author Sally Budack
 * TCSS360 Winter 2015
 *
 */
public class User implements Serializable {
	
	/** business rule  */
	public static final int MAX_NUMBER_OF_DRAFTS = 3;
	
	private static final long serialVersionUID = -2126788306291595004L;

	private final String myName;
	
	private final String myUserID;
	
	private final int myPin;
	
	private final boolean myAdminFlag;
	
	private final List<Draft> myDrafts;

	/**
	 * User
	 * @param name
	 * @param userID
	 * @param pin
	 * @param isAdmin
	 */
	public User(final String name, final String userID, final int pin, final boolean isAdmin) {
		myName = name;
		myUserID = userID;
		myPin = pin;
		myAdminFlag = isAdmin;
		myDrafts = new ArrayList<Draft>();
	}
	
	/**
	 * getName
	 * @return name
	 */
	public String getName() {
		return myName;
	}
	
	/**
	 * getUserID
	 * @return userID
	 */
	public String getUserID() {
		return myUserID;
	}

	/**
	 * isAdmin
	 * @return boolean true if admin, false if writer
	 */
	public boolean isAdmin() {
		return myAdminFlag;
	}
	
	/**
	 * getPin
	 * @return pin number
	 */
	public int getPin() {
		return myPin;
	}

	/**
	 * getDrafts
	 * @return list of drafts 
	 */
	public List<Draft> getDrafts() {
		return new ArrayList<Draft>(myDrafts);
	}
	
	/**
	 * addDraft
	 * @param draft
	 * @throws IllegalStateException
	 */
	public void addDraft(final Draft draft) throws IllegalStateException {
		if (myDrafts.size() >= MAX_NUMBER_OF_DRAFTS) {
			throw new IllegalStateException("Too many drafts!");
		}
		myDrafts.add(draft);
	}
	
	/**
	 * hashCode
	 */
	public int hashCode() {
		return toString().hashCode() + 1;
	}
	
	/**
	 * equals
	 */
	public boolean equals(final Object other) {
		if (other.getClass() != getClass()) {
			return false;
		}
		final User user = (User) other;
		boolean result = true;
		result = result && user.getName().equals(myName);
		result = result && user.myPin == myPin;
		result = result && user.myUserID == myUserID;
		return result;
	}
	
	/**
	 * toString
	 */
	public String toString() {
		return myName + " " + myUserID + " " + myPin;
	}
}
