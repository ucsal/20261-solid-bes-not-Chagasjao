package br.com.ucsal.olimpiadas;

import java.util.List;
import java.util.Scanner;

public class CadastroService {

    private final Scanner in;
    private final List<Participante> participantes;
    private final List<Prova> provas;
    private final List<Questao> questoes;

    private long proximoParticipanteId = 1;
    private long proximaProvaId = 2; // 1 já foi usada no seed
    private long proximaQuestaoId = 2; // 1 já foi usada no seed

    public CadastroService(Scanner in, List<Participante> participantes, List<Prova> provas, List<Questao> questoes) {
        this.in = in;
        this.participantes = participantes;
        this.provas = provas;
        this.questoes = questoes;
    }

    public void cadastrarParticipante() {
        System.out.print("Nome: ");
        String nome = in.nextLine();

        System.out.print("Email (opcional): ");
        String email = in.nextLine();

        if (nome == null || nome.isBlank()) {
            System.out.println("nome inválido");
            return;
        }

        Participante participante = new Participante();
        participante.setId(proximoParticipanteId++);
        participante.setNome(nome);
        participante.setEmail(email);

        participantes.add(participante);
        System.out.println("Participante cadastrado: " + participante.getId());
    }

    public void cadastrarProva() {
        System.out.print("Título da prova: ");
        String titulo = in.nextLine();

        if (titulo == null || titulo.isBlank()) {
            System.out.println("título inválido");
            return;
        }

        Prova prova = new Prova();
        prova.setId(proximaProvaId++);
        prova.setTitulo(titulo);

        provas.add(prova);
        System.out.println("Prova criada: " + prova.getId());
    }

    public void cadastrarQuestao() {
        if (provas.isEmpty()) {
            System.out.println("não há provas cadastradas");
            return;
        }

        Long provaId = escolherProva();
        if (provaId == null) {
            return;
        }

        System.out.println("Enunciado:");
        String enunciado = in.nextLine();

        System.out.print("FEN inicial (opcional): ");
        String fenInicial = in.nextLine();

        String[] alternativas = new String[5];
        for (int i = 0; i < 5; i++) {
            char letra = (char) ('A' + i);
            System.out.print("Alternativa " + letra + ": ");
            alternativas[i] = letra + ") " + in.nextLine();
        }

        System.out.print("Alternativa correta (A–E): ");
        char correta;
        try {
            correta = Questao.normalizar(in.nextLine().trim().charAt(0));
        } catch (Exception e) {
            System.out.println("alternativa inválida");
            return;
        }

        Questao questao = new Questao();
        questao.setId(proximaQuestaoId++);
        questao.setProvaId(provaId);
        questao.setEnunciado(enunciado);
        questao.setFenInicial(fenInicial == null || fenInicial.isBlank() ? null : fenInicial);
        questao.setAlternativas(alternativas);
        questao.setAlternativaCorreta(correta);

        questoes.add(questao);
        System.out.println("Questão cadastrada: " + questao.getId() + " (na prova " + provaId + ")");
    }

    private Long escolherProva() {
        System.out.println("\nProvas:");
        for (Prova prova : provas) {
            System.out.printf("  %d) %s%n", prova.getId(), prova.getTitulo());
        }
        System.out.print("Escolha o id da prova: ");

        try {
            long id = Long.parseLong(in.nextLine());
            boolean existe = provas.stream().anyMatch(prova -> prova.getId() == id);
            if (!existe) {
                System.out.println("id inválido");
                return null;
            }
            return id;
        } catch (Exception e) {
            System.out.println("entrada inválida");
            return null;
        }
    }
}
