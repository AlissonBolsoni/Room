package br.com.alura.roomapplication.delegates;

import br.com.alura.roomapplication.models.Aluno;

public interface AlunosDelegate {
    void lidaComClickFAB();

    void voltaParaTelaAnterior();

    void alteraNomeActivity(String nome);

    void lidaComAlunoSelecionado(Aluno aluno);
}
