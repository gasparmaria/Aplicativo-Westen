package com.example.westen;

import java.io.Serializable;

public class Funcionario implements Serializable {

    private String FuncionarioCPF,
            FuncionarioNome,
            FuncionarioEmail,
            FuncionarioSenha,
            FuncionarioCargo,
            FuncionarioComplementoEndereco,
            FuncionarioTelefone,
            FuncionarioBairro,
            FuncionarioCidade,
            FuncionarioUF,
            FuncionarioCEP,
            FuncionarioLogradouro;

    private int FuncionarioNumeroEndereco;

    private byte[] FuncionarioImagem;

    public Funcionario() {

    }

    public Funcionario(String funcionarioCPF, String funcionarioNome, String funcionarioEmail, String funcionarioSenha, String funcionarioCargo, String funcionarioComplementoEndereco, String funcionarioTelefone, String funcionarioBairro, String funcionarioCidade, String funcionarioUF, String funcionarioCEP, String funcionarioLogradouro, int funcionarioNumeroEndereco, byte[] funcionarioImagem) {
        FuncionarioCPF = funcionarioCPF;
        FuncionarioNome = funcionarioNome;
        FuncionarioEmail = funcionarioEmail;
        FuncionarioSenha = funcionarioSenha;
        FuncionarioCargo = funcionarioCargo;
        FuncionarioComplementoEndereco = funcionarioComplementoEndereco;
        FuncionarioTelefone = funcionarioTelefone;
        FuncionarioBairro = funcionarioBairro;
        FuncionarioCidade = funcionarioCidade;
        FuncionarioUF = funcionarioUF;
        FuncionarioCEP = funcionarioCEP;
        FuncionarioLogradouro = funcionarioLogradouro;
        FuncionarioNumeroEndereco = funcionarioNumeroEndereco;
        FuncionarioImagem = funcionarioImagem;
    }

    public String getFuncionarioCPF() {
        return FuncionarioCPF;
    }

    public void setFuncionarioCPF(String funcionarioCPF) {
        FuncionarioCPF = funcionarioCPF;
    }

    public String getFuncionarioNome() {
        return FuncionarioNome;
    }

    public void setFuncionarioNome(String funcionarioNome) {
        FuncionarioNome = funcionarioNome;
    }

    public String getFuncionarioEmail() {
        return FuncionarioEmail;
    }

    public void setFuncionarioEmail(String funcionarioEmail) {
        FuncionarioEmail = funcionarioEmail;
    }

    public String getFuncionarioSenha() {
        return FuncionarioSenha;
    }

    public void setFuncionarioSenha(String funcionarioSenha) {
        FuncionarioSenha = funcionarioSenha;
    }

    public String getFuncionarioCargo() {
        return FuncionarioCargo;
    }

    public void setFuncionarioCargo(String funcionarioCargo) {
        FuncionarioCargo = funcionarioCargo;
    }

    public String getFuncionarioComplementoEndereco() {
        return FuncionarioComplementoEndereco;
    }

    public void setFuncionarioComplementoEndereco(String funcionarioComplementoEndereco) {
        FuncionarioComplementoEndereco = funcionarioComplementoEndereco;
    }

    public String getFuncionarioTelefone() {
        return FuncionarioTelefone;
    }

    public void setFuncionarioTelefone(String funcionarioTelefone) {
        FuncionarioTelefone = funcionarioTelefone;
    }

    public String getFuncionarioBairro() {
        return FuncionarioBairro;
    }

    public void setFuncionarioBairro(String funcionarioBairro) {
        FuncionarioBairro = funcionarioBairro;
    }

    public String getFuncionarioCidade() {
        return FuncionarioCidade;
    }

    public void setFuncionarioCidade(String funcionarioCidade) {
        FuncionarioCidade = funcionarioCidade;
    }

    public String getFuncionarioUF() {
        return FuncionarioUF;
    }

    public void setFuncionarioUF(String funcionarioUF) {
        FuncionarioUF = funcionarioUF;
    }

    public String getFuncionarioCEP() {
        return FuncionarioCEP;
    }

    public void setFuncionarioCEP(String funcionarioCEP) {
        FuncionarioCEP = funcionarioCEP;
    }

    public String getFuncionarioLogradouro() {
        return FuncionarioLogradouro;
    }

    public void setFuncionarioLogradouro(String funcionarioLogradouro) {
        FuncionarioLogradouro = funcionarioLogradouro;
    }

    public int getFuncionarioNumeroEndereco() {
        return FuncionarioNumeroEndereco;
    }

    public void setFuncionarioNumeroEndereco(int funcionarioNumeroEndereco) {
        FuncionarioNumeroEndereco = funcionarioNumeroEndereco;
    }

    public byte[] getFuncionarioImagem() {
        return FuncionarioImagem;
    }

    public void setFuncionarioImagem(byte[] funcionarioImagem) {
        FuncionarioImagem = funcionarioImagem;
    }
}
