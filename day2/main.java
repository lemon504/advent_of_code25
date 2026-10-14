import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static void main(String[] args) {
        String[][] ranges = getRanges();
        List<Long> res = new ArrayList<>();
        for(String[] range : ranges) res.addAll(getInvalidNumbers(range));
        System.out.println(res.stream().mapToLong(Long::longValue).sum());
    }

    //reads ranges from input file
    public static String[][] getRanges(){
        try(BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String tmp = br.readLine();
            String[] ranges = tmp.split(",");
            String[][] res = new String[ranges.length][];
            for (int i = 0; i < ranges.length; i++) res[i] = ranges[i].split("-");
            return res;
        }catch (IOException e) {
            return null;
        }
    }

    public static List<Long> getInvalidNumbers(String[] range){
        List<Long> res = new ArrayList<>();
        outerloop:
        for (long i = Long.valueOf(range[0]); i <= Long.valueOf(range[range.length-1]); i++) {
            //cur is now every number we need to check
            String cur = String.valueOf(i);
            
            innerloop:
            for(int x = 2; x <= cur.length(); x++) {
                //System.out.println("now checking " + i + " with "+ x+ " partitions");
                String[] subStrings = partitionString(cur, x);

                if (subStrings == null) continue;

                //lets see if every substring matches the first one
                String comp = subStrings[0];
                for (int j = 1; j < x; j++) {
                    if (!comp.equals(subStrings[j])) {
                        //System.out.println("NO!");
                        continue innerloop;
                    }
                }
                //System.out.println("YES!");
                res.add(Long.valueOf(cur));
                continue outerloop;
            }
        }
        return res;
    }

    public static String[] partitionString(String input, int numberPartitions){
        if (input.length() % numberPartitions != 0) return null;

        int step = input.length()/numberPartitions;
        String[] res = new String[numberPartitions];

        int j = 0;
        for(int i = 0; i < input.length(); i+= step){
            res[j++] = input.substring(i, i+step);
        }
        return res;
    }
}