package File;

import Command.DeleteInfo;
import Command.TimeCommand;

import javax.print.attribute.standard.MediaSize;
import java.io.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoadFile
{
    private static LoadFile instance;
    private String filePath;
    private String fileTimeInfo;
    private long StartTime;
    private long EndTime;
    private ArrayList<String> content;
    private String fileName;
    private TimeCommand historyLogger = TimeCommand.getInstance();
    List<File> sessionFiles = new ArrayList<>();
    private boolean isSaved = false;

    private LoadFile()
    {
        content = new ArrayList<>();
    }

    public String getRoot()
    {
        return content.get(0);
    }

    public static LoadFile getInstance()
    {
        if (instance == null)
        {
            instance = new LoadFile();
        }
        return instance;
    }

    public void loadFile(String filePath)
    {
        this.filePath = filePath;
        File file = new File(filePath);
        try
        {
            isSaved = false;
            if (!file.exists())
            {
                // 文件不存在，创建新文件
                file.createNewFile();
            }
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                content.add(line);
            }
            fileName = filePath;
            // Close File
            bufferedReader.close();
            fileReader.close();
            StartTime = System.currentTimeMillis();
            File loadedfile = new File(filePath);
            sessionFiles.add(loadedfile);
            historyLogger.logCommand("load " + filePath);
        } catch (IOException e)
        {
            System.out.println("Error while loading the file: " + e.getMessage());
        }
    }
    public void saveFile()
    {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                isSaved = true;
                for (String tmp : content) {
                    writer.write(tmp);
                    writer.newLine(); // 添加换行符
                }
                EndTime = System.currentTimeMillis();
                historyLogger.logCommand("save");
                historyLogger.saveAllLogs();
                historyLogger.clearHistoryLogger();
                long time = EndTime - StartTime;
                long hours = time / 360000; // 1小时 = 3600000毫秒
                long minutes = (time % 3600000) / 60000; // 1分钟 = 60000毫秒
                long seconds = (time % 60000) / 1000; // 1秒 = 1000毫秒
                String res =  ("./" + fileName+ " " + hours + " 小时 " + minutes + " 分钟 " + seconds + " 秒");
                fileTimeInfo = res;
                try (BufferedWriter wr = new BufferedWriter(new FileWriter("filelog.txt",true)) // 这里的true表示追加模式
                ) {
                    wr.write(res);
                    wr.newLine();
                } catch (IOException e) {
                    System.out.println("Error while appending to the file: " + e.getMessage());
                }
                content.clear();
            } catch (IOException e) {
                System.out.println("Error while saving the file: " + e.getMessage());
            }
    }

    public void insert(int index, String text)
    {
        if (isSaved) {
            System.out.println("File is already saved. Cannot perform this operation.");
            historyLogger.logCommand("insert " + index + text);
            return;
        }
        //index = index - 1;
        if (index < 0 || index > content.size())
        {
            //System.out.println("Invalid index!");
            return;
        }
        content.add(index, text);
        System.out.println("Insert successfully");
    }

    public String deleteIndex(int index)
    {    //通过索引的方式删除
        if (isSaved) {
            System.out.println("File is already saved. Cannot perform this operation.");
            historyLogger.logCommand("delete " + index);
            return null;
        }
        String deletedText = content.get(index - 1);
        content.remove(index - 1);
        historyLogger.logCommand("delete " + index);
        System.out.println("Delete successfully");
        return deletedText;
    }

    public DeleteInfo deleteContent(String X) {
        if (isSaved) {
            System.out.println("File is already saved. Cannot perform this operation.");
            historyLogger.logCommand("delete " + X);
            return null;
        }
        int index = -1;
        String deletedLine = null;
        for (int i = 0; i < content.size(); i++) {
            String line = content.get(i);
            if (line.contains(X)) {
                index = i;
                deletedLine = content.remove(i);
                break; // 找到匹配行后，立即退出循环
            }
        }
        historyLogger.logCommand("delete " + X);
        System.out.println("Delete successfully");
        //System.out.println("Delete" + deletedLine + index);
        return new DeleteInfo(deletedLine,index);
    }


    public ArrayList<String> getContent()
    {
        return content;
    }
    public void history(int count){
        List<String> history = historyLogger.getHistory(count);
        for (String entry : history) {
            System.out.println(entry);
        }
    }
    public void statscurrent(){
        System.out.println(fileTimeInfo);
    }
    public void statsall(){
        try (BufferedReader reader = new BufferedReader(new FileReader("filelog.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getMessage());
        }
    }
}


