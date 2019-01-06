package br.com.alura.roomapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.databases.AlunoDao;
import br.com.alura.roomapplication.databases.GeradorBD;
import br.com.alura.roomapplication.delegates.AlunosDelegate;
import br.com.alura.roomapplication.models.Aluno;

public class FormularioAlunosFragment extends Fragment {

    private Aluno aluno = new Aluno();
    private EditText campoNome;
    private EditText campoEmail;
    private AlunosDelegate delegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        delegate = (AlunosDelegate) getActivity();

        delegate.alteraNomeActivity("Cadastro de Alunos");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_alunos, container, false);

        configuraComponentesDaView(view);

        colocaAlunoNaTela();

        return view;
    }

    private void colocaAlunoNaTela() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            this.aluno = (Aluno) bundle.getSerializable("aluno");
            this.campoNome.setText(aluno.getNome());
            this.campoEmail.setText(aluno.getEmail());
        }
    }

    private void configuraComponentesDaView(View view) {
        Button buttonCadastrar = view.findViewById(R.id.formulario_alunos_cadastrar);
        this.campoNome = view.findViewById(R.id.formulario_alunos_nome);
        this.campoEmail = view.findViewById(R.id.formulario_alunos_email);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizaAluno();

                persisteAluno();

                delegate.voltaParaTelaAnterior();
            }
        });
    }

    private void persisteAluno() {
        GeradorBD geradorBD = new GeradorBD();
        AlunoDao alunoDao = geradorBD.geraBD(getContext()).getAlunoDao();

        if (ehAlunoNovo())
            alunoDao.insere(aluno);
        else
            alunoDao.altera(aluno);
    }

    private boolean ehAlunoNovo() {
        return aluno.getId() == null;
    }

    private void atualizaAluno() {

        aluno.setNome(campoNome.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
    }
}
