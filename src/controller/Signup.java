package controller;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import model.DbModel;
import model.User;

public class Signup {

    @FXML
    private TextField email;

    @FXML
    private TextField nom;

    @FXML
    private TextField password;

    @FXML
    private TextField phone;

    @FXML
    private TextField prenom;

    @FXML
    void signup(ActionEvent event) throws IllegalArgumentException, IllegalAccessException, IOException {
        User p = new User(nom.getText(),prenom.getText(),email.getText(),phone.getText(),password.getText(),"Employee",5000);
        System.out.println(p);
        DbModel.AddItem(p);
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        Main.stage.hideSignout();
        Main.stage.setScene(scene);
    }

}
