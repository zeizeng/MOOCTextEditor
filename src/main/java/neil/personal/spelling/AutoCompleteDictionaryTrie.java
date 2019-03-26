package neil.personal.spelling;

import java.util.*;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 *
 * @author You
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

    private TrieNode root;
    private int size;


    public AutoCompleteDictionaryTrie() {
        root = new TrieNode();
    }


    /**
     * Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should convert the
     * string to all lower case before you insert it.
     *
     * This method adds a word by creating and linking the necessary trie nodes
     * into the trie, as described outlined in the videos for this week. It
     * should appropriately use existing nodes in the trie, only creating new
     * nodes when necessary. E.g. If the word "no" is already in the trie,
     * then adding the word "now" would add only one additional node
     * (for the 'w').
     *
     * @return true if the word was successfully added or false if it already exists
     * in the dictionary.
     */
    public boolean addWord(String word) {

        TrieNode curr = root;
        TrieNode next;
        boolean inserted = false;
        for (char c : word.toLowerCase().toCharArray()) {
            next = curr.getChild(c);
            if (next == null) {
                next = curr.insert(c);
                inserted = true;
            }
            curr = next;
        }
        if (!curr.endsWord()) {
            curr.setEndsWord(true);
            inserted = true;
        }
        if (inserted) size++;
        return inserted;
    }

    /**
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {

        return size;
    }


    /**
     * Returns whether the string is a word in the trie, using the algorithm
     * described in the videos for this week.
     */
    @Override
    public boolean isWord(String s) {
        TrieNode trieNode = root;
        for (char c : s.toLowerCase().toCharArray()) {
            TrieNode currentNode = trieNode.getChild(c);
            if (currentNode == null) {
                return false;
            }
            trieNode = trieNode.getChild(c);
        }
        return trieNode.endsWord();

    }

    /**
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions
     * of the prefix string. All legal completions must be valid words in the
     * dictionary. If the prefix itself is a valid word, it is included
     * in the list of returned words.
     *
     * The list of completions must contain
     * all of the shortest completions, but when there are ties, it may break
     * them in any order. For example, if there the prefix string is "ste" and
     * only the words "step", "stem", "stew", "steer" and "steep" are in the
     * dictionary, when the user asks for 4 completions, the list must include
     * "step", "stem" and "stew", but may include either the word
     * "steer" or "steep".
     *
     * If this string prefix is not in the trie, it returns an empty list.
     *
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */
    @Override
    public List<String> predictCompletions(String prefix, int numCompletions) {
        // TODO: Implement this method
        // This method should implement the following algorithm:
        // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
        //    empty list
        // 2. Once the stem is found, perform a breadth first search to generate completions
        //    using the following algorithm:
        //    Create a queue (LinkedList) and add the node that completes the stem to the back
        //       of the list.
        //    Create a list of completions to return (initially empty)
        //    While the queue is not empty and you don't have enough completions:
        //       remove the first Node from the queue
        //       If it is a word, add it to the completions list
        //       Add all of its child nodes to the back of the queue
        // Return the list of completions
        List<String> toReturn = new ArrayList<>(numCompletions);
        List<TrieNode> queen = new LinkedList<>();
        TrieNode trieNode = getNodeByPrefix(prefix);
        if (trieNode == null)
            return toReturn;
        queen.add(trieNode);
        int n = 0;
        TrieNode current;
        while (!queen.isEmpty() && n < numCompletions) {
            current = queen.remove(0);
            if (current.endsWord()) {
                toReturn.add(current.getText());
                n++;
            }
            for (char c : current.getValidNextCharacters()) {
                queen.add(current.getChild(c));
            }
        }

        return toReturn;
    }

    private TrieNode getNodeByPrefix(String prefix) {
        TrieNode current = root;
        TrieNode next;
        for (char c : prefix.toLowerCase().toCharArray()) {
            next = current.getChild(c);
            if (next == null)
                return null;
            current = next;
        }
        return current;
    }

    // For debugging
    public void printTree() {
        printNode(root);
    }

    /**
     * Do a pre-order traversal from this node down
     */
    public void printNode(TrieNode curr) {
        if (curr == null)
            return;

        System.out.println(curr.getText());

        TrieNode next = null;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }


}