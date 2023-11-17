import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java Server <A-I|J-R|S-Z>");
            System.exit(1);
        }

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor aguardando conexões para a faixa de letras: " + args[0]);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado");

                handleClient(clientSocket, args[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket, String range) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            char firstLetter = range.toUpperCase().charAt(0);
            System.out.println("Recebida a faixa de letras: " + firstLetter);

            Trie portugueseTrie = loadTrieForLanguageAndRange("pt-br", range);
            Trie spanishTrie = loadTrieForLanguageAndRange("es", range);

            String inputWord;
            while ((inputWord = reader.readLine()) != null) {
                System.out.println("Palavra recebida: " + inputWord);

                String language = reader.readLine().toLowerCase();
                System.out.println("Idioma recebido: " + language);

                Trie currentTrie = (language.equals("pt-br")) ? portugueseTrie : spanishTrie;

                if (currentTrie == null) {
                    System.err.println("Trie para o idioma " + language + " e a faixa de letras " + firstLetter + " não encontrada.");
                    break;
                }

                List<String> similarWords = currentTrie.searchSimilarWords(inputWord, 2);
                System.out.println("Palavras semelhantes encontradas: " + similarWords);

                for (String similarWord : similarWords) {
                    writer.println(similarWord);
                }

                if ("exit".equalsIgnoreCase(inputWord)) {
                    break;
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Trie loadTrieForLanguageAndRange(String language, String range) {
        Trie trie = new Trie(true);

        String fileName = range + "_" + language + ".txt";
        loadWordsFromFile(fileName, trie);

        return trie.getNumOfWords() > 0 ? trie : null;
    }


    private static void loadWordsFromFile(String fileName, Trie trie) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                trie.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
