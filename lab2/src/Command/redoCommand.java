package Command;

public class redoCommand extends Command
{
    public redoCommand(String line)
    {
        super(line);
    }

    @Override
    public CanDo execute() throws Exception
    {
        ExecuteCommand editor =ExecuteCommand.getInstance();
        editor.redoCommand();
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
