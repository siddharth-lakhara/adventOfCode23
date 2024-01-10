package Solutions.Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class day7 {

    public static boolean isPart2 = false;
    public void solvePart1() {
        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day7/day7_part1"));
            inStream = new Scanner(br);

            TreeSet<Hand> allHands = new TreeSet<>();

            while (inStream.hasNextLine()) {
                String[] line = inStream.nextLine().split(" ");
                String handValue = line[0];
                int bid = Integer.parseInt(line[1]);
                int rank = calculateHandRank(handValue);

                allHands.add(new Hand(handValue, rank, bid));
            }

            long answer = calculateTotalBids(allHands);
            System.out.println(answer);

        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    public void solvePart2() {
        isPart2 = true;

        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day7/day7_example"));
            inStream = new Scanner(br);

            TreeSet<Hand> allHands = new TreeSet<>();

            while (inStream.hasNextLine()) {
                String[] line = inStream.nextLine().split(" ");
                String handValue = line[0];
                int bid = Integer.parseInt(line[1]);
                int rank = calculateHandRank(handValue);

                allHands.add(new Hand(handValue, rank, bid));
            }

            long answer = calculateTotalBids(allHands);
            System.out.println(answer);

        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    private long calculateTotalBids(TreeSet<Hand> allHands) {
        long sum = 0;
        long idx = 1;
        for (Hand hand: allHands) {
            sum += idx * hand.bid;
            idx++;
        }
        return sum;
    }

    private int calculateHandRank(String handValue) {
        HashMap<Character, Integer> counter = new HashMap<>();
        for (char h: handValue.toCharArray()) {
            counter.put(
                    h,
                    counter.getOrDefault(h, 0) + 1
            );
        }

        int rankCreds = 0;
        if (isPart2) {
//            replace 'J' with best approach
            if (counter.containsKey('J') && counter.get('J') != 5) {
                rankCreds += counter.get('J');
                counter.remove('J');
            }
        }

        ArrayList<Integer> pairs = new ArrayList<>(counter.values());
        pairs.sort(Collections.reverseOrder());
        pairs.set(0, pairs.get(0) + rankCreds);

        int rank = switch (pairs.get(0)) {
            case 5 -> Hand.HandRanks.FIVE_OF_KIND.getValue();
            case 4 -> Hand.HandRanks.FOUR_OF_KIND.getValue();
            case 3 -> pairs.get(1) == 2 ?
                    Hand.HandRanks.FULL_HOUSE.getValue() :
                    Hand.HandRanks.THREE_OF_KIND.getValue();
            case 2 -> pairs.get(1) == 2 ?
                    Hand.HandRanks.TWO_PAIR.getValue() :
                    Hand.HandRanks.ONE_PAIR.getValue();
            default -> Hand.HandRanks.HIGH_CARD.getValue();
        };

        return rank;
    }


}

