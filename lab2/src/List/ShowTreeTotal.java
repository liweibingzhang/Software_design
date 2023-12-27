package List;

import File.FileJudge;
import File.LoadFile;

import java.util.ArrayList;

public class ShowTreeTotal implements Observer {
    ShowTreeDir showTreeDir;

    @Override
    public void show()
    {
        LoadFile loadedFile = LoadFile.getInstance();
        ArrayList<String> content = loadedFile.getContent();
        RNode rNode = new RNode(content);
        ArrayList<TreeNode> nodes = rNode.getChildren();
        for (TreeNode node : nodes)
        {
            ArrayList<String> result = PrintTree.Print(node);
            for (String line : result)
            {
                System.out.println(line);
            }
        }
    }
}
