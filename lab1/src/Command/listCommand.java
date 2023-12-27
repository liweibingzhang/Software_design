package Command;

import Command.Command;
import List.ShowContent;

public class listCommand implements Command {
    @Override
    public void execute() {
        ShowContent showContent = new ShowContent();
        showContent.show();
    }

    @Override
    public CanDo undo() {
        return CanDo.Neglect;
    }

    @Override
    public CanDo redo(){
        return CanDo.Neglect;
    }
}
