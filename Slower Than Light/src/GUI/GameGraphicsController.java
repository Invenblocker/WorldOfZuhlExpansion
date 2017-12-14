/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import acq.IInjectableController;
import acq.IItem;
import acq.ILogFacade;
import acq.IRoom;
import acq.ITimeHolder;
import acq.IVisualUpdater;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sdown
 */
public class GameGraphicsController implements Initializable, IInjectableController, IVisualUpdater
{
    private final String CONTROL_ROOM_URL = "images/rooms/control-room.png";
    private final String ENTRY_RAMP_URL = "images/rooms/entry-ramp.png";
    private final String KITCHEN_URL = "images/rooms/kitchen.png";
    private final String LAB_URL = "images/rooms/lab.png";
    private final String LEFT_THRUSTER_URL = "images/rooms/left-thruster.png";
    private final String LIVING_QUARTERS_URL = "images/rooms/living-quarters.png";
    private final String LOADING_BAY_URL = "images/rooms/loading-bay.png";
    private final String OFFICE_URL = "images/rooms/office.png";
    private final String OXYGEN_CONTAINER_URL = "images/rooms/oxygen-container.png";
    private final String RIGHT_THRUSTER_URL = "images/rooms/right-thruster.png";
    private final String SHIELD_GENERATOR_URL = "images/rooms/shield-generator.png";
    private final String WORKSHOP_URL = "images/rooms/workshop.png";
    
    private final int MAX_CHARS = 15;
    
    @FXML
    private ImageView currentRoomDisplay;
    @FXML
    private ImageView currentRoomCharacter;
    @FXML
    private Circle chaseAlert;
    @FXML
    private StackPane logPane;
    @FXML
    private TextArea logTextArea;
    @FXML
    private Label playerItem1;
    @FXML
    private Label playerItem2;
    @FXML
    private Pane playerItem1Button;
    @FXML
    private Pane playerItem2Button;
    @FXML
    private Label roomItem1;
    @FXML
    private Label roomItem2;
    @FXML
    private Pane roomItem1Button;
    @FXML
    private Pane roomItem2Button;
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
    private MiniMap minimap;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        logFacade = GUI.getInstance().getILogFacade();
        
        // setup player log
        logTextArea.setEditable(false);
        logTextArea.setWrapText(true);
        
        // print welcome message
        printWelcome();
        
        // setup minimap
        minimap = new MiniMap(logFacade.getRoomPositions(), minimapCanvas.getGraphicsContext2D());
        updateMinimap();
        updateIsChasingPlayer();
        
        logFacade.injectVisualCaller(this);
        logFacade.play();
        
