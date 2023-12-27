//package Command;
//
//import java.io.*;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class MarkdownEditor {
//    private static MarkdownEditor instance;
//    private String filePath;
//    private StringBuilder content;
//    private final CommandHistory commandHistory = new CommandHistory();
//    private MarkdownEditor() {
//        content = new StringBuilder();
//    }
//    private Command lastUndoCommand;
//    TimeStamp historyLogger = TimeStamp.getInstance();
//    List<File> sessionFiles = new ArrayList<>();
//    private String currentFile;
//    public static MarkdownEditor getInstance() {
//        if (instance == null) {
//            instance = new MarkdownEditor();
//        }
//        return instance;
//    }
//    private void createNewFile(String filePath) {
//        try {
//            File newFile = new File(filePath);
//            if (newFile.createNewFile()) {
//                System.out.println("File created successfully.");
//                sessionFiles.add(newFile);
//            } else {
//                System.out.println("Failed to create the file.");
//            }
//        } catch (IOException e) {
//            System.out.println("Error while creating the file: " + e.getMessage());
//        }
//    }
//    public void loadFile(String filePath) {
//        this.filePath = filePath;
//        currentFile = filePath;
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                content.append(line).append("\n");
//            }
//            System.out.println("Load successfully");
//
//            File loadedfile = new File(filePath);
//            sessionFiles.add(loadedfile);
//        } catch (IOException e) {
//            System.out.println("Error while loading the file: " + e.getMessage());
//            if (!new File(filePath).exists()) {
//                createNewFile(filePath);
//            }
//        }
//    }
//    public void insertContent(String text, int lineNumber) {
//        if (lineNumber <= 0) {
//            // 如果行号小于等于0，追加到文件末尾
//            content.append(text).append("\n");
//        } else {
//            // 如果指定行号，插入到该行之前
//            String[] lines = content.toString().split("\n");
//
//            if (lineNumber > lines.length) {
//                // 如果行号大于文件行数，追加到文件末尾
//                content.append(text).append("\n");
//            } else {
//                StringBuilder updatedContent = new StringBuilder();
//                for (int i = 0; i < lines.length; i++) {
//                    if (i == lineNumber - 1) {
//                        updatedContent.append(text).append("\n");
//                    }
//                    updatedContent.append(lines[i]).append("\n");
//                }
//                content.setLength(0); // 清空原内容
//                content.append(updatedContent);
//            }
//        }
//    }
//    public void saveToFile() {
//        if (filePath != null) {
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//                writer.write(content.toString());
//                System.out.println("Save successfully");
//            } catch (IOException e) {
//                System.out.println("Error while saving the file: " + e.getMessage());
//            }
//        } else {
//            System.out.println("No file loaded. Please use 'load' command first.");
//        }
//    }
//    public  void appendhead(String text){
//        content.insert(0, text + "\n");
//    }
//    public void appendtail(String text) {
//        content.append(text).append("\n");
//    }
//    public DeleteResult deleteContent(String target) {
//        String deletedContent = ""; //储存已删除的内容
//        int deletedLineNumber = -1; //储存已删除的行号
//        String contentStr = content.toString();
//        String[] lines = contentStr.split("\n");
//        // Check if the target is a line number
//        if (target.matches("\\d+")) {
//            int lineNumber = Integer.parseInt(target);
//            if (lineNumber > 0 && lineNumber <= lines.length) {
//                // Delete the line at the specified line number
//                //记录已经删除的内容
//                deletedContent = lines[lineNumber - 1];
//                deletedLineNumber = lineNumber;
//                StringBuilder updatedContent = new StringBuilder();
//                for (int i = 0; i < lines.length; i++) {
//                    if (i != lineNumber - 1) {
//                        updatedContent.append(lines[i]).append("\n");
//                    }
//                }
//                content.setLength(0); // Clear the original content
//                content.append(updatedContent);
//            } else {
//                System.out.println("Invalid line number. No content deleted.");
//            }
//        } else {
//            // Delete the specified title/text
//            boolean contentDeleted = false;
//            StringBuilder updatedContent = new StringBuilder();
//            for (int i = 0; i < lines.length; i++) {
//                if (lines[i].trim().equals(target)) {
//                    deletedLineNumber = i + 1; // 记录被删除内容的行号
//                    deletedContent = lines[i]; // 记录已删除的内容
//                    contentDeleted = true;
//                } else {
//                    updatedContent.append(lines[i]).append("\n");
//                }
//            }
//            content.setLength(0); // Clear the original content
//            content.append(updatedContent);
//            if (!contentDeleted) {
//                System.out.println("No matching content found. Nothing deleted.");
//            }
//        }
//            return new DeleteResult(deletedContent, deletedLineNumber);
//    }
//
//    public void undoCommand() {
//        Command lastCommand = commandHistory.peek();
//        if (!commandHistory.isEmpty()) {
//            if (lastCommand.canundo()) { // 检查命令是否支持撤销
//                commandHistory.pop();
//                lastCommand.undo();
//                lastUndoCommand = lastCommand;
//                System.out.println("Undo successful");
//            } else {
//                System.out.println("Command.Command cannot be undone.");
//            }
//        } else {
//            System.out.println("Nothing to undo.");
//        }
//    }
//    public void redoCommand() {
//        if (lastUndoCommand != null) {
//            lastUndoCommand.execute(); // 重新执行最后一次的undo命令
//            commandHistory.push(lastUndoCommand); // 将命令推回commandHistory中
//            lastUndoCommand = null; // 重置最后一次的undo命令
//            System.out.println("Redo successful");
//        } else {
//            System.out.println("Cannot redo.");
//        }
//    }
//    public void listContentCommand(){
//        if (content.length() > 0) {
//            System.out.println(content.toString());
//        } else {
//            System.out.println("The document is empty.");
//        }
//    }
//    public void historyCommand(int count){
//        if (count <= 0) {
//            System.out.println("Invalid count value. Please provide a valid count.");
//            return; // 不执行历史记录的打印
//        }
//        List<String> history = historyLogger.getHistory(count);
//        for (String entry : history) {
//            System.out.println(entry);
//        }
//    }
//    public void displayAllStats() {
//        System.out.println("Stats for all files in the session:");
//
//        for (File file : sessionFiles) {
//            String fileName = file.getName();
//            //long workingDuration = calculateWorkingDuration(file);
//            //String formattedDuration = formatDuration(workingDuration);
//
//            //System.out.println(fileName + " " + formattedDuration);
//        }
//    }
//
//    public void displayCurrentStats(String FileStartTime,String FileEndTime) {
//        if (currentFile != null) {
//            System.out.println("Stats for the current file:");
//            String workingDuration = calculateTimeDifference(FileStartTime,FileEndTime);
//            System.out.println("./"+ currentFile+ " " + workingDuration);
//        } else {
//            System.out.println("No current file loaded.");
//        }
//    }
//    public static String calculateTimeDifference(String timestamp1, String timestamp2) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//        System.out.println(timestamp1);
//        System.out.println(timestamp2);
//        try {
//            Date date1 = sdf.parse(timestamp1);
//            Date date2 = sdf.parse(timestamp2);
//            long timeDifferenceMillis = Math.abs(date1.getTime() - date2.getTime());
//            long hours = timeDifferenceMillis / (60 * 60 * 1000);
//            long minutes = (timeDifferenceMillis % (60 * 60 * 1000)) / (60 * 1000);
//            long seconds = (timeDifferenceMillis % (60 * 1000)) / 1000;
//
//            return hours + "小时" + minutes + "分钟" + seconds + "秒";
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return "时间戳格式无效";
//        }
//    }
//
////    private TreeNode root;
////    public void parseMarkdownContent() {
////        String markdown = content.toString();
////        this.root = parse(markdown);
////    }
////
////    public void listTreeCommand() {
////        parseMarkdownContent();
////        if (root != null) {
////            printTree(root, 0);
////        } else {
////            System.out.println("Markdown content is empty.");
////        }
////    }
////    private TreeNode parse(String markdown) {
////        String[] lines = markdown.split("\n");
////        return parseNode(lines, 0);
////    }
////
////    private TreeNode parseNode(String[] lines, int index) {
////        if (index >= lines.length) {
////            return null;
////        }
////
////        String line = lines[index].trim();
////        if (line.startsWith("#")) {
////            TreeNode node = new TreeNode(line);
////            int level = line.lastIndexOf('#') + 1;
////            while (index + 1 < lines.length && lines[index + 1].trim().startsWith("#")) {
////                index++;
////                node.addChild(parseNode(lines, index));
////            }
////            return node;
////        } else {
////            return null;
////        }
////    }
////
////    private void printTree(TreeNode node, int depth) {
////        if (node == null) {
////            return;
////        }
////        StringBuilder indent = new StringBuilder();
////        for (int i = 0; i < depth; i++) {
////            indent.append("  ");
////        }
////        System.out.println(indent.toString() + node.label);
////        for (TreeNode child : node.children) {
////            printTree(child, depth + 1);
////        }
////    }
//
//
//    public CommandHistory getCommandHistory() {
//        return commandHistory;
//    }
//    public void executeCommand(Command command) {
//        command.execute();
//    }
//}
