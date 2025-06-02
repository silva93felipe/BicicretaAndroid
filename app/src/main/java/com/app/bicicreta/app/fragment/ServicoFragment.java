package com.app.bicicreta.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.activity.CadastroPecaActivity;
import com.app.bicicreta.app.activity.CadastroServicoActivity;
import com.app.bicicreta.app.adapter.AdapterPeca;
import com.app.bicicreta.app.adapter.AdapterServico;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.ItemSpinner;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.model.Servico;
import com.app.bicicreta.app.repository.BicicletaRepository;
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
    private Spinner bicicletaSpinner;
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

    private List<Bicicleta> getAllBicicletas(){
        BicicletaRepository repository = new BicicletaRepository(_context);
        return repository.getAll();
    }

    private void getAllByBicicletaId(int id){
        ServicoRepository repository = new ServicoRepository(_context);
        servicos = repository.getAllByBicicletaId(id);
    }

    private void iniciarSpinnerBicicleta(){
        bicicletaSpinner = _view.findViewById(R.id.bicicletaSpinnerServico);
        List<ItemSpinner> itemList = new ArrayList<>();
        itemList.add(new ItemSpinner(0, "Selecione uma bicicleta"));
        for (Bicicleta bicicleta : getAllBicicletas()){
            itemList.add(new ItemSpinner(bicicleta.getId(), bicicleta.getModelo()));
        }
        ArrayAdapter<ItemSpinner> adapter = new ArrayAdapter<ItemSpinner>(_context, android.R.layout.simple_spinner_item, itemList){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);

                }else {
                    tv.setTextColor(Color.YELLOW);
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bicicletaSpinner.setAdapter(adapter);
        bicicletaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ItemSpinner item = (ItemSpinner)parent.getItemAtPosition(position);
                if(position > 0){
                    int bicicletaId = item.getId();
                    getAllByBicicletaId(bicicletaId);
                    inicializarRecycleView(_view);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private void iniciarComponentes(View view){
        inicializarRecycleView(view);
        iniciarSpinnerBicicleta();
        buttonSalvar = view.findViewById(R.id.buttonNovoServico);
        buttonSalvar.setOnClickListener(v -> handleCadastroServico());
    }

    private void inicializarRecycleView(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewServico);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        AdapterServico adapter = new AdapterServico(servicos, p -> {
            handleAtualizarServico(p);
        });
        recyclerView.setAdapter(adapter);
        exibirMessageListaVazia(view);
    }

    private void exibirMessageListaVazia(View view){
        nadaExibirServicoImageView = view.findViewById(R.id.nadaExibirServicoImageView);
        nadaExibirServicoImageView.setVisibility(View.GONE);
        if(servicos.isEmpty()){
            nadaExibirServicoImageView.setVisibility(View.VISIBLE);
        }
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