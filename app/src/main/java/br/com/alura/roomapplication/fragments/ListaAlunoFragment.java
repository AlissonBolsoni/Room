package br.com.alura.roomapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.databases.AlunoDao;
import br.com.alura.roomapplication.databases.GeradorBD;
import br.com.alura.roomapplication.delegates.AlunosDelegate;
import br.com.alura.roomapplication.models.Aluno;

public class ListaAlunoFragment extends Fragment {

    private AlunosDelegate delegate;
    private FloatingActionButton buttonAdd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        delegate = (AlunosDelegate) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        configuraComponentesDa(view);

        return view;
    }

    private void configuraComponentesDa(View view) {
        configuraLista(view);

        configuraFAB(view);
    }

    private void configuraFAB(View view) {
        buttonAdd = view.findViewById(R.id.fragment_lista_fab);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.lidaComClickFAB();
            }
        });
    }

    private void configuraLista(View view) {
        final ListView listView = view.findViewById(R.id.fragment_lista);

        final AlunoDao alunoDao = new GeradorBD().geraBD(getContext()).getAlunoDao();
        List<Aluno> alunos = alunoDao.busca();
        final ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, alunos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {

                Aluno aluno = (Aluno) listView.getItemAtPosition(posicao);

                delegate.lidaComAlunoSelecionado(aluno);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                final Aluno aluno = (Aluno) listView.getItemAtPosition(posicao);

                Snackbar.make(buttonAdd, "Quer deletar o aluno: " + aluno.getNome() + " ?", Snackbar.LENGTH_LONG)
                        .setAction("Sim", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alunoDao.deleta(aluno);
                                adapter.remove(aluno);
                            }
                        })
                        .show();

                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        delegate.alteraNomeActivity("Lista de Alunos");
    }
}
