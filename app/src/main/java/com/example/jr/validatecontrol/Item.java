package com.example.jr.validatecontrol;

/**
 * Created by Jr on 28/04/2016.
 */
public class Item {
    private int idImage;
    private String nome;

    public Item(int idImage, String nome) {
        this.idImage = idImage;
        this.nome = nome;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

