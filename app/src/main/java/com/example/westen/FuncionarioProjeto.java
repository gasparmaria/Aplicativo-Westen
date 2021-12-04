package com.example.westen;

import java.io.Serializable;

public class FuncionarioProjeto implements Serializable {
    private String FK_FuncionarioCPF;
    private int FK_ProjetoID;

    public FuncionarioProjeto() {
    }

    public FuncionarioProjeto(String FK_FuncionarioCPF, int FK_ProjetoID) {
        this.FK_FuncionarioCPF = FK_FuncionarioCPF;
        this.FK_ProjetoID = FK_ProjetoID;
    }

    public String getFK_FuncionarioCPF() {
        return this.FK_FuncionarioCPF;
    }

    public void setFK_FuncionarioCPF(String Fk_Cpf) {
        this.FK_FuncionarioCPF = Fk_Cpf;
    }

    public int getFK_ProjetoID() {
        return this.FK_ProjetoID;
    }

    public void setFK_ProjetoID(int Fk_ProjetoID) {
        this.FK_ProjetoID = Fk_ProjetoID;
    }
}