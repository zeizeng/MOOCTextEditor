package neil.personal.textgen;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    // The list of words with their next words
    private List<ListNode> wordList;

    // The starting "word"
    private String starter;

    // The random number generator
    private Random rnGenerator;

    public MarkovTextGeneratorLoL(Random generator) {
        wordList = new LinkedList<>();
        starter = "";
        rnGenerator = generator;
    }


    /**
     * Train the generator by adding the sourceText
     */
    @Override
    public void train(String sourceText) {
        Map<String, ListNode> wordNodes = new HashMap<>();
        String[] words = sourceText.split("[^a-zA-Z']+");
        String startWord = "";
        String lastWord = "";
        for (String word : words) {
            if (wordNodes.isEmpty()) {
                wordNodes.put(word, new ListNode(word));
                lastWord = word;
                startWord = word;
                continue;
            }
            if (!wordNodes.containsKey(word))
                wordNodes.put(word, new ListNode(word));
            if (!lastWord.equals(""))
                wordNodes.get(lastWord).addNextWord(word);
            lastWord = word;
        }
        wordNodes.get(lastWord).addNextWord(startWord);
        for (Map.Entry<String, ListNode> nodeEntry : wordNodes.entrySet()) {
            wordList.add(nodeEntry.getValue());
        }

    }

    /**
     * Generate the number of words requested.
     */
    @Override
    public String generateText(int numWords) {
        String current = wordList.get(0).getWord();
        String output = current;
        for (int index = 1; index < numWords; index++) {
            for (ListNode listNode : wordList) {
                if (listNode.getWord().equals(current)) {
                    current = listNode.getRandomNextWord(rnGenerator);
                    output = output + " " + current;
                    break;
                }
            }
        }
        return output;
    }


    // Can be helpful for debugging
    @Override
    public String toString() {
        String toReturn = "";
        for (ListNode n : wordList) {
            toReturn += n.toString();
        }
        return toReturn;
    }

    /**
     * Retrain the generator from scratch on the source text
     */
    @Override
    public void retrain(String sourceText) {
        wordList.clear();
        train(sourceText);
    }



    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     *
     * @param args
     */
    public static void main(String[] args) {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
        String textString2 = "You say yes, I say no, " +
                "You say stop, and I say go, go, go, " +
                "Oh no. You say goodbye and I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "I say high, you say low, " +
                "You say why, and I say I don't know. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "Why, why, why, why, why, why, " +
                "Do you say goodbye. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "You say yes, I say no, " +
                "You say stop and I say go, go, go. " +
                "Oh, oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }

}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word) {
        this.word = word;
        nextWords = new LinkedList<String>();
    }

    public String getWord() {
        return word;
    }

    public void addNextWord(String nextWord) {
        if (!nextWords.contains(nextWord))
            nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator) {
        if (nextWords.size() == 1)
            return nextWords.get(0);
        int current = generator.nextInt(nextWords.size());
        return nextWords.get(current);
    }

    public String toString() {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}


