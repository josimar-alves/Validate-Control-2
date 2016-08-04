package com.example.jr.validatecontrol;

/**
 * Created by Jr on 28/04/2016.
 */
public class Produto {
    private String nome;
    private int dia;
    private int mes;
    private int ano;

    public Produto(String nome, int dia, int mes, int ano) {
        this.nome = nome;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getData() {
        return this.dia + "/" + (this.mes+1) + "/" + this.ano;
    }

    public String toString() {
        return "Produto: " + this.nome + " | Data: " + this.dia + "/" + (this.mes+1) + "/" + this.ano;
    }
}