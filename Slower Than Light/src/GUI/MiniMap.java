/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import acq.IRoom;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author Peter
 */
public class MiniMap
{
    LayeredSprite ls = new LayeredSprite();
    Map<String, Point> roomPositions = new HashMap<>();
    GraphicsContext gc;
    
    List<IRoom> destroyedRooms;
    String playerRoom;
    String saboteurRoom;
    
    /**
     * Initializes a minimap, given a Map consisting og String names and Point coordinates, togehter with a GraphicsContext.
     * @param roomPositions
     * @param gc 
     */
    public MiniMap(Map<String, Point> roomPositions, GraphicsContext gc)
    {
        this.roomPositions = roomPositions;
        this.gc = gc;
        
        destroyedRooms = new ArrayList<>();
    }
    
    /**
     * Update is called when inside the control room. it updates the local variables, whereafter it calls redraw, to redraw the minimap.
     * @param destroyedRooms
     * @param playerRoom
     * @param saboteurRoom 
     */
    public void update(List<IRoom> destroyedRooms, String playerRoom, String saboteurRoom)
    {
        this.destroyedRooms = destroyedRooms;
        this.playerRoom = playerRoom;
        this.saboteurRoom = saboteurRoom;
        redraw();
        
        System.out.println("***   Rooms updated   ***");
        System.out.println("Saboteur is in room: " + saboteurRoom);
        System.out.print("Rooms destroyed: ");
        for (IRoom destroyedRoom : destroyedRooms)
            System.out.print(destroyedRoom.getName() + " ");
        System.out.println("");
    }
    
    /**
     * updatePlayerPosition updates the position of the player, together with the state of the room he enters.
     * @param playerRoom 
     */
    public void updatePlayerPosition(IRoom playerRoom)
    {
        this.playerRoom = playerRoom.getName();
        
        // update the room the player enters
        if (playerRoom.isOperating() && destroyedRooms.contains(playerRoom))
            destroyedRooms.remove(playerRoom);
        else if (!playerRoom.isOperating() && !destroyedRooms.contains(playerRoom))
            destroyedRooms.add(playerRoom);
        
        redraw();
    }
    
    /**
     * updateSaboteurPosition is called whenever the player is having a PC in his inventory, or is in the control room.
     * it updates the position of the saboteur.
     * @param saboteurRoom 
     */
    public void updateSaboteurPosition(IRoom saboteurRoom)
    {
        this.saboteurRoom = saboteurRoom.getName();
        
        redraw();
    }
    
    /*
    public void updateDestroyedRooms(List<IRoom> destroyedRooms)
    {
        this.destroyedRooms = destroyedRooms;
        redraw();
    }
    */
    
    /**
     * Updates all the objects on the minimap and draw them once again
     */
    private void redraw ()
    {
        ls = new LayeredSprite();
        updateRoom(destroyedRooms, ls);
        updatePlayer(playerRoom, ls);
        updateSaboteur(saboteurRoom, ls);
        ls.render(gc);
    }
    
    /**
     * Adds the rooms to the LayeredSprite, with the destroyed rooms drawn on top of the non-destroyed rooms. 
     * @param destroyedRooms Array som består af de rum som er ødelagt.
     * @param gc Det
     */
    private void updateRoom(List<IRoom> destroyedRooms, LayeredSprite ls)
    {
        
        for (int i = 0; i < destroyedRooms.size(); i++){            // kører gennem array med ødelagte rum
            for (String key : roomPositions.keySet()){              // kører gennem vores hashmap
                if (destroyedRooms.get(i).getName().equals(key))        // tjekker om rummet i hashmappet er det samme som det ødelagte rum
                    ls.addSprite(2, new DestroyedRoomDraw(roomPositions.get(key), key));     // tilføjer koordinaterne til det ødelagte rum og tilføjer det til minimappet
                else if(!destroyedRooms.get(i).getName().equals(key))
                    ls.addSprite(1, new RoomDraw(roomPositions.get(key), key));
            }
        }
    }
    
    /**
     * Adds the player to the LayeredSprite - on top of the rooms, regardless of their state, but on same layer as the saboteur.
     * @param playerRoom
     * @param ls 
     */
    private void updatePlayer(String playerRoom, LayeredSprite ls)
    {
        for (String key : roomPositions.keySet())
            if (key.equals(playerRoom))
                ls.addSprite(3, new PlayerDraw(roomPositions.get(key)));
    }
    
    
    /**
     * Adds the saboteur to the LayeredSprite - on top of the rooms, regardless of their state, but on same layer as the player.
     * @param saboteurRoom
     * @param ls 
     */
    private void updateSaboteur(String saboteurRoom, LayeredSprite ls)
    {
        for (String key : roomPositions.keySet())
            if (key.equals(saboteurRoom))
                ls.addSprite(3, new SaboteurDraw(roomPositions.get(key)));
    }
    
}





