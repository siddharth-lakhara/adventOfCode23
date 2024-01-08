package Solutions.Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    private static class NumberPos {
        public int start;
        public int end;

        public int value;

        public NumberPos(int start, int end, int value) {
            this.start = start;
            this.end = end;
            this.value = value;
        }
    }


    private final Pattern numberRegex = Pattern.compile("[\\d]+");
    private final Pattern symbolRegex = Pattern.compile("[^\\d.]");
    private final Pattern starSymbolRegex = Pattern.compile("[*]");

//    solve using regex
    public void solveExample() {
        Scanner inStream = null;

        HashMap<Integer, ArrayList<NumberPos>> numbersMap = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> symbolsMap = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day3_example"));
            inStream = new Scanner(br);
            int lineCount = 0;
            while (inStream.hasNextLine()) {
                String line = inStream.nextLine();
                Matcher numberMatcher = numberRegex.matcher(line);
                Matcher symbolMatcher = symbolRegex.matcher(line);

                fillNumbersInLine(numbersMap, lineCount, numberMatcher);
                fillSymbolsInLine(symbolsMap, lineCount, symbolMatcher);

                lineCount++;
            }

            int answer = 0;
            for (int symbolLineNumber: symbolsMap.keySet()) {
                ArrayList<Integer> symbolsPositions = symbolsMap.get(symbolLineNumber);
                for (int symbolPos: symbolsPositions) {
                    answer += sumPartsInLine(symbolPos, numbersMap.get(symbolLineNumber-1));
                    answer += sumPartsInLine(symbolPos, numbersMap.get(symbolLineNumber));
                    answer += sumPartsInLine(symbolPos, numbersMap.get(symbolLineNumber+1));
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

//    regex giving wrong answer so drop it
//    Now find number and search a symbol around that number
//    Apparently regex either counting some numbers twice or counting non parts
    public void solvePart1() {
        Scanner inStream = null;

        HashMap<Integer, ArrayList<NumberPos>> numbersMap = new HashMap<>();
        HashMap<Integer, String> linesMap = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day3_part1"));
            inStream = new Scanner(br);
            int lineCount = 0;
            while (inStream.hasNextLine()) {
                String line = inStream.nextLine();
                linesMap.put(lineCount, line);

                Matcher numberMatcher = numberRegex.matcher(line);
                fillNumbersInLine(numbersMap, lineCount, numberMatcher);

                lineCount++;
            }

            int answer = 0;
            for (int lineNumber: numbersMap.keySet()) {
                ArrayList<NumberPos> numberPositions = numbersMap.get(lineNumber);
                for (NumberPos numPos: numberPositions) {
                    if (hasSymbolAroundNumber(numPos, linesMap, lineNumber)) {
                        answer += numPos.value;
                    }
                }
            }

            System.out.println("Answer: " + answer);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    public void solvePart2() {
        Scanner inStream = null;

        HashMap<Integer, ArrayList<NumberPos>> numbersMap = new HashMap<>();
        HashMap<Integer, String> linesMap = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day3_part1"));
            inStream = new Scanner(br);
            int lineCount = 0;
            while (inStream.hasNextLine()) {
                String line = inStream.nextLine();
                linesMap.put(lineCount, line);

                Matcher numberMatcher = numberRegex.matcher(line);
                fillNumbersInLine(numbersMap, lineCount, numberMatcher);

                lineCount++;
            }

            HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> gearInfo = new HashMap<>();
            for (int lineNumber: numbersMap.keySet()) {
                ArrayList<NumberPos> numberPositions = numbersMap.get(lineNumber);
                for (NumberPos numPos: numberPositions) {
                    updateGearInfo(numPos, linesMap, lineNumber, gearInfo);
                }
            }

            long answer = calculateGearRatio(gearInfo);

            System.out.println("Answer: " + answer);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    private long calculateGearRatio(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> gearInfo) {
        long sum = 0;
        for (int rowIdx: gearInfo.keySet()) {
            HashMap<Integer, ArrayList<Integer>> row = gearInfo.get(rowIdx);
            for (int col: row.keySet()) {
                if (row.get(col).size() == 2) {
                    long ratio = 1;
                    for (int r: row.get(col)) {
                        ratio *= r;
                    }
                    sum += ratio;
                }

            }
        }

        return sum;
    }

    private void updateGearInfo(
            NumberPos numPos, HashMap<Integer, String> linesMap, int lineNumber, HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> gearInfo
    ) {
        final int LINE_LIMIT = linesMap.get(0).length();
//         check previous line
        if (lineNumber > 0) {
            int colPos = findStarSymbolPos(linesMap.get(lineNumber-1), numPos, LINE_LIMIT);
            if (colPos != -1) {
                if (!gearInfo.containsKey(lineNumber-1)) {
                    gearInfo.put(lineNumber-1, new HashMap<>());
                }
                if (!gearInfo.get(lineNumber-1).containsKey(colPos)) {
                    gearInfo.get((lineNumber-1)).put(colPos, new ArrayList<>());
                }

                gearInfo.get((lineNumber-1))
                        .get(colPos)
                        .add(numPos.value);
            }
        }

//        check current line
        int colPos = findStarSymbolPos(linesMap.get(lineNumber), numPos, LINE_LIMIT);
        if (colPos != -1) {
            if (!gearInfo.containsKey(lineNumber)) {
                gearInfo.put(lineNumber, new HashMap<>());
            }
            if (!gearInfo.get(lineNumber).containsKey(colPos)) {
                gearInfo.get((lineNumber)).put(colPos, new ArrayList<>());
            }

            gearInfo.get((lineNumber))
                    .get(colPos)
                    .add(numPos.value);
        }

//        check next line
        if (lineNumber < linesMap.keySet().size()-1) {
            int colPosNextLine = findStarSymbolPos(linesMap.get(lineNumber+1), numPos, LINE_LIMIT);
            if (colPosNextLine != -1) {
                if (!gearInfo.containsKey(lineNumber+1)) {
                    gearInfo.put(lineNumber+1, new HashMap<>());
                }
                if (!gearInfo.get(lineNumber+1).containsKey(colPosNextLine)) {
                    gearInfo.get((lineNumber+1)).put(colPosNextLine, new ArrayList<>());
                }

                gearInfo.get((lineNumber+1))
                        .get(colPosNextLine)
                        .add(numPos.value);
            }
        }
    }

    private int findStarSymbolPos(String line, NumberPos numberPos, int LINE_LIMIT) {
        int start = Math.max(numberPos.start-1, 0);
        int end = Math.min(numberPos.end+1, LINE_LIMIT);
        String subStr = line.substring(start, end);
        Matcher starMatcher = starSymbolRegex.matcher(subStr);
//        TODO: what if multiple stars around the same number?
        if (starMatcher.find()) {
            return start + starMatcher.start();
        } else {
            return -1;
        }
    }

    private boolean hasSymbolAroundNumber(NumberPos numPos, HashMap<Integer, String> linesMap, int lineNumber) {
        final int LINE_LIMIT = linesMap.get(0).length();
//         check previous line
        if (lineNumber > 0) {
            if (hasSymbol(linesMap.get(lineNumber-1), numPos, LINE_LIMIT)) {
                return true;
            }
        }

//        check current line
        if (hasSymbol(linesMap.get(lineNumber), numPos, LINE_LIMIT)) {
            return true;
        }

//        check next line
        if (lineNumber < linesMap.keySet().size()-1) {
            if (hasSymbol(linesMap.get(lineNumber+1), numPos, LINE_LIMIT)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasSymbol(String line, NumberPos numberPos, int LINE_LIMIT) {
        int start = Math.max(numberPos.start-1, 0);
        int end = Math.min(numberPos.end+1, LINE_LIMIT);
        String subStr = line.substring(start, end);
        Matcher symbolMatcher = symbolRegex.matcher(subStr);
        return symbolMatcher.find();
    }

    private int sumPartsInLine(int symbolPos, ArrayList<NumberPos> numberPositions) {
        int answer = 0;

        if (numberPositions != null) {
            for (int currIdx = 0; currIdx<numberPositions.size(); currIdx++) {
                NumberPos numberPos = numberPositions.get(currIdx);
                if (symbolPos >= numberPos.start-1 && symbolPos <= numberPos.end+1) {
                    answer += numberPos.value;
                    numberPositions.remove(currIdx);
                    currIdx--;
                }

                if (symbolPos < numberPos.start) {
                    break;
                }
            }
        }
        return answer;
    }

    private void fillNumbersInLine(HashMap<Integer, ArrayList<NumberPos>> numbersMap, int lineCount, Matcher numberMatcher) {
        while (numberMatcher.find()) {
            int startIdx = numberMatcher.start();
            int endIdx = numberMatcher.end();
            int value = Integer.parseInt(numberMatcher.group());

            if (!numbersMap.containsKey(lineCount)) {
                numbersMap.put(lineCount, new ArrayList<>());
            }
            numbersMap.get(lineCount).add(new NumberPos(startIdx, endIdx, value));
        }

    }

    private void fillSymbolsInLine(HashMap<Integer, ArrayList<Integer>> symbolsMap, int lineCount, Matcher symbolMatcher) {
        while (symbolMatcher.find()) {
            if (!symbolsMap.containsKey(lineCount)) {
                symbolsMap.put(lineCount, new ArrayList<>());
            }
            symbolsMap.get(lineCount).add(symbolMatcher.start());
        }
    }
}
