package com.example.westen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.westen.Conexao;
import com.example.westen.FuncionarioProjeto;

public class FuncionarioProjetoDAO{
    private Conexao conexao;
    private SQLiteDatabase banco;

    public FuncionarioProjetoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long insertFuncionarioProjeto(FuncionarioProjeto funcionarioProjeto)
    {
        ContentValues values = new ContentValues();
        values.put("FK_FuncionarioCPF", funcionarioProjeto.getFK_FuncionarioCPF());
        values.put("FK_ProjetoID", funcionarioProjeto.getFK_ProjetoID());

        return banco.insert("tbFuncionarioProjeto", null, values);
    }
}
