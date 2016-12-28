package ModelVO;

import java.sql.Date;

public class ProductsModelVO 
{
	private Integer idproductos;
	private String name;
	private String descripcion;
	private String image;
	private Date publishdate;
	private Integer idtype;
	private String price;
	private Integer idstore;
	
	public Integer getIdproductos() {
		return idproductos;
	}
	public void setIdproductos(Integer idproductos) {
		this.idproductos = idproductos;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getPublishdate() {
		return publishdate;
	}
	public void setPublishdate(Date publishdate) {
		this.publishdate = publishdate;
	}
	public Integer getIdtype() {
		return idtype;
	}
	public void setIdtype(Integer idtype) {
		this.idtype = idtype;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getIdstore() {
		return idstore;
	}
	public void setIdstore(Integer idstore) {
		this.idstore = idstore;
	}
	

}
