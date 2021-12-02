package com.example.westen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "db_aplicativoWesten.db";
    private static final int version = 1;

    public Conexao(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbCliente(" +
                "ClienteCNPJ TEXT PRIMARY KEY," +
                "ClienteNome TEXT NOT NULL," +
                "ClienteDescricao TEXT NOT NULL," +
                "ClientePathLogo TEXT UNIQUE," +
                "ClienteNumeroEndereco INT NOT NULL," +
                "ClienteComplementoEndereco TEXT," +
                "ClienteTelefone TEXT UNIQUE," +
                "ClienteBairro TEXT NOT NULL," +
                "ClienteCidade TEXT NOT NULL," +
                "ClienteUF TEXT NOT NULL," +
                "ClienteCEP TEXT NOT NULL," +
                "ClienteLogradouro TEXT NOT NULL)");
        db.execSQL("CREATE TABLE tbFuncionario(" +
                "FuncionarioCPF TEXT PRIMARY KEY," +
                "FuncionarioNome TEXT NOT NULL," +
                "FuncionarioEmail TEXT NOT NULL," +
                "FuncionarioSenha TEXT NOT NULL," +
                "FuncionarioPathFoto TEXT," +
                "FuncionarioCargo TEXT," +
                "FuncionarioNumeroEndereco INT NOT NULL," +
                "FuncionarioComplementoEndereco TEXT," +
                "FuncionarioTelefone TEXT UNIQUE," +
                "FuncionarioBairro TEXT NOT NULL," +
                "FuncionarioCidade TEXT NOT NULL," +
                "FuncionarioUF TEXT NOT NULL," +
                "FuncionarioCEP TEXT NOT NULL," +
                "FuncionarioLogradouro TEXT NOT NULL)");
        db.execSQL("CREATE TABLE tbProjeto(" +
                "ProjetoID INT PRIMARY KEY AUTOINCREMENT," +
                "ProjetoDataInicio TEXT NOT NULL," +
                "ProjetoDataFinal TEXT NOT NULL," +
                "ProjetoStatus TEXT NOT NULL," +
                "ProjetoDescricao TEXT UNIQUE," +
                "ProjetoServico TEXT NOT NULL," +
                "ProjetoPreco DECIMAL(9,2)," +
                "FK_ClienteCNPJ TEXT," +
                "FOREIGN KEY (FK_ClienteCNPJ) REFERENCES Cliente (ClienteCNPJ))");
        db.execSQL("CREATE TABLE tbFuncionarioProjeto(" +
                "FK_FuncionarioCPF TEXT," +
                "FK_ProjetoID INT," +
                "PRIMARY KEY (FK_FuncionarioCPF, FK_ProjetoID)," +
                "FOREIGN KEY (FK_FuncionarioCPF) REFERENCES Funcionario (FuncionarioCPF)," +
                "FOREIGN KEY (FK_ProjetoID) REFERENCES Projeto (ProjetoID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
