package com.example.westen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.Funcionario;
import com.example.westen.FuncionarioProjeto;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioProjetoDAO{
    private final Conexao conexao;
    private final SQLiteDatabase banco;

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

    public List<FuncionarioProjeto> selectFuncionariosProjeto (int projetoID){
        List<FuncionarioProjeto> listafuncionarioProjeto = new ArrayList<FuncionarioProjeto>();
        Cursor cursor = banco.query("tbFuncionarioProjeto",
                new String[]{"FK_FuncionarioCPF", "FK_ProjetoID"},
                "FK_ProjetoID = ?",
                new String[]{String.valueOf(projetoID)},
                null,
                null,
                null
        );
        while(cursor.moveToNext())
        {
            FuncionarioProjeto funcionarioProjeto = new FuncionarioProjeto();
            funcionarioProjeto.setFK_FuncionarioCPF(cursor.getString(0));
            funcionarioProjeto.setFK_ProjetoID(cursor.getInt(1));

            listafuncionarioProjeto.add(funcionarioProjeto);
        }

        return listafuncionarioProjeto;
    }

    public void deleteFuncionarioProjeto(int projetoID){
        ContentValues values = new ContentValues();
        banco.delete("tbFuncionarioProjeto", "FK_ProjetoID = ?", new String[]{String.valueOf(projetoID)});
    }
}
