/* Name:Christopher Badolato
Course: CNT 4714 Spring 2020 Assignment title: 
Project Three:  Two-Tier Client-Server Application Development With MySQL and JDBC
March 5, 2020
Class:  FXMLDocumentController.java*/ 
package project3cbadolato_clientserver;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Label connectionLabel;
    @FXML
    private TextArea executionTextArea;
    @FXML
    private TextArea sqlCommandTextField;
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
    private Button clearCommandButton;
    @FXML
    private Button executeCommandButton;
    @FXML
    private Button clearResultsButton;
    
    String databaseURL;
    String JDBCdriver;
    Connection connection = null;   


    
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            // TODO          
        JDBCChoiceBox.getItems().addAll("com.mysql.jdbc.Driver", "Other", null);
        databaseURLchoiceBox.getItems().addAll("jdbc:mysql://localhost:3312/bikedb", "jdbc:mysql://localhost:3312/project3","Other", null);      
    }     

    @FXML
        //connects us to the server of our choice
    private void connectToServer(MouseEvent event) {        
        try {
            Class.forName(JDBCChoiceBox.getValue());
        } catch (ClassNotFoundException ex) {        
            executionTextArea.appendText("Cannot find Driver\n"); 
        }           
            
        try{
            connection = DriverManager.getConnection(databaseURLchoiceBox.getValue() ,usernameTextField.getText(), passwordTextField.getText());       
            connectionLabel.setText("Connected to " + databaseURLchoiceBox.getValue());
        }
            //if we cannot connect to the server we display an error message
        catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Database Error");   
            alert.setContentText("Cannot Connect to Database");
            alert.setHeaderText(null);              
            alert.setResizable(true);        
            alert.getDialogPane().setPrefSize(500, 200);
            alert.showAndWait();                  
        } 
    }


    @FXML
    private void clearSQLCommand(MouseEvent event) {
        sqlCommandTextField.clear();
    }

    @FXML
    private void executeSQLCommand(MouseEvent event){      
        String newCommand = sqlCommandTextField.getText();     
        String[] commandList =  newCommand.split(";");  
            //for each command we will run each query.
        for(int i = 0; i < commandList.length; i++){ 
            Statement newStatement = null;
            ResultSet results = null;            
            newStatement = createStatementObject();       
                //get results from query
            results = executeStatement(newStatement,commandList[i]);        
            writeResults(results);
            closeStatement(newStatement);
        }              
    }

    @FXML
    private void clearResultsWindow(MouseEvent event) {
        executionTextArea.clear();
    }
        //creates statement object typed in by user
    private Statement createStatementObject(){
        Statement newStatement = null;
        try {
            newStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            executionTextArea.appendText("Cannot create new statement!\n");
        }
        return newStatement;
    }
        //gets the result set
    private ResultSet getResults(Statement newStatement){
        ResultSet results = null;
            try {
                results = newStatement.getResultSet();
            } catch (SQLException ex) {
                executionTextArea.appendText("entered statement error!\n");
            }
        return results;
    }
        //will write results from the database select to the textArea
    private void writeResults(ResultSet results){
        try {        
                //We will return if results are null (update, instert, delete commands, anything but select returns null.)
            if(results == null){
                return;
            }
            int columnCount = results.getMetaData().getColumnCount(); 
            ResultSetMetaData metaData = results.getMetaData();
                //show column names
            for(int i = 1; i < (columnCount + 1); i++){                   
                    executionTextArea.appendText(metaData.getColumnName(i) + "\t\t\t");                
            }          
            executionTextArea.appendText("\n");
            for(int i = 0; i < columnCount * 25; i++){
                executionTextArea.appendText("_");
            }
            executionTextArea.appendText("\n");
                //write results to text area
            while(results.next()){             
                StringBuilder sb = new StringBuilder();             
                for(int j = 1; j < (columnCount + 1); j++){
                    String value = results.getString(j);
                    sb.append(String.format("%-30.30s", value));
                }
                sb.append("\n");
                executionTextArea.appendText(sb.toString()); 
                System.out.print(sb.toString());
            }
            executionTextArea.appendText("\n");
            System.out.print("\n");
                //No results error
        } catch (SQLException ex) {
            executionTextArea.appendText("No results!\n");
        }
    }
        //close the current statement
    private void closeStatement(Statement newStatement ){
        try {      
            newStatement.close();
        } catch (SQLException ex) {
            System.out.println("Cannot Close statement");
        }
    }
    private ResultSet executeStatement(Statement newStatement, String newCommand){
        ResultSet results = null;
        String[] errorCommand = newCommand.split(" ");
        try {
            /*later this will be used for other SQL commands.
                if(newStatement.execute(newCommand)){
                    newStatement.executeQuery(newCommand);                 
                    results = getResults(newStatement);
                }
                else{
                    newStatement.executeUpdate(newCommand);
                }
            */
            newStatement.executeQuery(newCommand);                 
            results = getResults(newStatement);
            //if we don't have priviledge to change the data we just need to return null.
        } catch (SQLException ex) {          
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cannot access data with way" + errorCommand[0].toUpperCase());   
            alert.setContentText(errorCommand[0].toUpperCase() + " command denied by user '" + usernameTextField.getText() + "'@" + "'localhost' for table " + errorCommand[2]);
            alert.setHeaderText(null);              
            alert.setResizable(true);        
            alert.getDialogPane().setPrefSize(700, 200);
            alert.showAndWait();
            return null;
        }
        return results;
    }
}
