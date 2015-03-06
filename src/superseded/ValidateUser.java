package superseded;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * @author Sally Budack
 * TCSS360 Winter 2015
 *
 */
public class ValidateUser {

	String name;
	String userID;
	String pinNum;
	String role;

	Map<String, String> id = new HashMap<String, String>();
	Map<String, String> access = new HashMap<String, String>();


	/**
	 * ValidateUser
	 * @param name
	 * @param userID
	 * @param pinNum
	 * @param role
	 */
	public ValidateUser(String name, String userID, String pinNum, String role){
		this.name = name;
		this.userID = userID;
		this.pinNum = pinNum;
		this.role = role;

	}

	/**
	 * ValidateUser
	 */
	public ValidateUser(){

	}

	/**
	 * getName
	 * @param loginName
	 * @return name
	 */
	public String getName(String loginName){		
		return id.get(loginName);
	}

	/**
	 * getRole
	 * @param loginName
	 * @return role
	 */
	public String getRole(String loginName){
		return access.get(loginName);
	}

	/**
	 * isValid
	 * @param searchString
	 * @return validation
	 * @throws FileNotFoundException
	 */
	public boolean isValid(String searchString) throws FileNotFoundException{ 

		boolean valid = false;
		// open the data file
		File file = new File("staff.txt");

		// create a scanner from the file
		Scanner inputFile = new Scanner (file);

		// set up the scanner to use "," as the delimiter
		inputFile.useDelimiter("[\\r,]");

		// While there is another line to read.
		while(inputFile.hasNext()){
			// read the 3 parts of the line
			String myName = inputFile.next(); //Read name
			String myUserID = inputFile.next(); //Read userID
			String myPinNum = inputFile.next(); //Read pinNum
			String myRole = inputFile.next(); //Read userID	             

			id.put(myUserID, myName);
			access.put(myUserID, myRole);



			//Check if user input is valid
			if(searchString.equals(myName)) {
				valid = true;
			}

			if(searchString.equals(myUserID)) {
				valid = true;
			}

			if(searchString.equals(myPinNum)) {	            	 
				valid = true;
			}

			if(searchString.equals(myRole)) {
				valid = true;
			}	             

		}
		// close the file
		inputFile.close();
		return valid;
	}
}
