package Command;
import File.LoadFile;
import java.util.ArrayList;

public class DeleteContentCommand implements Command {
    private DeleteInfo deleteInfo;
    private String deletedContent;

    public DeleteContentCommand(String content) {
        this.deletedContent = content;
    }

    @Override
    public void execute() {
        LoadFile loadFile = LoadFile.getInstance();
        this.deleteInfo = loadFile.deleteContent(deletedContent);
    }

    @Override
    public CanDo undo() {
        LoadFile loadFile = LoadFile.getInstance();
        loadFile.insert(deleteInfo.getDeletedLineNumber(), deleteInfo.getDeletedContent());
        return CanDo.Do;
    }

    @Override
    public CanDo redo() {
        LoadFile loadFile = LoadFile.getInstance();
        loadFile.deleteIndex(deleteInfo.getDeletedLineNumber()+1);
        return CanDo.Do;
    }
}
