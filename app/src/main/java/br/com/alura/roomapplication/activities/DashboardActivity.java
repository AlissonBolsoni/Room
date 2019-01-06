package br.com.alura.roomapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.fragments.ListaAlunoFragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button buttonAluno = findViewById(R.id.dash_btn_aluno);

        buttonAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, AlunosActivity.class);
                startActivity(intent);
            }
        });

    }
}
