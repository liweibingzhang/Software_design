package List;

import File.LoadFile;
public class ShowContent implements Observer{
    @Override
    public void show() {
        LoadFile loadFile = LoadFile.getInstance();
        for (String str : loadFile.getContent()) {
            System.out.println(str);
        }
    }
}
