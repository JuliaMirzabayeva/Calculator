import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class Solution
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите строку для вычислений: ");
        String line = reader.readLine();
        reader.close();
        if(StringTest.testString(line)) //проверка строки с помощью регулярного выражения
            try {
                System.out.println(String.format(Locale.ENGLISH, "%.4f",MyCalculator.calculate(line))); // округление результата до 4-го знака
            }
            catch (Exception e) {
                System.out.println("null");
                }
        else
           System.out.println("null");

    }

}
