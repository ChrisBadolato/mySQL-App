/* Name:Christopher Badolato
Course: CNT 4714 Spring 2020 Assignment title: 
Project Three:  Two-Tier Client-Server Application Development With MySQL and JDBC
March 5, 2020
Class:  Project3CBadolato_clientServer.java*/ 

package project3cbadolato_clientserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Project3CBadolato_clientServer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
