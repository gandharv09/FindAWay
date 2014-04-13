package iiitd.ngo.findaway;

import java.io.Serializable;
import java.sql.Blob;

public class Data  implements Serializable{
	 // Number from witch the sms was send
	   private String discount;
	   private static final long serialVersionUID = 1L;
	   // SMS text body
	   private String location;
	   private String name;
	   private String Image;
	   private String Description;
	   private String Index;
	   private String Validity;
	   private String LatLong;
	   private String Address;
	   private String Phone;
	   private String Zomato;
	   private byte[] bimage;
	   private String coupon;
	   private String term;
	   
	public Data(String id,String n,String l,String d,String de,String v,String ad,String ph,String ll,String z, String cp,String t,byte[] bs)
	{
		this.Index=id;
		this.name=n;
		this.location=l;
		this.discount=d;
		this.Description=de;
		this.Validity=v;
		this.Address=ad;
		this.Phone=ph;
		this.LatLong=ll;
		this.setZomato(z);
		this.bimage=bs;
		this.setCoupon(cp);
		this.setTerm(t);
	}
	public Data() {
		// TODO Auto-generated constructor stub
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getIndex() {
		return Index;
	}
	public void setIndex(String index) {
		Index = index;
	}
	public byte[] getBimage() {
		return bimage;
	}
	public void setBimage(byte[] bimag) {
		this.bimage = bimag;
	}
	public String getValidity() {
		return Validity;
	}
	public void setValidity(String validity) {
		Validity = validity;
	}
	public String getLatLong() {
		return LatLong;
	}
	public void setLatLong(String latLong) {
		LatLong = latLong;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getZomato() {
		return Zomato;
	}
	public void setZomato(String zomato) {
		Zomato = zomato;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	    
	
}
