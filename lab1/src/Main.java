import Command.*;
import java.util.Scanner;
public class Main {
    private static boolean isSaved = false;
    public static boolean isNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            //System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        //HistoryCommand commandHistory = new HistoryCommand();
        Scanner scanner = new Scanner(System.in);
        ExecuteCommand editor = ExecuteCommand.getInstance();

        TimeCommand Start = new TimeCommand(); // 创建一个 TimeStamp 实例
        Start.startSession(); // 记录会话开始时间
        Start.startFileSession();
        TimeCommand historyLogger = TimeCommand.getInstance();
        while (true) {
            System.out.println("Enter a command:");
            String input = scanner.nextLine();

            String[] tokens = input.split(" ");
            if (tokens.length > 0) {
                String command = tokens[0];
                switch (command) {
                    case "load":
                        if (tokens.length > 1) {
                            String filePath = tokens[1];
                            //isSaved = false;
                            editor.executeCommand(new LoadCommand(filePath));
                        } else {
                            System.out.println("Usage: load <file_path>");
                        }
                        break;
                    case "save":
                        //isSaved = true;
                        editor.executeCommand(new SaveCommand());
                        break;
                    case "insert":
                        if (tokens.length >= 2) {
                            int lineNumber;
                            String text;
//                            if (isSaved) {
//                                System.out.println("File is already saved. Cannot perform this operation.");
//                                break;
//                            }
                            if (isNum(tokens[1])) {
                                lineNumber = Integer.parseInt(tokens[1]);
                                text = input.substring(input.indexOf(tokens[2])); // 从第三个标记开始的部分是文本
                            } else {
                                // 如果第二个标记不是数字，将其作为一部分文本
                                lineNumber = -1;
                                text = input.substring(input.indexOf(tokens[1]));
                            }
                            historyLogger.logCommand("insert " + lineNumber + " " + text);
                            editor.executeCommand(new InsertCommand(lineNumber-1, text));
                        } else {
                            System.out.println("Invalid input format for 'insert' command.");
                        }
                        break;
                    case "append-head":
                        if (tokens.length > 1) {
                            String text = input.substring(input.indexOf(tokens[1]));
                            historyLogger.logCommand("append-head " + text);
                            editor.executeCommand(new InsertCommand(0, text));
                        } else {
                            System.out.println("Usage: append-head text");
                        }
                        break;
                    case "append-tail":
                        if (tokens.length > 1) {
                            String text = input.substring(input.indexOf(tokens[1]));
                            historyLogger.logCommand("append-tail " + text);
                            editor.executeCommand(new InsertCommand(-1, text));
                        } else {
                            System.out.println("Usage: append-tail text");
                        }
                        break;
                    case "delete":
                        if (isSaved) {
                            System.out.println("File is already saved. Cannot perform this operation.");
                            break;
                        }
                        if (tokens.length > 1) {
                            String target = tokens[1];
                            if(isNum(target)) {
                                int index = Integer.parseInt(target);
                                editor.executeCommand(new DeleteIndexCommand(index));
                            }
                            else {
                                target = input.substring(input.indexOf(tokens[1]));
                                editor.executeCommand(new DeleteContentCommand(target));
                            }
                        } else {
                            System.out.println("Usage: delete text/line_number");
                        }
                        break;
                    case "undo":
                        if (isSaved) {
                            System.out.println("File is already saved. Cannot perform this operation.");
                            break;
                        }
                        editor.undoCommand();
                        //System.out.println("Undo successfully");
                        historyLogger.logCommand("undo");
                        break;
                    case "redo":
                        if (isSaved) {
                            System.out.println("File is already saved. Cannot perform this operation.");
                            break;
                        }
                        editor.redoCommand();
                        //System.out.println("Redo successfully");
                        historyLogger.logCommand("redo");
                        break;
                    case "list":
                        if (isSaved) {
                            System.out.println("File is already saved. Cannot perform this operation.");
                            break;
                        }
                        editor.executeCommand(new listCommand());
                        historyLogger.logCommand("list");
                        break;
                    case "list-tree":
                        if (isSaved) {
                            System.out.println("File is already saved. Cannot perform this operation.");
                            break;
                        }
                        editor.executeCommand(new listTreeCommand());
                        historyLogger.logCommand("list-tree");
                        break;
                    case "dir-tree":
                        if (isSaved) {
                            System.out.println("File is already saved. Cannot perform this operation.");
                            break;
                        }
                        if (tokens.length > 1) {
                            editor.executeCommand(new listdirCommand(tokens[1]));
                            historyLogger.logCommand("dir-tree " + tokens[1]);
                        } else {
                            System.out.println("Usage: dir-tree <title>");
                        }
                        break;
                        case "history":
                        if(tokens.length > 1){
                            int count = Integer.parseInt(tokens[1]);
                            editor.executeCommand(new TimeCommand(count));
                            historyLogger.logCommand("history " + count);
                        }else{
                            System.out.println("Usage: history <count>");
                        }
                        break;
                    case "stats":
                        if (tokens.length > 1) {
                            String option = tokens[1];
                            editor.executeCommand(new StatsCommand(option));
                            //historyLogger.logCommand("stats " + option);
                        } else {
                            System.out.println("Usage: stats <all | current>");
                        }
                        break;
                    case "exit":
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Unknown command. Type 'help' for a list of commands.");
                        break;
                }
            }
        }
    }

    String FilEndTime = "";
}
