package Command;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Command.CommandOB;
import Date.LogPath;

public class logCommand implements CommandOB
{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    private OutputStreamWriter outputStreamWriter;
    private String logfilePath;

    public logCommand(String logfilePath)
    {
        this.logfilePath = logfilePath;
        startLog(logfilePath);
        startSession();
    }

    public void startLog(String logfilePath)
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(logfilePath, true);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void Showhistory(int count) throws Exception
    {
        List<String> history = new ArrayList<>();
        String filePath = LogPath.CommandLogFilePath; // 替换为实际的文件路径
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null)
        {
            if (line.charAt(0) == 's')
                continue;
            history.add(line);
        }
        reader.close();
        for (int i = history.size() - 1; i >= 0; i--)
        {
            if (i == history.size() - count - 1)
                break;
            System.out.println(history.get(i));
        }
    }

    private void writeLine(String line)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void startSession()
    {
        Date date = new Date();
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String formattedDate = dateFormat2.format(date);
        String line = "session start at " + formattedDate;
        writeLine(line);
        Open();
    }

    @Override
    public void load(Command command)
    {
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        String line = formattedDate + " " + command.getCommand_line();
        writeLine(line);
        Open();
    }

    private void Open()
    {
        try
        {
            outputStreamWriter.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        startLog(logfilePath);
    }
}
