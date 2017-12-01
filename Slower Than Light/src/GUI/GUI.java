/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import acq.IGUI;
import acq.IInjectableController;
import acq.IItem;
import acq.ILogFacade;
import acq.IRoom;
import java.util.Map;
import logic.Game;
import acq.IVisualUpdater;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Erik
 */
public class GUI extends Application implements IGUI, IVisualUpdater
{
    private static GUI instance = null;
    public static GUI getInstance()
    {
        if (instance == null)
            instance = new GUI();
        
        return instance;
    }
    
    private ILogFacade logFacade;
    private Log log;
    
    public GUI()
    {
        //this.minimap = new MiniMap();       //Creates new minimap object
        this.log = new Log(5);
    }
    
    @Override
    public void injectLogic(ILogFacade _logFacade)
    {
        logFacade = _logFacade;
    }

    @Override
    public void updateWithTimer()
    {
        writeToLog("Updated timer");
    }
  
    public void updateMinimap(IRoom saboteurRoom, IRoom[] destroyedRooms) { //updates saboteur position, calls update in MiniMap class.
        //minimap.update(saboteurRoom, destroyedRooms);
        
    }
  
    public void updateRoom(IRoom room)       //updates player position to minimap   
    {
        //minimap.updatePlayerPosition(room);
        writeToLog("You moved to " + logFacade.getPlayer().getCurrentRoom().getName());
    }
  
    public void updateInventory(IItem[] inventory)
    {
        System.out.println(inventory);
    }
  
    public void investigate(IRoom room)
    {
        
    }
  
    /**
      * Prints a short description of the game and then a list of commands.
      */
    public void printHelp()
    {
        System.out.println("YOU BE FUCKED");
        System.out.println();
        System.out.println("Your command words are:");
        Game.getInstance().getParser().showCommands();
    }
  
    public void printWelcome() {
        writeToLog("Welcome to the game");
        
    }
    public void prinInventory(IItem[] inventory)
    {
        System.out.println(inventory);
    }
    
    public void showHighScore(Map <String, Integer> highScore) {
        
    }
    
    public void writeToLog (String text)
    {
        log.write(text);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml")); //Creates new FXML Loader which loads MainMenu.fxml
        Parent root = loader.load();                                 //Sets root equals to our loaded MainMenu.fxml
        
        Scene scene = new Scene(root);                          //Creates new Scene of our root
        
        IInjectableController controller = loader.getController();      //Injects loader into our controller
        controller.injectStage(primaryStage);                            //Injects primarystage                      //Injects logFacade
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
        
        primaryStage.setScene(scene);                               //Sets secene
        primaryStage.show();                                        //Shows stage
    }

    @Override
    public void startApplication(String[] args) 
    {
        launch(args);
    }
    
    
    
    public ILogFacade getILogFacade() {return logFacade;}
}

