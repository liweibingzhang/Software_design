package Command;
import java.time.LocalDateTime;
public abstract class Command {
        private String command_line;

        public Command(String str)
        {
            this.command_line = str;
        }

        public String getCommand_line()
        {
            return command_line;
        }

        public abstract CanDo execute() throws Exception;

        public abstract CanDo undo();

        public abstract CanDo redo();
}
