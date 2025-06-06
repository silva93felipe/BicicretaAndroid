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
        void deleteItem(Viagem item);
        void editItem(Viagem item);
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
        ImageView deleteImagemView;
        ImageView editImagemView;

        public ViagemViewHolder(@NonNull View itemView) {
            super(itemView);
            dataViagem = itemView.findViewById(R.id.dataServicoViewHolder);
            quilometrosRodados = itemView.findViewById(R.id.quilometroServicoViewHolder);
            destino = itemView.findViewById(R.id.descricaoServicoViewHolder);
            nomeBicicleta = itemView.findViewById(R.id.nomeBicicletaServicoViewHolder);
            deleteImagemView = itemView.findViewById(R.id.deleteViagemImagemView);
            editImagemView = itemView.findViewById(R.id.editViagemImagemView);

        }
        public void bind (final Viagem item, OnItemClickListener listener){
            itemView.findViewById(R.id.deleteViagemImagemView).setOnClickListener( v -> listener.deleteItem(item));
            itemView.findViewById(R.id.editViagemImagemView).setOnClickListener( v -> listener.editItem(item));
        }
    }
}
