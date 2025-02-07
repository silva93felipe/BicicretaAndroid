package com.app.bicicreta.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Viagem;

import java.util.List;

public class AdapterViagem extends RecyclerView.Adapter<AdapterViagem.ViagemViewHolder> {
    private List<Viagem> viagens;
    public AdapterViagem(List<Viagem> viagens) {
        this.viagens = viagens;
    }

    @NonNull
    @Override
    public ViagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.viagem_item, parent, false);
        return new ViagemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViagemViewHolder holder, int position) {
        Viagem viagem = viagens.get(position);
        holder.dataViagem.setText(viagem.getData());
        holder.destino.setText(viagem.getDestino());
        holder.nomeBicicleta.setText(viagem.getNomeBicicleta());
        holder.quilometrosRodados.setText(viagem.getQuilometros());
    }

    @Override
    public int getItemCount() {
        return viagens.size();
    }

    public class ViagemViewHolder extends RecyclerView.ViewHolder{
        TextView dataViagem;
        TextView quilometrosRodados;
        TextView destino;
        TextView nomeBicicleta;
        ImageView icone;

        public ViagemViewHolder(@NonNull View itemView) {
            super(itemView);
            dataViagem = itemView.findViewById(R.id.dataCompraViewHolderTextView);
            quilometrosRodados = itemView.findViewById(R.id.quilometroPecaViewHolderTextView);
            destino = itemView.findViewById(R.id.nomePecaViewHolderTextView);
            nomeBicicleta = itemView.findViewById(R.id.nomeBicicletaPecaViewHolderTextView);
        }
    }
}
