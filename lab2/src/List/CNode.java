package List;

public class CNode extends TreeNode
{
    private String column_num;
    public CNode(String name, int level, int lineNum,String columnNum)
    {
        super(name, level, lineNum);
        this.column_num = columnNum;
    }
    public String print(){
        return column_num + ". ";
    }
}
