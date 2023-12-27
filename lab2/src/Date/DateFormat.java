package Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
public class DateFormat extends Formatter
{
    @Override
    public String format(LogRecord record)
    {
        return record.getMessage() + "\n";
    }
}
