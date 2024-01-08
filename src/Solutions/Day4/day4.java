package Solutions.Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class day4 {
    public void solveExample() {
        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day4_part1"));
            inStream = new Scanner(br);

            int sum = 0;
            while (inStream.hasNextLine()) {
                String line = inStream.nextLine();
                String numbers = line.split(": ")[1];
                String[] numbersSplit = numbers.split(" \\| ");
                CardsSplit cardsSplit = parseCards(numbersSplit);
                int duplicates = countWinningNumbers(cardsSplit.winningNumbers(), cardsSplit.cardNumbers());

                if (duplicates > 0)
                    sum += (int) Math.pow(2, duplicates-1);
            }
            System.out.println(sum);
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
                    new FileReader("resources/day4_part1"));
            inStream = new Scanner(br);

            HashMap<Integer, Integer> cardsCounter = new HashMap<>();
            while (inStream.hasNextLine()) {
                String line = inStream.nextLine();
                String[] linesSplit = line.split(": ");
                int cardNum = Integer.parseInt(linesSplit[0].split("\\s+")[1]);
                String numbers = linesSplit[1];
                String[] numbersSplit = numbers.split(" \\| ");
                updateCardsCounter(cardsCounter, numbersSplit, cardNum);
            }
            long sum = countCards(cardsCounter);
            System.out.println(sum);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    private long countCards(HashMap<Integer, Integer> cardsCounter) {
        long sum = 0;
        for (int cardCount: cardsCounter.values()) {
            sum += cardCount;
        }
        return sum;
    }

    private void updateCardsCounter(HashMap<Integer, Integer> cardsCounter, String[] numbersSplit, int currentCardNum) {
//        add original card to counter
        cardsCounter.put(
                currentCardNum,
                cardsCounter.getOrDefault(currentCardNum, 0) + 1
        );

        CardsSplit cardsSplit = parseCards(numbersSplit);
        int winningNumbers = countWinningNumbers(cardsSplit.winningNumbers(), cardsSplit.cardNumbers());
        while (winningNumbers > 0) {
            int key = currentCardNum+winningNumbers;
            int count = cardsCounter.getOrDefault(key, 0);
            cardsCounter.put(key, count+cardsCounter.get(currentCardNum));
            winningNumbers--;
        }
    }

    private CardsSplit parseCards(String[] numbersSplit) {
        String winningNumbersStr = numbersSplit[0];
        String cardNumbersStr = numbersSplit[1];
        Set<Integer> winningNumbers = parseNumbers(winningNumbersStr);
        Set<Integer> cardNumbers = parseNumbers(cardNumbersStr);
        return new CardsSplit(winningNumbers, cardNumbers);
    }

    private record CardsSplit(Set<Integer> winningNumbers, Set<Integer> cardNumbers) {
    }

    private int countWinningNumbers(Set<Integer> winningNumbers, Set<Integer> cardNumbers) {
        int count = 0;
        for (int winNumber: winningNumbers) {
            if (cardNumbers.contains(winNumber)) {
                count++;
            }
        }
        return count;
    }

    private Set<Integer> parseNumbers(String numbersStr) {
        return Arrays.stream(numbersStr.split("\\s+"))
                .filter((String val) -> !val.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toSet())
        ;
    }
}


