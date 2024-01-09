package Solutions.Example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Example {

    public void solveExample() {
        Scanner inStream = null;
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("resources/day3_example"));
            inStream = new Scanner(br);

            while (inStream.hasNextLine()) {
                String line = inStream.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }
}

