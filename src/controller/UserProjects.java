package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.*;

public class UserProjects implements Initializable{

    private int idUser=Login.CurrentUser.getId();


    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button detailsBtn;

    @FXML
    private Label email;

    @FXML
    private VBox list;

    @FXML
    private TableColumn<Project, String> name;

    @FXML
    private Label nbrprojects;

    @FXML
    private Label nom;

    @FXML
    private TableColumn<Project, Integer> nomchef;

    @FXML
    private Label phone;

    @FXML
    private Label prenom;

    @FXML
    private Label salaire;

    @FXML
    private TableColumn<Project, String> status;

    @FXML
    private TableView<Project> table;

    @FXML
    private Label type;

    public ObservableList<Project> getData() throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoSuchMethodException, SecurityException, InvocationTargetException, SQLException{
        ObservableList<Project>  data =  FXCollections.observableArrayList();
        DbModel<Project> D = new DbModel<Project>(new Project());

        ArrayList<Project> pp = D.queryAll(String.format("select * from project where project.id in (select u.id from project as u,proj_user as p where (u.id=p.project_id and p.user_id=%s));",idUser));
        // ArrayList<Project> U= D.queryAll(String.format("select * from users where users.idUser in (select u.idUser from users as u,proj_emp as p where (u.idUser=p.idUser and p.idProject=%s))",idProject));
        System.out.println(pp);
        for (Project item : pp){
            data.add(item);
        }

        return data;
    }

    public void init(){
    	name.setCellValueFactory(new PropertyValueFactory<Project,String>("name")); 
    	nomchef.setCellValueFactory(new PropertyValueFactory<Project,Integer>("chef_id")); 
    	status.setCellValueFactory(new PropertyValueFactory<Project,String>("stat")); 
        DbModel<User> Dbe = new DbModel<User>(new User());
        // DbModel<Project> Dbp = new DbModel<Project>(new Project());
        // Parent comm;
        String auth = Login.CurrentUser.getType();
        if (auth.equals("Employee")){
            addBtn.setVisible(false);
            deleteBtn.setVisible(false);
        }else if (Login.CurrentUser.getId()!=idUser){
            addBtn.setVisible(false);
            deleteBtn.setVisible(false);
        }
        try {
            // ArrayList<Project> pp = Dbp.queryAll(String.format("select * from project where project.id in (select u.idprojet from project as u,proj_emp as p where (u.idprojet=p.idproject and p.idUser=%s));",idUser));
            User e = Dbe.queryfirst(String.format("select * from user where id=%s", idUser));
            ObservableList<Project> data = getData();
            nom.setText(e.nom);
            prenom.setText(e.prenom);
            email.setText(e.email);
            phone.setText(e.phone);
            type.setText(e.type);
            salaire.setText(Double.toString(e.salaire));
            nbrprojects.setText(Integer.toString(data.size()));
            table.setItems(data);





        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException
                | NoSuchMethodException | SecurityException | SQLException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }

    }



    public int getIdUser() {
        return idUser;
    }



    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @FXML
    void add(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProjectAdd.fxml"));
        root = loader.load();
        ProjectAdd.fromProjectChef=1;
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }

    @FXML
    void delete(ActionEvent event) {
        ObservableList<Project> itemSelected,allItems;
        allItems = table.getItems();

        itemSelected = table.getSelectionModel().getSelectedItems();
		for (Project p : itemSelected){
			// DbModel.deleteItem(p);
            DbModel.queryExecute(String.format("DELETE FROM proj_user where (user_id,project_id)=(%s,%s)", idUser,p.getId()));
		}
        itemSelected.forEach(allItems::remove);
		System.out.println("Done");
    }

    @FXML
    void details(ActionEvent event) throws IOException {
        ObservableList<Project> itemSelected;
        itemSelected = table.getSelectionModel().getSelectedItems();
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProjectUsers.fxml"));
        root = loader.load();
		// UserProjects cont = loader.getController();
		// cont.setIdUser(1);
		// cont.init();
		ProjectUsers p = loader.getController();
		p.setIdProject(itemSelected.get(0).getId());
		p.myinit();
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    
        
    }

}
