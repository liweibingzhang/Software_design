package Command;

import File.LoadFile;

public class InsertCommand extends Command{
    private String text;
    private int lineNumber;
    public InsertCommand(String line, int lineNumber,String text) {
        super(line);
        this.lineNumber = lineNumber;
        this.text = text;
    }
    @Override
    public CanDo execute() {
        LoadFile loadFile  = LoadFile.getInstance();
        if(lineNumber == -1){
            lineNumber = loadFile.getContent().size();
        }
        loadFile.insert(lineNumber, text);
        return CanDo.Do;
    }
    @Override
    public CanDo undo() {
        LoadFile loadFile = LoadFile.getInstance();
        loadFile.deleteIndex(lineNumber + 1);
        return CanDo.Do;
    }
    @Override
    public CanDo redo(){
        LoadFile loadFile = LoadFile.getInstance();
        loadFile.insert(lineNumber, text);
        return CanDo.Do;
    }
}
