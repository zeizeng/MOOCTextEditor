package neil.personal.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Description
 *
 * @author weizengflyinsky@gmail.com
 * @version 1.0
 * @date 2019/3/511:17
 */
public class Test {

    public static void main(String[] args) {
        BufferedReader reader = null;
        try {
            String nextWord;
            reader = new BufferedReader(new FileReader("src/data/dict.txt"));
            while ((nextWord = reader.readLine()) != null) {
                System.out.println(nextWord);
            }
        } catch (IOException e) {
            System.err.println("Problem loading dictionary file: " );
            e.printStackTrace();
        }
    }
}
