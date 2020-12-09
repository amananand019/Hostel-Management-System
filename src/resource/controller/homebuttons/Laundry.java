package resource.controller.homebuttons;

import DBHelper.DBHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Laundry implements Initializable {
    @FXML
    private JFXTextField tf_usn;

    @FXML
    private JFXComboBox<Integer> cb_monthly;

    @FXML
    private JFXTextField tf_totalCost;

    @FXML
    private JFXButton bt_add;

    int combo;

    private DBHandler handler;
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_monthly.setItems(FXCollections.observableArrayList(2,3,4));
        cb_monthly.setOnAction(actionEvent -> {
            combo = cb_monthly.getValue();
        });

        bt_add.setOnAction(event ->{
            try {
                dbWrite();
                if(!connection.isClosed()){ connection.close(); }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            tf_totalCost.setText(String.valueOf(combo*2000));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Package Added");
            alert.setHeaderText("Laundry pack "+combo+" washes/month"+" has been added to "+ tf_usn.getText());
            alert.showAndWait();

            Stage stage = (Stage) bt_add.getScene().getWindow();
            stage.close();
        });

    }

    private boolean dbCheck() throws SQLException {
        String check = "SELECT * FROM usn_count WHERE usn=?";
        handler = new DBHandler();
        connection = handler.getConnection();
        try {
            PreparedStatement statementCheck;
            statementCheck = connection.prepareStatement(check);
            statementCheck.setString(1, tf_usn.getText());
            ResultSet checkSet = statementCheck.executeQuery();

            if (checkSet.next()){
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private void dbWrite() throws SQLException {

        if(!dbCheck()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("usn not found");
            alert.setHeaderText(tf_usn.getText() + " has not assigned any bed");
            alert.showAndWait();
            return;
        }

        PreparedStatement statement;
        String insert = "INSERT INTO laundry VALUES(?,?)";
        statement = connection.prepareStatement(insert);
        statement.setString(1, tf_usn.getText());
        statement.setInt(2, combo*2000);
        statement.executeUpdate();
    }

}
