package resource;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("Welcome");
        Parent root = FXMLLoader.load(getClass().getResource("layout/login.fxml"));
        primaryStage.setTitle("JSS Boys Hostel");
        primaryStage.setScene(new Scene(root, 1000, 740));
        primaryStage.show();
        primaryStage.setResizable(false);

        //temp
//        Stage homeStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/resource/layout/home.fxml"));
//        Scene scene = new Scene(root);
//        homeStage.setScene(scene);
//        homeStage.show();
//        homeStage.setResizable(false);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Good Bye.");

        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
