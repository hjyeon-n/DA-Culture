package jy.project.review.dto;

public class UserDto {
	private String userID;
	private String pwd;
	private String email;
	
	public UserDto(String userID, String pwd, String email) {
		super();
		this.userID = userID;
		this.pwd = pwd;
		this.email = email;
	}
	
	public UserDto() {
		this(null, null, null);
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isFilled() {
		this.sanitize();
		if (this.userID != null && this.pwd != null) return true;
		return false;
	}
	
	public void sanitize() {
		if (this.userID != null && this.userID.equals("")) this.userID = null;
		if (this.pwd != null && this.pwd.equals("")) this.pwd = null;
		if (this.email != null && this.email.equals("")) this.email = null;
	}
	
	@Override
	public String toString() {
		return "UserDto [userID=" + userID + ", pwd=" + pwd + ", email=" + email + "]";
	}
}
