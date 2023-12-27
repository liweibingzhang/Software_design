package Command;

import List.ShowTreeDir;
import List.ShowTreeTotal;

public class listdirCommand extends Command{
     private String title;

    public listdirCommand(String line, String title)
    {
        super(line);
        this.title = title;
    }

    @Override
    public CanDo execute()
    {
        if (title == null)
        {
            ShowTreeTotal showTreeTotal = new ShowTreeTotal();
            showTreeTotal.show();
        } else
        {
            ShowTreeDir showTreeDir = new ShowTreeDir(this.title);
            showTreeDir.show();
        }
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
