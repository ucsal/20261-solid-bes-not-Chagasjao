package br.com.ucsal.olimpiadas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        List<Participante> participantes = new ArrayList<>();
        List<Prova> provas = new ArrayList<>();
        List<Questao> questoes = new ArrayList<>();
        List<Tentativa> tentativas = new ArrayList<>();

        NotaService notaService = new NotaService();
        TabuleiroPrinter tabuleiroPrinter = new FenPrinter();
        CadastroService cadastroService = new CadastroService(in, participantes, provas, questoes);
        AplicacaoProvaService aplicacaoProvaService = new AplicacaoProvaService(
                in,
                participantes,
                provas,
                questoes,
                tentativas,
                notaService,
                tabuleiroPrinter
        );

        seed(provas, questoes);

        while (true) {
            System.out.println("\n=== OLIMPÍADA DE QUESTÕES (V2 - ENXUTA) ===");
            System.out.println("1) Cadastrar participante");
            System.out.println("2) Cadastrar prova");
            System.out.println("3) Cadastrar questão (A–E) em uma prova");
            System.out.println("4) Aplicar prova (selecionar participante + prova)");
            System.out.println("5) Listar tentativas (resumo)");
            System.out.println("0) Sair");
            System.out.print("> ");

            switch (in.nextLine()) {
                case "1" -> cadastroService.cadastrarParticipante();
                case "2" -> cadastroService.cadastrarProva();
                case "3" -> cadastroService.cadastrarQuestao();
                case "4" -> aplicacaoProvaService.aplicarProva();
                case "5" -> aplicacaoProvaService.listarTentativas();
                case "0" -> {
                    System.out.println("tchau");
                    return;
                }
                default -> System.out.println("opção inválida");
            }
        }
    }

    private static void seed(List<Prova> provas, List<Questao> questoes) {
        Prova prova = new Prova();
        prova.setId(1L);
        prova.setTitulo("Olimpíada 2026 • Nível 1 • Prova A");
        provas.add(prova);

        Questao q1 = new Questao();
        q1.setId(1L);
        q1.setProvaId(prova.getId());
        q1.setEnunciado("""
                Questão 1 — Mate em 1.
                É a vez das brancas.
                Encontre o lance que dá mate imediatamente.
                """);
        q1.setFenInicial("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");
        q1.setAlternativas(new String[]{"A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#"});
        q1.setAlternativaCorreta('C');
        questoes.add(q1);
    }
}
