package Solutions.Day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day9 {

    public void solvePart1() {
        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day9/part1"));
            inStream = new Scanner(br);

            long answer = 0;
            while (inStream.hasNextLine()) {
                String line = inStream.nextLine();
                List<Long> numbers = Arrays.stream(line.split(" "))
                        .map(Long::parseLong)
                        .toList();
                Long nextTerm = findNextTerm(numbers, false);
                answer += nextTerm;
            }
            System.out.println(answer);
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
                    new FileReader("resources/day9/part1"));
            inStream = new Scanner(br);

            long answer = 0;
            while (inStream.hasNextLine()) {
                String line = inStream.nextLine();
                List<Long> numbers = Arrays.stream(line.split(" "))
                        .map(Long::parseLong)
                        .toList();
                Long nextTerm = findNextTerm(numbers, true);
                answer += nextTerm;
            }
            System.out.println(answer);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    private Long findNextTerm(List<Long> numbers, boolean isReverseFind) {
        List<Long> diffSequence = new ArrayList<>(numbers.size()-1);
        long prevDiff = numbers.get(1) - numbers.get(0);
        int sameDiffCount = 0;
        for (int idx = 1; idx < numbers.size(); idx++) {
            long currDiff = numbers.get(idx) - numbers.get(idx-1);
            diffSequence.add(currDiff);
            if (currDiff == prevDiff) {
                sameDiffCount++;
            } else {
                sameDiffCount = 0;
            }
        }

        long nextTerm;
        if (sameDiffCount == diffSequence.size()) {
            nextTerm = prevDiff;
        } else {
            nextTerm = findNextTerm(diffSequence, isReverseFind);
        }

        if (isReverseFind) {
            return numbers.get(0) - nextTerm;
        }
        return numbers.get(numbers.size()-1) + nextTerm;
    }
}

