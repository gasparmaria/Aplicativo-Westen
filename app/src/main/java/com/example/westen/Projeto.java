package com.example.westen;

import java.io.Serializable;

public class Projeto implements Serializable {

    private int ProjetoID;

    private String ProjetoStatus,
            ProjetoDescricao,
            ProjetoServico,
            ProjetoDataInicio,
            ProjetoDataFinal,
            FK_ClienteCNPJ;

    public Projeto() {
    }


    public Projeto(String projetoStatus, String projetoDescricao, String projetoServico, String projetoDataInicio, String projetoDataFinal, String FK_ClienteCNPJ) {
        ProjetoStatus = projetoStatus;
        ProjetoDescricao = projetoDescricao;
        ProjetoServico = projetoServico;
        ProjetoDataInicio = projetoDataInicio;
        ProjetoDataFinal = projetoDataFinal;
        this.FK_ClienteCNPJ = FK_ClienteCNPJ;

    }

    public Projeto(String projetoServico) {
        ProjetoServico = projetoServico;
    }

    public int getProjetoID() {
        return ProjetoID;
    }

    public void setProjetoID(int projetoID) {
        ProjetoID = projetoID;
    }

    public String getFK_ClienteCNPJ() {
        return FK_ClienteCNPJ;
    }

    public void setFK_ClienteCNPJ(String FK_ClienteCNPJ) {
        this.FK_ClienteCNPJ = FK_ClienteCNPJ;
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

    public String getProjetoServico() {
        return ProjetoServico;
    }

    public void setProjetoServico(String projetoServico) {
        ProjetoServico = projetoServico;
    }

    public String getProjetoDataInicio() {
        return ProjetoDataInicio;
    }

    public void setProjetoDataInicio(String projetoDataInicio) {
        ProjetoDataInicio = projetoDataInicio;
    }

    public String getProjetoDataFinal() {
        return ProjetoDataFinal;
    }

    public void setProjetoDataFinal(String projetoDataFinal) {
        ProjetoDataFinal = projetoDataFinal;
    }
}
