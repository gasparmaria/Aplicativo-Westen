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

    public long insertFuncionario(Funcionario funcionario){
        ContentValues values = new ContentValues();
        values.put("FuncionarioCPF", funcionario.getFuncionarioCPF());
        values.put("FuncionarioNome", funcionario.getFuncionarioNome());
        values.put("FuncionarioEmail", funcionario.getFuncionarioEmail());
        values.put("FuncionarioSenha", funcionario.getFuncionarioSenha());
        values.put("FuncionarioFoto", funcionario.getFuncionarioImagem());
        values.put("FuncionarioCargo", funcionario.getFuncionarioCargo());
        values.put("FuncionarioNumeroEndereco", funcionario.getFuncionarioNumeroEndereco());
        values.put("FuncionarioComplementoEndereco", funcionario.getFuncionarioComplementoEndereco());
        values.put("FuncionarioTelefone", funcionario.getFuncionarioTelefone());
        values.put("FuncionarioBairro", funcionario.getFuncionarioBairro());
        values.put("FuncionarioCidade", funcionario.getFuncionarioCidade());
        values.put("FuncionarioUF", funcionario.getFuncionarioUF());
        values.put("FuncionarioCEP", funcionario.getFuncionarioCEP());
        values.put("FuncionarioLogradouro", funcionario.getFuncionarioLogradouro());

        return banco.insert("tbFuncionario", null, values);
    }

    public List<Funcionario> selectFuncionario(){
        List<Funcionario> listaFuncionarios = new ArrayList<>();

        Cursor cursor = banco.rawQuery("SELECT  " +
                        "FuncionarioCPF, " +
                        "FuncionarioNome, " +
                        "FuncionarioEmail, " +
                        "FuncionarioSenha, " +
                        "FuncionarioCargo, " +
                        "FuncionarioNumeroEndereco, " +
                        "FuncionarioComplementoEndereco, " +
                        "FuncionarioTelefone, " +
                        "FuncionarioBairro, " +
                        "FuncionarioCidade, " +
                        "FuncionarioUF, " +
                        "FuncionarioCEP, " +
                        "FuncionarioLogradouro, " +
                        "FuncionarioFoto " +
                "FROM tbFuncionario",
                null
        );

        while(cursor.moveToNext()){
            Funcionario funcionario = new Funcionario();

            funcionario.setFuncionarioCPF(cursor.getString(0));
            funcionario.setFuncionarioNome(cursor.getString(1));
            funcionario.setFuncionarioEmail(cursor.getString(2));
            funcionario.setFuncionarioSenha(cursor.getString(3));
            funcionario.setFuncionarioCargo(cursor.getString(4));
            funcionario.setFuncionarioNumeroEndereco(cursor.getInt(5));
            funcionario.setFuncionarioComplementoEndereco(cursor.getString(6));
            funcionario.setFuncionarioTelefone(cursor.getString(7));
            funcionario.setFuncionarioBairro(cursor.getString(8));
            funcionario.setFuncionarioCidade(cursor.getString(9));
            funcionario.setFuncionarioUF(cursor.getString(10));
            funcionario.setFuncionarioCEP(cursor.getString(11));
            funcionario.setFuncionarioLogradouro(cursor.getString(12));
            funcionario.setFuncionarioImagem(cursor.getBlob(13));

            listaFuncionarios.add(funcionario);
        }

        return listaFuncionarios;
    }

    public String[] selectTodosNomesFuncionarios(){
        ArrayList<String> listaFuncionariosNomes = new ArrayList<String>();

        Cursor cursor = banco.query("tbFuncionario",
                new String[] {
                        "FuncionarioNome"
                },
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext())
        {
            listaFuncionariosNomes.add(cursor.getString(0));
        }

        String[] arrayNomes = new String[listaFuncionariosNomes.size()];
        arrayNomes = (String[]) listaFuncionariosNomes.toArray(arrayNomes);

        return arrayNomes;
    }

    public String selectCPFPorNome(String nome){
        String cpf = null;
        Cursor cursor = banco.query("tbFuncionario",
                new String[] {
                    "FuncionarioCPF"
                },
                "FuncionarioNome = ?",
                new String[]{nome},
                null,
                null,
                null);

        while (cursor.moveToNext())
        {
            cpf = cursor.getString(0);
        }

        return cpf;
    }

    public Funcionario selectFuncionarioPorEmail(String email){
        Funcionario funcionario = new Funcionario();
        Cursor cursor = banco.query("tbFuncionario",
                new String[] {
                    "FuncionarioCPF, " +
                    "FuncionarioNome, " +
                    "FuncionarioEmail, " +
                    "FuncionarioSenha, " +
                    "FuncionarioCargo, " +
                    "FuncionarioNumeroEndereco, " +
                    "FuncionarioComplementoEndereco, " +
                    "FuncionarioTelefone, " +
                    "FuncionarioBairro, " +
                    "FuncionarioCidade, " +
                    "FuncionarioUF, " +
                    "FuncionarioCEP, " +
                    "FuncionarioLogradouro, " +
                    "FuncionarioFoto "
                },
                "FuncionarioEmail = ?",
                new String[]{email},
                null,
                null,
                null,
                String.valueOf(1));

        while (cursor.moveToNext())
        {
            funcionario.setFuncionarioCPF(cursor.getString(0));
            funcionario.setFuncionarioNome(cursor.getString(1));
            funcionario.setFuncionarioEmail(cursor.getString(2));
            funcionario.setFuncionarioSenha(cursor.getString(3));
            funcionario.setFuncionarioCargo(cursor.getString(4));
            funcionario.setFuncionarioNumeroEndereco(cursor.getInt(5));
            funcionario.setFuncionarioComplementoEndereco(cursor.getString(6));
            funcionario.setFuncionarioTelefone(cursor.getString(7));
            funcionario.setFuncionarioBairro(cursor.getString(8));
            funcionario.setFuncionarioCidade(cursor.getString(9));
            funcionario.setFuncionarioUF(cursor.getString(10));
            funcionario.setFuncionarioCEP(cursor.getString(11));
            funcionario.setFuncionarioLogradouro(cursor.getString(12));
            funcionario.setFuncionarioImagem(cursor.getBlob(13));
        }

        return funcionario;
    }

    public long updateFuncionario(Funcionario funcionario){
        ContentValues values = new ContentValues();
        values.put("FuncionarioCPF", funcionario.getFuncionarioCPF());
        values.put("FuncionarioNome", funcionario.getFuncionarioNome());
        values.put("FuncionarioEmail", funcionario.getFuncionarioEmail());
        values.put("FuncionarioSenha", funcionario.getFuncionarioSenha());
        values.put("FuncionarioFoto", funcionario.getFuncionarioImagem());
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
