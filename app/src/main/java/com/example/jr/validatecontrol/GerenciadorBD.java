package com.example.jr.validatecontrol;

/**
 * Created by Jr on 28/04/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GerenciadorBD {
    private static BancoDeDados bd = null;

    public GerenciadorBD (Context c) {
        if (bd == null) {
            bd = new BancoDeDados (c);
        }
    }

    public void addProduto (Produto p) {
        SQLiteDatabase database = this.bd.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", p.getNome());
        values.put("descricao", p.getDescricao());
        values.put("validade", p.getData());
        database.insert("produto", null, values);
    }

    public ArrayList <Produto> getAllProdutos () {
        ArrayList<Produto> usuarios = null;
        SQLiteDatabase database = bd.getWritableDatabase();
        String sql = "SELECT nome, descricao, validade FROM produto";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            usuarios = new ArrayList<Produto>();

            do {
                usuarios.add(new Produto(cursor.getString(0), cursor.getString(2), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return usuarios;
    }


//    public void addUsuario(Usuario u) {
//        SQLiteDatabase database = this.bd.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("nome", u.getNome());
//        values.put("senha", u.getSenha());
//        database.insert("usuario", null, values);
//    }
//
//    public void removerUsuario(String nome) {
//        SQLiteDatabase database = this.bd.getWritableDatabase();
//        database.execSQL("DELETE FROM usuario WHERE nome = '" + nome + "'");
//    }
}
