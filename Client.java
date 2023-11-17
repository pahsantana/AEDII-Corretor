import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            String userInputWord;

            while (true) {
                System.out.print("Digite uma palavra (ou 'exit' para sair): ");
                userInputWord = consoleReader.readLine();

                if ("exit".equalsIgnoreCase(userInputWord)) {
                    break;
                }

                char firstLetter = userInputWord.toUpperCase().charAt(0);
                int port = getPortForFirstLetter(firstLetter);

                try (Socket socket = new Socket("localhost", port)) {
                    System.out.println("Conectado ao servidor na porta: " + port);

                    BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                    // Enviar a palavra do usuário ao servidor
                    writer.println(userInputWord);

                    // Enviar o idioma ao servidor
                    String language = getLanguage(consoleReader);
                    writer.println(language);

                    String serverResponse;
                    while ((serverResponse = serverReader.readLine()) != null) {
                        System.out.println("Palavra correspondente do servidor: " + serverResponse);

                        if ("exit".equalsIgnoreCase(serverResponse)) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao se conectar ao servidor na porta: " + port);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getPortForFirstLetter(char firstLetter) {
        switch (firstLetter) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
                return 12345;
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
                return 12346;
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return 12347;
            default:
                throw new IllegalArgumentException("Letra inválida: " + firstLetter);
        }
    }

    private static String getLanguage(BufferedReader consoleReader) throws IOException {
        System.out.print("Escolha o idioma (pt-br ou es): ");
        String language = consoleReader.readLine().toLowerCase();

        // Verificar se o idioma é válido
        if (!language.equals("pt-br") && !language.equals("es")) {
            System.out.println("Idioma inválido. Usando pt-br como padrão.");
            return "pt-br";
        }

        return language;
    }
}
