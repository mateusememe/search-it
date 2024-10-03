package com.mateusememe;

public class SearchIt
{
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Erro: É necessário fornecer um argumento de busca.");
            printHelp();
            System.exit(1);
            return;
        }

        String searchTerm = args[0].toLowerCase();

        if (searchTerm.equals("--help") || searchTerm.equals("-h")) {
            printHelp();
            return;
        }

        // Processar o termo de busca...
        System.out.println("Termo de busca fornecido: %s".formatted(searchTerm));
    }

    private static void printHelp() {
        System.out.println("Use: java -jar search.it.jar <termo de busca>");
        System.out.println("Opções:");
        System.out.println("  <termo de busca>   Um termo de busca obrigatório (string)");
        System.out.println("  --help, -h    Mostra esta mensagem de ajuda");
    }
}
