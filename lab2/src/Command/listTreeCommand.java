package Command;
import List.ShowTreeDir;
import List.ShowTreeTotal;
import File.LoadFile;
import File.FileJudge;
public class listTreeCommand extends Command {
    public listTreeCommand(String line)
    {
        super(line);
    }

    @Override
    public CanDo execute()
    {
        ShowTreeTotal showTreeTotal = new ShowTreeTotal();
        showTreeTotal.show();
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
