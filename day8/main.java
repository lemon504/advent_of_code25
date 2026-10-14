import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class main {

    public record Pair<A, B>(A a, B b) {}

    public static void main(String[] args) {
        problem2();
    }
    
    static double distance(int[] A, int[] B) {
        if (A.length != 5 || B.length != 5) return -1;

        double sum1 = Math.pow(B[1] - A[1], 2);
        double sum2 = Math.pow(B[2] - A[2], 2);
        double sum3 = Math.pow(B[3] - A[3], 2);

        return Math.sqrt(sum1+sum2+sum3);
    }

    //get the Informatione from the Input.txt and stores them in Arrays: [id, x, y, z, circuitId]
    public static List<int[]> getLinesFromInput() {
        try {
            List<String> lines = Files.readAllLines(Path.of("input.txt"));
            List<int[]> res = new ArrayList<>();

            int id = 0;
            for (String line : lines) {
                String[] coords = line.split(",");
                int[] tmp = {id, Integer.valueOf(coords[0]), Integer.valueOf(coords[1]), Integer.valueOf(coords[2]), id++};
                res.add(tmp);
            }

            return res;
        } catch (IOException e) {
            return null;
        }
    }

    static Pair<Integer, Integer> getNearestPair(List<int[]> coords, List<Pair<Integer, Integer>> alreadyConnected) {

        double nearest = Double.MAX_VALUE;
        Pair<Integer, Integer> res = new Pair<>(-1, -1);

        for (int[] coordA : coords) {
            for (int[] coordB : coords) {
                if (coordA[0] != coordB[0] && distance(coordA, coordB) < nearest && !alreadyConnected(coordA[0], coordB[0], alreadyConnected)) {
                    nearest = distance(coordA, coordB);
                    res = new Pair<>(coordA[0], coordB[0]);
                }
            }
        }
    
        return res;
    }

    static boolean alreadyConnected(int idA, int idB, List<Pair<Integer, Integer>> alreadyConnected) {
        return alreadyConnected.stream().anyMatch(p -> {
            return (p.a == idA && p.b == idB) || (p.a == idB && p.b == idA);
        });
    }

    static void problem1(){
        List<Pair<Integer, Integer>> alreadyConnected = new ArrayList<>();
        List<int[]> coords = getLinesFromInput();

        for (int i = 0; i < 1000; i++) {
            Pair<Integer, Integer> nearest = getNearestPair(coords, alreadyConnected);
            alreadyConnected.add(new Pair<>(nearest.a, nearest.b));

            int circuitA = coords.get(nearest.a)[4];
            int circuitB = coords.get(nearest.b)[4];

            // Merge Circuit
            if (circuitA != circuitB) {
                for (int[] tmp : coords) {
                    if (tmp[4] == circuitB) {
                        tmp[4] = circuitA;
                    }
                }
            }
        }

        System.out.println("There are "+ coords.stream().map(c -> c[4]).distinct().count() + " distinct circuits");

        Map<Integer, Integer> amounts = new HashMap<>();

        for (int[] coord : coords) amounts.put(coord[4], amounts.getOrDefault(coord[4], 0) + 1);

        List<Integer> biggestThree = amounts.values().stream().sorted().toList().reversed().stream().limit(3).toList();
        System.out.println(biggestThree.toString());
        System.out.println(biggestThree.stream().reduce(1, (a,b) -> a*b));
    }

    static void debugCoords(int[] coord) {
        System.out.println("[" + coord[0] + ", " + coord[1] + ", " + coord[2] + ", " + coord[3]  + ", " + coord[4] + "]");
    }

    static boolean sameCircuit(List<int[]> coords) {
        return !coords.stream().anyMatch(c -> c[4] != coords.getFirst()[4]);
    }

    static void problem2() {
        List<Pair<Integer, Integer>> alreadyConnected = new ArrayList<>();
        List<int[]> coords = getLinesFromInput();

        while (true) {
            Pair<Integer, Integer> nearest = getNearestPair(coords, alreadyConnected);
            alreadyConnected.add(new Pair<>(nearest.a, nearest.b));

            int circuitA = coords.get(nearest.a)[4];
            int circuitB = coords.get(nearest.b)[4];

            // Merge Circuit
            if (circuitA != circuitB) {
                for (int[] tmp : coords) {
                    if (tmp[4] == circuitB) {
                        tmp[4] = circuitA;
                    }
                }
            }

            if (sameCircuit(coords)) {
                System.out.println("Koordinaten " + nearest.a + " ("+ coords.get(nearest.a)[1] +")"+ " und " + nearest.b + " (" +coords.get(nearest.b)[1] +") schließen den kreis");
                System.out.println(coords.get(nearest.a)[1] * coords.get(nearest.b)[1]);
                return;
            }
        }
    }
}