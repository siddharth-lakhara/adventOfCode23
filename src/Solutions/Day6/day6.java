package Solutions.Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day6 {

    private final static Pattern NUMBERS_REGEX = Pattern.compile("[\\d]+");
    public void solvePart1() {
        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day6_part1"));
            inStream = new Scanner(br);

            String timesString = inStream.nextLine().split(": ")[1];
            String distThresholdString = inStream.nextLine().split(": ")[1];

            ArrayList<Integer> times = parseNumbersFromInput(timesString);
            ArrayList<Integer> distThreshold = parseNumbersFromInput(distThresholdString);

            long answer = 1;
            for (int idx = 0; idx<times.size(); idx++) {
                long numWays = calculateWays((long) times.get(idx), (long) distThreshold.get(idx));
                System.out.println("Num ways for idx " + (idx+1) + " = " + numWays);
                if (numWays != 0) {
                    answer *= numWays;
                }
            }
            System.out.println("Answer: " + answer);
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
                    new FileReader("resources/day6_part1"));
            inStream = new Scanner(br);

            String timesString = inStream.nextLine().split(": ")[1];
            String distThresholdString = inStream.nextLine().split(": ")[1];

            long times = parseCombinedNumbersFromInput(timesString);
            long distThreshold = parseCombinedNumbersFromInput(distThresholdString);

            long numWays = calculateWays(times, distThreshold);
            System.out.println("Answer: " + numWays);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    private long parseCombinedNumbersFromInput(String numbersString) {
        StringBuilder numbersBuffer = new StringBuilder();
        Matcher numbersMatcher = NUMBERS_REGEX.matcher(numbersString);
        while (numbersMatcher.find()) {
            numbersBuffer.append(numbersMatcher.group());
        }

        return Long.parseLong(numbersBuffer.toString());
    }

//    O(N) Solution
//    private int calculateWays(int totalTime, int distThreshold) {
//        int count = 0;
//        for (int i=0; i<totalTime; i++) {
//            if (i*(totalTime-i) > distThreshold) {
//                count += 1;
//            }
//        }
//        return count;
//    }

//    O(1) solution
//
//    APPROACH:
//    TIMES ARRAY DIFF IS 2*N WHERE N IS THE TERM FROM MID POINT
//    SO SIMPLY COUNT THE NUMBER OF TERMS GREATER THAN THE THRESHOLD

    private long calculateWays(long totalTime, long distThreshold) {
        long halfTime = totalTime/2;
        long distMax = halfTime * (totalTime-halfTime);
        if (distMax < distThreshold) {
            return 0;
        }

        boolean isEven = (totalTime & 1) == 0;
        if (isEven) {
            distMax++;
        }

        long D = (long) Math.sqrt(1+4*(distMax-distThreshold));
        long root = (D-1)/2;
        long answer = (root+1)*2;

        return isEven ? answer-1 : answer;
    }

    private ArrayList<Integer> parseNumbersFromInput(String numbersString) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Matcher numbersMatcher = NUMBERS_REGEX.matcher(numbersString);
        while (numbersMatcher.find()) {
            numbers.add( Integer.parseInt(numbersMatcher.group()) );
        }

        return numbers;
    }
}

