package br.com.alura.roomapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.delegates.AlunosDelegate;
import br.com.alura.roomapplication.fragments.FormularioAlunosFragment;
import br.com.alura.roomapplication.fragments.ListaAlunoFragment;
import br.com.alura.roomapplication.models.Aluno;

public class AlunosActivity extends AppCompatActivity implements AlunosDelegate {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);

        exibeFragment(new ListaAlunoFragment(), false);

    }

    @Override
    public void lidaComClickFAB() {
        exibeFragment(new FormularioAlunosFragment(), true);
    }

    @Override
    public void voltaParaTelaAnterior() {
        onBackPressed();
    }

    @Override
    public void alteraNomeActivity(String nome) {
        setTitle(nome);
    }

    @Override
    public void lidaComAlunoSelecionado(Aluno aluno) {
        FormularioAlunosFragment formulario = new FormularioAlunosFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("aluno", aluno);
        formulario.setArguments(bundle);
        exibeFragment(formulario, true);
    }

    private void exibeFragment(Fragment fragment, boolean empilhado) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.alunos_frame, fragment);

        if (empilhado)
            transaction.addToBackStack(null);

        transaction.commit();
    }
}
