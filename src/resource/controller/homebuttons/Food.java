package resource.controller.homebuttons;

import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Food implements Initializable {
    @FXML
    private JFXRadioButton rb_north;

    @FXML
    private JFXRadioButton rb_south;

    @FXML
    private ImageView iv_food;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iv_food.setImage(new Image("/resource/drawable/food/North.jpg"));


        rb_north.setOnAction(actionEvent -> {
            iv_food.setImage(new Image("/resource/drawable/food/North.jpg"));
        });

        rb_south.setOnAction(actionEvent -> {
            iv_food.setImage(new Image("/resource/drawable/food/South.jpg"));
        });
    }
}
