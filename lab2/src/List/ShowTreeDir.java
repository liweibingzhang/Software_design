package List;

import File.LoadFile;
import File.FileJudge;

import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShowTreeDir implements Observer {
    String title;
    public ShowTreeDir(String title)
    {
        this.title = title;
    }
    private ArrayList<TreeNode> findName(RNode rNode){
        ArrayList<TreeNode> res = new ArrayList<>();
        findNameinTree(res,rNode);
        return res;
    }
    /**
     * 递归地在树结构中查找具有特定名称的 TNode 节点，并将符合条件的节点添加到结果列表中。
     *
     * @param res 用于存储找到的符合条件的节点的 ArrayList
     * @param treeNode 当前正在检查的树节点
     */
    private void findNameinTree(ArrayList<TreeNode> res,TreeNode treeNode){
        if(treeNode == null) return;
        if(treeNode.getName().equals(title) && treeNode instanceof TNode){
            res.add(treeNode);
        }
        for(TreeNode node:treeNode.getChildren()){
            findNameinTree(res,node);
        }
    }
    @Override
    public void show()
    {
        LoadFile loadFile = LoadFile.getInstance();
        ArrayList<String> content = loadFile.getContent();
        RNode rNode = new RNode(content);
        ArrayList<TreeNode> nodes = findName(rNode);
        if (nodes.isEmpty())
        {
            System.out.println("No title!");
            return;
        }
        for(TreeNode node:nodes){
            ArrayList<String> result = PrintTree.Print(node);
            for(String line:result){
                System.out.println(line);
            }
        }

    }

}