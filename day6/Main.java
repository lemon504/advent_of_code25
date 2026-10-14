import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println(problem1());
        System.out.println(problem2());
    }

    public static List<String> getLinesFromInput() {
        try {
            return Files.readAllLines(Path.of("input.txt"));
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static List<String> removeWhiteSpaces(String input) {
        input = input.strip();
        String[] tmp = input.split(" ");

        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = tmp[i].strip();
        }

        List<String> res = new ArrayList<>();

        for (String o : tmp) {
            if (!o.isEmpty()) res.add(o);
        }

        return res;
    }

    public static long problem1() {
        //get all lines and save the operations line
        List<String> lines = getLinesFromInput();
        List<String> operators = removeWhiteSpaces(lines.getLast());
        lines.removeLast();

        //matrix of all numbers
        List<List<String>> numbers = new ArrayList<>();
        lines.forEach(c -> {numbers.add(removeWhiteSpaces(c));});

        long res = 0;

        for (String ops : operators) {
            long tmp;
            if (ops.equals("+")) {
                //perform +
                tmp = 0;
                for (List<String> l : numbers) tmp += Long.valueOf(l.remove(0));
            } else {
                //perform *
                tmp = 1;
                for (List<String> l : numbers) tmp *= Long.valueOf(l.remove(0));
            }
            res += tmp;
        }
        return res;
    }

    public static long problem2(){
        //get all lines and seperate them in cols
        List<String> lines = getLinesFromInput();
        List<List<String>> cols = getColsWithWhiteSpaces(lines);

        long res = 0;
        for (List<String> col : cols) {
            if (col.removeLast().equals("+")){
                //perform +
                List<Integer> modifiedCol = getModifiedCol(col);
                long tmp = 0;
                for (Integer i : modifiedCol) tmp += i;
                res += tmp;

            } else {
                //perform *
                List<Integer> modifiedCol = getModifiedCol(col);
                long tmp = 1;
                for (Integer i : modifiedCol) tmp *= i;
                res += tmp;
            }
        }
        return res;
    }

    public static List<Integer> getModifiedCol(List<String> col) {
        List<Integer> res = new ArrayList<>();
        int digits = col.getFirst().length();
        
        for (int i = 0; i < digits; i++) {
            String number = "";
            for (String tmp : col) number += tmp.charAt(i);
            if (!number.isBlank()) res.add(Integer.valueOf(number.strip()));
        }

        return res;
    }

    public static List<List<String>> getColsWithWhiteSpaces(List<String> lines) {
        String operators = lines.getLast();
        List<List<String>> res = new ArrayList<>();

        for (int i = 0; i < operators.length(); i++) {
            if (operators.charAt(i) == '+' || operators.charAt(i) == '*'){

                //all numbers in a coloumn start from the index of the operator, and end at the index of the next operator
                int j = 1;
                int pointer = i+1;
                while (pointer < operators.length()) {
                    if (operators.charAt(pointer) != ' ') break;
                    pointer++;
                    j++;
                }

                //j ist now the amount of chars we have to read each line
                List<String> col = new ArrayList<>();
                for (String line : lines) {
                    String tmp = "";
                    for (int k = i; k < i+j; k++) {
                        tmp += line.charAt(k);
                    }
                    col.add(tmp);
                }
                //strip the operations line, so we have easier cases (just "+" and "*")
                col.add(col.removeLast().strip());
                res.add(col);
            }
        }

        return res;
    }
}