
import java.util.ArrayList;
import java.util.Stack;

public class MyCalculator {

    private static ArrayList<String> listOfValues = new ArrayList<>();
    private static ArrayList<String> mainList = new ArrayList<>();

    public static double calculate(String line) throws Exception
    {   parseString(line);
        if(!fillMainList()) throw new Exception();
        return getResult();
    }

    private static void parseString(String line) // заполняет listOfValues отдельными элементами строки
    {
        char[] lineToArray = line.toCharArray();
        String value = "";
        for(char c: lineToArray)
        {
            if (c!= '+' && c!= '-' && c!= '*'&& c!= '/'&& c!='(' && c!= ')')
            {
                value = value + c;
            }
            else
            {
                if( value.length() > 0) listOfValues.add(value);
                listOfValues.add(String.valueOf(c));
                value = "";
            }
        }
        if(!value.equals("")) listOfValues.add(value);
        listOfValues.add("end");

    }

    private static boolean fillMainList() // реализация обратной польской нотации
    {
        Stack<String> mainStack = new Stack<>();
        Stack<String> unarStack = new Stack<>(); // необходим для унарных операций(операций, находящихся после "(" или в начале выражения)
        mainStack.push("end"); //необходим для проверки на корректный ввод (неверное количество скобок)
        unarStack.push("end"); // необходим для проверки стека на пустоту
        int pointer = 0;
        String el;
        while(true)
        {
            el = listOfValues.get(pointer);
            switch (el)
            {
                case "+":
                case "-":
                {
                    if(pointer == 0 || listOfValues.get(pointer - 1).equals("(")|| listOfValues.get(pointer - 1).equals("/")|| listOfValues.get(pointer - 1).equals("*")) // проверка на унарную операцию
                    {
                        unarStack.push(el);
                        pointer++;
                    }
                    else if(!mainStack.peek().equals("(") && !mainStack.peek().equals("end"))
                    {
                        mainList.add(mainStack.pop());
                    }
                    else {
                        mainStack.push(el);
                        pointer++;
                    }
                    break;
                }
                case "/":
                case "*":
                {
                    if(mainStack.peek().equals("*") || mainStack.peek().equals("/"))
                        mainList.add(mainStack.pop());
                    else
                    {
                        mainStack.push(el);
                        pointer++;
                    }
                    break;
                }
                case "(":
                {
                    unarStack.push(el);
                    mainStack.push(el);
                    pointer++;
                    break;
                }
                case ")":
                {
                    unarStack.pop();
                    while (true) {
                        if (!mainStack.peek().equals("end") && !mainStack.peek().equals("(")) //убираем все между скобками
                            mainList.add(mainStack.pop());
                        else if (unarStack.peek().equals("-") || unarStack.peek().equals("+")) // для ситуации вроде "...(-(...)).."
                            mainList.add(unarStack.pop());
                        else if (mainStack.peek().equals("(")) {
                            mainStack.pop();
                            pointer++;
                            break;
                        } else return false;
                    }
                    break;
                }
                case "end":
                {
                    if(!mainStack.peek().equals("end")&& !mainStack.peek().equals("("))
                        mainList.add(mainStack.pop());
                    else if(mainStack.peek().equals("end"))
                    {
                       return true;
                    }
                    else{
                        return false;
                    }
                    break;
                }
                default:
                {
                    if(unarStack.peek().equals("+") || unarStack.peek().equals("-"))
                        mainList.add(unarStack.pop() + el);
                    else
                        mainList.add(el);
                    pointer++;
                }
            }
        }
    }

    private static double getResult() // вычисление конечного результата
    {
        Stack<Double> doubleStack = new Stack<>();
        for(String el: mainList)
        {
            if(!el.equals("+") && !el.equals("-") && !el.equals("*") && !el.equals("/"))
                doubleStack.push(Double.parseDouble(el));
            else {
                if((el.equals("-") || el.equals("+")) && doubleStack.size() == 1)
                    doubleStack.push(makeOperation(doubleStack.pop(), 0, el));
                else doubleStack.push(makeOperation(doubleStack.pop(), doubleStack.pop(), el));
            }
        }
        return doubleStack.pop();
    }
    private static double makeOperation(double number2, double number1, String operation)
    {
        switch (operation)
        {
            case "+": return number1 + number2;
            case "-": return number1 - number2;
            case "*": return number1*number2;
            case "/": return number1/number2;
        }
        return 0;
    }
}
