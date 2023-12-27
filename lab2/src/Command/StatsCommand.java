package Command;

import File.LoadFile;
import File.LogStatsObserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StatsCommand extends Command{
    private String option; // "all" 或 "current"
    private long startTime; // Session 开始时间
    private long endTime; // Session 结束时间

    public StatsCommand(String line, String option) {
        super(line);
        this.option = option;
    }

    @Override
    public CanDo execute() {
        LogStatsObserver.showStatus(option);
        return CanDo.Neglect;
    }

    @Override
    public CanDo undo() {
        // 实现 undo 操作，如果需要的话
        return CanDo.Neglect;
    }

    @Override
    public CanDo redo() {
        // 实现 redo 操作，如果需要的话
        return CanDo.Neglect;
    }
}