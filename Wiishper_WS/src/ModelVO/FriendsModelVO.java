package ModelVO;

import java.sql.Date;

public class FriendsModelVO 
{
	private Integer friender;
	private Integer fiendee;
	private Date befriend_date;
	public Integer getFriender() {
		return friender;
	}
	public void setFriender(Integer friender) {
		this.friender = friender;
	}
	public Integer getFiendee() {
		return fiendee;
	}
	public void setFiendee(Integer fiendee) {
		this.fiendee = fiendee;
	}
	public Date getBefriend_date() {
		return befriend_date;
	}
	public void setBefriend_date(Date befriend_date) {
		this.befriend_date = befriend_date;
	}
	

}
