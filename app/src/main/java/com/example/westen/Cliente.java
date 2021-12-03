package com.example.westen;

import java.io.Serializable;

public class Cliente implements Serializable {

    private String  ClienteCNPJ,
            ClienteNome,
            ClienteDescricao,
            ClienteComplementoEndereco,
            ClienteTelefone,
            ClienteBairro,
            ClienteCidade,
            ClienteUF,
            ClienteCEP,
            ClienteLogradouro;
    private int ClienteNumeroEndereco;
    private byte[] ClienteImagem;

    public Cliente(){ }

    public Cliente(String clienteNome, byte[] clienteImagem) {
        ClienteNome = clienteNome;
        ClienteImagem = clienteImagem;
    }

    public Cliente(String clienteCNPJ,
                   String clienteNome,
                   String clienteDescricao,
                   String clienteComplementoEndereco,
                   String clienteTelefone,
                   String clienteBairro,
                   String clienteCidade,
                   String clienteUF,
                   String clienteCEP,
                   String clienteLogradouro,
                   int clienteNumeroEndereco,
                   byte[] clienteImagem) {
            ClienteCNPJ = clienteCNPJ;
            ClienteNome = clienteNome;
            ClienteDescricao = clienteDescricao;
            ClienteComplementoEndereco = clienteComplementoEndereco;
            ClienteTelefone = clienteTelefone;
            ClienteBairro = clienteBairro;
            ClienteCidade = clienteCidade;
            ClienteUF = clienteUF;
            ClienteCEP = clienteCEP;
            ClienteLogradouro = clienteLogradouro;
            ClienteNumeroEndereco = clienteNumeroEndereco;
            ClienteImagem = clienteImagem;
    }

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

    public byte[] getClienteImagem() {
        return ClienteImagem;
    }

    public void setClienteImagem(byte[] clienteImagem) {
        ClienteImagem = clienteImagem;
    }
}