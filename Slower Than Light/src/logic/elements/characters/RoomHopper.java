package logic.elements.characters;

import acq.IRoomHopper;
import logic.SystemLog;
import logic.elements.rooms.Room;

/**
 *
 * @author Invenblocker & JN97
 */
public abstract class RoomHopper implements IRoomHopper
{
    private Room currentRoom;
    
    /**
     * @author Invenblocker
     * 
     * A constructor for the RoomHopper object.
     * 
     * @param room the character's starting room.
     */
    public RoomHopper(Room room)
    {
        currentRoom = room;
    }
    
    /**
     * @author Invenblocker
     * 
     * Moves the object to a new room and returns the old room.
     * 
     * @param newRoom The room the character should move to.
     * @return The room the character was in before moving.
     */
    public Room setRoom(Room newRoom)
    {
        Room oldRoom = currentRoom;
        currentRoom = newRoom;
        
        String msg = "Moved from \"" + oldRoom + "\" to \"" + newRoom + "\".";
        getActionLog().writeToLog(msg);
        System.out.println(msg);
        
        return oldRoom;
    }
    
    /**
     * @author Invenblocker
     * 
     * Checks which room the character is currently in.
     * 
     * @return The room in which the character is currently located.
     */
    @Override
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * @author JN97
     * @return RoomHopper's ACTION_LOG
     */
    public abstract SystemLog getActionLog();
    
    /**
     * @author JN97
     * @return RoomHopper's ERROR_LOG
     */
    public abstract SystemLog getErrorLog();
}
