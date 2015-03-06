package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
	
	public static final int MAX_NUMBER_OF_DRAFTS = 3;
	
	private static final long serialVersionUID = -2126788306291595004L;

	private final String myName;
	
	private final String myUserID;
	
	private final int myPin;
	
	private final boolean myAdminFlag;
	
	private final List<Draft> myDrafts;

	public User(final String name, final String userID, final int pin, final boolean isAdmin) {
		myName = name;
		myUserID = userID;
		myPin = pin;
		myAdminFlag = isAdmin;
		myDrafts = new ArrayList<Draft>();
	}
	
	public String getName() {
		return myName;
	}
	
	public String getUserID() {
		return myUserID;
	}

	public boolean isAdmin() {
		return myAdminFlag;
	}
	
	public int getPin() {
		return myPin;
	}

	public List<Draft> getDrafts() {
		return new ArrayList<Draft>(myDrafts);
	}
	
	public void addDraft(final Draft draft) throws IllegalStateException {
		if (myDrafts.size() >= MAX_NUMBER_OF_DRAFTS) {
			throw new IllegalStateException("Too many drafts!");
		}
		myDrafts.add(draft);
	}
	
	public int hashCode() {
		return toString().hashCode() + 1;
	}
	
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
	
	public String toString() {
		return myName + " " + myUserID + " " + myPin;
	}
}
