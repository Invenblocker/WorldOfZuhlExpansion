/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import acq.IInjectableController;
import acq.IItem;
import acq.ILogFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sdown
 */
public class GameGraphicsController implements Initializable, IInjectableController {

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
    private Button commandButton0;
    @FXML
    private Button commandButton1;
    @FXML
    private Button commandButton2;
    @FXML
    private Button commandButton3;
    @FXML
    private Button commandButton4;
    @FXML
    private Button commandButton5;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    
    private ILogFacade logFacade;
    
    @Override
    public void injectLogFacade(ILogFacade _logFacade) {
        logFacade = _logFacade;
    }

    @Override
    public void injectStage(Stage stage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
    
    public void walkUp() {
        logFacade.processCommand("go up");
    }
    
    public void walkDown() {
        logFacade.processCommand("go down");
    }

    public void walkLeft() {
        logFacade.processCommand("go left");
    }

    public void walkRight() {
        logFacade.processCommand("go right");
    }

    public void dropItem0() {
        logFacade.processCommand("drop 0");
    }
    
    public void dropItem1() {
        logFacade.processCommand("drop 1");
    }
    
    public void takeItem0() {
        logFacade.processCommand("take 0");
    }

    public void takeItem1() {
        logFacade.processCommand("take 1");
    }
    
    public void repair() {
        logFacade.processCommand("repair");
        //INCOMPLETE
    }
    
    public void giveTask() {
        
    }

    public void investigate() {
        logFacade.processCommand("investigate");
    }

    public void saveGame() {
        logFacade.processCommand("save");
    }

    public void help() {
        logFacade.processCommand("help");
    }

    public void quit() {
        logFacade.processCommand("quit");
    }

    public void sabouteurAlert() {
        
    }

    
    















}
