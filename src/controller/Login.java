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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import model.DbModel;
public class Login {
    public static User CurrentUser;
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    void login(ActionEvent event) throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, IOException {
        String e = email.getText();
        String p = password.getText();
        DbModel<User> D = new DbModel<User>(new User());
        CurrentUser = D.queryfirst(String.format("select * from user where email='%s' && password='%s'" ,e,p));
        System.out.println(CurrentUser!=null?"yes":"non");   
        // CurrentUser=ee;

        if (CurrentUser != null){
            String auth = Login.CurrentUser.getType();
            System.out.println(auth);
            if (auth.equals("admin")){
                Parent root;
                FXMLLoader l = new FXMLLoader(getClass().getResource("../view/UserData.fxml"));
                root = l.load();
                UserData u = l.getController();
                u.init();
                Scene scene = new Scene(root);
                Main.stage.showLogout();
                Main.stage.setScene(scene);
            } else{
                Parent root;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UserProjects.fxml"));
                root = loader.load();
                UserProjects u = loader.getController();
                u.init();
                Scene scene = new Scene(root);
                Main.stage.showLogout();
                Main.stage.setScene(scene);
            }

        }
    }

}
