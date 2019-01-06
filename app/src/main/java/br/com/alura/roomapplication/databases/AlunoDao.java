package br.com.alura.roomapplication.databases;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.alura.roomapplication.models.Aluno;

@Dao
public interface AlunoDao {

    @Insert
    void insere(Aluno aluno);

    @Update
    void altera(Aluno aluno);

    @Query("select * from Aluno order by nome")
    List<Aluno> busca();

    @Delete
    void deleta(Aluno aluno);
}
