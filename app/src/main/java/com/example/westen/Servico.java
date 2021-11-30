package com.example.westen;

import java.io.Serializable;

public class Servico implements Serializable {
    private int ServicoID;
    private String ServicoNome;


    public int getServicoID() {
        return this.ServicoID;
    }

    public void setServicoID(int ServicoID) {
        this.ServicoID = ServicoID;
    }

    public String getServicoNome() {
        return this.ServicoNome;
    }

    public void setServicoNome(String ServicoNome) {
        this.ServicoNome = ServicoNome;
    }
}