package Command;

import List.ShowContent;

public class listContentCommand extends Command{
    public listContentCommand(String line){
        super(line);
    }
    @Override
    public CanDo execute() {
        ShowContent showContent = new ShowContent();
        showContent.show();
        return CanDo.Neglect;
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
