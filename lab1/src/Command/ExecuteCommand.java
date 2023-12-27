package Command;
import java.util.ArrayList;
public class ExecuteCommand {
    private static ExecuteCommand instance;
    private ArrayList<Command> commands;
    private int commands_index;
    private Boolean canredo;

    private ExecuteCommand(){
        commands = new ArrayList<>();
        commands_index = -1;
    }
    public static ExecuteCommand getInstance(){
        if (instance == null){
            instance = new ExecuteCommand();
        }
        return instance;
    }
    public void executeCommand(Command command){
        try{
            command.execute();
            commands_index++;
            commands.add(command);
            canredo = false;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void redoCommand() {
        if (commands_index == commands.size())
            canredo = false;
        if (!canredo) return;
        CanDo redoExecuted = commands.get(commands_index + 1).redo();
        if (redoExecuted.equals(CanDo.Do)) {
            commands_index++;
        } else if (redoExecuted.equals(CanDo.Neglect)) {
            commands_index++;
            redoCommand();
        } else if (redoExecuted.equals(CanDo.Stop)) {
            System.out.println("You cannot redo this command!");
        }
    }
    public void undoCommand() {
        if (commands_index < 0) return;
        CanDo undoExecuted = commands.get(commands_index).undo();
        if (undoExecuted.equals(CanDo.Do)) {
            commands_index--;
            canredo = true;
        }
        else if (undoExecuted.equals(CanDo.Neglect)) {
            commands_index--;
            undoCommand();
        }
        else if (undoExecuted.equals(CanDo.Stop)) {
            return;
        }
        else {
            System.out.println("Invalid Command cannot be undo!");
        }
    }
}
