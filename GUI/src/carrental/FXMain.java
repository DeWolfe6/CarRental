/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Basel, Duy, Jacob, Ismail
 */
public class FXMain extends Application {
    
    @Override
    public void start(Stage primaryStage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/StartScreen.fxml"));   
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
