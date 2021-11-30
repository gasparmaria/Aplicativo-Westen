package com.example.westen;

import java.io.Serializable;

public class Funcionario implements Serializable {

    private String FuncionarioCpf,
                   FuncionarioNome,
                   FuncionarioEmail,
                   FuncionarioSenha,
                   FuncionarioPathLogo,
                   FuncionarioComplementoEndereco,
                   FuncionarioTelefone;

    private int FK_CargoID,
                FuncionarioNumeroEndereco;

    public String getFuncionarioCpf() {
        return FuncionarioCpf;
    }

    public void setFuncionarioCpf(String funcionarioCpf) {
        FuncionarioCpf = funcionarioCpf;
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

    public String getFuncionarioPathLogo() {
        return FuncionarioPathLogo;
    }

    public void setFuncionarioPathLogo(String funcionarioPathLogo) {
        FuncionarioPathLogo = funcionarioPathLogo;
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

    public int getFK_CargoID() {
        return FK_CargoID;
    }

    public void setFK_CargoID(int FK_CargoID) {
        this.FK_CargoID = FK_CargoID;
    }

    public int getFuncionarioNumeroEndereco() {
        return FuncionarioNumeroEndereco;
    }

    public void setFuncionarioNumeroEndereco(int funcionarioNumeroEndereco) {
        FuncionarioNumeroEndereco = funcionarioNumeroEndereco;
    }
}
