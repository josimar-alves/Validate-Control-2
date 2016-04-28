package com.example.jr.validatecontrol;

/**
 * Created by Jr on 28/04/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {

    private static String itensTabela = "CREATE TABLE produto (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, descricao TEXT, validade TEXT)";

    public BancoDeDados(Context context) {
        super(context, "validateControl", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(itensTabela);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
