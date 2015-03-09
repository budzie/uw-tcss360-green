package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

	public static final int MAX_NUMBER_OF_DRAFTS = 3;

	private static final long serialVersionUID = -2126788306291595004L;

	private final String myName;

	private final int myPin;

	private final boolean myAdminFlag;

	private final List<Draft> myDrafts;

	public User(final String name, final int pin, final boolean isAdmin) {
		myName = name;
		myPin = pin;
		myAdminFlag = isAdmin;
		myDrafts = new ArrayList<Draft>();
	}

	public User(final String name, final int pin) {
		this(name, pin, false);
	}

	public String getName() {
		return myName;
	}

	public boolean isAdmin() {
		return myAdminFlag;
	}

	public List<Draft> getDrafts() {
		return new ArrayList<Draft>(myDrafts);
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
		return result;
	}

	public String toString() {
		return myName + " " + myPin;
	}
}
