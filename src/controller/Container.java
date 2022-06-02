package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class Container implements Initializable{


    @FXML
    void login(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        root = loader.load();
		main.getChildren().setAll(root);
    }

    @FXML
    private Button loginBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private AnchorPane main;

    public void showLogin(){
        loginBtn.setVisible(true);
        signupBtn.setVisible(true);
        logoutBtn.setVisible(false);
    }

    public void showLogout(){
        loginBtn.setVisible(false);
        signupBtn.setVisible(false);
        logoutBtn.setVisible(true);
    }
    public void hideSignout(){
        signupBtn.setVisible(false);

    }

    @FXML
    private Button signupBtn;

    public void setMain(AnchorPane main) {
        this.main = main;
    }

    @FXML
    void signup(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Signup.fxml"));
        root = loader.load();
		main.getChildren().setAll(root);
    }

    public void setScene(Scene scene){
        Parent root = scene.getRoot();
        main.getChildren().setAll(root);

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        root = loader.load();
        showLogin();
		main.getChildren().setAll(root);
    }
    // #AFB7DC;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signupBtn.setOnMouseEntered(e->signupBtn.setStyle("-fx-background-color: #7A89CC;"));
        loginBtn.setOnMouseEntered(e->loginBtn.setStyle("-fx-background-color: #7A89CC;"));
        logoutBtn.setOnMouseEntered(e->logoutBtn.setStyle("-fx-background-color: #7A89CC;"));
        signupBtn.setOnMouseExited(e->signupBtn.setStyle("-fx-background-color: #AFB7DC;"));
        loginBtn.setOnMouseExited(e->loginBtn.setStyle("-fx-background-color: #AFB7DC;"));
        logoutBtn.setOnMouseExited(e->logoutBtn.setStyle("-fx-background-color: #AFB7DC;"));
        logoutBtn.setVisible(false);
        AnchorPane.setBottomAnchor(main, (double) 0);
        AnchorPane.setLeftAnchor(main, (double) 0);
        AnchorPane.setRightAnchor(main, (double) 0);
        AnchorPane.setTopAnchor(main, (double) 0);
        
    }

}
