package superseded;

/**
 * @author Sally Budack
 * TCSS360 Winter 2015
 *
 */
public class Analyst {

	protected String name;
	protected String role;
	public String userID;
	public String pinNum;
	protected boolean isAdmin;
	
	/**
	 * @param name
	 * @param pinNum 
	 * @param role 
	 */
	public Analyst(String name, String pinNum, String role){
		this.name = name;	
		this.pinNum = pinNum;
		this.role =role;		
	}


	/**
	 * Analyst
	 */
	public Analyst() {
	
	}


	/**
	 * @return the id
	 */
	public String getUserID() {		
		return userID;
	}

	public void setUserID(String name){
		int firstSpace = name.indexOf(" "); // detect the first space character
		String firstName = name.substring(0, firstSpace);  // get everything upto the first space character
		String lastName = name.substring(firstSpace).trim(); // get everything after the first 
		String firstInit = firstName.substring(0, 0); 
		userID = firstInit.toLowerCase() + lastName.toLowerCase();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * 
	 * setPinNum
	 * @param pinNum
	 */
	public void setPinNum(String pinNum){
		this.pinNum = pinNum;
	}
	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}


	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		if(role.equalsIgnoreCase("writer")){
			isAdmin = false;
		}else if(role.equalsIgnoreCase("administrator")){
			isAdmin = true;
		}
	}


	/**
	 * @return the pinNum
	 */
	public String getPinNum() {				
		return pinNum;
	}


	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}


	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return "Analyst [ Name:" + name + ", userID: " + userID + ", pinNum: "
				+ pinNum + "role: " + role + "]";
	}
}
