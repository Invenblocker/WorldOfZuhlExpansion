/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.processors;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import logic.elements.characters.Helper;
import logic.elements.characters.Item;
import logic.elements.characters.Player;
import logic.elements.characters.Saboteur;
import logic.elements.rooms.ControlRoom;
import logic.elements.rooms.Exit;
import logic.elements.rooms.ItemRoom;
import logic.elements.rooms.Room;
import logic.elements.rooms.WorkshopRoom;

/**
 *
 * @author Erik
 */
public class GameElementsConverter
{
    private String roomsInfo;
    private String itemsInfo;
    private String specialItemsInfo;
    private List<String> exitInfo;
    private String playerInfo;
    private String saboteurInfo;
    private String helperInfo;
    private String timeHolderInfo;
    private String roomPositions;
    
    public GameElementsConverter ()
    {
        exitInfo = new ArrayList<>();
    }
    
    public void convertRooms (Map<String, Room> rooms)
    {
        roomsInfo = "Room: ";
        for (String roomName : rooms.keySet())
        {
            roomsInfo += roomName + " ";
            
            Room room = rooms.get(roomName);
            if (room instanceof ItemRoom)
                roomsInfo += "ItemRoom ";
            else if (room instanceof ControlRoom)
                roomsInfo += "ControlRoom ";
            else if (room instanceof WorkshopRoom)
                roomsInfo += "WorkshopRoom ";
            
            roomsInfo += room.isOperating() + " ";
        }
        
        // convert exits
        for(String key : rooms.keySet())
            convertExit(rooms, rooms.get(key));
        
    }
    
    public void convertRoomPositions (Map<String, Point> roomPosition, Player player, Saboteur saboteur){
        this.roomPositions = "RoomPos: ";
        
        this.roomPositions += player.getCurrentRoom().getName() + " " + saboteur.getCurrentRoom().getName() + " ";
        for (String key : roomPosition.keySet()) {
            this.roomPositions += key + " " + roomPosition.get(key).x + " " + roomPosition.get(key).y + " ";
        }
        
    }
    
    private void convertExit (Map<String, Room> rooms, Room room)
    {
        String roomName = room.getName();           // Rummet der bliver arbejdet på's navn.
        String exitString = roomName + " ";
        Room roomUp = rooms.get(roomName).getExit("up");
        Room roomDown = rooms.get(roomName).getExit("down");
        Room roomLeft = rooms.get(roomName).getExit("left");
        Room roomRight = rooms.get(roomName).getExit("right");
        
        if (roomUp != null)
        {
            exitString += "up ";                        // Hvis direction er up, bliver det skrevet ind i .txtfilen.
            
            Room exitRoom = rooms.get(roomName);        // får referencen af rummet vi arbejder på.
     
            if(roomName.equals("RightThruster")){
                System.out.println(rooms.get(roomName).getCollectionOfExits());
                Exit exit = exitRoom.getExit(roomUp);
                System.out.println("Exis =" + exit);
            }
            
            Exit exit = exitRoom.getExit(roomUp);    // Får fat i det rigtige exit.
            
            exitString += exit.isOperating() + " ";     // prints the boolean value to the txt file
            exitString += roomUp.getName() + " ";       // prints the room on the opposing side of the exit to the txt file.
             System.out.println(exitString);
            
        }
        if (roomDown != null)
        {
            exitString += "down ";                      // Hvis direction er down, bliver det skrevet ind i .txtfilen.
            
            Room exitRoom = rooms.get(roomName);        // får referencen af rummet vi arbejder på.
            
            Exit exit = exitRoom.getExit(roomDown);    // Får fat i 
            
            System.out.println("Exit operating? " + exit.isOperating());
            exitString += exit.isOperating() + " ";     // prints the boolean value to the txt file
            exitString += roomDown.getName() + " ";     // prints the room on the opposing side of the exit to the txt file.
             System.out.println(exitString);
        }
        if (roomLeft != null)
        {
            exitString += "left ";                      // Hvis direction er down, bliver det skrevet ind i .txtfilen.
            
            Room exitRoom = rooms.get(roomName);        // får referencen af rummet vi arbejder på.
            Exit exit = exitRoom.getExit(roomLeft);    // Får fat i 
         
            System.out.println("Exit operating? " + exit.isOperating());
            exitString += exit.isOperating() + " ";     // prints the boolean value to the txt file
            exitString += roomLeft.getName() + " ";     // prints the room on the opposing side of the exit to the txt file.
             System.out.println(exitString);
        }
        if (roomRight != null)
        {
            exitString += "right ";                      // Hvis direction er down, bliver det skrevet ind i .txtfilen.
            
            Room exitRoom = rooms.get(roomName);        // får referencen af rummet vi arbejder på.
            Exit exit = exitRoom.getExit(roomRight);    // Får fat i 

            System.out.println("Exit operating? " + exit.isOperating());
            
            exitString += exit.isOperating() + " ";     // prints the boolean value to the txt file
           
            exitString += roomRight.getName() + " ";     // prints the room on the opposing side of the exit to the txt file.
             System.out.println(exitString);
            
        }
        
       // System.out.println(rooms.get("controlRoom").getExit(rooms.get("EntryRamp")).equals(rooms.get("EntryRamp").getExit(rooms.get("controlRoom"))));
        
        exitInfo.add(exitString);
    }
    
