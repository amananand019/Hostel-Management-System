package resource.controller;

import DBHelper.DBHandler;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import resource.utils.Constants;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class HomeView implements Initializable {

    @FXML
    private Label clock;

    @FXML
    private Label tf_bed_count;

    @FXML
    private JFXButton food;

    @FXML
    private JFXButton singleSharing;

    @FXML
    private JFXButton doubleSharing;

    @FXML
    private JFXButton tripleSharing;

    @FXML
    private JFXButton laundry;

    DBHandler handler;
    Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClock();
    }
    private void initClock(){

        try {
            tf_bed_count.setText(String.valueOf(15- bedCount()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tf_bed_count.setOnMouseClicked(event -> {
            try {
                tf_bed_count.setText(String.valueOf(15 - bedCount()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();

            if(currentTime.getHour()>9 && currentTime.getMinute()>9){
                clock.setText(+currentTime.getHour() + ":" + currentTime.getMinute());
            }else if(currentTime.getMinute()<10){
                clock.setText(currentTime.getHour() + ":0" + currentTime.getMinute());
            }else if(currentTime.getHour()<10){
                clock.setText("0"+currentTime.getHour() + ":"+ currentTime.getMinute());
            }else {
                clock.setText("0"+currentTime.getHour() + ":0" + currentTime.getMinute());
            }

        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        singleSharing.setOnAction(actionEvent -> {
            openStage(Constants.SINGLESHARING);
        });

        doubleSharing.setOnAction(actionEvent -> {
            openStage(Constants.DOUBLESHARING);
        });

        tripleSharing.setOnAction(actionEvent -> {
            openStage(Constants.TRIPLESHARING);
        });

        laundry.setOnAction(actionEvent -> {
            openStage(Constants.LAUNDRY);
        });

        food.setOnAction(actionEvent -> {
            openStage(Constants.FOOD);
        });

    }

    private void openStage(String url){
        Stage newStage = new Stage();
        Parent loader = null;
        try {
            loader = FXMLLoader.load(getClass().getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene newScene = new Scene(loader);
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();
        newStage.setResizable(false);
    }

    private int bedCount() throws SQLException {
        handler = new DBHandler();
        connection = handler.getConnection();

        String query = "SELECT count(usn) as count FROM usn_count";

        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        if(rs.next()){
            return Integer.parseInt(rs.getString("count"));
        }
        return -1;
    }
}
