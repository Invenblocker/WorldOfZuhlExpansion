package logic.processors;

import java.util.ArrayList;
import logic.Game;
import logic.elements.characters.Item;
import logic.elements.rooms.ItemRoom;
import logic.elements.rooms.Room;
import logic.elements.rooms.WorkshopRoom;
import logic.user_input.Command;
import logic.user_input.CommandWord;

public class GameCommand {
    private final Game game;

    public GameCommand(){
        game = Game.getInstance();

    }

    /**
     * Processes the command entered by the player running its function and
     * checking if the game should end.
     * @param command The command entered by the player.
     * @return A boolean that states if the game should end.
     */
    public boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (null != commandWord) switch (commandWord) {
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case TAKE:
                takeItem(command);
                break;
            case DROP:
                dropItem(command);
                break;
            case REPAIR:
                repairItem(command);
                break;
            case INVESTIGATE:
                investigate();
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            default:
                System.out.println("I don't know what you mean...\nType \"help\" for a list of commands.");
                return false;
        }
        else
        {
            System.out.println("Please write in a command before hitting enter.");
        }
        return wantToQuit;
    }
    
    /**
     * Goes to the room specified by the player, if the player has not entered a
     * second word in the command, asks the player for direction and does
     * nothing. If the direction is invalid, tells the player that they cannot
     * go in that direction and does nothing.
     * @param command The command entered by the player
     */
    private void goRoom (Command command)
    {
        if (!command.hasSecondWord())
        {
            System.out.println("Go where?");
            return;
        }
        
        Room currentRoom = game.getPlayer().getCurrentRoom();
        Room exitRoom = currentRoom.getExit(command.getSecondWord());
        
        if (exitRoom != null)
            game.getPlayer().setRoom(exitRoom);
        else
            System.out.println("There is no way here !");
    }

    private void takeItem (Command command) 
    {

    }

    private void dropItem (Command command) 
    {

    }

    private void repairItem (Command command) 
    {

    }

    /**
     * Investigate the room.
     * Prints all the items in the room in which the player is currently located 
     */
    private void investigate () 
    {
        ItemRoom currenRoomAsItemRoom = (ItemRoom) game.getPlayer().getCurrentRoom();
        ArrayList<Item>roomInventory = new ArrayList<>();
        if (currenRoomAsItemRoom != null) 
        {
            if (currenRoomAsItemRoom.getItem()!= null)
                roomInventory.add(currenRoomAsItemRoom.getItem());
            if (currenRoomAsItemRoom.getSpecialItem()!= null)
                roomInventory.add(currenRoomAsItemRoom.getSpecialItem());
            
            WorkshopRoom currentRoomAsWorkshopRoom = (WorkshopRoom) game.getPlayer().getCurrentRoom();
            if (currentRoomAsWorkshopRoom != null) 
                roomInventory.addAll(currentRoomAsWorkshopRoom.getItems());
            
            System.out.println("These items are in this room :");
            for (int i = 0; i < roomInventory.size(); i++) 
            {
                System.out.println(i + roomInventory.get(i).getName());
            } 
        }
        else
            System.out.println("There is no item !");
    }

    /**
     * Quits the game if no second word has been entered by the player.
     * @param command The command entered by the player
     * @return Returns if the quit command is valid.
     */
    private boolean quit(Command command) 
    {
        if (command.hasSecondWord())
        {
            System.out.println("What do you want to quit?");
            return false;
        }
        else
            return true;
    }

    /**
     * Prints a short description of the game and then a list of commands.
     */
    private void printHelp()
    {
        System.out.println("YOU BE FUCKED");
        System.out.println();
        System.out.println("Your command words are:");
        game.getParser().showCommands();
    }

}
