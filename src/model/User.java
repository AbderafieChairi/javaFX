package model;

import application.CustumClass;

public class User extends CustumClass{
	
	public Integer id;
	public String nom;
	public String prenom;
	public String email;
	public String phone;
	public String password;	
	public String type;
	public Integer salaire;
	
	public User() {
		this.setId(id);
		this.nom = "None";
		this.prenom = "None";
		this.email = "None";
		this.phone = "None";
		this.password = "None";
		this.type = "None";
		this.salaire = 0;
		setTableName("user");
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}





	public User(String nom, String prenom,String email, String phone, String password, String type, Integer salaire) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.type = type;
		this.salaire = salaire;
		setTableName("user");
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}






	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getPassword() {
		return password;
	}
	
	
	public void setPassword(String password) {
		this.password = password;
	}



	public void setMdp(String password) {
		this.password = password;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Integer getSalaire() {
		return salaire;
	}


	public void setSalaire(Integer salaire) {
		this.salaire = salaire;
	}


	
}
