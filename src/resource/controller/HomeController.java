package resource.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import resource.utils.Constants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class HomeController implements Initializable {

    @FXML
    private JFXButton menu;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    public static AnchorPane temporaryPane;
    public static JFXDrawer temporaryDrawer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        temporaryPane = contentPane;
        temporaryDrawer = drawer;

        try {
            StackPane pane1 = FXMLLoader.load(getClass().getResource(Constants.HOMEVIEW));
            ObservableList<Node> elements = pane1.getChildren();
            HomeController.temporaryPane.getChildren().setAll(elements);

        } catch (IOException e) {
            e.printStackTrace();
        }

        initDrawer();


    }


    private void initDrawer(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resource/layout/drawerContent.fxml"));
            Parent root = loader.load();
            drawer.setSidePane(root);
            menu.setOnAction(actionEvent -> {
                if(drawer.isClosed()){
                    drawer.open();
                }else {
                    drawer.close();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
