package ModelVO;

import java.sql.Date;

public class UsersModelVO 
{
	private Integer idusers;
	private String name;
	private String surname;
	private String email;
	private String profilepic;
	private Date birthdate;
	private Date entrydate;
	private String password;
	private String username;
	private String apikey;
	private String gender;
	
	public Integer getIdUsers()
	{
		return idusers;
	}
	public void setIdUsers(Integer idusers)
	{
		this.idusers = idusers;
	}
	public String getName()
	{
		return name;
	}
	public Integer getIdusers() {
		return idusers;
	}
	public void setIdusers(Integer idusers) {
		this.idusers = idusers;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public Date getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(Date entrydate) {
		this.entrydate = entrydate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getSurname()
	{
		return surname;
	}
	public void setSurname(String surname)
	{
		this.surname = surname;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getProfilepic()
	{
		return profilepic;
	}
	public void setProfilepic(String profilepic)
	{
		this.profilepic = profilepic;
	}
	

}
