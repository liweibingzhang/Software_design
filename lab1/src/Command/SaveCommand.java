package Command;
import File.LoadFile;

public class SaveCommand implements Command {
    @Override
    public void execute() {
        LoadFile loadFile = LoadFile.getInstance();
        loadFile.saveFile();
    }

    @Override
    public CanDo undo(){
        return CanDo.Stop;
    }
    @Override
    public CanDo redo(){
        return CanDo.Stop;
    }
}
