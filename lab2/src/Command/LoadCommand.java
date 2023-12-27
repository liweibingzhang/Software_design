package Command;

import File.LoadFile;

public class LoadCommand extends Command{
    String filePath;

    public LoadCommand(String line, String filePath) {
        super(line);
        this.filePath = filePath;
    }
    @Override
    public CanDo execute() throws Exception {
        LoadFile loadFile = LoadFile.getInstance();
        loadFile.loadFile(filePath);
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
