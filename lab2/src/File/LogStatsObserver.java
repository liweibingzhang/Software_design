package File;


import Date.LogPath;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class LogStatsObserver implements LogObserver
{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    RandomAccessFile ran;
    long ses;

    public LogStatsObserver(String logFilePath)
    {
        initObserver(logFilePath);
        startSession();
    }

    private void initObserver(String logFilePath)
    {
        try
        {
            ran = new RandomAccessFile(logFilePath, "rw");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void showStatus(String option)
    {
        if(option.equals("current"))
            statsCurrent();
        else if(option.equals("all"))
            statsAll();
    }

    private static void statsCurrent()
    {
        LoadFile loadFile = LoadFile.getInstance();
        LogInfo logInfo = loadFile.getCurFileInfo();
        Duration totalTime = logInfo.getTotalTime().plus(Duration.between(logInfo.getStartTime(), LocalDateTime.now()));
        String operationTime = getOperationTime(totalTime);
        String line = logInfo.getFileName() + " " + operationTime;
        System.out.println(line);
    }

    private static void statsAll()
    {
        statsCurrent();
        LoadFile loadFile = LoadFile.getInstance();
        ArrayList<LogInfo> fileInfoA = loadFile.getOtherInfo();
        for (LogInfo logInfo : fileInfoA)
        {
            Duration totalTime = logInfo.getTotalTime();
            String operationTime = getOperationTime(totalTime);
            String line = logInfo.getFileName() + " " + operationTime + "\n";
            System.out.println(line);
        }
    }

    private void reOpen()
    {
        try
        {
            ran = new RandomAccessFile(LogPath.FileLogFilePath, "rw");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void show()
    {
        LoadFile loadedFile = LoadFile.getInstance();
        try
        {
            ran.seek(ses);
            for (LogInfo logInfo : loadedFile.getHistroyFile())
            {
                Duration totalTime = logInfo.getTotalTime();
                String operationTime = getOperationTime(totalTime);
                String line = logInfo.getFileName() + " " + operationTime + "\n";
                byte[] textBytes = line.getBytes(StandardCharsets.UTF_8);
                ran.write(textBytes);
            }
            ran.setLength(ran.getFilePointer());
            ran.close();
            reOpen();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void startSession()
    {
        Date date = new Date();
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

        String formattedDate = dateFormat2.format(date);
        String line = "session start at " + formattedDate + "\n";
        try
        {
            byte[] textBytes = line.getBytes(StandardCharsets.UTF_8);
            ran.seek(ran.length());
            ran.write(textBytes);
            ses = ran.getFilePointer();
            ran.close();
            reOpen();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    private static String getOperationTime(Duration duration)
    {
        long seconds = duration.getSeconds();
        if (seconds / 3600 > 0)
        {
            long hours = seconds / 3600;
            long minutes = (seconds % 3600) / 60;
            return hours + " 小时 " + minutes + " 分钟";
        } else if (seconds / 60 > 0)
        {
            long minutes = seconds / 60;
            return minutes + " 分钟";
        } else
        {
            return seconds + " 秒";
        }
    }
}
