package List;
import File.FileJudge;
import File.LoadFile;
import java.util.ArrayList;
public class TreeList implements Observer {
    private String title;
    public TreeList(String title) {
        this.title = title;
    }
    @Override
    public void show(){
        LoadFile loadFile = LoadFile.getInstance();
        int index= -1;
        ArrayList<String> content = loadFile.getContent();
        for(String tmp : content){
            index++;
            String name = FileJudge.getName(tmp);
            if(name.equals(title)){
                break;
            }
        }
        if(index == -1){
            System.out.println("Wrong input!");
            return;
        }
        ArrayList<String> res =new ArrayList<>();
        int parent_level = FileJudge.getLevel(content.get(index));
        int len = content.size();
        int file_level = -1;
        for(int i = index; i < len ;i++){
            boolean isList = false;
            int cur_level = FileJudge.getLevel(content.get(i));
            if(i > index){
                if(cur_level == parent_level){
                    break;
                }
            }
            int next_level;
            if (i + 1 < len) {
                next_level = FileJudge.getLevel(content.get(i + 1));
            } else {
                next_level = -1;
            }
            if(cur_level != 0 && next_level == 0){
                file_level = cur_level + 1;
            }
            if(cur_level == 0){
                isList = true;
                cur_level = file_level;
            }
            String name = FileJudge.getName(content.get(i));
            StringBuilder tmp = new StringBuilder();
            tmp.append("    ".repeat(Math.max(0, cur_level - parent_level)));
            if((isList && next_level == -1) || (!isList && (cur_level < next_level || next_level == 0))){
                 if(isList){
                     tmp.append("└──").append(content.get(i));
                 }
                 else{
                     tmp.append("└──").append(name);
                 }
                 res.add(tmp.toString());
            }
            else if (!isList || next_level == 0){
                if(isList){
                    tmp.append("├──").append(content.get(i));
                }
                else{
                    tmp.append("├──").append(name);
                }
                res.add(tmp.toString());
            }
            if(next_level < cur_level && next_level != -1){
                int loc =(next_level - parent_level) * 3;
                for(int j = 0; j < res.size();j++){
                    String s = res.get(j);
                    if(s.charAt(s.length() - 1) == ' '){
                        res.set(j, s.substring(0, loc - 1) + "│" + s.substring(loc + 1));
                    }
                }
            }
        }
        for(String tmp:res){
            System.out.println(tmp);
        }
    }

}
