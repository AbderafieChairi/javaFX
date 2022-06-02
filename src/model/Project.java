package model;

import application.CustumClass;

public class Project extends CustumClass{
	
	public Integer id=0;
	public String name;
	public String description;
	public String stat="En cours";
	public Integer chef_id;


	public String getStat() {
		return stat;
	}
	public Integer getChef_id() {
		return chef_id;
	}
	public void setChef_id(Integer chef_id) {
		this.chef_id = chef_id;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Project(){
		setId(0);
		this.name="none";
		this.description="none";
		this.stat="none";
		this.chef_id=0;
		setTableName("project");	
	}
    public Project(String name, Integer chef_id, String description) {
		setName(name);
		setChef_id(chef_id);
		setDescription(description);
		setTableName("project");	
	}

	// public User() {
	// 	this.setId(id);
	// 	this.nom = "None";
	// 	this.prenom = "None";
	// 	this.email = "None";
	// 	this.phone = "None";
	// 	this.password = "None";
	// 	this.type = "None";
	// 	this.salaire = 0;
	// 	setTableName("user");
	// }

}
