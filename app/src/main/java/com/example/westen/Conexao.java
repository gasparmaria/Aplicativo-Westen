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
        db.execSQL("CREATE TABLE Bairro(" +
                "BairroID INT PRIMARY KEY AUTOINCREMENT," +
                "BairroNome TEXT NOT NULL)");
        db.execSQL("CREATE TABLE Cidade(" +
                "CidadeID INT PRIMARY KEY AUTOINCREMENT," +
                "CidadeNome TEXT NOT NULL)");
        db.execSQL("CREATE TABLE Estado(" +
                "EstadoID INT PRIMARY KEY AUTOINCREMENT," +
                "EstadoUF CHAR(2) NOT NULL)");
        db.execSQL("CREATE TABLE Endereco(" +
                "Cep CHAR(8) PRIMARY KEY," +
                "Logradouro TEXT NOT NULL," +
                "FK_BairroID INT," +
                "FK_CidadeID INT," +
                "FK_EstadoID INT," +
                "CONSTRAINT Endereco_Bairro FOREIGN KEY (FK_BairroID) REFERENCES Bairro (BairroID)," +
                "CONSTRAINT Endereco_Cidade FOREIGN KEY (FK_CidadeID) REFERENCES Cidade (CidadeID)," +
                "CONSTRAINT Endereco_Estado FOREIGN KEY (FK_EstadoID) REFERENCES Estado (EstadoID))");
        db.execSQL("CREATE TABLE Pessoa(" +
                "PessoaID INT PRIMARY KEY AUTOINCREMENT," +
                "PessoaNumeroEndereco INT NOT NULL," +
                "PessoaComplementoEndereco TEXT," +
                "PessoaNumeroTelefone TEXT UNIQUE," +
                "FK_Cep CHAR(8) NOT NULL," +
                "CONSTRAINT Cep_Pessoa FOREIGN KEY (FK_Cep) REFERENCES Endereco (Cep))");
        db.execSQL("CREATE TABLE Cliente(" +
                "ClienteCnpj CHAR(14) PRIMARY KEY," +
                "ClienteNome TEXT NOT NULL," +
                "ClienteDescricao TEXT NOT NULL," +
                "ClientePathLogo TEXT UNIQUE," +
                "FK_PessoaID INT NOT NULL," +
                "CONSTRAINT Pessoa_Cliente FOREIGN KEY (FK_PessoaID) REFERENCES Pessoa (PessoaID))");
        db.execSQL("CREATE TABLE Cargo(" +
                "CargoID INT PRIMARY KEY AUTOINCREMENT," +
                "CargoNome TEXT NOT NULL UNIQUE)");
        db.execSQL("CREATE TABLE Funcionario(" +
                "FuncionarioCpf CHAR(11) PRIMARY KEY," +
                "FuncionarioNome TEXT NOT NULL," +
                "FuncionarioEmail TEXT NOT NULL," +
                "FuncionarioSenha CHAR(8) NOT NULL," +
                "FuncionarioPathFoto TEXT," +
                "FK_CargoID INT NOT NULL," +
                "FK_PessoaID INT NOT NULL," +
                "CONSTRAINT Cargo_Funcionario FOREIGN KEY (FK_CargoID) REFERENCES Cargo (CargoID)," +
                "CONSTRAINT Pessoa_Funcionario FOREIGN KEY (FK_PessoaID) REFERENCES Pessoa (PessoaID))");
        db.execSQL("CREATE TABLE Servico(" +
                "ServicoID INT PRIMARY KEY AUTOINCREMENT," +
                "ServicoNome TEXT NOT NULL)");
        db.execSQL("CREATE TABLE Projeto(" +
                "ProjetoID INT PRIMARY KEY AUTOINCREMENT," +
                "ProjetoDataInicio DATE NOT NULL," +
                "ProjetoDataFinal DATE NOT NULL," +
                "ProjetoStatus TEXT NOT NULL," +
                "ProjetoDescricao TEXT UNIQUE," +
                "ProjetoPreco DECIMAL(9,2)," +
                "FK_ServicoID INT," +
                "FK_Cnpj CHAR(14)," +
                "CONSTRAINT Servico_Projeto FOREIGN KEY (FK_ServicoID) REFERENCES Servico (ServicoID)," +
                "CONSTRAINT Cliente_Projeto FOREIGN KEY (FK_Cnpj) REFERENCES Cliente (ClienteCnpj))");
        db.execSQL("CREATE TABLE ParticipaProjeto(" +
                "FK_FuncionarioCpf CHAR(11)," +
                "FK_ProjetoID INT," +
                "PRIMARY KEY (Cpf, ProjetoID)," +
                "CONSTRAINT Funcionario_ParticipaProjeto FOREIGN KEY (FK_FuncionarioCpf) REFERENCES Funcionario (FuncionarioCpf)," +
                "CONSTRAINT Projeto_ParticipaProjeto FOREIGN KEY (FK_ProjetoID) REFERENCES Projeto (ProjetoID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
