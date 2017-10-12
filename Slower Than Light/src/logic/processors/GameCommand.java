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

    /**
     * Prints a short description of the game and then a list of commands.
     */
    private void printHelp()
    {
        System.out.println( "YOU BE FUCKED" );
        System.out.println("You have finally gotten a job as a spaceship pilot.");
        System.out.println("Your first assignment is to fly a cargo ship filled");
        System.out.println("with important supplies to Earth's base on the moon.");
        System.out.println("However a saboteur has infiltrated the spaceship, and");
        System.out.println("does not plan to let you reach the moon.");
        System.out.println("You have to move around the spaceship to fix his havoc");
        System.out.println("But do not let him run into you, or he will kill you.");
        System.out.println();
        System.out.println("Your command words are:");
        game.getParser().showCommands();
    }

}
