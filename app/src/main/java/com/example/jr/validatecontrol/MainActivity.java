package com.example.jr.validatecontrol;

/**
 * Created by Jr on 28/04/2016.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ViewPager pager;
    private String titles[] = new String[]{"Adicionar Produto", "Produtos Cadastrados", "Produtos Vencidos"};
    private Toolbar toolbar;

    private SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        pager = (ViewPager) findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles));

        slidingTabLayout.setViewPager(pager);

        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });

        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                Log.e("Jesus", " " + position);
                if (position == 1) {
                    listarProdutos();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public void adicionarProduto(View view) {
        EditText mNome = (EditText) findViewById(R.id.nome);
        EditText mDescricao = (EditText) findViewById(R.id.descricao);
        DatePicker mData = (DatePicker) findViewById(R.id.data);
        View focus = null;

        String nome = mNome.getText().toString();
        String descricao = mDescricao.getText().toString();
        String data = mData.getDayOfMonth() + "/" + mData.getMonth() + "/" + mData.getYear();

        if (TextUtils.isEmpty(nome)) {
            mNome.setError("Campo Vazio");
            focus = mNome;
            focus.requestFocus();
        }
        if (TextUtils.isEmpty(descricao)) {
            mDescricao.setError("Campo Vazio");
            focus = mDescricao;
            focus.requestFocus();
        } else {
            Produto p = new Produto(nome, descricao, data);
            GerenciadorBD gerenciadorBD = new GerenciadorBD(this);
            gerenciadorBD.addProduto(p);
            Toast.makeText(getBaseContext(), "O(a) " + nome + " foi Cadastrado(a)!", Toast.LENGTH_LONG).show();
            mNome.setText("");
            mDescricao.setText("");
        }
    }

    public void listarProdutos() {
        GerenciadorBD gerenciadorBD = new GerenciadorBD(this);
        ArrayList<Produto> produtos = gerenciadorBD.getAllProdutos();

        List<Item> itens = new ArrayList<Item>();

        for (Produto p: produtos) {
            itens.add(new Item(R.drawable.splash, p.getNome()));
        }

        ListView listView = (ListView) findViewById(R.id.listaDeProdutos);
        listView.setAdapter(new ItemAdapter(this, itens));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Teste", ""+position);
            }
        });
    }

}
