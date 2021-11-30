package com.example.westen;

import java.io.Serializable;

public class ParticipaProjeto implements Serializable {
    private String Fk_Cpf;
    private int Fk_ProjetoID;

    public String getFk_Cpf() {
        return this.Fk_Cpf;
    }

    public void setFk_Cpf(String Fk_Cpf) {
        this.Fk_Cpf = Fk_Cpf;
    }

    public int getFk_ProjetoID() {
        return this.Fk_ProjetoID;
    }

    public void setFk_ProjetoID(int Fk_ProjetoID) {
        this.Fk_ProjetoID = Fk_ProjetoID;
    }
}