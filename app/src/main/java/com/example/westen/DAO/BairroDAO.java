package com.example.westen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.westen.Bairro;
import com.example.westen.Conexao;

public class BairroDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public BairroDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public Bairro inserirBairro(Bairro bairro)
    {
        ContentValues valuesBairro = new ContentValues();
        String selectBairro = "SELECT BairroID FROM Bairro WHERE BairroNome = ?";
        Cursor cursorBairro = banco.rawQuery(selectBairro, new String[]{ bairro.getBairroNome() });

        if(cursorBairro.getCount() < 1){
            valuesBairro.put("BairroNome", bairro.getBairroNome());
            banco.insert("Bairro", null, valuesBairro);
        }

        bairro.setBairroNome();
        return bairro;
    }


}