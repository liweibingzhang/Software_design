package File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileJudge
{

    public static String getName(String line)
    {
        String[] words = line.split("\\s+");
        return words[words.length - 1];
    }

    public static int getLevel(String line)
    {
        int level = 0;
        for (int i = 0; i < line.length(); i++)
        {
            if (line.charAt(i) == '#')
            {
                level++;
            } else
            {
                break;
            }
        }
        return level;
    }
}
