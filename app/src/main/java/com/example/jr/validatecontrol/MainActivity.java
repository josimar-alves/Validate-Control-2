package com.example.jr.validatecontrol;

/**
 * Created by Jr on 28/04/2016.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.alertdialogpro.AlertDialogPro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
            toolbar.setTitle("Validate Control");
        }

        Log.e("lol", "" + RESULT_OK);

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
                if (position == 1) {
                    listarProdutos();
                } else if (position == 2) {
                    listarProdutosVencidos();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public void adicionarProduto(View view) {
        EditText mNome = (EditText) findViewById(R.id.nome);
        DatePicker mData = (DatePicker) findViewById(R.id.data);
        View focus = null;

        String nome = mNome.getText().toString();
        String data = mData.getDayOfMonth() + "/" + mData.getMonth()+1 + "/" + mData.getYear();

        if (TextUtils.isEmpty(nome)) {
            mNome.setError("Campo Vazio");
            focus = mNome;
            focus.requestFocus();
        }
         else {
            Produto p = new Produto(nome, mData.getDayOfMonth(), mData.getMonth(), mData.getYear());
            GerenciadorBD gerenciadorBD = new GerenciadorBD(this);
            gerenciadorBD.addProduto(p);
            this.addNotification(p, mData.getDayOfMonth(), mData.getMonth(), mData.getYear());
            Toast.makeText(getBaseContext(), "O(a) " + nome + " foi Cadastrado(a)!", Toast.LENGTH_LONG).show();
            mNome.setText("");
        }
    }

    public void listarProdutos() {
        GerenciadorBD gerenciadorBD = new GerenciadorBD(this);
        final ArrayList<Produto> produtos = gerenciadorBD.getAllProdutos();

        List<Item> itens = new ArrayList<Item>();

        if (produtos == null) {
            itens.add(new Item(R.drawable.error, "Lista Vazia"));
        } else {
            for (Produto p : produtos) {
                itens.add(new Item(R.drawable.produto, p.getNome()));
            }
        }

        ListView listView = (ListView) findViewById(R.id.listaDeProdutos);
        listView.setAdapter(new ItemAdapter(this, itens));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialogPro.Builder builder = new AlertDialogPro.Builder(MainActivity.this);
                builder.setIcon(R.drawable.produto).
                        setTitle(produtos.get(position).getNome()).
                        setMessage("Validade: " + produtos.get(position).getData()).
                        setNeutralButton("Ok", null).
                        setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removerProduto(produtos.get(position).getNome());
                            }
                        }).
                        setNegativeButton("Editar", null).
                        show();
            }
        });
    }

    public void listarProdutosVencidos() {
        GerenciadorBD gerenciadorBD = new GerenciadorBD(this);
        ArrayList<Produto> produtos = gerenciadorBD.getAllProdutos();

        List<Item> itens = new ArrayList<Item>();

        GregorianCalendar calendar = new GregorianCalendar();
        int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        int mes = calendar.get(GregorianCalendar.MONTH);
        int ano = calendar.get(GregorianCalendar.YEAR);

        if (produtos != null) {
            boolean teste = false;
            for (Produto p : produtos) {
                if (p.getAno() < ano) {
                    teste = true;
                } else if (p.getAno() == ano) {
                    if (p.getMes() < mes) {
                        teste = true;
                    } else if (p.getMes() == mes) {
                        if (p.getDia() < dia) {
                            teste = true;
                        }
                    }
                }

                if (teste == true) {
                    itens.add(new Item(R.drawable.produto, p.getNome()));
                    teste = false;
                }
            }
        }

        if (itens.size() <= 0) {
            itens.add(new Item(R.drawable.error, "Lista Vazia"));
        }

        ListView listView = (ListView) findViewById(R.id.listaDeProdutosVencidos);
        listView.setAdapter(new ItemAdapter(this, itens));
    }

    public void removerProduto(String nome) {
        GerenciadorBD gerenciadorBD = new GerenciadorBD(this);
        gerenciadorBD.removerUsuario(nome);
        listarProdutos();
    }

    public void addNotification(Produto produto, int dia, int mes, int ano) {

        Intent intent = new Intent("ALARME_VALIDADE");
        intent.putExtra("nome", produto.getNome());

        PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar c = Calendar.getInstance();

        c.set(ano, mes, dia - 1, 12, 0, 0);

        AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarme.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), p);

    }
}