        updateBackground(logFacade.getPlayer().getCurrentRoom().getName());
        updatePlayerItemButtons();
        updateRoomItemButtons();
        updateSaboteurAlert();
    }
    
    @Override
    public void injectStage(Stage _stage) {
        stage = _stage;
    }

    @Override
    public void updateMinimap()
    {
        if (!updateGameEnd())
        {
            List<IRoom> destroyedRoom = logFacade.getGameInfo().getDestroyedRooms();
            String playerRoom = logFacade.getPlayer().getCurrentRoom().getName();
            String saboteurRoom = logFacade.getSaboteur().getCurrentRoom().getName();
            minimap.update(destroyedRoom, playerRoom, saboteurRoom);
            
            updateIsChasingPlayer();
        }
    }

    @Override
    public void updateSaboteurRoom()
    {
        if (!updateGameEnd())
        {
            minimap.updateSaboteurPosition(logFacade.getSaboteur().getCurrentRoom());
            
            updateIsChasingPlayer();
        }
    }

    @Override
    public void updateIsChasingPlayer()
    {
        if (!updateGameEnd())
            updateSaboteurAlert();
    }
    
    @Override
    public void updateProgressBar()
    {
        if (!updateGameEnd())
        {
            ITimeHolder th = logFacade.getTimeHolder();
            double timeProgress = th.getTimeLeft() / th.getTimeMax();
            double oxygenProgress = th.getOxygenLeft() / th.getOxygenMax();
            
            timeBar.setProgress(timeProgress);
            oxygenBar.setProgress(oxygenProgress);
        }
    }

    @Override
    public boolean updateGameEnd()
    {
        if (logFacade.getGameInfo().isGameFinished())
        {
            if (logFacade.getTimeHolder().getTimeLeft()<= 0)
                processGameWon();
            else if (logFacade.getTimeHolder().getOxygenLeft() <= 0)
                processGameLost("oxygon ran out");
            else if (logFacade.getPlayer().getCurrentRoom() == logFacade.getSaboteur().getCurrentRoom())
                processGameLost("saboteur hit player");
            else if (logFacade.getGameInfo().getDestroyedRoomsPercentage() > logFacade.getGameInfo().getALLOWED_ROOMS_DESTROYED_PERCENTAGE())
                processGameLost("to many rooms were destroyed");
            
            return true;
        }
        
        return false;
    }
    
    @FXML
    public void walkUp() {walk("up");}
    
    @FXML
    public void walkDown() {walk("down");}

    @FXML
    public void walkLeft() {walk("left");}

    @FXML
    public void walkRight() {walk("right");}
    
    @FXML
    public void dropItem0()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        logFacade.processCommand("drop 0");
        updatePlayerItemButtons();
        updateRoomItemButtons();
        System.out.println("Drop 0");
    }
    
    @FXML
    public void dropItem1()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        logFacade.processCommand("drop 1");
        updatePlayerItemButtons();
        updateRoomItemButtons();
        System.out.println("Drop 1");
    }
    
    @FXML
    public void takeItem0()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        logFacade.processCommand("take 0");
        updatePlayerItemButtons();
        updateRoomItemButtons();
        System.out.println("take 0");
    }

    @FXML
    public void takeItem1()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        logFacade.processCommand("take 1");
        updatePlayerItemButtons();
        updateRoomItemButtons();
        System.out.println("take 1");
    }
    
    @FXML
    public void repair()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        logFacade.processCommand("repair");
        updatePlayerItemButtons();
        
        IRoom playerRoom = logFacade.getPlayer().getCurrentRoom();
        minimap.updatePlayerPosition(playerRoom);
        
        System.out.println("repair");
    }
    
    @FXML
    public void talk()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
    }

    @FXML
    public void investigate()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        logFacade.processCommand("investigate");
        
        IRoom currentRoom = logFacade.getPlayer().getCurrentRoom();
        
        if (currentRoom.isOperating())
            writeToLog("The room is working");
        else
            writeToLog("This room is broken, if you want to repair this room, you'll need a " + logFacade.getRepairItemInCurrentRoom().getName() + ".");
    }

    @FXML
    public void saveGame()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        logFacade.processCommand("save");
    }

    @FXML
    public void help()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        printHelp();
    }

    @FXML
    public void quit() throws IOException
    {
        logFacade.quit();
        changeScene("MainMenu.fxml");
    }
    
    @FXML
    private void keyPressed(KeyEvent event) throws IOException 
    {
        switch(event.getCode())
        {
            case D:
                walk("right");
                break;
            case W:
                walk("up");
                break;
            case A:
                walk("left");
                break;
            case S:
                walk("down");
                break;
            case DIGIT9:
                takeItem0();
                break;
            case DIGIT0:
                takeItem1();
                break;
            case R:
                repair();
                break;
            case I:
                investigate();
                break;
            case T:
               //
                break;
            case H:
                help();
                break;
            case DIGIT1:
                dropItem0();
                break;
            case DIGIT2:
                dropItem1();
                break;
            case Q:
                quit();
                break;
                
        }
    }
    
    private void walk(String direction)
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        logFacade.processCommand("go " + direction);
        
        IRoom playerRoom = logFacade.getPlayer().getCurrentRoom();
        minimap.updatePlayerPosition(playerRoom);
        
        updateBackground(playerRoom.getName());
        updateRoomItemButtons();
        updateSaboteurAlert();
        
        if (logFacade.getGameInfo().isGameFinished() && logFacade.getPlayer().getCurrentRoom() == logFacade.getSaboteur().getCurrentRoom())
            processGameLost("player walking into saboteur");
    }
    
    public void updateBackground(String roomName)
    {
        switch(roomName)
        {
            case "controlRoom":
                currentRoomDisplay.setImage(new Image(CONTROL_ROOM_URL));
                break;
            case "Workshop":
                currentRoomDisplay.setImage(new Image(WORKSHOP_URL));
                break;
            case "Kitchen":
                currentRoomDisplay.setImage(new Image(KITCHEN_URL));
                break;
            case "EntryRamp":
                currentRoomDisplay.setImage(new Image(ENTRY_RAMP_URL));
                break;
            case "Lab":
                currentRoomDisplay.setImage(new Image(LAB_URL));
                break;
            case "LeftThruster":
                currentRoomDisplay.setImage(new Image(LEFT_THRUSTER_URL));
                break;
            case "LoadingBay":
                currentRoomDisplay.setImage(new Image(LOADING_BAY_URL));
                break;
            case "RightThruster":
                currentRoomDisplay.setImage(new Image(RIGHT_THRUSTER_URL));
                break;
            case "Office":
                currentRoomDisplay.setImage(new Image(OFFICE_URL));
                break;
            case "OxygenContainer":
                currentRoomDisplay.setImage(new Image(OXYGEN_CONTAINER_URL));
                break;
            case "LivingQuarters":
                currentRoomDisplay.setImage(new Image(LIVING_QUARTERS_URL));
                break;
            case "ShieldGenerator":
                currentRoomDisplay.setImage(new Image(SHIELD_GENERATOR_URL));
                break;
            default:
                System.out.println("GameGraphicsController: Cannot change the background to that room");
                break;
        }
    }
    
    private void updatePlayerItemButtons()
    {
        IItem[] itemsInInvetory = logFacade.getPlayerItems();
        
        
        switch (logFacade.getPlayer().getItemCount())
        {
            case 0:
                playerItem1.setText("");
                playerItem2.setText("");
                break;
            case 1:
                if (itemsInInvetory[0] != null)
                {
                    playerItem1.setText(itemsInInvetory[0].getName());
                    playerItem2.setText("");
                }
                else
                {
                    playerItem1.setText("");
                    playerItem2.setText(itemsInInvetory[1].getName());
                }
                break;
            default:
                playerItem1.setText(itemsInInvetory[0].getName());
                playerItem2.setText(itemsInInvetory[1].getName());
                break;
        }
    }
    
    private void updateRoomItemButtons()
    {
        IItem[] itemsInCurrentRoom = logFacade.getItemsInCurrentRoom();
        
        switch (itemsInCurrentRoom.length)
        {
            case 0:
                roomItem1.setText("");
                roomItem2.setText("");
                break;
            case 1:
                if (itemsInCurrentRoom[0] != null)
                {
                    roomItem1.setText(itemsInCurrentRoom[0].getName());
                    roomItem2.setText("");
                }
                else
                {
                    roomItem1.setText("");
                    roomItem2.setText(itemsInCurrentRoom[1].getName());
                }
                break;
            default:
                roomItem1.setText(itemsInCurrentRoom[0].getName());
                roomItem2.setText(itemsInCurrentRoom[1].getName());
                break;
        }
    }
    
    public void updateSaboteurAlert()
    {
        if (logFacade.getSaboteur().isChasingPlayer())
            chaseAlert.setFill(Color.DARKRED);
        else
            chaseAlert.setFill(Color.GREEN);
    }

    private boolean updateGameEnds()
    {
        if (logFacade.getGameInfo().isGameFinished())
        {
            if (logFacade.getTimeHolder().getTimeLeft()<= 0)
                processGameWon();
            else if (logFacade.getTimeHolder().getOxygenLeft() <= 0)
                processGameLost("oxygon ran out");
            else if (logFacade.getPlayer().getCurrentRoom() == logFacade.getSaboteur().getCurrentRoom())
                processGameLost("saboteur hit player");
            
            return true;
        }
        
        return false;
    }
    
    private void processGameWon()
    {
        logFacade.quit();
        System.out.println("Game won. Congratulations!!!");
        logFacade.getGameInfo().saveHighScore("Player 1");
        changeScene("Highscore.fxml");
    }

    private void processGameLost(String msg)
    {
        logFacade.quit();
        System.out.println("Game lost because " + msg);
        changeScene("Highscore.fxml");
    }
    
    private void changeScene(String sceneName)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            IInjectableController controller = loader.getController();
            controller.injectStage(stage);

            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            System.out.println("Game: Tried to change scene to " + sceneName);
            e.printStackTrace();
        }
    }
  
    private void printWelcome()
    {
        writeToLog("Welcome to the game");
    }
    
    private void printHelp()
    {
        writeToLog("You can use the keys:\n  W,A,S,D,R,I,H and Q");
    }
    
    private void writeToLog(String text)
    {
        if (logTextArea.getText().equals(""))
            logTextArea.appendText("- " + text);
        else
            logTextArea.appendText("\n- " + text);
    }
    
}
