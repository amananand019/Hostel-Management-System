package resource.controller;

import DBHelper.DBHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import resource.utils.ModelTableSearch;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchView implements Initializable {
    @FXML
    private TableView<ModelTableSearch> table_search;

    @FXML
    private TableColumn<ModelTableSearch, String> col_usn;

    @FXML
    private TableColumn<ModelTableSearch, String> col_name;

    @FXML
    private TableColumn<ModelTableSearch, String> col_sharing;

    @FXML
    private TableColumn<ModelTableSearch, String> col_bed;

    @FXML
    private TableColumn<ModelTableSearch, String> col_year;

    @FXML
    private TableColumn<ModelTableSearch, String> col_basefee;

    @FXML
    private TableColumn<ModelTableSearch, String> col_laundry;

    @FXML
    private TableColumn<ModelTableSearch, String> col_total;

    @FXML
    private JFXTextField tf_usn;

    @FXML
    private JFXButton searchByUSN;

    @FXML
    private JFXTextField tf_name;

    @FXML
    private JFXButton searchByName;

    DBHandler handler;
    Connection connection;

    ObservableList<ModelTableSearch> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        handler = new DBHandler();

        searchByUSN.setOnAction(event -> {
            try {
                table_search.getItems().clear();
                searchByUSN();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        searchByName.setOnAction(actionEvent -> {
            try {
                table_search.getItems().clear();
                searchByName();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void searchByUSN() throws SQLException {
        connection = handler.getConnection();
        String query = "SELECT d.Sno, d.usn,d.name, d.sharing, d.bed, d.year, d.fee, l.package_fee as laundry, d.username, d.date FROM laundry l " +
                "right join( " +
                "SELECT history.Sno as Sno, history.staff_username as username, history.student_usn as usn, history.bed_number as bed, history.date as date, history.sharing as sharing, single_sharing.name as name, single_sharing.year as year, single_sharing.fee as fee FROM history " +
                "right join single_sharing " +
                "on history.student_usn = single_sharing.USN " +
                "union " +
                "SELECT history.Sno as Sno, history.staff_username as username, history.student_usn as usn, history.bed_number as bed, history.date as date, history.sharing as sharing, double_sharing.name as name, double_sharing.year as year, double_sharing.fee as fee FROM history " +
                "right join double_sharing " +
                "on history.student_usn = double_sharing.usn " +
                "union " +
                "SELECT history.Sno as Sno, history.staff_username as username, history.student_usn as usn, history.bed_number as bed, history.date as date, history.sharing as sharing, triple_sharing.name as name, triple_sharing.year as year, triple_sharing.fee as fee FROM history " +
                "right join triple_sharing " +
                "on history.student_usn = triple_sharing.usn) d " +
                "on d.usn = l.usn " +
                "WHERE d.usn = ?";

        PreparedStatement statementUsn = connection.prepareStatement(query);
        statementUsn.setString(1, tf_usn.getText());
        ResultSet rs = statementUsn.executeQuery();

        while (rs.next()){
            String laundry = rs.getString("laundry");
            if(rs.wasNull()){
                laundry = "0";
            }
            obList.add(new ModelTableSearch(rs.getString("usn"),
                    rs.getString("name"),
                    rs.getString("sharing"),
                    rs.getString("bed"),
                    rs.getString("year"),
                    rs.getString("fee"),
                    laundry));
        }
        connection.close();

        col_usn.setCellValueFactory(new PropertyValueFactory<>("search_usn"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("search_name"));
        col_sharing.setCellValueFactory(new PropertyValueFactory<>("search_sharing"));
        col_bed.setCellValueFactory(new PropertyValueFactory<>("search_bed"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("search_year"));
        col_basefee.setCellValueFactory(new PropertyValueFactory<>("search_baseFee"));
        col_laundry.setCellValueFactory(new PropertyValueFactory<>("search_laundry"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));

        table_search.setItems(obList);
    }

    private void searchByName() throws SQLException {
        connection = handler.getConnection();
        String query = "SELECT d.Sno, d.usn,d.name, d.sharing, d.bed, d.year, d.fee, l.package_fee as laundry, d.username, d.date FROM laundry l " +
                "right join( " +
                "SELECT history.Sno as Sno, history.staff_username as username, history.student_usn as usn, history.bed_number as bed, history.date as date, history.sharing as sharing, single_sharing.name as name, single_sharing.year as year, single_sharing.fee as fee FROM history " +
                "right join single_sharing " +
                "on history.student_usn = single_sharing.USN " +
                "union " +
                "SELECT history.Sno as Sno, history.staff_username as username, history.student_usn as usn, history.bed_number as bed, history.date as date, history.sharing as sharing, double_sharing.name as name, double_sharing.year as year, double_sharing.fee as fee FROM history " +
                "right join double_sharing " +
                "on history.student_usn = double_sharing.usn " +
                "union " +
                "SELECT history.Sno as Sno, history.staff_username as username, history.student_usn as usn, history.bed_number as bed, history.date as date, history.sharing as sharing, triple_sharing.name as name, triple_sharing.year as year, triple_sharing.fee as fee FROM history " +
                "right join triple_sharing " +
                "on history.student_usn = triple_sharing.usn) d " +
                "on d.usn = l.usn " +
                "WHERE d.name like '%" +tf_name.getText()+ "%'";

        PreparedStatement statementUsn = connection.prepareStatement(query);
        //statementUsn.setString(1, tf_name.getText());
        ResultSet rs = statementUsn.executeQuery();

        while (rs.next()){
            String laundry = rs.getString("laundry");
            if(rs.wasNull()){
                laundry = "0";
            }
            obList.add(new ModelTableSearch(rs.getString("usn"),
                    rs.getString("name"),
                    rs.getString("sharing"),
                    rs.getString("bed"),
                    rs.getString("year"),
                    rs.getString("fee"),
                    laundry));
        }
        connection.close();

        col_usn.setCellValueFactory(new PropertyValueFactory<>("search_usn"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("search_name"));
        col_sharing.setCellValueFactory(new PropertyValueFactory<>("search_sharing"));
        col_bed.setCellValueFactory(new PropertyValueFactory<>("search_bed"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("search_year"));
        col_basefee.setCellValueFactory(new PropertyValueFactory<>("search_baseFee"));
        col_laundry.setCellValueFactory(new PropertyValueFactory<>("search_laundry"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));

        table_search.setItems(obList);
    }
}
