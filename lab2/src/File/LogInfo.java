package File;

import java.time.Duration;
import java.time.LocalDateTime;

public class LogInfo
{
    private String fileName;
    private LocalDateTime startTime;
    private Duration totalTime;

    public LogInfo(String fileName, LocalDateTime startTime)
    {
        this.fileName = fileName;
        this.startTime = startTime;
        this.totalTime = Duration.ZERO;
    }

    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }
    public LocalDateTime getStartTime()
    {
        return this.startTime;
    }

    public String getFileName()
    {
        return this.fileName;
    }

    public Duration getTotalTime()
    {
        return this.totalTime;
    }

    public void plusTotalTime(Duration duration)
    {
        this.totalTime = this.totalTime.plus(duration);
    }
}
