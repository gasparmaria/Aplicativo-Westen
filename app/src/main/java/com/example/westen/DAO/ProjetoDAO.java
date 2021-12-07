package com.example.westen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.westen.Cliente;
import com.example.westen.Conexao;
import com.example.westen.Funcionario;
import com.example.westen.Projeto;

import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public ProjetoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long insertProjeto(Projeto projeto){
        ContentValues values = new ContentValues();
        values.put("ProjetoDataInicio", projeto.getProjetoDataInicio());
        values.put("ProjetoDataFinal", projeto.getProjetoDataFinal());
        values.put("ProjetoStatus", projeto.getProjetoStatus());
        values.put("ProjetoDescricao", projeto.getProjetoDescricao());
        values.put("ProjetoServico", projeto.getProjetoServico());
        values.put("FK_ClienteCNPJ", projeto.getFK_ClienteCNPJ());

        return banco.insert("tbProjeto", null, values);
    }

    public List<Projeto> selectProjeto(){
        List<Projeto> listaProjetos = new ArrayList<>();

        Cursor cursor = banco.query("tbProjeto",
                new String[] {
                        "ProjetoID",
                        "ProjetoDataInicio",
                        "ProjetoDataFinal",
                        "ProjetoStatus",
                        "ProjetoDescricao",
                        "ProjetoServico",
                        "FK_ClienteCNPJ"
                },
                null,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()){
            Projeto projeto = new Projeto();

            projeto.setProjetoID(cursor.getInt(0));
            projeto.setProjetoDataInicio(cursor.getString(1));
            projeto.setProjetoDataFinal(cursor.getString(2));
            projeto.setProjetoStatus(cursor.getString(3));
            projeto.setProjetoDescricao(cursor.getString(4));
            projeto.setProjetoServico(cursor.getString(5));
            projeto.setFK_ClienteCNPJ(cursor.getString(6));

            listaProjetos.add(projeto);
        }

        return listaProjetos;
    }

    public int selectUltimoProjetoCadastrado()
    {
        int id = 0;

        Cursor cursor = banco.query("tbProjeto",
                new String[] {
                        "ProjetoID"
                },
                null,
                null,
                null,
                null,
                "ProjetoID DESC ",
                String.valueOf(1));

        while(cursor.moveToNext()){
            id = cursor.getInt(0);
        }

        return id;
    }

    public long updateProjeto(Projeto projeto, Cliente cliente) {
        ContentValues values = new ContentValues();
        values.put("ProjetoDataInicio", projeto.getProjetoDataInicio());
        values.put("ProjetoDataFinal", projeto.getProjetoDataFinal());
        values.put("ProjetoStatus", projeto.getProjetoStatus());
        values.put("ProjetoDescricao", projeto.getProjetoDescricao());
        values.put("ProjetoServico", projeto.getProjetoServico());
        values.put("FK_ClienteCNPJ", cliente.getClienteCNPJ());

        return banco.update("tbProjeto", values, "ProjetoID = ?", new String[]{String.valueOf(projeto.getProjetoID())});
    }

    public void deleteProjeto(Projeto projeto){
        banco.delete("tbFuncionarioProjeto",
                "FK_ProjetoID = ?",
                new String[]{String.valueOf(projeto.getProjetoID())});

        banco.delete("tbProjeto",
                "ProjetoID = ?",
                new String[]{String.valueOf(projeto.getProjetoID())});
    }
}
