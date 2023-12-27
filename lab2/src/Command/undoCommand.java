package Command;

public class undoCommand extends Command
{
    public undoCommand(String line)
    {
        super(line);
    }

    @Override
    public CanDo execute() throws Exception
    {
        ExecuteCommand editor =ExecuteCommand.getInstance();
        editor.undoCommand();
        return CanDo.Neglect;
    }

    @Override
    public CanDo undo()
    {
        return CanDo.Neglect;
    }

    @Override
    public CanDo redo()
    {
        return CanDo.Neglect;
    }
}