    public void convertItems (Map<String, Item> items)
    {
        itemsInfo = "Item: ";
        for (String itemKey : items.keySet())
        {
            itemsInfo += "Tool ";
            itemsInfo += itemKey + " ";
            itemsInfo += items.get(itemKey).getDefaultRoom().getName() + " ";
        }
    }
    
    public void convertSpecialItems (Map<String, Item> specialItems)
    {
        specialItemsInfo = "SpecialItem: ";
        for (String itemKey : specialItems.keySet())
        {
            specialItemsInfo += "Tool ";
            specialItemsInfo += itemKey + " ";
        }
    }
    
    public void convertPlayer (Player player)
    {
        playerInfo = "Player: ";
        playerInfo += player.getCurrentRoom().getName() + " "; //players current room name
        playerInfo += 2 + " ";                                 //Inventory size
        Item[] playerInventory = player.getInventory();          //Item array that holds players current inventory
        int itemCount = player.getItemCount();                   //itemcount of how many items player current holds
        for (int i = 0; i < itemCount; i++)
        {
            playerInfo += playerInventory[i].getName() + " ";  //writes the current items into the txtfile
        }
    }
    
    public void convertSaboteur (Saboteur saboteur)
    {
        saboteurInfo = "Saboteur: ";
        saboteurInfo += saboteur.getCurrentRoom().getName() + " ";
        saboteurInfo += saboteur.DEFAULT_CHANCE_OF_SABOTAGE + " ";
        saboteurInfo += saboteur.CHANCE_OF_SABOTAGE_GROWTH + " ";
        saboteurInfo += saboteur.DEFAULT_CHANCE_OF_SABOTAGE + " ";
        saboteurInfo += saboteur.getChanceOfSabotage() + " ";
        saboteurInfo += saboteur.getStunCountdown() + " ";
    }
    
    public void convertHelper (Helper helper)
    {
        helperInfo = "Helper: ";
        helperInfo += helper.getCurrentRoom().getName() + " ";
        helperInfo += helper.getName() + " ";
        helperInfo += helper.getHelperTask() + " ";
        helperInfo += helper.CHANCE_OF_DISCOVERY_GROWTH + " ";
        helperInfo += helper.DEFAULT_CHANCE_OF_DISCOVERY + " ";
        helperInfo += helper.getChanceOfDiscovery() + " ";
    }
    
    public void convertTimeHolder (TimeHolder timeHolder)
    {
        timeHolderInfo = "TimeHolder: ";
        timeHolderInfo += timeHolder.getTimeLeft() + " ";
        timeHolderInfo += timeHolder.getOxygenLeft() + " ";
        timeHolderInfo += timeHolder.getHelperCountdown() + " ";
        timeHolderInfo += timeHolder.getSaboteurCountdown() + " ";
    }

    public String getRoomsInfo() {return roomsInfo;}

    public String getItemsInfo() {return itemsInfo;}

    public String getSpecialItemsInfo() {return specialItemsInfo;}

    public List<String> getExitInfo() {return exitInfo;}

    public String getPlayerInfo() {return playerInfo;}

    public String getSaboteurInfo() {return saboteurInfo;}

    public String getHelperInfo() {return helperInfo;}

    public String getTimeHolderInfo() {return timeHolderInfo;}
    
    public String getRoomPositions() {return roomPositions;}
    
}
