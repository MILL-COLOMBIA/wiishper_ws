package ModelVO;

import java.sql.Date;

public class TastesModelVO 
{
	private Integer idusers;
	private Integer idproductos;
	private Boolean liked;
	private Date inter_date;
	public Integer getIdusers() {
		return idusers;
	}
	public void setIdusers(Integer idusers) {
		this.idusers = idusers;
	}
	public Integer getIdproductos() {
		return idproductos;
	}
	public void setIdproductos(Integer idproductos) {
		this.idproductos = idproductos;
	}
	public Boolean getLiked() {
		return liked;
	}
	public void setLiked(Boolean liked) {
		this.liked = liked;
	}
	public Date getInter_date() {
		return inter_date;
	}
	public void setInter_date(Date inter_date) {
		this.inter_date = inter_date;
	}

}
