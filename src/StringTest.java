import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTest {

    public static boolean testString(String line)
    {
        Pattern p = Pattern.compile("^([-+]?([(][-+]?)*[0-9]+\\.?[0-9]*[/*+-]?[0-9]*\\.?[0-9]*[)]*[/*]?)+$");
        Matcher m = p.matcher(line);
        return m.matches();

    }
}
