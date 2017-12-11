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
import acq.IVisualUpdater;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.KeyStroke;

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
        
        // print welcome message
        printWelcome();
        
        // setup minimap
        minimap = new MiniMap(logFacade.getRoomPositions(), minimapCanvas.getGraphicsContext2D());
        updateMinimap();
        updateIsChasingPlayer();
        
        logFacade.injectGUIUpdateMethod(this);
        logFacade.play();
        
        updateHeader();
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
            
            return true;
        }
        
        return false;
    }
    
    @FXML
    public void walkUp()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        walk("up");
    }
    
    @FXML
    public void walkDown()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        walk("down");
    }

    @FXML
    public void walkLeft()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        
        walk("left");
    }

    @FXML
    public void walkRight()
    {
        if(logFacade.getGameInfo().isGameFinished())
            return;
        walk("right");
    }
    
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
    
    private void walk(String direction)
    {
        logFacade.processCommand("go " + direction);
        
        updateHeader();
        updateRoomItemButtons();
        updateSaboteurAlert();
        
        IRoom playerRoom = logFacade.getPlayer().getCurrentRoom();
        minimap.updatePlayerPosition(playerRoom);
        
        if (logFacade.getGameInfo().isGameFinished() && logFacade.getPlayer().getCurrentRoom() == logFacade.getSaboteur().getCurrentRoom())
            processGameLost("player walking into saboteur");
    }
    
    private void updateHeader()
    {
        currentRoomName.setText(logFacade.getPlayer().getCurrentRoom().getName());
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
        System.out.println("Game lost from " + msg);
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
        System.out.println("Welcome to the game");
    }
    
    private void printHelp()
    {
        System.out.println("This is your help...");
    }

    @FXML
    private void keyPressed(KeyEvent event) 
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
            case DIGIT1:
                dropItem0();
                break;
            case DIGIT2:
                dropItem1();
                break;
                
                    
        }

    }
    
}
