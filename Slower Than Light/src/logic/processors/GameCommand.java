package logic.processors;

import acq.IWriter;
import database.txtWriter;
import java.util.ArrayList;
import java.util.List;
import logic.Game;
import logic.LogFacade;
import logic.elements.characters.Helper;
import logic.elements.characters.HelperTask;
import logic.elements.characters.Item;
import logic.elements.characters.Tool;
import logic.elements.rooms.ItemRoom;
import logic.elements.rooms.Room;
import logic.elements.rooms.WorkshopRoom;
import logic.user_input.Command;
import logic.user_input.CommandWord;
import logic.SystemLog;

public class GameCommand {
    private Game game;

    public GameCommand()
    {
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
            case SAVE:
                saveGame();
                break;
            case HELP:
                game.getGUI().printHelp();
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            case PRINT:
                SystemLog.printGlobalLog();
                break;
            default:
                System.out.println("I don't know what you mean...\nType \"help\" for a list of commands.");
                return false;
        }
        else
            System.out.println("Please write in a command before hitting enter.");
        
        return wantToQuit;
    }
    
    public Item[] getItemsInCurrentRoomItems ()
    {
        List<Item> itemList = roomItemList();
        Item[] itemArray = new Item[itemList.size()];
        itemArray = roomItemList().toArray(itemArray);
        
        return itemArray;
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
                if(game.getGameInfo().getHelper().getHelperTask() == HelperTask.BODYGUARD)
                {
                   game.getGameInfo().killHepler();
                   game.getSaboteur().setStunCountdown(15);
                }
                else
                {
                    game.getGameInfo().setGameFinished(true);
                    System.out.println("You went into the same room as the saboteur. ");
                    System.out.println("Game over !! ");
                    return;
                }
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
        catch (NumberFormatException | IndexOutOfBoundsException e) 
        {
            System.out.println("This is not a valid item ! ");
        }
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
       catch (NumberFormatException | IndexOutOfBoundsException e) 
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
     * This method uses a switch-statement, to determine what action the object of class Helper, should perform.
     * The cases are enums, obtained from the class HelperTask.
     */
    private void helperAction(Command command) 
    {
       
       Room currentRoom = game.getPlayer().getCurrentRoom();
       Room HelperCurrentRoom = game.getGameInfo().getHelper().getCurrentRoom();
       HelperTask helperTask = game.getGameInfo().getHelper().getHelperTask();
       Helper performTask = game.getGameInfo().getHelper();
       String helperName = game.getGameInfo().getHelper().getName();
       if (currentRoom == HelperCurrentRoom && currentRoom.isControlRoom())
       {
       if (!command.hasSecondWord()) switch (helperTask)   
           {
                case BODYGUARD:
                   performTask.setTask(helperTask);
                   break;
                case SEARCH:
                    performTask.setTask(helperTask);
                    break;
                case RETURN_TO_DEFAULT:
                    performTask.setTask(helperTask);
                    break;
                default:
                    System.out.println("What task did you mean ? ");
           }
        else
            System.out.println("Please type in a task for helper before hitting enter ! ");
       }
       System.out.println("You need to be in the control room to give" + helperName + "a control ! ");
       return;
    }
    
    /**
     * This method saves the game, including the game's current status,
     * for example the operating status of the rooms and the time and oxygen left.
     */
    private void saveGame() 
    {
        // convert elements
        GameElementsConverter gec = new GameElementsConverter();
        gec.convertRoomPositions(game.getRoomPositions());
        gec.convertRooms(game.getRooms());
        gec.convertItems(game.getItems());
        gec.convertSpecialItems(game.getSpecialItems());
        gec.convertPlayer(game.getPlayer());
        gec.convertSaboteur(game.getSaboteur());
        gec.convertHelper(game.getHelper());
        gec.convertTimeHolder(game.getTimeHolder());
        
        // save game to file
        IWriter writer = LogFacade.getInstance().getDataFacade().getWriter();
        writer.saveGame(gec.getRoomsInfo(), gec.getItemsInfo(), gec.getSpecialItemsInfo(),
                gec.getExitInfo(), gec.getPlayerInfo(), gec.getSaboteurInfo(), gec.getHelperInfo(),
                gec.getTimeHolderInfo(), game.getGameInfo().getRoomsRepaired(), gec.getRoomPositions());
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
    
    /**
     * ArrayList, which holds information about the items in the player's current room.
     * Checks are made, to see if the currentRoom is an instance of the class ItemRoom or a WorkShopRoom.
     * In both cases the currentRoom's inventory is returned.
     * However, if the currentRoom is not an instance of either of these classes, it returns null.
     */
    private ArrayList<Item> roomItemList ()
    {
        ArrayList<Item>roomInventory = new ArrayList<>();
        Room currentRoom = game.getPlayer().getCurrentRoom();

        if (currentRoom instanceof ItemRoom)
        {
            ItemRoom currentRoomAsItemRoom =(ItemRoom) currentRoom ;
            if (currentRoomAsItemRoom.getItem()!= null)
                roomInventory.add(currentRoomAsItemRoom.getItem());
            if (currentRoomAsItemRoom.getSpecialItem()!= null)
                roomInventory.add(currentRoomAsItemRoom.getSpecialItem());

            return roomInventory;
        }
        else if (currentRoom instanceof WorkshopRoom) 
        {
            WorkshopRoom currentRoomAsWorkshopRoom  = (WorkshopRoom) currentRoom;
            roomInventory.addAll(currentRoomAsWorkshopRoom.getItems());

            return roomInventory;
        }

        return null;
    }
}
