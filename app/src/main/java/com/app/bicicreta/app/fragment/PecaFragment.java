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
import android.widget.TextView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.activity.CadastroPecaActivity;
import com.app.bicicreta.app.activity.PecasActivity;
import com.app.bicicreta.app.adapter.AdapterPeca;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.repository.PecaRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PecaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PecaFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Peca> pecas = new ArrayList<>();
    private Button buttonSalvar;
    private ImageView nadaExibirPecaImageView;
    private Context _context;
    public PecaFragment(Context context) {
        _context = context;
    }
    public static PecaFragment newInstance() {
        PecaFragment fragment = new PecaFragment(newInstance().getContext());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllPecas();
    }

    private void getAllPecas(){
        PecaRepository repository = new PecaRepository(_context);
        pecas = repository.getAllWithBicicleta();
    }

    private void inicializarRecycleView(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewPeca);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        AdapterPeca adapter = new AdapterPeca(pecas, p -> {
            handleAtualizarPeca(p);
        });
        recyclerView.setAdapter(adapter);
    }

    private void iniciarComponentes(View view){
        inicializarRecycleView(view);
        buttonSalvar = view.findViewById(R.id.buttonNovaPeca);
        buttonSalvar.setOnClickListener(v -> handleCadastroPeca());
        nadaExibirPecaImageView = view.findViewById(R.id.nadaExibirPecaImageView);
        exibirMessageListaVazia();
    }

    private void exibirMessageListaVazia(){
        if(pecas.isEmpty()){
            nadaExibirPecaImageView.setVisibility(View.VISIBLE);
        }else{
            nadaExibirPecaImageView.setVisibility(View.GONE);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peca, container, false);
        iniciarComponentes(view);
        inicializarRecycleView(view);
        return view;
    }
}