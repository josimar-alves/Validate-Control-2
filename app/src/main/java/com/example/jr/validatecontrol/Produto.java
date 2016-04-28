package com.example.jr.validatecontrol;

/**
 * Created by Jr on 28/04/2016.
 */
public class Produto {
    private String nome;
    private String data;
    private String descricao;

    public Produto(String nome, String descricao, String data) {
        this.nome = nome;
        this.data = data;
        this.descricao = descricao;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data.toString();
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String toString() {
        return "Produto: " + this.nome + " | Descrição: " + this.descricao + "| Data: " + this.data;
    }
}