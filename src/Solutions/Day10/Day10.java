package Solutions.Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

    private final Pattern startPointRegex = Pattern.compile("[S]");

    public void solvePart1() {
        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/Day10/part1"));
            inStream = new Scanner(br);

            ArrayList<char[]> map = new ArrayList<>();
            MapCoordinates startPoint = null;
            int row = 0;
            while (inStream.hasNextLine()) {
                String line = inStream.nextLine();
                Matcher startPointMatcher = startPointRegex.matcher(line);
                if (startPointMatcher.find()) {
                    startPoint = new MapCoordinates(
                            row,
                            startPointMatcher.start()
                            );
                }
                map.add(line.toCharArray());
                row++;
            }

            findInitialDirection(map, startPoint);
            startPoint.updateCoordinates();
            long answer = (countSteps(map, startPoint))/2;
            System.out.println(answer);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    private final HashSet<Character> upValues = new HashSet<>(
            Set.of('|', 'F', '7')
    );
    private final HashSet<Character> downValues = new HashSet<>(
            Set.of('|', 'J', 'L')
    );
    private final HashSet<Character> rightValues = new HashSet<>(
            Set.of('-', 'L', 'F')
    );

    private boolean canGoUp(char point) {
        return upValues.contains(point);
    }
    private boolean canGoDown(char point) {
        return downValues.contains(point);
    }
    private boolean canGoRight(char point) {
        return rightValues.contains(point);
    }

    private void findInitialDirection(ArrayList<char[]> map, MapCoordinates startPoint) {
        if (startPoint.row > 0 && canGoUp(map.get(startPoint.row-1)[startPoint.col])) {
            startPoint.direction = MapCoordinates.Direction.UP;
            return ;
        }

        if (startPoint.row < map.size()-1 && canGoDown(map.get(startPoint.row+1)[startPoint.col])) {
            startPoint.direction = MapCoordinates.Direction.DOWN;
            return;
        }

        if (startPoint.col > 0 && canGoRight(map.get(startPoint.row)[startPoint.col+1])) {
            startPoint.direction = MapCoordinates.Direction.RIGHT;
            return;
        }

//      since a possible solution is always available, no need to check
        startPoint.direction = MapCoordinates.Direction.LEFT;
    }

    private long countSteps(ArrayList<char[]> map, MapCoordinates currPointCoordinates) {
        char currPoint = map.get(currPointCoordinates.row)[currPointCoordinates.col];
        if (currPoint == 'S') {
            return 1;
        }

        //        find next step direction
        switch(currPoint) {
            case 'F':
                currPointCoordinates.direction = currPointCoordinates.direction == MapCoordinates.Direction.LEFT ?
                    MapCoordinates.Direction.DOWN : MapCoordinates.Direction.RIGHT;
                break;
            case 'L':
                currPointCoordinates.direction = currPointCoordinates.direction == MapCoordinates.Direction.LEFT ?
                        MapCoordinates.Direction.UP : MapCoordinates.Direction.RIGHT;
                break;
            case 'J':
                currPointCoordinates.direction = currPointCoordinates.direction == MapCoordinates.Direction.RIGHT ?
                        MapCoordinates.Direction.UP : MapCoordinates.Direction.LEFT;
                break;
            case '7':
                currPointCoordinates.direction = currPointCoordinates.direction == MapCoordinates.Direction.RIGHT ?
                        MapCoordinates.Direction.DOWN : MapCoordinates.Direction.LEFT;
                break;
            case '-':
            case '|':
            default:
                break;
        }

        currPointCoordinates.updateCoordinates();
        return 1 + countSteps(map, currPointCoordinates);
    }
}

