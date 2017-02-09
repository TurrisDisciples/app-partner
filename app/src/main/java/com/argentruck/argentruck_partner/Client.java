package com.argentruck.argentruck_partner;


import java.io.Serializable;

public class Client implements Serializable {

    private String id;
    private String nombreYApellido;
    private String telefono;
    private String direccion;
    private String mail;
    private String capacidadContratada;
    private int imagen;

    public Client(String id, String nombreYApellido, String telefono, String direccion, String mail, String capacidadContratada, int imagen) {
        this.id = id;
        this.nombreYApellido = nombreYApellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.mail = mail;
        this.capacidadContratada = capacidadContratada;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreYApellido() {
        return nombreYApellido;
    }

    public void setNombreYApellido(String nombreYApellido) {
        this.nombreYApellido = nombreYApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCapacidadContratada() {
        return capacidadContratada;
    }

    public void setCapacidadContratada(String capacidadContratada) {
        this.capacidadContratada = capacidadContratada;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "le erraste";
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

