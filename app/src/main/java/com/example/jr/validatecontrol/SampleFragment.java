package com.example.jr.validatecontrol;

/**
 * Created by Jr on 28/04/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SampleFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static SampleFragment newInstance(int position) {
        SampleFragment f = new SampleFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        position = getArguments().getInt(ARG_POSITION);
        View rootView = null;
        if (position == 0){
            rootView = inflater.inflate(R.layout.adicionar_produto, container, false);
        } else if (position == 1){
            rootView = inflater.inflate(R.layout.list_produtos, container, false);
        } else {
            rootView = inflater.inflate(R.layout.list_produtos_vencidos, container, false);
        }
        return rootView;
    }
}