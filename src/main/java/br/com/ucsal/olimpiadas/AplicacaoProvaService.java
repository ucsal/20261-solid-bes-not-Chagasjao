package br.com.ucsal.olimpiadas;

import java.util.List;
import java.util.Scanner;

public class AplicacaoProvaService {

    private final Scanner in;
    private final List<Participante> participantes;
    private final List<Prova> provas;
    private final List<Questao> questoes;
    private final List<Tentativa> tentativas;
    private final NotaService notaService;
    private final TabuleiroPrinter tabuleiroPrinter;

    private long proximaTentativaId = 1;

    public AplicacaoProvaService(
            Scanner in,
            List<Participante> participantes,
            List<Prova> provas,
            List<Questao> questoes,
            List<Tentativa> tentativas,
            NotaService notaService,
            TabuleiroPrinter tabuleiroPrinter
    ) {
        this.in = in;
        this.participantes = participantes;
        this.provas = provas;
        this.questoes = questoes;
        this.tentativas = tentativas;
        this.notaService = notaService;
        this.tabuleiroPrinter = tabuleiroPrinter;
    }

    public void aplicarProva() {
        if (participantes.isEmpty()) {
            System.out.println("cadastre participantes primeiro");
            return;
        }

        if (provas.isEmpty()) {
            System.out.println("cadastre provas primeiro");
            return;
        }

        Long participanteId = escolherParticipante();
        if (participanteId == null) {
            return;
        }

        Long provaId = escolherProva();
        if (provaId == null) {
            return;
        }

        List<Questao> questoesDaProva = questoes.stream()
                .filter(q -> q.getProvaId() == provaId)
                .toList();

        if (questoesDaProva.isEmpty()) {
            System.out.println("esta prova não possui questões cadastradas");
            return;
        }

        Tentativa tentativa = new Tentativa();
        tentativa.setId(proximaTentativaId++);
        tentativa.setParticipanteId(participanteId);
        tentativa.setProvaId(provaId);

        System.out.println("\n--- Início da Prova ---");

        for (Questao questao : questoesDaProva) {
            System.out.println("\nQuestão #" + questao.getId());
            System.out.println(questao.getEnunciado());

            tabuleiroPrinter.imprimir(questao.getFenInicial());

            for (String alternativa : questao.getAlternativas()) {
                System.out.println(alternativa);
            }

            System.out.print("Sua resposta (A–E): ");
            char marcada;
            try {
                marcada = Questao.normalizar(in.nextLine().trim().charAt(0));
            } catch (Exception e) {
                System.out.println("resposta inválida (marcando como errada)");
                marcada = 'X';
            }

            Resposta resposta = new Resposta();
            resposta.setQuestaoId(questao.getId());
            resposta.setAlternativaMarcada(marcada);
            resposta.setCorreta(questao.isRespostaCorreta(marcada));

            tentativa.getRespostas().add(resposta);
        }

        tentativas.add(tentativa);

        int nota = notaService.calcularNota(tentativa);
        System.out.println("\n--- Fim da Prova ---");
        System.out.println("Nota (acertos): " + nota + " / " + tentativa.getRespostas().size());
    }

    public void listarTentativas() {
        System.out.println("\n--- Tentativas ---");
        for (Tentativa tentativa : tentativas) {
            int nota = notaService.calcularNota(tentativa);
            System.out.printf(
                    "#%d | participante=%d | prova=%d | nota=%d/%d%n",
                    tentativa.getId(),
                    tentativa.getParticipanteId(),
                    tentativa.getProvaId(),
                    nota,
                    tentativa.getRespostas().size()
            );
        }
    }

    public Long escolherParticipante() {
        System.out.println("\nParticipantes:");
        for (Participante participante : participantes) {
            System.out.printf("  %d) %s%n", participante.getId(), participante.getNome());
        }
        System.out.print("Escolha o id do participante: ");

        try {
            long id = Long.parseLong(in.nextLine());
            boolean existe = participantes.stream().anyMatch(participante -> participante.getId() == id);
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

    public Long escolherProva() {
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
