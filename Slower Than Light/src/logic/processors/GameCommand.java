package logic.processors;

import java.util.ArrayList;
import logic.Game;
import logic.elements.characters.Item;
import logic.elements.characters.Tool;
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

        if (null != commandWord) switch (commandWord)
        {
            case GO:
                goRoom(command);
                game.getGUI().updateRoom(game.getPlayer().getCurrentRoom());
                printInventory();
                break;
            case TAKE:
                takeItem(command);
                break;
            case DROP:
                dropItem(command);
                break;
            case REPAIR:
                repairRoom(command);
                break;
            case INVESTIGATE:
                investigate();
                break;
            case INVENTORY:
                printInventory();
                break;
            case HELP:
                game.getGUI().printHelp();
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            default:
                System.out.println("I don't know what you mean...\nType \"help\" for a list of commands.");
                return false;
        }
        else
            System.out.println("Please write in a command before hitting enter.");
        
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
        {
            Room pastRoom = game.getPlayer().setRoom(exitRoom);
            
            if (game.getSaboteur().getCurrentRoom() == exitRoom)
            {
                game.getGameInfo().setGameFinished(true);
                System.out.println("You went into the same room as the saboteur. ");
                System.out.println("Game over !! ");
            }
            
            int saboteurCountdown = game.getSaboteur().chasePlayer(pastRoom);
            if (saboteurCountdown != -1)
                game.getTimeHolder().setSaboteurCountdown(saboteurCountdown);   
        }
        else
            System.out.println("There is no way here !");
        
    }
    
    /**
     * Checks if the player types a second word and if it's valid. It checks if the 
     * player have space in his inventory and if that's the case
     * it colleges the item that the user picked. 
     * @param command 
     */
    private void takeItem (Command command)
    {
        if (!command.hasSecondWord())
        {
            System.out.println("Take what ? ");
            return;
        }
        
        Item[] inventory = game.getPlayer().getInventory();
        int itemCount = game.getPlayer().getItemCount();
        
        if (itemCount == inventory.length)
        {
            System.out.println("Your inventory is full ! ");
            System.out.println("You need to drop a item to be able to pike up a new item ");
            return;
        }
        
        ArrayList<Item> roomInventory = roomItemList();
        try {
            int itemReference = Integer.parseInt(command.getSecondWord());
            Item itemTaken = roomInventory.get(itemReference);
            game.getPlayer().addItem(itemTaken); 
        } 
        catch (Exception e) 
        {
            System.out.println("This is not a valid item ! ");
        }
    }
    
    private ArrayList<Item> roomItemList ()
    {
        ArrayList<Item>roomInventory = new ArrayList<>();
        Room currenRoom = game.getPlayer().getCurrentRoom();
        
        if (currenRoom instanceof ItemRoom)
        {
            ItemRoom currenRoomAsItemRoom =(ItemRoom) currenRoom ;
            if (currenRoomAsItemRoom.getItem()!= null)
                roomInventory.add(currenRoomAsItemRoom.getItem());
            if (currenRoomAsItemRoom.getSpecialItem()!= null)
                roomInventory.add(currenRoomAsItemRoom.getSpecialItem());
            
            return roomInventory;
        }
        else if (currenRoom instanceof WorkshopRoom) 
        {
            WorkshopRoom currentRoomAsWorkshopRoom  = (WorkshopRoom) currenRoom;
                roomInventory.addAll(currentRoomAsWorkshopRoom.getItems());
            return roomInventory;
        }
        
        return null;
    }
     /**
      * Gets the players inventory. After that it make you choose a item that you want to drop.
      * It will take the item that you choose and drop it into the room. 
      * It checks what kind of room you are in and what to do with the item.
      * If the room isen't a workshop room the item that was dropped will be set to default.
      * @param command 
      */
    private void dropItem (Command command) 
    {
        if (!command.hasSecondWord()) 
        {
            System.out.println("What item did you mean ? ");
            return;
        }
        Item[] inventory = game.getPlayer().getInventory();
        try 
        {
           int itemIndex = Integer.parseInt(command.getSecondWord());
           Item itemDropped;
           itemDropped = inventory[itemIndex];
           if(game.getPlayer().removeItem(itemDropped)) 
           {
              Room currentRoom = game.getPlayer().getCurrentRoom();

               if (currentRoom instanceof WorkshopRoom) 
               {
                    WorkshopRoom currentRoomAsWorkshopRoom = (WorkshopRoom) currentRoom;
                    currentRoomAsWorkshopRoom.addItem(itemDropped);
               }
               else
                   setItemToDefault(itemDropped);
           }

       } 
       catch (Exception e) 
       {
           System.out.println("This is not a valid item ! ");
       }
    }
    
    /**
     * This class starts by checking if the room is broken. If the room is broken 
     * it will compare the repair tool to the player's inventory. If the player have the
     * item needed to repair the room it will do so, and remove the item fra player's inventory
     * and set it to default. Else it says what you need to fix the room.
     * @param command 
     */
    private void repairRoom (Command command) 
    {
        ArrayList<Tool> roomRepairTool = game.getPlayer().getCurrentRoom().getRepairTools();
        Item[] inventory = game.getPlayer().getInventory();
        Room roomCheck = game.getPlayer().getCurrentRoom();
        
        if (!roomCheck.isOperating())
        {
            for (Tool tool : roomRepairTool)
            {
                for (Item item : inventory)
                {
                    if (tool == item)
                    {
                        System.out.println("You had the necessary tool to repair the room. " + roomRepairTool + " was used and removed from the inventory");  
                        game.getPlayer().getCurrentRoom().setOperating(true);
                        game.getPlayer().removeItem(item);
                        setItemToDefault(item);
                        game.getGameInfo().updateRoomsDestroyed();
                    }
                    else
                        System.out.println("You didn't have the " + roomRepairTool + ". You can't repair this room!");
                }
            }
        }
    }
    
    /**
     * Investigate the room.
     * Prints all the items in the room in which the player is currently located 
     */
    private void investigate ()
    {
        Room roomCheck  = game.getPlayer().getCurrentRoom();

        if (!roomCheck.isOperating())
        {
            System.out.println("This room is broken, if you want to repair this room, you'll need a " + roomCheck.getRepairTools().get(0) + ".");
            return;
        }

        ArrayList<Item> roomInventory = roomItemList();
        if (roomInventory != null && !roomInventory.isEmpty())
        {
            System.out.println("These items are in this room: ");
            
            for (int i = 0; i < roomInventory.size(); i++)
                System.out.println("[" +i + "] "+ roomInventory.get(i).getName());
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
     * Finds the item's default room and sets the item to that room. 
     * @param item 
     */
    private void setItemToDefault(Item item)
    {
        Room defaultRoom = item.getDefaultRoom();
        
        if (defaultRoom != null && defaultRoom instanceof ItemRoom) 
        {
            ItemRoom defaultRoomAsItemRoom = (ItemRoom)item.getDefaultRoom();
            defaultRoomAsItemRoom.setItem(item);
        }
    }
    
    /**
     * Prints out players inventory.
     */
    private void printInventory()
    {
        Item[] playerInventory = game.getPlayer().getInventory();
        
        for (int i = 0; i < playerInventory.length; i++)
            if (playerInventory[i] != null) 
                System.out.println("[" + i + "] "+ playerInventory[i].getName());
    }
}
