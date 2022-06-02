package model;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

import application.CustumClass;
public class DbModel<T extends CustumClass> {

    T ob;
    public DbModel(T ob){
        this.ob=ob;
    }
	// T createContents(Class<T> clazz) throws InstantiationException, IllegalAccessException {
    //     return clazz.newInstance();
    // }
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DATABASE_URL =
	"jdbc:mysql://localhost/entreprise";
	static Connection connection;
	static Statement statement;



	public static ResultSet queryExecute(String queryStatement) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet ;
		// ResultSetMetaData metaData ;
		// String resultat = "";
		try {
			Class.forName( JDBC_DRIVER );
			connection = DriverManager.getConnection(DATABASE_URL, "root", "");
			statement = connection.createStatement();
            System.out.println(queryStatement);
			try {
				resultSet = statement.executeQuery(queryStatement);
				return resultSet;
			} catch (Exception e) {
				statement.executeUpdate(queryStatement);
				return null;
			}
			
            
		}
		catch ( ClassNotFoundException classNotFound ) {
			classNotFound.printStackTrace();
			System.exit( 1 );
		}
		catch ( SQLException sqlException ) {
			sqlException.printStackTrace();
			System.exit( 1 );
		}
        return null;
	}

    public static void AddItem(CustumClass C) throws IllegalArgumentException, IllegalAccessException{
        String keys="";
        String values="";
        Field[] fields = C.getClass().getFields();
        for (Field f:fields){
            if (f.getName()!="id"){
                keys+=f.getName()+",";
                if (f.getType()==String.class){
                    values+="'"+f.get(C)+"',";
                }else{
                    values+=f.get(C)+",";
    
                }
            }
        }
        keys=keys.substring(0, keys.length()-1);
        values=values.substring(0, values.length()-1);
        String queryStatement="INSERT INTO "+C.getTableName()+"("+keys+") VALUES ("+values+");"; 
		System.out.println(queryStatement);
        queryExecute(queryStatement);
		System.out.println("Done !!!!");
    }
    public static void updateItem(CustumClass C) throws IllegalArgumentException, IllegalAccessException{
        String keys="";
        String values="";
		String item="";
        Field[] fields = C.getClass().getFields();
        for (Field f:fields){
            keys=f.getName()+"=";
			if (f.getType()==String.class){
				values="'"+f.get(C)+"',";
			}else{
				values=f.get(C)+",";
			}
			item+=keys+values;
        }
        item=item.substring(0, item.length()-1);
        String queryStatement="UPDATE "+C.getTableName()+" SET "+item+" WHERE id="+C.getId()+";"; 
		System.out.println(queryStatement);
        queryExecute(queryStatement);
		System.out.println("Done !!!!");
    }

    public static void deleteItem(CustumClass C) throws IllegalArgumentException, IllegalAccessException{
        Field[] fields = C.getClass().getFields();
        String queryStatement="DELETE FROM "+C.getTableName()+" WHERE "+fields[0].getName()+"="+fields[0].get(C)+";"; 
		System.out.println(queryStatement);
        queryExecute(queryStatement);
		System.out.println("Done !!!!");
    }
    public static <E> void append(ArrayList<E> list, Class<E> cls) throws Exception {
        E elem = cls.getDeclaredConstructor().newInstance();   // OK
        list.add(elem);
    }
    public static void hhhh(){
        
    }

    // <E> E append(Class<E> cls) throws Exception {
    //     E elem = cls.getDeclaredConstructor().newInstance();   // OK
    //     return
    // }

    @SuppressWarnings("unchecked")
    public ArrayList<T> queryAll() throws IllegalArgumentException, IllegalAccessException, SQLException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Field[] fields = ob.getClass().getFields();
        ArrayList<T>  data =  new ArrayList<>();
        ResultSet result = DbModel.queryExecute("SELECT * FROM "+ob.getTableName()+";");
        while ( result.next() ) {
            int j =1;
            T CC = (T) ob.getClass().getDeclaredConstructor().newInstance();
            for (Field f :fields) {

                if(f.getType()==String.class){
                    f.set(CC, result.getString(j));
                }else{
                    f.set(CC,result.getInt(j));
                }
                j++;
            }
            data.add(CC);
        }
        return data;
    }
    @SuppressWarnings("unchecked")
    public ArrayList<T> queryAll(String queryStatement) throws IllegalArgumentException, IllegalAccessException, SQLException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Field[] fields = ob.getClass().getFields();
        ArrayList<T>  data =  new ArrayList<>();
        // System.out.println("SELECT * FROM "+this.getTableName()+";");
        ResultSet result = DbModel.queryExecute(queryStatement);
        while ( result.next() ) {
            int j =1;
            T CC = (T) ob.getClass().getDeclaredConstructor().newInstance();
            for (Field f :fields) {

                if(f.getType()==String.class){
                    f.set(CC, result.getString(j));
                }else{
                    f.set(CC,result.getInt(j));
                }
                j++;
            }
            data.add(CC);
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    public T queryfirst(String queryStatement) throws IllegalArgumentException, IllegalAccessException, SQLException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Field[] fields = ob.getClass().getFields();
        System.out.println(queryStatement);
        ResultSet result = DbModel.queryExecute(queryStatement);
        if ( result.next() ) {
            int j =1;
            T CC = (T) ob.getClass().getDeclaredConstructor().newInstance();
            for (Field f :fields) {

                if(f.getType()==String.class){
                    f.set(CC, result.getString(j));
                }else{
                    f.set(CC,result.getInt(j));
                }
                j++;
            }
            return CC;
        }
        return null;
    }

    public void initDb() throws IllegalArgumentException, IllegalAccessException{
		Connection connection = null;
		Statement statement = null;
        final String DATABASE_URL1 =
	"jdbc:mysql://localhost";
        String sqlStatement1 ="CREATE DATABASE IF NOT EXISTS entreprise";


        String sqlStatement2="CREATE TABLE IF NOT EXISTS User(id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,nom VARCHAR(200) NOT NULL,prenom VARCHAR(200) NOT NULL,email VARCHAR(200) NOT NULL UNIQUE,phone VARCHAR(200),password VARCHAR(200) NOT NULL,type VARCHAR(200) NOT NULL,salaire FLOAT(20) NOT NULL);";
            String sqlStatement3="CREATE TABLE IF NOT EXISTS project(id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,name VARCHAR(200) NOT NULL,description VARCHAR(200) NOT NULL,stat VARCHAR(200) NOT NULL,chef_id INT(10) NOT NULL,FOREIGN KEY(chef_id) REFERENCES User(id));";
                                
                                
        String sqlStatement4="CREATE TABLE IF NOT EXISTS Proj_user(user_id INT(10) NOT NULL,FOREIGN KEY(user_id) REFERENCES user(id),project_id INT(10) NOT NULL,FOREIGN KEY(project_id) REFERENCES project(id));";
        // queryExecute(sqlStatement);			
		try {
			Class.forName( JDBC_DRIVER );
			connection = DriverManager.getConnection(DATABASE_URL1, "root", "");
			statement = connection.createStatement();
			try {
                statement.executeUpdate(sqlStatement1);
			} catch (Exception e) {
			}
			
            
		}
		catch ( ClassNotFoundException classNotFound ) {
			classNotFound.printStackTrace();
			System.exit( 1 );
		}
		catch ( SQLException sqlException ) {
			sqlException.printStackTrace();
			System.exit( 1 );
		}
        queryExecute(sqlStatement2);
        queryExecute(sqlStatement3);
        queryExecute(sqlStatement4);
        DbModel<Project> D = new DbModel<Project>(new Project());
        Project p = new Project("Java",1,"First project");
        DbModel.queryExecute("INSERT INTO user (id,nom,prenom,email,phone,password,type,salaire) VALUES (1,'abderafie','chairi','aa','0707070707','aa','admin ','5000');");
        DbModel.AddItem(p);
        DbModel.queryExecute("INSERT INTO proj_user (user_id, project_id)VALUES (1, 1);");

    }



    public static void main(String[] args) throws SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {	
        // initDb();
    //     // select * from users where email='hgjhg' && password='jhgj'
        // DbModel<Employee> D = new DbModel<Employee>(new Employee());
    //     ArrayList<Employee> ee = D.queryAll();
    //     System.out.println(ee!=null?"yes":"non");
    // }
        // 	// Project p = new Project(3,"New test",1);
	// 	// deleteItem(p);
    //     // addIte
        // DbModel<Project> D = new DbModel<>(new Project());
        // Project p = D.queryfirst("select * from project where id=1");
        // System.out.println(p);
        // Project p = new Project("hhh",1,"descriptions");

        // AddItem(p);
        // ArrayList<Project> list = D.queryAll();
        // for (Project p : list){
        //     System.out.println(p);
        // }
    //     Employee e = new Employee(7,"jamal", "abderafie", "hhh.com", "077777", "hhhhh", "Boss", 99999);
    //     deleteItem(e);
    //     DbModel1<Employee> D = new DbModel1<>(new Employee());
    //     ArrayList<Employee> list = D.queryAll();
    //     for (Employee p : list){
    //         System.out.println(p);
    //     }
    //     // Employee e     
    }
}
