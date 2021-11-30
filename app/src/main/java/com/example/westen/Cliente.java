package com.example.westen;

import java.io.Serializable;

public class Cliente implements Serializable {

    private String  ClienteCNPJ,
            ClienteNome,
            ClienteDescricao,
            ClientePathLogo,
            ClienteComplementoEndereco,
            ClienteTelefone;
    private int ClienteNumeroEndereco;

    public String getClienteCNPJ() {
        return ClienteCNPJ;
    }

    public void setClienteCNPJ(String clienteCNPJ) {
        ClienteCNPJ = clienteCNPJ;
    }

    public String getClienteNome() {
        return ClienteNome;
    }

    public void setClienteNome(String clienteNome) {
        ClienteNome = clienteNome;
    }

    public String getClienteDescricao() {
        return ClienteDescricao;
    }

    public void setClienteDescricao(String clienteDescricao) {
        ClienteDescricao = clienteDescricao;
    }

    public String getClientePathLogo() {
        return ClientePathLogo;
    }

    public void setClientePathLogo(String clientePathLogo) {
        ClientePathLogo = clientePathLogo;
    }

    public String getClienteComplementoEndereco() {
        return ClienteComplementoEndereco;
    }

    public void setClienteComplementoEndereco(String clienteComplementoEndereco) {
        ClienteComplementoEndereco = clienteComplementoEndereco;
    }

    public String getClienteTelefone() {
        return ClienteTelefone;
    }

    public void setClienteTelefone(String clienteTelefone) {
        ClienteTelefone = clienteTelefone;
    }

    public int getClienteNumeroEndereco() {
        return ClienteNumeroEndereco;
    }

    public void setClienteNumeroEndereco(int clienteNumeroEndereco) {
        ClienteNumeroEndereco = clienteNumeroEndereco;
    }
}