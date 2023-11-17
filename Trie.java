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

    public void addFromFile(String filePath, char firstLetter) {
        clear(); // Clear the Trie before adding words for a new first letter
        loadFile(filePath, firstLetter);
    }

    private void loadFile(String filePath, char firstLetter) {
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

    if (node.isEndOfWord && calculateLevenshteinDistance(currentWord.toLowerCase(), inputWord.toLowerCase()) <= maxDistance) {
        char firstLetter = currentWord.charAt(0);
        if ((firstLetter >= 'a' && firstLetter <= 'i') || (firstLetter >= 'j' && firstLetter <= 'r') || (firstLetter >= 's' && firstLetter <= 'z')) {
            similarWords.add(currentWord);
        }
    }

    for (char c : node.children.keySet()) {
        searchSimilarWords(node.children.get(c), inputWord, currentWord + c, maxDistance, similarWords);
    }
}


    private int calculateLevenshteinDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[2][n + 1];

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            dp[i % 2][0] = i;

            for (int j = 1; j <= n; j++) {
                int cost = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;
                dp[i % 2][j] = Math.min(
                        Math.min(dp[(i - 1) % 2][j] + 1, dp[i % 2][j - 1] + 1),
                        dp[(i - 1) % 2][j - 1] + cost
                );
            }
        }

        return dp[m % 2][n];
    }

    private void clear() {
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
}
