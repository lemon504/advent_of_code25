import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static char BLANK_TILE = '.';
    public static char GREEN_TILE = 'X';
    public static char RED_TILE = '#';
    
    public record Pair<A, B>(A a, B b) {}
    
    public static void main(String[] args) {
        problem2();
    }

    public static List<int[]> getLinesFromInput() {
        try {
            List<String> lines = Files.readAllLines(Path.of("input.txt"));
            List<int[]> res = new ArrayList<>();
            int id = 0;
            for (String line : lines) {
                String[] coords = line.split(",");
                int[] tmp = {id++, Integer.valueOf(coords[0]), Integer.valueOf(coords[1])};
                res.add(tmp);
            }

            return res;
        } catch (IOException e) {
            return null;
        }
    }

    static void problem1() {
        List<int[]> lines = getLinesFromInput();
        long biggest = Long.MIN_VALUE;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = i+1; j < lines.size(); j++) {
                long area = calcArea(lines.get(i), lines.get(j));
                if (area > biggest) biggest = area;
            }
        }

        System.out.println(biggest);
    }

    static long calcArea(int[] x, int[] y) {
        long width = Math.abs(x[1] - y[1]) + 1;
        long height = Math.abs(x[2] - y[2]) + 1;
        
        return width * height;
    }

    static void problem2() {
        List<int[]> lines = getLinesFromInput();
        long biggest = Long.MIN_VALUE;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = i+1; j < lines.size(); j++) {
                if (RectangleInBounds(lines.get(i), lines.get(j), lines)) {
                    long area = calcArea(lines.get(i), lines.get(j));
                    if (area > biggest) biggest = area;
                }
            }
        }

        System.out.println(biggest);
    }

    static boolean RectangleInBounds(int[] first, int[] second, List<int[]> coords) {
    int minX = Math.min(first[1], second[1]);
    int maxX = Math.max(first[1], second[1]);
    int minY = Math.min(first[2], second[2]);
    int maxY = Math.max(first[2], second[2]);

    for (int i = minX; i <= maxX; i++) {
        for (int j = minY; j <= maxY; j++) {
            int[] coord = {-1, i, j};
            if (!withinTiles(coord, coords)) return false;
        }
    }
    return true;
}

    static boolean checkBound(List<int[]> candidates, int[] coord, List<int[]> coords, boolean checkFirstCoord) {
        for (int[] bound : candidates) {
            int prev = (bound[0] - 1 < 0) ? coords.size() - 1 : bound[0] - 1;
            int next = (bound[0] + 1) % coords.size();

            int[] prevCoord = coords.get(prev);
            int[] nextCoord = coords.get(next);

            if (checkFirstCoord) {

                if (Math.min(prevCoord[2], bound[2]) <= coord[2] && coord[2] <= Math.max(prevCoord[2], bound[2])) return true;
                if (Math.min(nextCoord[2], bound[2]) <= coord[2] && coord[2] <= Math.max(nextCoord[2], bound[2])) return true;

            } else {

                if (Math.min(prevCoord[1], bound[1]) <= coord[1] && coord[1] <= Math.max(prevCoord[1], bound[1])) return true;
                if (Math.min(nextCoord[1], bound[1]) <= coord[1] && coord[1] <= Math.max(nextCoord[1], bound[1])) return true;

            }
        }
        return false;
    }

    static boolean withinTiles(int[] coord, List<int[]> coords) {
        //bound nach links (1. Koordinate <= coords x)
        List<int[]> leftBoundCoords = coords.stream().filter(c -> c[1] <= coord[1]).toList();
        boolean leftBound = checkBound(leftBoundCoords, coord, coords, true);

        //bound nach rechts (1. Koordinate >= coords x)
        List<int[]> rightBoundCoords = coords.stream().filter(c -> c[1] >= coord[1]).toList(); 
        boolean rightBound = checkBound(rightBoundCoords, coord, coords, true);


        //bound nach oben (2. Koordinate <= coords y)
        List<int[]> upperBoundCoords = coords.stream().filter(c -> c[2] <= coord[2]).toList();
        boolean upperBound = checkBound(upperBoundCoords, coord, coords, false);

        //bound nach unten (2. Koordinate >= coords y)
        List<int[]> lowerBoundCoords = coords.stream().filter(c -> c[2] >= coord[2]).toList();
        boolean lowerBound = checkBound(lowerBoundCoords, coord, coords, false);

        return leftBound && rightBound && lowerBound && upperBound;
    }
}