package Command;

import Command.Command;
import File.LoadFile;

public class LoadCommand implements Command {
    String filePath;

    public LoadCommand(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public void execute() {
        LoadFile loadFile = LoadFile.getInstance();
        loadFile.loadFile(filePath);
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
