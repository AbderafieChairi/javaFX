package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.DbModel;
import model.Project;

public class ProjectAdd {

    public Integer updtadeItem = 0;
    public static Integer fromProjectChef=0;

    @FXML
    private TextArea desc;

    @FXML
    private TextField nom;

    public TextArea getDesc() {
        return desc;
    }

    public TextField getNom() {
        return nom;
    }

    private void add() throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, IOException{
        DbModel<Project> Dbp = new DbModel<Project>(new Project());

        // Project p = new Project(nom.getText(),Login.CurrentUser.idUser,desc.getText());
        Project p = new Project(nom.getText(),Login.CurrentUser.id,desc.getText());
        System.out.println(p);
        DbModel.AddItem(p);
        Project pp = Dbp.queryfirst(String.format("select * from project where name='%s'", p.getName()));
        System.out.println(pp);
        DbModel.queryExecute(String.format("INSERT INTO proj_user (user_id, project_id) VALUES (%s, %s);", Login.CurrentUser.id,pp.getId()));
    }


    private void update() throws IllegalArgumentException, IllegalAccessException{
        Project p = new Project(nom.getText(),Login.CurrentUser.id,desc.getText());
        p.setId(updtadeItem);
        DbModel.updateItem(p);
        updtadeItem=0;
    }



    @FXML
    void submit(ActionEvent event) throws SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {

 
        if (updtadeItem==0){
            add();
        }{
            update();
        }
        nom.setText("");
        desc.setText("");
        Parent root;

        if (fromProjectChef!=0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UserProjects.fxml"));
            root = loader.load();
            UserProjects u = loader.getController();
            u.init();
            fromProjectChef=0; 
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ProjectData.fxml"));
            root = loader.load(); 
        }
        Scene scene = new Scene(root);
        Main.stage.setScene(scene);    

    }

}
