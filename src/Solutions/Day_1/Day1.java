package Solutions.Day_1;

import Solutions.Utils.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Day1 {

    private String className;
    public Day1() {
        String[] classNames = this.getClass().getName().split("\\.");
        this.className = classNames[classNames.length-1];
    }
    public void solveSample() throws IOException {
        final String fileName = Constants.EXAMPLE_FILE  + this.className;
        BufferedReader br = new BufferedReader(
                new FileReader(fileName));
        Scanner inStream = new Scanner(br);

        int answer = 0;
        while (inStream.hasNextLine()) {
            String nextLine = inStream.nextLine();
            StringBuilder numberSb = new StringBuilder();
            for (int idx = 0; idx < nextLine.length(); idx++) {
                char c = nextLine.charAt(idx);
                int val = c - '0';
                if (val >= 0 && val <= 9) {
                    numberSb.append(val);
                }
            }
            String numberStr = ("" + numberSb.charAt(0) + numberSb.charAt(numberSb.length()-1));
            int number = Integer.parseInt(numberStr);
            answer += number;
        }
        inStream.close();
        System.out.println("Example answer: " + answer);
    }

    public void solvePart1() throws IOException {
        final String fileName = Constants.PART1  + this.className;
        BufferedReader br = new BufferedReader(
                new FileReader(fileName));
        Scanner inStream = new Scanner(br);

        int answer = 0;
        while (inStream.hasNextLine()) {
            String nextLine = inStream.nextLine();
            StringBuilder numberSb = new StringBuilder();
            for (int idx = 0; idx < nextLine.length(); idx++) {
                char c = nextLine.charAt(idx);
                int val = c - '0';
                if (val >= 0 && val <= 9) {
                    numberSb.append(val);
                }
            }
            String numberStr = ("" + numberSb.charAt(0) + numberSb.charAt(numberSb.length()-1));
            int number = Integer.parseInt(numberStr);
            answer += number;
        }
        inStream.close();
        System.out.println("Part1 answer: " + answer);
    }
//    public void solutionPart2();
}
