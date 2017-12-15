package GUI;

import acq.IRoom;
import java.awt.Point;
import java.util.ArrayList;
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
    private LayeredSprite ls = new LayeredSprite();
    private Map <String, Point> roomPositions = new HashMap<>();
    private GraphicsContext gc;
    
    private List<IRoom> destroyedRooms;
    private String playerRoom;
    private String saboteurRoom;
    
    /**
     * Initializes a minimap, given a Map consisting og String names and Point coordinates, togehter with a GraphicsContext.
     * @param roomPositions
     * @param gc 
     */
    public MiniMap(Map <String, Point> roomPositions, GraphicsContext gc)
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
    
    
    /**
     * Updates all the objects on the minimap and draw them once again
     */
    private void redraw ()
    {
        ls = new LayeredSprite();
        updateRoom();
        updatePlayer();
        updateSaboteur();
        ls.render(gc);
    }
    
    /**
     * Adds the rooms to the LayeredSprite, with the destroyed rooms drawn on top of the non-destroyed rooms.
     */
    private void updateRoom()
    {
        // iterates through the array with destroyed rooms
        List<String> destroyedRoomNames = new ArrayList<>();
        for (IRoom destroyedRoom : destroyedRooms)  
            destroyedRoomNames.add(destroyedRoom.getName());
        
        for (String roomName : roomPositions.keySet())
        {
            // checks if the room point in the hashmap is the same as the destroyed room
            // adds the coordinates to the destroyed room, and adds it to the minimap
            if (destroyedRoomNames.contains(roomName))  
                ls.addSprite(2, new DestroyedRoomDraw(roomPositions.get(roomName), roomName));  
            else
                ls.addSprite(1, new RoomDraw(roomPositions.get(roomName), roomName));
        }
    }
    
    /**
     * Adds the player to the LayeredSprite - on top of the rooms, regardless of their state, but on same layer as the saboteur.
     */
    private void updatePlayer()
    {
        for (String key : roomPositions.keySet())
            if (key.equals(playerRoom))
                ls.addSprite(3, new PlayerDraw(roomPositions.get(key)));
    }
    
    
    /**
     * Adds the saboteur to the LayeredSprite - on top of the rooms, regardless of their state, but on same layer as the player.
     */
    private void updateSaboteur()
    {
        for (String key : roomPositions.keySet())
            if (key.equals(saboteurRoom))
                ls.addSprite(3, new SaboteurDraw(roomPositions.get(key)));
    }
    
}





