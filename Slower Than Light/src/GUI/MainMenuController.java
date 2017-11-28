/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import acq.IGUI;
import acq.IInjectableController;
import acq.ILogFacade;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sdown
 */
public class MainMenuController implements Initializable, IInjectableController {

    ILogFacade logFacade;
    Stage stage;
    
    public void injectStage (Stage _stage)
    {
        stage = _stage;
    }
    
    @FXML
    private Button newGameButton;
    @FXML
    private Button loadGameButton;
    @FXML
    private Button highscoreButton;
    @FXML
    private Button quitButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
    
    
    }    

    @FXML
    private void handleNewGameBtn(MouseEvent event) throws IOException
    {
        logFacade.newGame();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLGameGraphics.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        
    }

    @FXML
    private void handleLoadGameBtn(MouseEvent event) throws IOException
    {
        logFacade.loadGame();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(" "));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleHighscoreBtn(MouseEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(" "));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleQuitBtn(MouseEvent event) throws IOException
    {
        System.exit(0);
    }

    @Override
    public void injectLogFacade(ILogFacade logFacade) 
    {
       this.logFacade = logFacade;
    }

   
    
    
}
