import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static Map<String, Trie> languageTrieMap = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java Server <A-I|J-R|S-Z>");
            System.exit(1);
        }

        loadTries();
        String fileName = args[0];

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor aguardando conexões para a faixa de letras: " + fileName);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado");

                handleClient(clientSocket, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket, String fileName) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            char firstLetter = reader.readLine().toUpperCase().charAt(0);
            System.out.println("Recebida a primeira letra da palavra: " + firstLetter);

            String inputWord;
            while ((inputWord = reader.readLine()) != null) {
                System.out.println("Palavra recebida: " + inputWord);

                String language = reader.readLine().toLowerCase();
                System.out.println("Idioma recebido: " + language);

                Trie languageTrie = languageTrieMap.get(language);
                if (languageTrie == null) {
                    System.err.println("Trie para o idioma " + language + " não encontrada.");
                    break;
                }

                List<String> similarWords = languageTrie.searchSimilarWords(inputWord, 2);
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

    private static void loadTries() {
        // Carregue os arquivos correspondentes a cada idioma
        for (String language : new String[]{"pt-br", "es"}) {
            for (String range : new String[]{"A-I", "J-R", "S-Z"}) {
                Trie trie = new Trie(true);
                String fileName = range + "_" + language + ".txt";
                loadWordsFromFile(fileName, trie);
                languageTrieMap.put(language, trie);
            }
        }
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

