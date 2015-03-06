package login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.User;

public class AllUsers implements Serializable {

	private static final String ORIGINAL_DELIMITER = ",";

	private static final long serialVersionUID = -8239109303918519701L;

	private static final String SAVE_FILE = "users.ser";

	private static final String ORIGINAL_FILE = "staff.txt";

	private final List<User> myUsers;

	private AllUsers(final List<User> users) {
		myUsers = new ArrayList<User>(users);
	}

	public User validateUser(final String userID, final int pin) {
		for(User u : myUsers) {
			boolean result = true;
			result = result && u.getUserID().equals(userID);
			result = result && u.getPin() == pin;
			if (result) {
				return u;
			}
		}
		return null;
	}

	public void serialize() throws IOException {
		final AllUsers temp = new AllUsers(myUsers);
		final FileOutputStream fileOut = new FileOutputStream(SAVE_FILE);
		final ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(temp);
		objOut.close();
		fileOut.close();
	}

	public static AllUsers deserialize() {
		try {
			final FileInputStream fileIn = new FileInputStream(SAVE_FILE);
			final ObjectInputStream objIn = new ObjectInputStream(fileIn);
			final AllUsers users = (AllUsers) objIn.readObject();
			objIn.close();
			fileIn.close();
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static AllUsers createFromOriginal() {
		try {
			final Scanner in = new Scanner(new File(ORIGINAL_FILE));
			final List<User> users = new ArrayList<User>();
			while (in.hasNextLine()) {
				final String[] line = in.nextLine().split(ORIGINAL_DELIMITER);
				System.out.println(Arrays.toString(line));
				if (line.length != 4) {
					throw new Exception("Input line not formatted properly!");
				}
				final String name = line[0];
				final String userID = line[1];
				final int pin = Integer.parseInt(line[2]);
				final boolean isAdmin = line[3].startsWith("admin");
				final User newUser = new User(name, userID, pin, isAdmin);
				users.add(newUser);
			}
			return new AllUsers(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
