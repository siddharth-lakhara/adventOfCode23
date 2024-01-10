package Solutions.Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {

    private record MapNode(String left, String right) {
    }

    private String sequence;
    private HashMap<String, MapNode> map;
    private ArrayList<String> startingNodes;

    public void solvePart1() {
        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day8/part1"));
            inStream = new Scanner(br);

            String sequence = inStream.nextLine();
            inStream.nextLine(); // skip empty line
            HashMap<String, MapNode> map = parseMap(inStream, false);

            this.sequence = sequence;
            this.map = map;

            long steps = countSteps(false);
            System.out.println(steps);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    public void solvePart2() {
        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day8/part1"));
            inStream = new Scanner(br);

            String sequence = inStream.nextLine();
            inStream.nextLine(); // skip empty line
            HashMap<String, MapNode> map = parseMap(inStream, true);

            this.sequence = sequence;
            this.map = map;

            long steps = countSteps(true);
            System.out.println(steps);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    private long countStepsSimultaneously() {
        ArrayList<Long> allStepsCount = new ArrayList<>(startingNodes.size());
        for (String node: startingNodes) {
            long answer = countSteps(0, node);
            allStepsCount.add(
                    answer
            );
            System.out.println("Found 1 answer: " + answer);
        }
        return findLCM(allStepsCount);
    }

    private static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    private static long findLCM(ArrayList<Long> numbers) {
        long lcm = numbers.get(0);
        for (int idx = 1; idx < numbers.size(); idx++) {
            long currentNumber = numbers.get(idx);
            lcm = (lcm * currentNumber) / gcd(lcm, currentNumber);
        }
        return lcm;
    }

    private long countSteps(long initialStepsCount, String startingNode) {
        long stepCount = initialStepsCount;
        String currentNode = startingNode;
        for (char s: sequence.toCharArray()) {
            currentNode = s == 'L' ?
                    map.get(currentNode).left()
                    : map.get(currentNode).right();
            stepCount++;

            if (currentNode.endsWith("Z")) {
                break;
            }
        }

//        Loop finished, found destination
        if (currentNode.endsWith("Z")) {
            return stepCount;
        }

//        Loop finished, but not reached destination
//        continue looking from the start of sequence
        return countSteps(stepCount, currentNode);
    }

    private long countSteps(boolean shouldCountSimultaneously) {
//        modified for part2
        if (shouldCountSimultaneously) {
            return countStepsSimultaneously();
        }
        return countSteps(0, "AAA");
    }

    private final static Pattern mapLinePattern = Pattern.compile("\\w+");

    private HashMap<String, MapNode> parseMap(Scanner inStream, boolean shouldCollectStartingNodes) {
        HashMap<String, MapNode> map = new HashMap<>();
        ArrayList<String> startingNodes = new ArrayList<>(6);

        while (inStream.hasNext()) {
            String line = inStream.nextLine();
            Matcher mapLineMatcher = mapLinePattern.matcher(line);
            ArrayList<String> nodesStr = new ArrayList<>(3);
            while (mapLineMatcher.find()) {
                nodesStr.add(mapLineMatcher.group());
            }
            map.put(nodesStr.get(0), new MapNode(nodesStr.get(1), nodesStr.get(2)));

            //        modified for part2
            if (shouldCollectStartingNodes && nodesStr.get(0).endsWith("A")) {
                startingNodes.add(nodesStr.get(0));
            }
        }

        //        modified for part2
        if (shouldCollectStartingNodes) {
            startingNodes.trimToSize();
            this.startingNodes = startingNodes;
        }

        return map;
    }
}

