package resource.controller.homebuttons;

import DBHelper.DBHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import resource.utils.User;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DoubleSharing implements Initializable {
    @FXML
    private JFXTextField tf_usn;

    @FXML
    private JFXTextField tf_name;

    @FXML
    private JFXRadioButton rb_north;

    @FXML
    private JFXTextField tf_year;

    @FXML
    private JFXButton bt_add;

    DBHandler handler;
    Connection connection;
    PreparedStatement preparedStatement1, preparedStatement2, preparedStatement3, statementCheck, statementHistory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bt_add.setOnAction(actionEvent -> {
            writeDB();

            try {
                if(!connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void writeDB(){
        handler = new DBHandler();
        try {
            connection = handler.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String check = "SELECT * FROM usn_count WHERE usn=?";
        try {
            statementCheck = connection.prepareStatement(check);
            statementCheck.setString(1, tf_usn.getText());
            ResultSet checkSet = statementCheck.executeQuery();

            if (checkSet.next()){
                existing();
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int bed = 5;
        try {
            String count = "SELECT * FROM count";
            preparedStatement1 = connection.prepareStatement(count);
            ResultSet result = preparedStatement1.executeQuery();

            if (result.next()) {
                bed = result.getInt("double_sharing");
            }

            if (bed == 15) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Full");
                alert.setHeaderText("Single Sharing is not available.");
                alert.show();
                closeStage();
                return;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        String query = "INSERT INTO double_sharing "
                +"VALUES(?,?,?,?,?,?)";

        try {
            preparedStatement2 = connection.prepareStatement(query);
            preparedStatement2.setString(1, tf_usn.getText());
            preparedStatement2.setString(2, tf_name.getText());
            if (rb_north.isSelected()) {
                preparedStatement2.setString(3, "N");
            } else {
                preparedStatement2.setString(3, "S");
            }

            preparedStatement2.setString(4, String.valueOf(bed + 1));
            preparedStatement2.setInt(5, Integer.parseInt(tf_year.getText()));
            preparedStatement2.setInt(6, 120000);
            preparedStatement2.executeUpdate();
        }catch (SQLException e) {
            if(e instanceof SQLIntegrityConstraintViolationException){
                existing();
                return;
            }else {
                e.printStackTrace();
            }
        }

        String update = "UPDATE count SET double_sharing=? WHERE idcount=1";

        bed = bed+1;

        try {
            preparedStatement3 = connection.prepareStatement(update);
            preparedStatement3.setInt(1, bed);
            preparedStatement3.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        String insertCheck = "INSERT INTO usn_count(usn) VALUES(?)";
        try {
            statementCheck = connection.prepareStatement(insertCheck);
            statementCheck.setString(1,tf_usn.getText());
            statementCheck.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String history = "INSERT INTO history(staff_username, student_usn, bed_number, date, sharing) VALUES(?,?,?,?,?)";
        try{
            statementHistory = connection.prepareStatement(history);
            statementHistory.setString(1, User.getUser());
            statementHistory.setString(2, tf_usn.getText());
            statementHistory.setString(3, String.valueOf(bed));

            Date dateobj = new Date();
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            String date = df.format(dateobj);
            statementHistory.setString(4,date);
            statementHistory.setString(5,"Double");
            statementHistory.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Info");
        alert2.setHeaderText("Bed number assigned: " + bed);
        alert2.showAndWait();
        closeStage();
    }

    private void closeStage(){
        Stage stage1 = (Stage) bt_add.getScene().getWindow();
        stage1.close();
    }

    private void existing(){
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Info");
        alert1.setHeaderText("USN already Assigned");
        alert1.setContentText("A bed is already assigned to this USN.\n Cannot assign two beds to a single student");
        alert1.show();
    }
}
