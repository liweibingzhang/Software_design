package List;

import java.util.ArrayList;

public class TNode extends TreeNode
{
    private ArrayList<TreeNode> children;
    private int tail_Index;
    public TNode(String name, int level, int lineNum)
    {
        super(name, level, lineNum);
        children = new ArrayList<>();
        tail_Index = -1;
    }
    public TreeNode getLastChild(){
        if(tail_Index == -1)
            return null;
        else
            return children.get(tail_Index);
    }
    public void addChild(TreeNode treeNode){
        children.add(treeNode);
        tail_Index ++ ;
    }
    public String print(){
        return "";
    }
    public ArrayList<TreeNode> getChildren(){
        return children;
    }
}
