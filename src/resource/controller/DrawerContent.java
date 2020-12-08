package resource.controller;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import resource.utils.Constants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DrawerContent implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_logout(ActionEvent event) throws IOException {
        Stage stage1 = (Stage)HomeController.temporaryPane.getScene().getWindow();
        stage1.close();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/resource/layout/login.fxml"));
        stage.setTitle("JSS Boys Hostel");
        stage.setScene(new Scene(root, 1000, 740));
        stage.show();
        stage.setResizable(false);


    }

    @FXML
    void openDashboard(ActionEvent event) {
        HomeController.temporaryDrawer.close();
        switchPane(Constants.DASHBOARDVIEW);
    }

    @FXML
    void openHistory(ActionEvent event) {
        HomeController.temporaryDrawer.close();
        switchPane(Constants.HISTORYVIEW);
    }

    @FXML
    void openHome(ActionEvent event) {
        HomeController.temporaryDrawer.close();
        switchPane(Constants.HOMEVIEW);
    }

    @FXML
    void openSearch(ActionEvent event) {
        HomeController.temporaryDrawer.close();
        switchPane(Constants.SETTINGSVIEW);
    }

    private void switchPane(String pane){
        HomeController.temporaryPane.getChildren().clear();
        try {
            StackPane pane1 = FXMLLoader.load(getClass().getResource(pane));
            ObservableList<Node> elements = pane1.getChildren();
            HomeController.temporaryPane.getChildren().setAll(elements);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
