package resource.controller;

import DBHelper.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import resource.utils.ModelTableHistory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HistoryView implements Initializable {
    @FXML
    private TableView<ModelTableHistory> table_history;

    @FXML
    private TableColumn<ModelTableHistory, String> col_date_time;

    @FXML
    private TableColumn<ModelTableHistory, String> col_staff_username;

    @FXML
    private TableColumn<ModelTableHistory, String> col_student_usn;

    @FXML
    private TableColumn<ModelTableHistory, String> col_sharing;

    @FXML
    private TableColumn<ModelTableHistory, String> col_bed_number;

    ObservableList<ModelTableHistory> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DBHandler handler = new DBHandler();

        try {
            Connection con = handler.getConnection();
            PreparedStatement statement;

            String query = "SELECT * FROM history ORDER BY date desc";
            statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            System.out.println("CHECK 0");

            while (rs.next()) {
                System.out.println("CHECK 1");
                obList.add(new ModelTableHistory(rs.getString("staff_username"),
                        rs.getString("student_usn"),
                        rs.getString("sharing"),
                        rs.getString("bed_number"),
                        rs.getString("date")));
                System.out.println("CHECK 2");
            }

            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("CHECK 3");
        col_date_time.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_staff_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_student_usn.setCellValueFactory(new PropertyValueFactory<>("usn"));
        col_sharing.setCellValueFactory(new PropertyValueFactory<>("sharing"));
        col_bed_number.setCellValueFactory(new PropertyValueFactory<>("bed"));
        System.out.println("CHECK 4");
        table_history.setItems(obList);
    }
}
