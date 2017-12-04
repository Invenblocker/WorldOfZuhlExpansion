/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import acq.IInjectableController;
import acq.ILogFacade;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mortenskovgaard
 */
public class HighscoreController implements Initializable, IInjectableController 
{

    @FXML
    private ListView<String> HighscoreLw;
    @FXML
    private Button BackToMainMenuBtn;

    ILogFacade logFacade;
    Stage stage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        logFacade = GUI.getInstance().getILogFacade();
        
       // ArrayList<String>highscoreString = new ArrayList<>();
        
    }    

    @Override
    public void injectStage(Stage _stage) 
    {
        stage = _stage;
    }

    @FXML
    private void handleBackToMainMenuBtn(MouseEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        
        IInjectableController controller = loader.getController();
        controller.injectStage(stage);
        
        stage.setScene(scene);
        stage.show();
    }
    
}
