package com.example.westen;

import java.io.Serializable;

public class Pessoa implements Serializable {

    private int PessoaID;
    private int PessoaNumeroEndereco;
    private String PessoaComplementoEndereco;
    private String PessoaTelefone;

    public int getPessoaID() {
        return this.PessoaID;
    }

    public void setPessoaID(int PessoaID) {
        this.PessoaID = PessoaID;
    }

    public int getPessoaNumeroEndereco() {
        return this.PessoaNumeroEndereco;
    }

    public void setPessoaNumeroEndereco(int PessoaNumeroEndereco) {
        this.PessoaNumeroEndereco = PessoaNumeroEndereco;
    }

    public String getPessoaComplementoEndereco() {
        return this.PessoaComplementoEndereco;
    }

    public void setPessoaComplementoEndereco(String PessoaComplementoEndereco) {
        this.PessoaComplementoEndereco = PessoaComplementoEndereco;
    }

    public String getPessoaTelefone() {
        return this.PessoaTelefone;
    }

    public void setPessoaTelefone(String PessoaTelefone) {
        this.PessoaTelefone = PessoaTelefone;
    }

}