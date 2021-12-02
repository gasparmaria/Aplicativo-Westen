package com.example.westen.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.westen.Funcionario;
import com.example.westen.Conexao;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public FuncionarioDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserirFuncionario(Funcionario funcionario){
        ContentValues values = new ContentValues();
        values.put("FuncionarioCPF", funcionario.getFuncionarioCPF());
        values.put("FuncionarioNome", funcionario.getFuncionarioNome());
        values.put("FuncionarioEmail", funcionario.getFuncionarioEmail());
        values.put("FuncionarioSenha", funcionario.getFuncionarioSenha());
        values.put("FuncionarioPathFoto", funcionario.getFuncionarioPathFoto());
        values.put("FuncionarioCargo", funcionario.getFuncionarioCargo());
        values.put("FuncionarioNumeroEndereco", funcionario.getFuncionarioNumeroEndereco());
        values.put("FuncionarioComplementoEndereco", funcionario.getFuncionarioComplementoEndereco());
        values.put("FuncionarioTelefone", funcionario.getFuncionarioTelefone());
        values.put("FuncionarioBairro", funcionario.getFuncionarioBairro());
        values.put("FuncionarioCidade", funcionario.getFuncionarioCidade());
        values.put("FuncionarioUF", funcionario.getFuncionarioUF());
        values.put("FuncionarioCEP", funcionario.getFuncionarioCEP());
        values.put("FuncionarioLogradouro", funcionario.getFuncionarioLogradouro());

        return banco.insert("Funcionario", null, values);
    }

    public List<Funcionario> selectFuncionario()
    {
        List<Funcionario> listaFuncionarios = new ArrayList<>();

        Cursor cursor = banco.query("tbFuncionario",
                new String[] {
                        "FuncionarioCPF",
                        "FuncionarioNome",
                        "FuncionarioEmail",
                        "FuncionarioSenha",
                        "FuncionarioPathFoto",
                        "FuncionarioCargo",
                        "FuncionarioNumeroEndereco",
                        "FuncionarioComplementoEndereco",
                        "FuncionarioTelefone",
                        "FuncionarioBairro",
                        "FuncionarioCidade",
                        "FuncionarioUF",
                        "FuncionarioCEP",
                        "FuncionarioLogradouro"
                },
                null,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()){
            Funcionario funcionario = new Funcionario();

            funcionario.setFuncionarioCPF(cursor.getString(0));
            funcionario.setFuncionarioNome(cursor.getString(1));
            funcionario.setFuncionarioEmail(cursor.getString(2));
            funcionario.setFuncionarioSenha(cursor.getString(3));
            funcionario.setFuncionarioPathFoto(cursor.getString(4));
            funcionario.setFuncionarioCargo(cursor.getString(5));
            funcionario.setFuncionarioNumeroEndereco(cursor.getInt(6));
            funcionario.setFuncionarioComplementoEndereco(cursor.getString(7));
            funcionario.setFuncionarioTelefone(cursor.getString(8));
            funcionario.setFuncionarioBairro(cursor.getString(9));
            funcionario.setFuncionarioCidade(cursor.getString(10));
            funcionario.setFuncionarioUF(cursor.getString(11));
            funcionario.setFuncionarioCEP(cursor.getString(12));
            funcionario.setFuncionarioLogradouro(cursor.getString(13));

            listaFuncionarios.add(funcionario);
        }

        return listaFuncionarios;
    }

    public long updateFuncionario(Funcionario funcionario){
        ContentValues values = new ContentValues();
        values.put("FuncionarioCPF", funcionario.getFuncionarioCPF());
        values.put("FuncionarioNome", funcionario.getFuncionarioNome());
        values.put("FuncionarioEmail", funcionario.getFuncionarioEmail());
        values.put("FuncionarioSenha", funcionario.getFuncionarioSenha());
        values.put("FuncionarioPathFoto", funcionario.getFuncionarioPathFoto());
        values.put("FuncionarioCargo", funcionario.getFuncionarioCargo());
        values.put("FuncionarioNumeroEndereco", funcionario.getFuncionarioNumeroEndereco());
        values.put("FuncionarioComplementoEndereco", funcionario.getFuncionarioComplementoEndereco());
        values.put("FuncionarioTelefone", funcionario.getFuncionarioTelefone());
        values.put("FuncionarioBairro", funcionario.getFuncionarioBairro());
        values.put("FuncionarioCidade", funcionario.getFuncionarioCidade());
        values.put("FuncionarioUF", funcionario.getFuncionarioUF());
        values.put("FuncionarioCEP", funcionario.getFuncionarioCEP());
        values.put("FuncionarioLogradouro", funcionario.getFuncionarioLogradouro());

        return banco.update("tbFuncionario", values, "FuncionarioCPF = ?", new String[]{ funcionario.getFuncionarioCPF() });
    }

    public Boolean verificarLogin(String email, String senha){
        Cursor cursor = banco.rawQuery("SELECT * FROM tbFuncionario WHERE FuncionarioEmail = ? AND FuncionarioSenha = ?", new String[] {email, senha});

        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
}
