package evolution;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter {

    public static void main(String[] args) throws Exception {

    }

    public static class MyCustomFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            StringBuffer sb = new StringBuffer();
            sb.append(record.getMessage());
            sb.append("\n");
            return sb.toString();
        }

    }

}
