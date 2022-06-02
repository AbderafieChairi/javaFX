package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
// import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
// import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import model.DbModel;
import model.User;
public class UserData implements Initializable{
    private int idProject;
    private ArrayList<Integer> ExcludedUsers=new ArrayList<>();

    @FXML
    private Button addToProjectBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button detailsBtn;
    
    @FXML
    private Button to_chefBtn;

    @FXML
    private Button to_empBtn;

    @FXML
    private TableColumn<User, String> email;

    @FXML
    private TableColumn<User, String> prenom;

    @FXML
    private TableColumn<User, String> nom;

    @FXML
    private TableColumn<User, String> phone;

    @FXML
    private TableColumn<User, String> type;

    @FXML
    private TableColumn<User, Integer> salaire;

    @FXML
    private TableView<User> table;



    @FXML
    void addToProject(ActionEvent event) throws IOException {
        ObservableList<User> itemSelected;

        itemSelected = table.getSelectionModel().getSelectedItems();
		for (User p : itemSelected){
			// DbModel.deleteItem(p);
            DbModel.queryExecute(String.format("insert into proj_user (user_id,project_id) values (%s,%s)", p.getId(),idProject));
		}
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProjectUsers.fxml"));
        root = loader.load();
		// UserProjects cont = loader.getController();
		// cont.setIdUser(1);
		// cont.init();
		ProjectUsers p = loader.getController();
		p.setIdProject(idProject);
		p.myinit();
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);        
		System.out.println("Done");
    }




    public ArrayList<Integer> getExcludedUsers() {
        return ExcludedUsers;
    }




    public void setExcludedUsers(ArrayList<Integer> excludedUsers) {
        this.ExcludedUsers = excludedUsers;
    }




    @FXML
    void delete(ActionEvent event) throws IllegalArgumentException, IllegalAccessException {
        ObservableList<User> itemSelected,allItems;
        allItems = table.getItems();

        itemSelected = table.getSelectionModel().getSelectedItems();
		for (User u : itemSelected){
        DbModel.queryExecute(String.format("delete from proj_user where user_id='%s'", u.id));
        DbModel.queryExecute(String.format("update project set chef_id=1 where chef_id='%s'", u.id));
			DbModel.deleteItem(u);
		}
        itemSelected.forEach(allItems::remove);
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    @FXML
    void details(ActionEvent event) throws IOException {
        ObservableList<User> itemSelected;
        itemSelected = table.getSelectionModel().getSelectedItems();
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UserProjects.fxml"));
        root = loader.load();
		// UserUsers cont = loader.getController();
		// cont.setIdUser(1);
		// cont.init();
		UserProjects p = loader.getController();
		p.setIdUser(itemSelected.get(0).getId());
		p.init();
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }

    @FXML
    void to_chef(ActionEvent event) {
        ObservableList<User> itemSelected,allItems;
        itemSelected =table.getSelectionModel().getSelectedItems();
        allItems = table.getItems();
		User p = itemSelected.get(0);
        DbModel.queryExecute(String.format("update user set type='chef' where id=%s", p.id));
        
        for (int i = 0; i < allItems.size(); i++) {
            User user = table.getItems().get(i);
            if (user.id==p.id){
                user.setType("Chef");
                table.getItems().set(i, user);
            }
        }
    }

    @FXML
    void to_emp(ActionEvent event) {
        ObservableList<User> itemSelected,allItems;
        itemSelected =table.getSelectionModel().getSelectedItems();
        allItems = table.getItems();
		User p = itemSelected.get(0);
        DbModel.queryExecute(String.format("update user set type='Employee' where id=%s", p.id));
        
        for (int i = 0; i < allItems.size(); i++) {
            User user = table.getItems().get(i);
            if (user.id==p.id){
                user.setType("Employee");
                table.getItems().set(i, user);
            }
        }
    }

    @FXML
    void filter(InputMethodEvent event) {

    }

    public ObservableList<User> getData() throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoSuchMethodException, SecurityException, InvocationTargetException, SQLException{
        ObservableList<User>  data =  FXCollections.observableArrayList();
        DbModel<User> D = new DbModel<User>(new User());
        ArrayList<User> U= D.queryAll();
        System.out.println(U);
        int k;
        for (User item : U){
            k=0;
            for (Integer i : ExcludedUsers){
                if (item.getId()==i) k=1;
            }
            if (k==0)
            data.add(item);
        }

        return data;
    }
    public void init(){
        try {
            table.setItems(getData());
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | NoSuchMethodException
                | SecurityException | InvocationTargetException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    // public void chef(){

    // }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String auth = Login.CurrentUser.getType();
        System.out.println(auth);
        if (auth.equals("admin")){
            detailsBtn.setVisible(false);
            addToProjectBtn.setVisible(false);
        }else {        
            to_chefBtn.setVisible(false);
            to_empBtn.setVisible(false);
            deleteBtn.setVisible(false);
            if(auth.equals("Employee"))
                addToProjectBtn.setVisible(false);
        }

        // if(Login.CurrentUser.getType()=="Employee")
    	email.setCellValueFactory(new PropertyValueFactory<User,String>("email")); 
    	nom.setCellValueFactory(new PropertyValueFactory<User,String>("nom")); 
    	prenom.setCellValueFactory(new PropertyValueFactory<User,String>("prenom")); 
    	salaire.setCellValueFactory(new PropertyValueFactory<User,Integer>("salaire")); 
    	type.setCellValueFactory(new PropertyValueFactory<User,String>("type")); 
    	phone.setCellValueFactory(new PropertyValueFactory<User,String>("phone")); 



    }

}
