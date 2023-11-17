import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
    char value;
    boolean isEndOfWord;
    Map<Character, Node> children = new HashMap<>();

    Node(char value) {
        this.value = value;
    }
}

public class Trie {
    private Node root;
    private int numOfWords;
    private boolean caseSensitive;

    public Trie(boolean caseSensitive) {
        root = new Node(' ');
        setNumberOfWords(0);
        setCaseSensitive(caseSensitive);
    }

    private void loadFile(String filePath, char firstLetter) {
        clear(); 
        boolean shouldLoad = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) {
                    continue; // Skip empty lines
                }

                char currentFirstLetter = getFirstLetter(trimmedLine);
                if (shouldLoad && currentFirstLetter != firstLetter) {
                    // Stop reading if the current word's initial letter is different
                    break;
                }

                if (currentFirstLetter == firstLetter) {
                    shouldLoad = true;
                    add(trimmedLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String word) {
        word = preprocessWord(word);

        Node current = root;
        for (char c : word.toCharArray()) {
            current.children.computeIfAbsent(c, Node::new);
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
        numOfWords++;
    }

    public List<String> searchSimilarWords(String inputWord, int maxDistance) {
        List<String> similarWords = new ArrayList<>();
        searchSimilarWords(root, inputWord, "", maxDistance, similarWords);
        return similarWords;
    }

    private void searchSimilarWords(Node node, String inputWord, String currentWord, int maxDistance, List<String> similarWords) {
        if (currentWord.length() > inputWord.length() + maxDistance) {
            return;
        }

        if (node.isEndOfWord && calculateLevenshteinDistance(currentWord, inputWord) <= maxDistance) {
            similarWords.add(currentWord);
        }

        for (char c : node.children.keySet()) {
            searchSimilarWords(node.children.get(c), inputWord, currentWord + c, maxDistance, similarWords);
        }
    }

    private int calculateLevenshteinDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(
                            dp[i - 1][j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1),
                            dp[i][j - 1] + 1,
                            dp[i - 1][j] + 1
                    );
                }
            }
        }

        return dp[word1.length()][word2.length()];
    }

    private int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public void clear() {
        root.children.clear();
        setNumberOfWords(0);
    }

    private char getFirstLetter(String word) {
        return word.isEmpty() ? ' ' : word.charAt(0);
    }

    private void setNumberOfWords(int words) {
        this.numOfWords = words;
    }

    private void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    private String preprocessWord(String word) {
        return caseSensitive ? word : word.toLowerCase();
    }

    private void dfs(Node node) {
        for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
            System.out.print("(" + entry.getKey() + ")->");
            dfs(entry.getValue());
        }
    }

    public void show() {
        System.out.println("");
        dfs(root);
    }

    public int getNumOfWords() {
        return numOfWords;
    }
}

