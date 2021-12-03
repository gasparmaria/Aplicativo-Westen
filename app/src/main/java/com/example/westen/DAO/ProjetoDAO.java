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

    public long insertProjeto(Projeto projeto, Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("ProjetoID", projeto.getProjetoID());
        values.put("ProjetoDataInicio", projeto.getProjetoDataInicio());
        values.put("ProjetoDataFinal", projeto.getProjetoDataFinal());
        values.put("ProjetoStatus", projeto.getProjetoStatus());
        values.put("ProjetoDescricao", projeto.getProjetoDescricao());
        values.put("ProjetoServico", projeto.getProjetoServico());
        values.put("ProjetoPreco", projeto.getProjetoPreco());
        values.put("FK_ClienteCNPJ", cliente.getClienteCNPJ());

        return banco.insert("tbFuncionario", null, values);
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
                        "ProjetoPreco",
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
            projeto.setProjetoPreco(cursor.getDouble(6));
            projeto.setFK_ClienteCNPJ(cursor.getString(7));

            listaProjetos.add(projeto);
        }

        return listaProjetos;
    }

    public long updateProjeto(Projeto projeto, Cliente cliente) {
        ContentValues values = new ContentValues();
        values.put("ProjetoID", projeto.getProjetoID());
        values.put("ProjetoDataInicio", projeto.getProjetoDataInicio());
        values.put("ProjetoDataFinal", projeto.getProjetoDataFinal());
        values.put("ProjetoStatus", projeto.getProjetoStatus());
        values.put("ProjetoDescricao", projeto.getProjetoDescricao());
        values.put("ProjetoServico", projeto.getProjetoServico());
        values.put("ProjetoPreco", projeto.getProjetoPreco());
        values.put("FK_ClienteCNPJ", cliente.getClienteCNPJ());

        return banco.update("tbProjeto", values, "ProjetoID = ?", new String[]{String.valueOf(projeto.getProjetoID())});
    }
}
