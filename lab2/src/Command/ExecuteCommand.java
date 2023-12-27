package Command;
import java.util.ArrayList;
public class ExecuteCommand {
    private static ExecuteCommand instance;
    private ArrayList<Command> commands;
    private int undocommands_index;
    private int maxcommands_index;
    private ArrayList<CommandOB> commandObList;
    private Boolean canredo;

    private ExecuteCommand(){
        commands = new ArrayList<>();
        commandObList = new ArrayList<>();
        undocommands_index = -1;
        maxcommands_index = -1;
    }
    public static ExecuteCommand getInstance(){
        if (instance == null){
            instance = new ExecuteCommand();
        }
        return instance;
    }
    public void add(CommandOB commandOb)
    {
        commandObList.add(commandOb);
    }
    public void Reload(Command command)
    {
        for (CommandOB commandOb : commandObList)
        {
            try
            {
                commandOb.load(command);
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    public void executeCommand(Command command){
        try{
            CanDo type = command.execute();
            if (type.equals(CanDo.Do))
            {
                undocommands_index++;
                maxcommands_index = undocommands_index;
                if (commands.size() == maxcommands_index)
                    commands.add(command);
                else
                    commands.set(undocommands_index, command);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void redoCommand() {
        if (undocommands_index == maxcommands_index)
            return;
        CanDo redoExecuted = commands.get(undocommands_index + 1).redo();
        if (redoExecuted.equals(CanDo.Do)) {
            undocommands_index++;
        } else if (redoExecuted.equals(CanDo.Neglect)) {
            undocommands_index++;
            redoCommand();
        } else if (redoExecuted.equals(CanDo.Stop)) {
            System.out.println("You cannot redo this command!");
        }
    }
    public void undoCommand() {
        if (undocommands_index < 0) return;
        CanDo undoExecuted = commands.get(undocommands_index).undo();
        if (undoExecuted.equals(CanDo.Do)) {
            undocommands_index--;
            canredo = true;
        }
        else if (undoExecuted.equals(CanDo.Neglect)) {
            undocommands_index--;
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
