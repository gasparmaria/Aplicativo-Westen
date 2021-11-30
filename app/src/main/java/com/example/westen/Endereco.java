package com.example.westen;

import java.io.Serializable;

public class Endereco implements Serializable {

    private String Cep,
                   Logradouro;

    private int FK_BairroID,
                FK_CidadeID,
                FK_EstadoID;

    public String getCep() {
        return Cep;
    }

    public void setCep(String cep) {
        Cep = cep;
    }

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String logradouro) {
        Logradouro = logradouro;
    }

    public int getFK_BairroID() {
        return FK_BairroID;
    }

    public void setFK_BairroID(int FK_BairroID) {
        this.FK_BairroID = FK_BairroID;
    }

    public int getFK_CidadeID() {
        return FK_CidadeID;
    }

    public void setFK_CidadeID(int FK_CidadeID) {
        this.FK_CidadeID = FK_CidadeID;
    }

    public int getFK_EstadoID() {
        return FK_EstadoID;
    }

    public void setFK_EstadoID(int FK_EstadoID) {
        this.FK_EstadoID = FK_EstadoID;
    }
}