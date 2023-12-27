package Command;
import File.LoadFile;

public class SaveCommand extends Command{
    public SaveCommand(String line) {
        super(line);
    }
    @Override
    public CanDo execute() throws Exception {
        LoadFile loadFile = LoadFile.getInstance();
        loadFile.saveFile();
        return CanDo.Do;
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
