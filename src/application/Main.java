package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DbModel;
import model.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import controller.Container;
import controller.Login;



public class Main extends Application {
	public static Container stage;
	@Override
	public void start(Stage primaryStage) throws IOException, IllegalArgumentException, IllegalAccessException, SQLException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException {
		DbModel<User> Dbe = new DbModel<User>(new User());
        Dbe.initDb();
		User e = Dbe.queryfirst(String.format("select * from user where id=%s", 2));        
        Login.CurrentUser=e; 
		Parent root;
		// DbModel.initDb();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Container.fxml"));
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        root = loader.load();
        Parent root1 = loader1.load();
		stage = loader.getController();
		stage.setScene(new Scene(root1));
        Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	public static void main(String[] args) {

		launch(args);
	}
}
