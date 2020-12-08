package resource.controller;

import DBHelper.DBHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import resource.utils.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {



    @FXML
    private JFXTextField tf_username;

    @FXML
    private JFXPasswordField tf_password;

    @FXML
    private JFXButton bt_login;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bt_login.setOnAction(actionEvent -> {
            try {
                if(!tf_username.getText().equals("") && !tf_password.getText().equals("")) {
                    authenticate();
                }
                else{
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText("Field Empty");
                    alert1.setContentText("Please fill username and password");
                    alert1.show();
                }
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });

        tf_password.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    authenticate();
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }


    void authenticate() throws SQLException, IOException {
        DBHandler dbHandler = new DBHandler();
        Connection connection = dbHandler.getConnection();
        if(connection!=null){
            System.out.println("Connection Open");
        }else {
            System.out.println("Connection Not Established");
        }

        String query = "SELECT * FROM auth WHERE username='"+tf_username.getText()+"' and password='"+tf_password.getText()+"'";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            System.out.println("Login Success");
            loginUser();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Login Failed!");
            alert.setContentText("Your username or password is incorrect.\nPlease try again.");
            alert.setOnCloseRequest(dialogEvent -> {
                tf_password.setText("");
            });
            alert.show();
        }
        connection.close();
        System.out.println("Connection close");
    }

    private void loginUser() throws IOException {
//        bt_login.getScene().getWindow().hide();
        User.setUser(tf_username.getText());
        Stage stage = (Stage) bt_login.getScene().getWindow();
        stage.close();
        Stage homeStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/resource/layout/home.fxml"));
        Scene scene = new Scene(root);
        homeStage.setScene(scene);
        homeStage.show();
        homeStage.setResizable(false);
    }
}
