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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import model.DbModel;
import model.Project;

public class ProjectData implements Initializable{



    @FXML
    private TableColumn<Project, String> desc;



    @FXML
    private TableColumn<Project, Integer> idchef;

    @FXML
    private TableColumn<Project, String> name;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<Project, String> stat;

    @FXML
    private TableView<Project> table;

    @FXML
    void add(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProjectAdd.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }

    @FXML
    void delete(ActionEvent event) throws IllegalArgumentException, IllegalAccessException {
        ObservableList<Project> itemSelected,allItems;
        allItems = table.getItems();

        itemSelected = table.getSelectionModel().getSelectedItems();
		for (Project p : itemSelected){
            DbModel.queryExecute(String.format("delete from proj_user where (user_id, project_id)=(%s, %s);", Login.CurrentUser.id,p.getId()));

			DbModel.deleteItem(p);
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

    @FXML
    void filter(InputMethodEvent event) {

    }

    @FXML
    void update(ActionEvent event) throws IOException {
        ObservableList<Project> itemSelected;
        itemSelected = table.getSelectionModel().getSelectedItems();
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProjectAdd.fxml"));
        root = loader.load();
        ProjectAdd p = loader.getController();
        p.updtadeItem = itemSelected.get(0).getId();
        p.getNom().setText(itemSelected.get(0).getName());
        p.getDesc().setText(itemSelected.get(0).getDescription());
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);
    }



    public ObservableList<Project> getData() throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoSuchMethodException, SecurityException, InvocationTargetException, SQLException{
        ObservableList<Project>  data =  FXCollections.observableArrayList();
        DbModel<Project> D = new DbModel<Project>(new Project());
        ArrayList<Project> U= D.queryAll();
        System.out.println(U);
        for (Project item : U){
            data.add(item);
        }

        return data;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	desc.setCellValueFactory(new PropertyValueFactory<Project,String>("description")); 
    	idchef.setCellValueFactory(new PropertyValueFactory<Project,Integer>("chef_id")); 
    	stat.setCellValueFactory(new PropertyValueFactory<Project,String>("stat")); 
    	name.setCellValueFactory(new PropertyValueFactory<Project,String>("name")); 


        try {
            table.setItems(getData());
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | NoSuchMethodException
                | SecurityException | InvocationTargetException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
