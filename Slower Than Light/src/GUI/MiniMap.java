/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import acq.IRoom;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Peter
 */
public class MiniMap extends Application {
    
     HashMap<String, Point> rooms = new HashMap<>();
       ArrayList<IRoom> dr = new ArrayList<>();
       String playerRoom;
       String saboteurRoom;
    
    @Override
    public void start(Stage primaryStage) {
        
        StackPane root = new StackPane();
        
     
        Scene scene = new Scene(root, 1000, 1000);
        LayeredSprite ls = new LayeredSprite();
        SetupCanvas sc = new SetupCanvas(1000, 1000);
        GraphicsContext gc = sc.getGraphicsContext2D();
        root.getChildren().add(sc);
       
        rooms.put("Room1", new Point(500,50));
        rooms.put("Room2", new Point(100,200));
        rooms.put("Room3", new Point(30,30));
        
        
       this.playerRoom = "Room1";
       this.saboteurRoom = "Room2";
        
        update(rooms, dr, ls, gc, playerRoom, saboteurRoom);
        
       
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    
    
    private void updateRoom(HashMap<String, Point> rooms, ArrayList<IRoom> destroyedRooms, LayeredSprite ls, GraphicsContext gc){ // Hashmappet er alle vores rum og koordinater, arraylisten består af de rum som er ødelagt.
        for (int i = 0; i < destroyedRooms.size(); i++){        // kører gennem arraylisten med ødelagte rum
            for (String key : rooms.keySet()){                  //kører gennem vores hashmap
                if (destroyedRooms.get(i).getName().equals(key)){         // tjekker om rummet i hashmappet er det samme som det ødelagte rum
                    ls.addSprite(2, new DestroyedRoom(rooms.get(key), key));     // tilføjer koordinaterne fra det ødelagte rum til vores layered sprite, som sørger for at tegne.
                }
                else if(!destroyedRooms.get(i).getName().equals(key)){
                    ls.addSprite(1, new drawRoom(rooms.get(key), key));
                }
            }  
        }      
    }
    
    private void updatePlayer(LayeredSprite ls, GraphicsContext gc, String playerRoom, HashMap<String, Point> rooms){
        for (String key : rooms.keySet()){
            if (key.equals(playerRoom)){
                 ls.addSprite(3, new PlayerDraw(rooms.get(key)));
            }
        }
    }
    
    
    
        private void updateSaboteur(LayeredSprite ls, GraphicsContext gc, String saboteurRoom, HashMap<String, Point> rooms){
        for (String key : rooms.keySet()){
            if (key.equals(saboteurRoom)){
                 ls.addSprite(3, new SaboteurDraw(rooms.get(key)));
            }
        }
    }
    
 
    
    public void update(HashMap<String, Point> rooms, ArrayList<IRoom> destroyedRooms, LayeredSprite ls, GraphicsContext gc, String playerRoom, String saboteurRoom){
        ls = new LayeredSprite(); // laver nyt layeredSprite så de gamle data er fjernet.
        this.dr = destroyedRooms;
        this.playerRoom = playerRoom;
        this.saboteurRoom = saboteurRoom;
        updateRoom(rooms, destroyedRooms, ls, gc);
        updatePlayer(ls, gc, playerRoom, rooms);
        updateSaboteur(ls, gc, saboteurRoom, rooms);
        ls.render(gc);
    }
    
    public void updatePlayerPosition(String playerRoom, LayeredSprite ls, GraphicsContext gc){
        this.playerRoom = playerRoom;
        update(rooms, dr, ls, gc, playerRoom, saboteurRoom);
    }
    
    public void updateDestroyedRooms(ArrayList<IRoom> destroyedRooms, LayeredSprite ls, GraphicsContext gc){
        this.dr = destroyedRooms;
        update(rooms, destroyedRooms, ls, gc, playerRoom, saboteurRoom);
    }
    
    
    /*
    public static void main(String[] args) {
        launch(args);
    }
*/
    
}





