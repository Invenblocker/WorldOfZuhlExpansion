package logic.processors;

import logic.Game;
import logic.elements.rooms.Room;
import logic.user_input.Command;
import logic.user_input.CommandWord;

public class GameCommand {
    private Game game;

    public GameCommand(){
        game = Game.getInstance();

    }

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
                    //goRoom(command);
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

    private void goRoom (Command command)
    {
        Room currentRoom = game.getPlayer().getCurrentRoom();
        switch (command.getSecondWord())
        {
            case "up":
                if (currentRoom.getExit("up")!=null) 
                    game.getPlayer().setRoom(currentRoom.getExit("up"));
                else 
                    System.out.println("There is no exit this way ! ");
                break;
            case "down":
                if (currentRoom.getExit("down")!=null) 
                    game.getPlayer().setRoom(currentRoom.getExit("down"));
                else 
                    System.out.println("There is no exit this way ! ");
                break;
            case "left":
                if (currentRoom.getExit("left")!=null) 
                    game.getPlayer().setRoom(currentRoom.getExit("left"));
                else 
                    System.out.println("There is no exit this way ! ");
                break;
            case "right":
                if (currentRoom.getExit("right")!=null) 
                    game.getPlayer().setRoom(currentRoom.getExit("right"));
                else 
                    System.out.println("There is no exit this way ! ");
                break;
            default:
             System.out.println("This way is not found");
                break;
        }
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

    private void investigate () 
    {

    }

    private boolean quit(Command command) 
    {
        return false;
    }

    private void printHelp()
    {
    System.out.println( "YOU BE FUCKED" );
    }

}
