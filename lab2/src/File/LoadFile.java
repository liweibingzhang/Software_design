package File;

import Command.DeleteInfo;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
public class LoadFile
{
    private static LoadFile instance;
    private ArrayList<String> content;
    private ArrayList<LogInfo> histroyFile;
    private int cur_Index;
    private String fileName;
    private List<LogObserver> logObservers = new ArrayList<>();
    List<File> sessionFiles = new ArrayList<>();
    private boolean isSaved = false;

    private LoadFile()
    {
        cur_Index = -1;
        histroyFile = new ArrayList<>();
        content = new ArrayList<>();
    }
    public LogInfo getCurFileInfo()
    {
        return histroyFile.get(cur_Index);
    }
    public ArrayList<LogInfo> getOtherInfo()
    {
        ArrayList<LogInfo> OtherInfo = new ArrayList<>();
        for (int i = 0; i < histroyFile.size(); i++)
        {
            if (i != cur_Index)
            {
                OtherInfo.add(histroyFile.get(i));
            }
        }
        return OtherInfo;
    }

    public void add(LogObserver logObserver)
    {
        logObservers.add(logObserver);
    }

    public void display()
    {
        for (LogObserver logob : logObservers)
        {
            logob.show();
        }
    }

    public ArrayList<LogInfo> getHistroyFile()
    {
        return histroyFile;
    }
    public String getRoot()
    {
        return content.get(0);
    }
    public ArrayList<String> getContent()
    {
        return content;
    }
    public static LoadFile getInstance()
    {
        if (instance == null)
        {
            instance = new LoadFile();
        }
        return instance;
    }
    public void updateFileInfo(){
        if(cur_Index != -1){
           LogInfo loginfo = histroyFile.get(cur_Index);
           LocalDateTime now = LocalDateTime.now();
           Duration totalTime = Duration.between(loginfo.getStartTime(),now);
           loginfo.plusTotalTime(totalTime);
           histroyFile.set(cur_Index,loginfo);
            display();
        }
    }
    public void creatFile(String loadfile) throws Exception{
        File file = new File(loadfile);
        if(!file.exists()){
           file.createNewFile();
        }
    }
    public void setFile(String loadfile){
        int i;
        for(i = 0; i < histroyFile.size(); i++){
            LogInfo logInfo = histroyFile.get(i);
            if(logInfo.getFileName().equals(loadfile)){
                logInfo.setStartTime(LocalDateTime.now());
                histroyFile.set(i,logInfo);
                cur_Index = i;
                break;
            }
        }
        if(i == histroyFile.size()){
            histroyFile.add(new LogInfo(loadfile, LocalDateTime.now()));
            cur_Index = i;
        }
    }
    public void readFile(String loadFile) throws Exception{
        content = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(loadFile);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String tmp;
        while ((tmp = bufferedReader.readLine()) != null)
        {
            content.add(tmp);
        }
        fileInputStream.close();
        bufferedReader.close();
        inputStreamReader.close();
    }
    public void loadFile(String filePath) throws Exception {
        updateFileInfo();
        creatFile(filePath);
        setFile(filePath);
        readFile(filePath);
    }
    public void saveFile() throws Exception
    {
        updateFileInfo();
        String fileName = histroyFile.get(cur_Index).getFileName();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            for (String str : content)
            {
                writer.write(str);
                writer.newLine();
            }
        }

//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//                isSaved = true;
//                for (String tmp : content) {
//                    writer.write(tmp);
//                    writer.newLine(); // 添加换行符
//                }
//                EndTime = System.currentTimeMillis();
//                historyLogger.logCommand("save");
//                historyLogger.saveAllLogs();
//                historyLogger.clearHistoryLogger();
//                long time = EndTime - StartTime;
//                long hours = time / 360000; // 1小时 = 3600000毫秒
//                long minutes = (time % 3600000) / 60000; // 1分钟 = 60000毫秒
//                long seconds = (time % 60000) / 1000; // 1秒 = 1000毫秒
//                String res =  ("./" + fileName+ " " + hours + " 小时 " + minutes + " 分钟 " + seconds + " 秒");
//                fileTimeInfo = res;
//                try (BufferedWriter wr = new BufferedWriter(new FileWriter("filelog.txt",true)) // 这里的true表示追加模式
//                ) {
//                    wr.write(res);
//                    wr.newLine();
//                } catch (IOException e) {
//                    System.out.println("Error while appending to the file: " + e.getMessage());
//                }
//                content.clear();
//            } catch (IOException e) {
//                System.out.println("Error while saving the file: " + e.getMessage());
//            }
    }

    public void insert(int index, String text)
    {
        if (index < 0 || index > content.size())
        {
            System.out.println("Invalid index!");
            return;
        }
        content.add(index, text);
        System.out.println("Insert successfully");
    }

    public String deleteIndex(int index)
    {
        String deletedText = content.get(index);
        content.remove(index);
        System.out.println("Delete successfully");
        return deletedText;
    }

    public DeleteInfo deleteContent(String X) {
        int index = -1;
        String deletedLine = null;
        for (int i = 0; i < content.size(); i++) {
            String line = content.get(i);
            if (line.contains(X)) {
                index = i;
                deletedLine = content.remove(i);
                break;
            }
        }
        System.out.println("Delete successfully");
        //System.out.println("Delete" + deletedLine + index);
        return new DeleteInfo(deletedLine,index);
    }

}


