package logic.elements.rooms;

/**
 *
 * @author Loc
 */
public class Exit {
    
    private Room exitRoom1;
    private Room exitRoom2;
    private boolean operating;
    
    public Exit(Room exitRoom1, Room exitRoom2)
    {
        this.exitRoom1 = exitRoom1;       //first exit that a room can have
        this.exitRoom2 = exitRoom2;       //second exit that a room can have
        this.operating = true;
    }

    public Room getExitRoom1() {
        return exitRoom1;
    }

    public Room getExitRoom2() {
        return exitRoom2;
    }

    public boolean isOperating()          //Checks if door is damaged
    {
        return operating;
    }

    public void setOperating(boolean value)  //Sets whether door is functional or not
    {
        this.operating = value;

    }
    
}


