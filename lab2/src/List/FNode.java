package List;

public class FNode extends TreeNode
{
    public FNode(String name, int level, int lineNum)
    {
        super(name, level, lineNum);
    }
    public String print(){
        return "·";
    }
}
