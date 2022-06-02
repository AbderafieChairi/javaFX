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
import model.DbModel;
import model.Project;
import model.User;



public class ProjectUsers implements Initializable{

    private int idProject;


    private ArrayList<Integer> ExcludedUsers=new ArrayList<>();

    @FXML
    private Button addBtn;

    @FXML
    private Label chef;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<User, String> email;

    @FXML
    private VBox list;

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private Label nbr;

    @FXML
    private Label nom;

    @FXML
    private TableColumn<User, Integer> salary;

    @FXML
    private Label stat;

    @FXML
    private TableView<User> table;

    public ObservableList<User> getData() throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoSuchMethodException, SecurityException, InvocationTargetException, SQLException{
        ObservableList<User>  data =  FXCollections.observableArrayList();
        DbModel<User> D = new DbModel<User>(new User());
        // ArrayList<User> U= D.queryAll(String.format("select * from users where users.idUser in (select u.idUser from users as u,proj_emp as p where (u.idUser=p.idUser and p.idProject=%s))",idProject));
        ArrayList<User> U = D.queryAll(String.format("select * from user where user.id in (select u.id from user as u,proj_user as p where (u.id=p.user_id and p.project_id=%s))",idProject));
        System.out.println(U);
        for (User item : U){
            data.add(item);
            ExcludedUsers.add(item.getId());
        }
        System.out.println(ExcludedUsers);

        return data;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public void myinit(){
    	email.setCellValueFactory(new PropertyValueFactory<User,String>("email")); 
    	name.setCellValueFactory(new PropertyValueFactory<User,String>("nom")); 
    	salary.setCellValueFactory(new PropertyValueFactory<User,Integer>("salaire")); 
        DbModel<Project> Dbp = new DbModel<Project>(new Project());
        DbModel<User> Dbe = new DbModel<User>(new User());

        try {
            ArrayList<User> ee = Dbe.queryAll(String.format("select * from user where user.id in (select u.id from user as u,proj_user as p where (u.id=p.user_id and p.project_id=%s))",idProject));
            Project p = Dbp.queryfirst(String.format("select * from project where id=%s", idProject));
            User Empchef = Dbe.queryfirst(String.format("select * from user where id = %s",p.getChef_id()));
            chef.setText(Empchef!=null? Empchef.nom:"Pas de Chef");
            nbr.setText(Integer.toString(ee.size()));
            nom.setText(p.getName());
            stat.setText("En cours");
            table.setItems(getData());
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | NoSuchMethodException
                | SecurityException | InvocationTargetException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Error");
        }
    }
    public void init(){
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String auth = Login.CurrentUser.getType();
        if (auth.equals("Employee")){
            addBtn.setVisible(false);
            deleteBtn.setVisible(false);
        }
        
    }

    @FXML
    void add(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UserData.fxml"));
        root = loader.load();
		UserData o = loader.getController();
		o.setIdProject(idProject);
        o.setExcludedUsers(ExcludedUsers);
        o.init();
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }

    @FXML
    void delete(ActionEvent event) throws IllegalArgumentException, IllegalAccessException {
        ObservableList<User> itemSelected,allItems;
        allItems = table.getItems();

        itemSelected = table.getSelectionModel().getSelectedItems();
		for (User p : itemSelected){
			// DbModel.deleteItem(p);
            DbModel.queryExecute(String.format("DELETE FROM proj_user where (user_id,project_id)=(%s,%s)", p.getId(),idProject));
		}
        itemSelected.forEach(allItems::remove);
		System.out.println("Done");
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

}
