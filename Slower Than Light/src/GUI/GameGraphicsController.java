/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import acq.IInjectableController;
import acq.IItem;
import acq.ILogFacade;
import acq.IVisualUpdater;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sdown
 */
public class GameGraphicsController implements Initializable, IInjectableController, IVisualUpdater {

    @FXML
    private ImageView currentRoomDisplay;
    @FXML
    private ImageView currentRoomCharacter;
    @FXML
    private Circle chaseAlert;
    @FXML
    private Text currentRoomName;
    @FXML
    private Text playerLog;
    @FXML
    private Label playerItem1;
    @FXML
    private Label playerItem2;
    @FXML
    private Label roomItem1;
    @FXML
    private Label roomItem2;
    @FXML
    private Button leftButton;
    @FXML
    private Button upButton;
    @FXML
    private Button rightButton;
    @FXML
    private Button downButton;
    @FXML
    private Canvas minimapCanvas;
    
    @FXML
    private ProgressBar oxygenBar;
    @FXML
    private ProgressBar timeBar;
    @FXML
    private Button repairButton;
    @FXML
    private Button investigateButton;
    @FXML
    private Button talkButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button saveButton;
    
    @FXML
    private Button quitButton;

    private ILogFacade logFacade;
    private Stage stage;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        logFacade = GUI.getInstance().getILogFacade();
        logFacade.injectGUIUpdateMethod(this);
        logFacade.play();
    }
    
    @Override
    public void injectStage(Stage _stage) {
        stage = _stage;
    }
    
    @Override
    public void updateWithTimer()
    {
        System.out.println("Updated timer");
    }
    
    @FXML
    public void walkUp() {
        logFacade.processCommand("go up");
    }
    
    @FXML
    public void walkDown() {
        logFacade.processCommand("go down");
    }

    @FXML
    public void walkLeft() {
        logFacade.processCommand("go left");
    }

    @FXML
    public void walkRight() {
        logFacade.processCommand("go right");
    }

    public void dropItem0() {
        logFacade.processCommand("drop 0");
    }
    
    public void dropItem1() {
        logFacade.processCommand("drop 1");
    }
    
    @FXML
    public void takeItem0() {
        logFacade.processCommand("take 0");
    }

    @FXML
    public void takeItem1() {
        logFacade.processCommand("take 1");
    }
    
    @FXML
    public void repair() {
        logFacade.processCommand("repair");
        //INCOMPLETE
    }
    
    @FXML
    public void talk() {
        
    }

    @FXML
    public void investigate() {
        logFacade.processCommand("investigate");
    }

    @FXML
    public void saveGame() {
        logFacade.processCommand("save");
    }

    @FXML
    public void help() {
        logFacade.processCommand("help");
    }

    @FXML
    public void quit() throws IOException {
        //logFacade.processCommand("quit");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        IInjectableController controller = loader.getController();
        controller.injectStage(stage);
        
        stage.setScene(scene);
        stage.show();
    }

    public void saboteurAlert() {
        
    }


    
    















}