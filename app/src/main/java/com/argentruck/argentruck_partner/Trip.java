package com.argentruck.argentruck_partner;

import java.io.Serializable;
import java.util.List;

public class Trip implements Serializable {

    private String id;
    private String origen;
    private String destino;
    private String capacidad;
    private String fecha;
    private List<Client> clientList;

    public Trip(String id, String origen, String destino, String capacidad, String fecha, List<Client> clientsList) {

        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.capacidad = capacidad;
        this.fecha = fecha.substring(8, 10) + "/" + fecha.substring(5, 7) + "/" + fecha.substring(0, 4) ;
        this.clientList = clientsList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(String fecha) {
        this.clientList = clientList;
    }

    @Override
    public String toString() {
        return "vacio";
//        return "Lead{" +
//                "ID='" + mId + '\'' +
//                ", Compañía='" + mCompany + '\'' +
//                ", Nombre='" + mName + '\'' +
//                ", Cargo='" + mTitle + '\'' +
//                ", Origen='" + origen + '\'' +
//                ", Destino='" + destino + '\'' +
//                ", Capacidad='" + mCapacidad+ '\'' +
//                ", Fecha='" + mFecha+ '\'' +
//                '}';
    }
}

