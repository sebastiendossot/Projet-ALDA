package net.persistance.db;

import javax.persistence.*;
//import java.sql.Date.*;

import java.util.Date;

@Entity
@Table(name="USER") //, strategy = GenerationType.IDENTITY ?
//pour que les annotations portent sur les geters
//@ACCESS(AccessType.property)
public class User implements java.io.Serializable {
	@Id 
	@GeneratedValue
	@Column(name="ID")
	private int id;
	@Column(name="FIRST_NAME")
	private String name;
	@Column(name="LAST_NAME")
	private String forename;
	@Temporal(TemporalType.DATE)
	@Column(name="ADDED")
	private Date added;

	public User(){
			this.name="";
			this.forename="";
			this.added=new Date();
		}

	public User(String name, String forename, Date added){
		this.name=name;
		this.forename=forename;
		this.added=added;
	}

	public String getName(){
		return name;
	}

	public String getForename(){
		return forename;
	}


	public  Date getDate(){
		return added;
	}


	public void setName(String name){
		this.name = name;
	}

	public void setForename(String forename){
		this.forename = forename;
	}

	public void setDate(Date added){
		this.added = added;
	}
}
