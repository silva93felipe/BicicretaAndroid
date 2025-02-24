package com.app.bicicreta.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.Peca;

import java.util.ArrayList;
import java.util.List;

public class AdapterBicicleta extends RecyclerView.Adapter<AdapterBicicleta.BicicletaViewHolder> {
    private List<Bicicleta> bicicletas;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Bicicleta item);
    }

    public AdapterBicicleta(List<Bicicleta> bicicletas, OnItemClickListener listener) {
        this.bicicletas = bicicletas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BicicletaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.bicicleta_item, parent, false);
        return new AdapterBicicleta.BicicletaViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull BicicletaViewHolder holder, int position) {
        Bicicleta bicicleta = bicicletas.get(position);
        holder.modelo.setText(bicicleta.getModelo());
        holder.tamanhoAro.setText(bicicleta.getAro() + "'");
        holder.tamanhoQuadro.setText(bicicleta.getTamanhoQuadro() +"'");
        holder.quantidadeMarchas.setText(String.valueOf(bicicleta.getQuantidadeMarchas()));
        holder.quilometrosRodados.setText(bicicleta.getQuilometrosRodados() + " Km");
        holder.bind(bicicleta, listener);
    }

    @Override
    public int getItemCount() {
        return bicicletas.size();
    }

    public class BicicletaViewHolder extends RecyclerView.ViewHolder {
        TextView modelo;
        TextView tamanhoAro;
        TextView tamanhoQuadro;
        TextView quantidadeMarchas;
        TextView quilometrosRodados;

        public BicicletaViewHolder(@NonNull View itemView) {
            super(itemView);
            modelo = itemView.findViewById(R.id.modeloTextViewHolder);
            tamanhoAro = itemView.findViewById(R.id.aroTextViewHolder);
            tamanhoQuadro = itemView.findViewById(R.id.tamanhoQuadroTextViewHolder);
            quantidadeMarchas = itemView.findViewById(R.id.marchasTextViewHolder);
            quilometrosRodados = itemView.findViewById(R.id.quilometrosTextViewHolder);
        }

        public void bind (final Bicicleta item, OnItemClickListener listener){
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}