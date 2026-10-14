
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class main {
    public static void main(String[] args) {
        System.out.println(countTotalFreshIds(getRanges()));
    }

    public static List<Tuple<Long>> getRanges() {

        List<Tuple<Long>> res = new ArrayList<>();
        String tmp;

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))){

            while (!(tmp = br.readLine()).isBlank()) {
                String[] range = tmp.split("-");
                Tuple<Long> element = new Tuple<>(Long.valueOf(range[0]), Long.valueOf(range[1]));
                res.add(element);
            }

            return res;

        }catch (IOException e){
            return null;
        }
    }

    public static long countTotalFreshIds(List<Tuple<Long>> rawRanges) {
        if (rawRanges.isEmpty()) return 0;

        // 1. Nach Startwert sortieren
        rawRanges.sort(Comparator.comparingLong(Tuple::first));

        List<Tuple<Long>> merged = new ArrayList<>();
        Tuple<Long> current = rawRanges.get(0);

        // 2. Intervalle verschmelzen
        for (int i = 1; i < rawRanges.size(); i++) {
            Tuple<Long> next = rawRanges.get(i);

            // Überlappen sie sich? (Ende von A >= Start von B)
            if (current.second() >= next.first()) {
                // Das neue Ende ist das Maximum beider Enden
                current.second(Math.max(current.second(), next.second()));
            } else {
                // Keine Überlappung -> aktuelles Intervall sichern
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);

        // 3. IDs zählen
        long total = 0;
        for (Tuple<Long> range : merged) {
            total += (range.second() - range.first()) + 1;
        }
        return total;
    }
}