package com.example.westen;

import java.io.Serializable;

public class Cidade implements Serializable {

    private int CidadeID;
    private String CidadeNome;

    public int getCidadeID() {
        return CidadeID;
    }

    public void setCidadeID(int cidadeID) {
        this.CidadeID = cidadeID;
    }

    public String getCidadeNome() {
        return CidadeNome;
    }

    public void setCidadeNome(String cidadeNome) {
        this.CidadeNome = cidadeNome;
    }
}