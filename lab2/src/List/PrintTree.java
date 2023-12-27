package List;

import java.util.ArrayList;

public class PrintTree
{
    public static ArrayList<String> Print(TreeNode root){
        ArrayList<String> res = new ArrayList<>();
        PrintNode(res,"",root);
        return res;
    }
    /**
     * 递归地深度打印树结构，生成每个节点的可视化表示，并将结果存储在 ArrayList 中。
     *
     * @param res 用于存储生成的可视化表示的字符串列表
     * @param treeNode 当前正在打印的树节点
     * @param pre 当前节点在树中的缩进前缀，用于维护树结构的可视化表示
     */
    private static void PrintNode(ArrayList<String> res, String pre, TreeNode treeNode){
        if(treeNode == null)
            return;

        // 构建表示当前节点的字符串
        String line = "";
        if(!treeNode.getChildren().isEmpty()){
            line = pre + "└── " + treeNode.print()+treeNode.getName();
        }else{
            if(treeNode.getParent().getLastChild() != treeNode){
                line = pre + "├── " + treeNode.print() + treeNode.getName();
            }else{
                line = pre + "└── " + treeNode.print() + treeNode.getName();
            }
        }

        // 处理同一层级的节点之间的竖线（|）替换
        if(treeNode.getNeighborLine() != -1 && treeNode.getLineNum() > treeNode.getNeighborLine()+1 && !pre.isEmpty()){
            for(int changeIdx=treeNode.getNeighborLine();changeIdx< treeNode.getLineNum();changeIdx++){
                int pos = pre.length();
                String changeStr = res.get(changeIdx);
                char replaceChar = changeStr.charAt(pos) == '└' ? '├' : '│';
                res.set(changeIdx, changeStr.substring(0, pos) + replaceChar + changeStr.substring(pos + 1));
            }
        }
        res.add(line);
        for(TreeNode node:treeNode.getChildren()){
            PrintNode(res,pre+"    ", node);
        }
    }
}