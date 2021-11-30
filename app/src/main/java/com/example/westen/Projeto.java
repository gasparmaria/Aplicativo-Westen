package com.example.westen;

import java.io.Serializable;
import java.util.Date;

public class Projeto implements Serializable {

    private int ProjetoID,
                FK_ServicoID,
                FK_ClienteID;

    private String ProjetoStatus,
                   ProjetoDescricao;

    private double ProjetoPreco;

    private Date ProjetoDataInicio,
                 ProjetoDataFinal;

    public int getProjetoID() {
        return ProjetoID;
    }

    public void setProjetoID(int projetoID) {
        ProjetoID = projetoID;
    }

    public int getFK_ServicoID() {
        return FK_ServicoID;
    }

    public void setFK_ServicoID(int FK_ServicoID) {
        this.FK_ServicoID = FK_ServicoID;
    }

    public int getFK_ClienteID() {
        return FK_ClienteID;
    }

    public void setFK_ClienteID(int FK_ClienteID) {
        this.FK_ClienteID = FK_ClienteID;
    }

    public String getProjetoStatus() {
        return ProjetoStatus;
    }

    public void setProjetoStatus(String projetoStatus) {
        ProjetoStatus = projetoStatus;
    }

    public String getProjetoDescricao() {
        return ProjetoDescricao;
    }

    public void setProjetoDescricao(String projetoDescricao) {
        ProjetoDescricao = projetoDescricao;
    }

    public double getProjetoPreco() {
        return ProjetoPreco;
    }

    public void setProjetoPreco(double projetoPreco) {
        ProjetoPreco = projetoPreco;
    }

    public Date getProjetoDataInicio() {
        return ProjetoDataInicio;
    }

    public void setProjetoDataInicio(Date projetoDataInicio) {
        ProjetoDataInicio = projetoDataInicio;
    }

    public Date getProjetoDataFinal() {
        return ProjetoDataFinal;
    }

    public void setProjetoDataFinal(Date projetoDataFinal) {
        ProjetoDataFinal = projetoDataFinal;
    }
}
