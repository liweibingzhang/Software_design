import Date.LogPath;
import Command.*;
import File.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

    public static void main(String[] args) throws Exception {
        System.setProperty("file.encoding", "UTF-8");
        logCommand logcommand = new logCommand(LogPath.CommandLogFilePath);
        LogStatsObserver logob = new LogStatsObserver(LogPath.FileLogFilePath);
        ExecuteCommand editor = ExecuteCommand.getInstance();
        LoadFile loadFile = LoadFile.getInstance();
        editor.add(logcommand);
        loadFile.add(logob);
        Main program = new Main();
        InputStream inputStream = System.in;
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        Scanner scanner = new Scanner(inputStream);
        System.out.println("Enter a Command:");
        while (true) {
            String input = scanner.nextLine();
            program.OB(input);
        }
    }

    public static void OB(String context) {
        String[] tokens = context.split(" ");
        String command = tokens[0];
        ExecuteCommand editor = ExecuteCommand.getInstance();
        switch (command) {
            case "load":
                if (tokens.length > 1) {
                    String filePath = tokens[1];
                    editor.executeCommand(new LoadCommand(context, filePath));
                } else {
                    System.out.println("Usage: load <file_path>");
                }
                break;
            case "save":
                editor.executeCommand(new SaveCommand(context));
                break;
            case "insert":
                if (tokens.length >= 2) {
                    if (isNum(tokens[1])) {
                        if (tokens.length != 4) {
                            System.out.println("Wrong format!");
                            break;
                        }
                        int index = Integer.parseInt(tokens[1]);
                        String target = tokens[2] + " " + tokens[3];
                        editor.executeCommand(new InsertCommand(context, index, target));
                    } else {
                        if (tokens.length != 3) {
                            System.out.println("Wrong format!");
                            break;
                        }
                        String target = tokens[1] + " " + tokens[2];
                        editor.executeCommand(new InsertCommand(context, -1, target));
                    }
                } else {
                    System.out.println("Invalid input format for 'insert' command.");
                }
                break;
            case "append-head":
                if (tokens.length > 1) {
                    String target = tokens[1] + " " + tokens[2];
                    editor.executeCommand(new InsertCommand(context, 0, target));
                } else {
                    System.out.println("Usage: append-head text");
                }
                break;
            case "append-tail":
                if (tokens.length > 1) {
                    String target = tokens[1] + " " + tokens[2];
                    editor.executeCommand(new InsertCommand(context, -1, target));
                } else {
                    System.out.println("Usage: append-tail text");
                }
                break;
            case "delete":
                if (tokens.length > 1) {
                    String target = tokens[1];
                    if (isNum(target)) {
                        int index = Integer.parseInt(target) - 1;
                        editor.executeCommand(new DeleteIndexCommand(context, index));
                    } else {
                        target = tokens[1];
                        editor.executeCommand(new DeleteContentCommand(context, target));
                    }
                } else {
                    System.out.println("Usage: delete text/line_number");
                }
                break;
            case "undo":
                editor.executeCommand(new undoCommand(context));
                break;
            case "redo":
                editor.executeCommand(new redoCommand(context));
                break;
            case "list":
                editor.executeCommand(new listContentCommand(context));
                break;
            case "list-tree":
                editor.executeCommand(new listTreeCommand(context));
                break;
            case "dir-tree":
                if (tokens.length > 1) {
                    editor.executeCommand(new listdirCommand(context, tokens[1]));
                } else {
                    System.out.println("Usage: dir-tree <title>");
                }
                break;
            case "history":
                if (tokens.length > 1) {
                    int cnt = Integer.parseInt(tokens[1]);
                    editor.executeCommand(new historyCommand(context, cnt));
                } else {
                    System.out.println("Usage: history <count>");
                }
                break;
            case "stats":
                if (tokens.length > 1) {
                    String option = tokens[1];
                    editor.executeCommand(new StatsCommand(context, option));
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
