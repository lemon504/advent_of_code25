import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        long res = getBanks().stream().map(m -> getBiggesVoltage(m)).mapToLong(Long::longValue).sum();
        System.out.println(res);
    }

    public static List<String> getBanks(){

        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))){

            List<String> res = new ArrayList<>();
            String cur;
            while((cur = br.readLine()) != null) res.add(cur);
            return res;

        }catch(IOException e){
            return null;
        }

    }

    public static long getBiggesVoltage(String bank){
        int currentIndex = 0;
        StringBuilder number = new StringBuilder();
        //i indicates how many digits are left bzw. whats the furthest we are allowed to go
        for (int i = 12; i > 0; i--) {
            int biggest = -1;
            int biggestIndex = -1;
            for (int j = currentIndex; j <= bank.length()-i; j++){
                int cur = bank.charAt(j) - '0';
                if (cur > biggest) {
                    biggest = cur;
                    biggestIndex = j;
                }
            }
            number.append(bank.charAt(biggestIndex));
            currentIndex = biggestIndex+1;
        }
        return Long.valueOf(number.toString());
    }
}