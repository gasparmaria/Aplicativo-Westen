package com.example.westen;

import java.io.Serializable;

public class Estado implements Serializable {

    private int EstadoID;
    private String EstadoUF;

    public int getEstadoID() {
        return EstadoID;
    }

    public void setEstadoID(int estadoID) {
        EstadoID = estadoID;
    }

    public String getEstadoUF() {
        return EstadoUF;
    }

    public void setEstadoUF(String estadoUF) {
        EstadoUF = estadoUF;
    }

}
