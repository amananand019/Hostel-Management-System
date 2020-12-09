package resource.controller;

import DBHelper.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resource.utils.User;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardView implements Initializable {

    @FXML
    private ImageView myImage;

    @FXML
    private Label name;

    @FXML
    private Label username;

    @FXML
    private Label email;

    @FXML
    private Label phone;

    DBHandler handler;
    Connection connection;

    File file;
    Image img;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handler = new DBHandler();
        try {
            connection = handler.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        username.setText(User.getUser());

        try {
            readDB(User.getUser());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private void readDB(String username) throws SQLException {
        String query = "Select * from staff_details where username=?";

        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            name.setText(resultSet.getString("name"));
            email.setText(resultSet.getString("email"));
            phone.setText(resultSet.getString("phone"));
            img = new Image(resultSet.getString("image"));
            myImage.setImage(img);
        }
        connection.close();
    }
}
