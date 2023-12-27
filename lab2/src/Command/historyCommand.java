package Command;
import Command.logCommand;
public class historyCommand extends Command
{
    int cnt;
    public historyCommand(String line, int count)
    {
        super(line);
        this.cnt = count;
    }

    @Override
    public CanDo execute() throws Exception
    {
        logCommand.Showhistory(cnt);
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
