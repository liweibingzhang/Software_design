package Command;
import File.LoadFile;
public class AppendHeadCommand implements Command {
    private String text;

    public AppendHeadCommand(String text) {
        this.text = text;
    }

    @Override
    public void execute() {
        LoadFile loadFile = LoadFile.getInstance();
        try {
            loadFile.insert(0, text);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public CanDo undo() {
        LoadFile loadFile = LoadFile.getInstance();
        try {
            loadFile.deleteIndex(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return CanDo.Do;
    }
    @Override
    public CanDo redo(){
        LoadFile loadFile = LoadFile.getInstance();
        try {
            loadFile.insert(0, text);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return CanDo.Do;
    }

}

