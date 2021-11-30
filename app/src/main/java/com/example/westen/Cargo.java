package com.example.westen;

import java.io.Serializable;

public class Cargo implements Serializable {
    private int CargoID;
    private String CargoNome;


    public int getCargoID() {
        return this.CargoID;
    }

    public void setCargoID(int CargoID) {
        this.CargoID = CargoID;
    }

    public String getCargoNome() {
        return this.CargoNome;
    }

    public void setCargoNome(String CargoNome) {
        this.CargoNome = CargoNome;
    }
}