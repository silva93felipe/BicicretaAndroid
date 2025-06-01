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
import com.app.bicicreta.app.activity.CadastroServicoActivity;
import com.app.bicicreta.app.adapter.AdapterPeca;
import com.app.bicicreta.app.adapter.AdapterServico;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.model.Servico;
import com.app.bicicreta.app.repository.PecaRepository;
import com.app.bicicreta.app.repository.ServicoRepository;

import java.util.ArrayList;
import java.util.List;

public class ServicoFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Servico> servicos = new ArrayList<>();
    private Button buttonSalvar;
    private ImageView nadaExibirServicoImageView;
    private Context _context;
    private View _view;
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
        _view = view;
        iniciarComponentes(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        iniciarComponentes(_view);
    }

    private void iniciarComponentes(View view){
        inicializarRecycleView(view);
        buttonSalvar = view.findViewById(R.id.buttonNovoServico);
        buttonSalvar.setOnClickListener(v -> handleCadastroServico());
        nadaExibirServicoImageView = view.findViewById(R.id.nadaExibirServicoImageView);
    }

    private void inicializarRecycleView(View view){
        getAllServicos();
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewServico);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        AdapterServico adapter = new AdapterServico(servicos, p -> {
            handleAtualizarServico(p);
        });
        recyclerView.setAdapter(adapter);
        exibirMessageListaVazia();
    }

    private void exibirMessageListaVazia(){
        nadaExibirServicoImageView.setVisibility(View.GONE);
        if(servicos.isEmpty()){
            nadaExibirServicoImageView.setVisibility(View.VISIBLE);
        }
    }

    private void getAllServicos(){
        ServicoRepository repository = new ServicoRepository(_context);
        servicos = repository.getAllWithBicicleta();
    }

    private void handleCadastroServico(){
        Intent cadastroPecaIntent = new Intent(getContext(), CadastroServicoActivity.class);
        startActivity(cadastroPecaIntent);
    }
    private void handleAtualizarServico(Servico servico){
        Intent intent = new Intent(getContext(), CadastroServicoActivity.class);
        intent.putExtra("servico", servico);
        startActivity(intent);
    }
}