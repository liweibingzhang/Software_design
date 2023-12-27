package Command;
import java.time.LocalDateTime;
public interface Command {
    LocalDateTime localDateTime = LocalDateTime.now();
    void execute();
    CanDo undo();
    CanDo redo();
}
