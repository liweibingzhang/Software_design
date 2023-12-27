package Command;

import File.LoadFile;

public class DeleteIndexCommand implements Command {
    private int index;
    private String line;
    public DeleteIndexCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute() {
        LoadFile loadFile = LoadFile.getInstance();
        try
        {
            this.line = loadFile.deleteIndex(index);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public CanDo undo(){
        LoadFile loadFile = LoadFile.getInstance();
        try
        {
            loadFile.insert(index-1, line);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return CanDo.Do;
    }
    @Override
    public CanDo redo(){
        LoadFile loadFile = LoadFile.getInstance();
        try
        {
            loadFile.deleteIndex(index);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return CanDo.Do;
    }
}

