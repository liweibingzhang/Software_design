//package Command;
//import File.LoadFile;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class TimeCommand extends Command{
//    private StringBuilder currentSessionLog;
//    public static TimeCommand instance;
//    private List<String> commandHistory;
//
//    private int count;
//    public TimeCommand() {
//        commandHistory = new ArrayList<>();
//        currentSessionLog = new StringBuilder();
//    }
//    public TimeCommand(int count){
//        this.count = count;
//    }
//    public static TimeCommand getInstance(){
//        if(instance == null){
//            instance = new TimeCommand();
//        }
//        return instance;
//    }
//    public void logCommand(String command) {
//        String timestamp = generateTimestamp();
//        String logEntry = timestamp + " " + command;
//        commandHistory.add(logEntry);
//        currentSessionLog.append(logEntry).append("\n");
//    }
//    public void startSession() {
//        String timestamp = generateTimestamp();
//        String sessionStartLog = "Session start at " + timestamp;
//        System.out.println(sessionStartLog);
//        commandHistory.add(sessionStartLog);
//        currentSessionLog.append(sessionStartLog).append("\n");
//        saveLog("log.txt");
//    }
//    public void saveLog(String filePath){
//        try (FileWriter writer = new FileWriter(filePath,true)) {
//            for (String logEntry : commandHistory) {
//                writer.write(logEntry + "\n");
//            }
//            System.out.println("Log file saved successfully.");
//        } catch (IOException e) {
//            System.err.println("Error while saving log file: " + e.getMessage());
//        }
//    }
//    public void startFileSession() {
//        String timestamp = generateTimestamp();
//        String sessionStartLog = "Session start at " + timestamp;
//        currentSessionLog.append(sessionStartLog).append("\n");
//        saveFileLog("filelog.txt");
//    }
//    public void saveFileLog(String filePath){
//        try (FileWriter writer = new FileWriter(filePath)) {
//            for (String logEntry : commandHistory) {
//                writer.write(logEntry + "\n");
//            }
//            System.out.println("Log file saved successfully.");
//        } catch (IOException e) {
//            System.err.println("Error while saving log file: " + e.getMessage());
//        }
//    }
//    public List<String> getHistory(int count) {
//        if (count <= 0 || count > commandHistory.size()) {
//            return new ArrayList<>(commandHistory);
//        }
//        return commandHistory.subList(commandHistory.size() - count, commandHistory.size());
//    }
//
//    private String generateTimestamp() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//        return sdf.format(new Date());
//    }
//    @Override
//    public CanDo execute() {
//        LoadFile loadFile = LoadFile.getInstance();
//        loadFile.history(count);
//        return CanDo.Neglect;
//    }
//    public void clearHistoryLogger() {
//        currentSessionLog.setLength(0); // 清空StringBuilder
//        commandHistory.clear(); // 清空List
//    }
//    public void saveAllLogs(){
//        saveLog("log.txt");
//    }
//    @Override
//    public CanDo undo() {
//        return CanDo.Stop;
//    }
//    @Override
//    public CanDo redo() {
//        return CanDo.Stop;
//    }
//}
//
