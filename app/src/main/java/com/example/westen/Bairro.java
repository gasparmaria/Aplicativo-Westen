package com.example.westen;

import java.io.Serializable;

public class Bairro implements Serializable {

    private int BairroID;
    private String BairroNome;

    public String getBairroNome() {
        return BairroNome;
    }

    public void setBairroNome(String bairroNome) {
        this.BairroNome = bairroNome;
    }

    public int getBairroID() {
        return BairroID;
    }

    public void setBairroID(int bairroID) {
        this.BairroID = bairroID;
    }
}