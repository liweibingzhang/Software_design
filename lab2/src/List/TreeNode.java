package List;

import java.util.ArrayList;

public abstract class TreeNode
{
    private int near_line;
    private TreeNode parent;
    private String name;
    private int level;
    private int line_num;

    public TreeNode(String name,int level,int lineNum)
    {
        this.name = name;
        this.level = level;
        this.line_num = lineNum;
        this.near_line = -1;
        parent = null;
    }
    public void setParent(TreeNode parent_node){
        this.parent = parent_node;
    }
    public TreeNode getParent(){
        return this.parent;
    }
    public String getName()
    {
        return name;
    }
    public int getLineNum()
    {
        return line_num;
    }
    public int getLevel(){
        return this.level;
    }
    public void setNeighborLine(int neighborLine){
        this.near_line = neighborLine;
    }
    public int getNeighborLine(){
        return this.near_line;
    }
    public void addChild(TreeNode treeNode){}
    public TreeNode getLastChild(){
        return null;
    }
    public abstract String print();
    public ArrayList<TreeNode> getChildren(){
        return new ArrayList<>();
    }
}
