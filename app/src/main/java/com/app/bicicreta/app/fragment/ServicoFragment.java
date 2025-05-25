package com.app.bicicreta.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.activity.CadastroPecaActivity;
import com.app.bicicreta.app.adapter.AdapterPeca;
import com.app.bicicreta.app.model.Peca;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicoFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Peca> pecas = new ArrayList<>();
    private Button buttonSalvar;
    private ImageView nadaExibirServicoImageView;
    private Context _context;
    public ServicoFragment(Context context) {
        _context = context;
    }
    public static ServicoFragment newInstance() {
        ServicoFragment fragment = new ServicoFragment(newInstance().getContext());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servico, container, false);
        iniciarComponentes(view);
        return view;
    }

    private void iniciarComponentes(View view){
        inicializarRecycleView(view);
        buttonSalvar = view.findViewById(R.id.buttonNovoServico);
        buttonSalvar.setOnClickListener(v -> handleCadastroPeca());
        nadaExibirServicoImageView = view.findViewById(R.id.nadaExibirServicoImageView);
        exibirMessageListaVazia();
    }

    private void inicializarRecycleView(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewServico);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        AdapterPeca adapter = new AdapterPeca(pecas, p -> {
            handleAtualizarPeca(p);
        });
        recyclerView.setAdapter(adapter);
    }

    private void exibirMessageListaVazia(){
        if(pecas.isEmpty()){
            nadaExibirServicoImageView.setVisibility(View.VISIBLE);
        }else{
            nadaExibirServicoImageView.setVisibility(View.GONE);
        }
    }

    private void handleCadastroPeca(){
        Intent cadastroPecaIntent = new Intent(getContext(), CadastroPecaActivity.class);
        startActivity(cadastroPecaIntent);
    }
    private void handleAtualizarPeca(Peca peca){
        Intent intent = new Intent(getContext(), CadastroPecaActivity.class);
        intent.putExtra("peca", peca);
        startActivity(intent);
    }


}