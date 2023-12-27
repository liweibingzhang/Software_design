package Command;
import List.TreeList;
import File.LoadFile;
import File.FileJudge;
public class listTreeCommand implements Command
{
    @Override
    public void execute()
    {
        LoadFile loadFile = LoadFile.getInstance();
        String root = FileJudge.getName(loadFile.getRoot());
        TreeList treelist = new TreeList(root);
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
