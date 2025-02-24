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
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Viagem item);
    }
    public AdapterViagem(List<Viagem> viagens, OnItemClickListener listener) {
        this.viagens = viagens;
        this.listener = listener;
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
        holder.nomeBicicleta.setText(viagem.getModeloBicicleta());
        holder.quilometrosRodados.setText(viagem.getQuilometros() + " Km");
        holder.bind(viagem, listener);
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
            dataViagem = itemView.findViewById(R.id.dataCompraPecaViewHolder);
            quilometrosRodados = itemView.findViewById(R.id.quilometroPecaViewHolder);
            destino = itemView.findViewById(R.id.nomePecaViewHolder);
            nomeBicicleta = itemView.findViewById(R.id.nomeBicicletaPecaViewHolder);
        }
        public void bind (final Viagem item, OnItemClickListener listener){
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
