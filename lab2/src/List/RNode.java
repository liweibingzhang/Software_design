package List;

import File.LoadFile;
import File.FileJudge;

import java.util.ArrayList;
public class RNode extends TreeNode
{
    private ArrayList<TreeNode> children;
    private int tail_Index;
    public RNode(ArrayList<String> text)
    {
        super("Root", 0, -1);
        this.children = new ArrayList<>();
        this.tail_Index = -1;
        try
        {
            createTree(text);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * 获取最后一个子节点
     * @return 最后一个子节点，如果没有则返回 null
     */
    public TreeNode getLastChild(){
        if(tail_Index == -1)
            return null;
        else
            return children.get(tail_Index);
    }
    /**
     * 添加子节点
     * @param treeNode 要添加的子节点
     */
    public void addChild(TreeNode treeNode){
        children.add(treeNode);
        tail_Index ++ ;
    }
    public ArrayList<TreeNode> getChildren(){
        return children;
    }
    @Override
    public String print()
    {
        return null;
    }
    /**
     * 递归打印整个树的结构
     */
    public void print_all(){
        if(children == null)
            return;
        for(TreeNode node:children){
            print_given(node);
        }
    }
    /**
     * 递归打印树中特定节点及其子节点
     * @param treeNode 要打印的节点
     */
    public void print_given(TreeNode treeNode){
        if(treeNode == null)
            return;
        System.out.println(treeNode.getName());
        if(treeNode.getChildren() == null)
            return;
        for(TreeNode node:treeNode.getChildren()){
            print_given(node);
        }
    }
    /**
     * 获取下一级别的等级
     * @param text 包含文本信息的 ArrayList
     * @param index 当前索引
     * @return 下一级别的等级，如果是最后一行则返回 -1
     */
    private int getNextLevel(ArrayList<String> text,int index){
        if(index == text.size()-1)
            return -1;
        else return FileJudge.getLevel(text.get(index+1));
    }
    private void insert(TreeNode treeNode){
        insertTree(this,treeNode);
    }
    /**
     * 递归插入节点到树中
     * @param treeNode 当前节点
     * @param Node 要插入的节点
     */
    private void insertTree(TreeNode treeNode,TreeNode Node){
        if(treeNode.getLevel() == Node.getLevel() - 1){
            if(treeNode.getLastChild() != null){
                TreeNode tmp = treeNode.getLastChild();
                Node.setNeighborLine(tmp.getLineNum());
            }
            Node.setParent(treeNode);
            treeNode.addChild(Node);
        }else{
            insertTree(treeNode.getLastChild(),Node);
        }
    }
    /**
     * 根据文本构建树
     * @param content 包含文本信息的 ArrayList
     * @throws Exception 如果文本格式错误
     */
    private void createTree(ArrayList<String> content) throws Exception{
        int list_level = -1;
        for(int i = 0; i < content.size();i++){
            String line = content.get(i);
            int cur_level = FileJudge.getLevel(line);
            int next_level = getNextLevel(content,i);
            String name = FileJudge.getName(line);
            if(cur_level > 0 && next_level == 0){
                list_level = cur_level + 1;
            }
            if(cur_level == 0){
                cur_level = list_level;
            }

            TreeNode node;
            if(line.contains("#")){
                node = new TNode(name,cur_level,i);
            }else if(line.contains("*")){
                node = new FNode(name,cur_level,i);
            }else if(line.contains(".")){
                node = new CNode(name,cur_level,i,FileJudge.deal_line(line));
            }else{
                throw new RuntimeException("Wrong format");
            }
            this.insert(node);

        }
    }

}
