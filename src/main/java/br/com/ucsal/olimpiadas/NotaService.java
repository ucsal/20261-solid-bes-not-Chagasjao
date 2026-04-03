package br.com.ucsal.olimpiadas;

public class NotaService {

    public int calcularNota(Tentativa tentativa) {
        int acertos = 0;
        for (Resposta resposta : tentativa.getRespostas()) {
            if (resposta.isCorreta()) {
                acertos++;
            }
        }
        return acertos;
    }
}
