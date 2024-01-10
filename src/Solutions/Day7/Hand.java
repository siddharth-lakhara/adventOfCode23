package Solutions.Day7;

import java.util.Comparator;
import java.util.HashMap;

public class Hand implements Comparable<Hand>, Comparator<Hand> {
    public String value;
    public int rank;
    public long bid;

    private final static HashMap<Character, Integer> cardRankMap = new HashMap<>() {{
        put('A', 14);
        put('K', 13);
        put('Q', 12);
        put('J', 11);
        put('T', 10);
    }};

    private final static HashMap<Character, Integer> cardRankMapUpdated = new HashMap<>() {{
        put('A', 14);
        put('K', 13);
        put('Q', 12);
        put('J', 1);
        put('T', 10);
    }};

    public Hand(String value, int rank, int bid) {
        this.value = value;
        this.rank = rank;
        this.bid = bid;
    }

    private static int getCardRank(char card) {
        if (card-'0' <= 9) {
            return card-'0';
        }
        return day7.isPart2 ? cardRankMapUpdated.get(card) : cardRankMap.get(card);
    }

    public static int compareHands(Hand h1, Hand h2) {
        char[] h1Value = h1.value.toCharArray();
        char[] h2Value = h2.value.toCharArray();

        for (int idx = 0; idx<h1Value.length; idx++) {
            if (h1Value[idx] == h2Value[idx]) {
                continue;
            }
            return getCardRank(h1Value[idx]) - getCardRank(h2Value[idx]);
        }
        return 0;
    }

    @Override
    public int compare(Hand h1, Hand h2) {
        if (h1.rank == h2.rank) {
            return compareHands(h1, h2);
        }
        return h1.rank - h2.rank;
    }

    @Override
    public int compareTo(Hand otherHand) {
        if (this.rank == otherHand.rank) {
            return compareHands(this, otherHand);
        }
        return this.rank - otherHand.rank;
    }

    public enum HandRanks {
        FIVE_OF_KIND(6),
        FOUR_OF_KIND(5),
        FULL_HOUSE(4),
        THREE_OF_KIND(3),
        TWO_PAIR(2),
        ONE_PAIR(1),
        HIGH_CARD(0);

        private final int rank;
        HandRanks(int rank) {
            this.rank = rank;
        }

        public int getValue() {
            return this.rank;
        }
    }
}
