package Command;

import File.LoadFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StatsCommand implements Command {
    private String option; // "all" 或 "current"
    private long startTime; // Session 开始时间
    private long endTime; // Session 结束时间

    public StatsCommand(String option) {
        this.option = option;
    }

    @Override
    public void execute() {
        LoadFile loadFile = LoadFile.getInstance();
        if ("current".equals(option)) {
            loadFile.statscurrent();
        } else if ("all".equals(option)) {
            loadFile.statsall();
        }
    }

    @Override
    public CanDo undo() {
        // 实现 undo 操作，如果需要的话
        return CanDo.Do;
    }

    @Override
    public CanDo redo() {
        // 实现 redo 操作，如果需要的话
        return CanDo.Do;
    }
}