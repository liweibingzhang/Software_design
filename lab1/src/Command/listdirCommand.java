package Command;

import List.TreeList;

public class listdirCommand implements Command{
     private String title;

    public listdirCommand(String title)
    {
        this.title = title;
    }

    @Override
    public void execute()
    {
        TreeList treelist = new TreeList(this.title);
        treelist.show();
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
