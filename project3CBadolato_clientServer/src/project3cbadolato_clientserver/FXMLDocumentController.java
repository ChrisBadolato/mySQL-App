/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3cbadolato_clientserver;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


/**
 *
 * @author Chris Badolato
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextArea executionTextArea;
    @FXML
    private TextField sqlCommandTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private ChoiceBox<String> databaseURLchoiceBox;
    @FXML
    private ChoiceBox<String> JDBCChoiceBox;
    @FXML
    private Button connectToServerButton;
    @FXML
    private Button connectToDatabaseButton;
    @FXML
    private Button clearCommandButton;
    @FXML
    private Button executeCommandButton;
    
    String databaseURL;
    String JDBCdriver;
    
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            // TODO    
        Connection connection = null;
        JDBCChoiceBox.getItems().addAll("com.mysql.jdbc.Driver", "Other", null);
        databaseURLchoiceBox.getItems().addAll("jdbc:mysql://localhost:3312/bikedb", "Other", null);      
    }     

    @FXML
    private void connectToServer(MouseEvent event) {
        Connection connection = null;
        try {
            Class.forName(JDBCChoiceBox.getValue());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Connecting to database...");      
        try{
            connection = DriverManager.getConnection(databaseURLchoiceBox.getValue() ,usernameTextField.getText(), passwordTextField.getText());
            System.out.println("Database Connected");
        }
        catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        } 
    }

    @FXML
    private void connectToDatabase(MouseEvent event) {
    }

    @FXML
    private void clearSQLCommand(MouseEvent event) {
    }

    @FXML
    private void executeSQLCommand(MouseEvent event) {
    }
}
