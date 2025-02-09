package com.app.bicicreta.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Peca;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterPeca extends RecyclerView.Adapter<AdapterPeca.PecaViewHolder> {
    private List<Peca> pecas = new ArrayList<>();
    Locale localBrasil = new Locale("pt", "BR");
    public AdapterPeca(List<Peca> pecas) {
        this.pecas = pecas;
    }

    @NonNull
    @Override
    public PecaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.peca_item, parent, false);
        return new AdapterPeca.PecaViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PecaViewHolder holder, int position) {
        Peca peca = pecas.get(position);
        holder.dataCompra.setText(peca.getDataCompra());
        holder.nomePeca.setText(peca.getNomePeca());
        holder.valor.setText(NumberFormat.getCurrencyInstance(localBrasil).format(peca.getValor()));
        holder.nomeBicicleta.setText(peca.getModeloBicicleta());
        holder.quilometros.setText(peca.getQuilometros() + " Km");
    }

    @Override
    public int getItemCount() {
        return pecas.size();
    }

    public class PecaViewHolder extends RecyclerView.ViewHolder{
        TextView dataCompra;
        TextView valor;
        TextView quilometros;
        TextView nomeBicicleta;
        TextView nomePeca;
        public PecaViewHolder(@NonNull View itemView) {
            super(itemView);
            dataCompra = itemView.findViewById(R.id.dataCompraPecaViewHolder);
            valor = itemView.findViewById(R.id.valorPecaViewHolder);
            quilometros = itemView.findViewById(R.id.quilometroPecaViewHolder);
            nomeBicicleta = itemView.findViewById(R.id.nomeBicicletaPecaViewHolder);
            nomePeca = itemView.findViewById(R.id.nomePecaViewHolder);
        }
    }
}
