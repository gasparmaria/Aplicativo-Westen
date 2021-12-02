package com.example.westen;

import java.io.Serializable;

public class Cliente implements Serializable {

    private String  ClienteCNPJ,
            ClienteNome,
            ClienteDescricao,
            ClientePathLogo,
            ClienteComplementoEndereco,
            ClienteTelefone,
            ClienteBairro,
            ClienteCidade,
            ClienteUF,
            ClienteCEP,
            ClienteLogradouro;
    private int ClienteNumeroEndereco;


    public String getClienteCNPJ() {
        return this.ClienteCNPJ;
    }

    public void setClienteCNPJ(String ClienteCNPJ) {
        this.ClienteCNPJ = ClienteCNPJ;
    }

    public String getClienteNome() {
        return this.ClienteNome;
    }

    public void setClienteNome(String ClienteNome) {
        this.ClienteNome = ClienteNome;
    }

    public String getClienteDescricao() {
        return this.ClienteDescricao;
    }

    public void setClienteDescricao(String ClienteDescricao) {
        this.ClienteDescricao = ClienteDescricao;
    }

    public String getClientePathLogo() {
        return this.ClientePathLogo;
    }

    public void setClientePathLogo(String ClientePathLogo) {
        this.ClientePathLogo = ClientePathLogo;
    }

    public String getClienteComplementoEndereco() {
        return this.ClienteComplementoEndereco;
    }

    public void setClienteComplementoEndereco(String ClienteComplementoEndereco) {
        this.ClienteComplementoEndereco = ClienteComplementoEndereco;
    }

    public String getClienteTelefone() {
        return this.ClienteTelefone;
    }

    public void setClienteTelefone(String ClienteTelefone) {
        this.ClienteTelefone = ClienteTelefone;
    }

    public String getClienteBairro() {
        return this.ClienteBairro;
    }

    public void setClienteBairro(String ClienteBairro) {
        this.ClienteBairro = ClienteBairro;
    }

    public String getClienteCidade() {
        return this.ClienteCidade;
    }

    public void setClienteCidade(String ClienteCidade) {
        this.ClienteCidade = ClienteCidade;
    }

    public String getClienteUF() {
        return this.ClienteUF;
    }

    public void setClienteUF(String ClienteUF) {
        this.ClienteUF = ClienteUF;
    }

    public String getClienteCEP() {
        return this.ClienteCEP;
    }

    public void setClienteCEP(String ClienteCEP) {
        this.ClienteCEP = ClienteCEP;
    }

    public String getClienteLogradouro() {
        return this.ClienteLogradouro;
    }

    public void setClienteLogradouro(String ClienteLogradouro) {
        this.ClienteLogradouro = ClienteLogradouro;
    }

    public int getClienteNumeroEndereco() {
        return this.ClienteNumeroEndereco;
    }

    public void setClienteNumeroEndereco(int ClienteNumeroEndereco) {
        this.ClienteNumeroEndereco = ClienteNumeroEndereco;
    }

}