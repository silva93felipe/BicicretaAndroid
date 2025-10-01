package com.app.bicicreta.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Troca;

import java.util.List;

public class AdapterTroca extends RecyclerView.Adapter<AdapterTroca.TrocaViewHolder>{
    private List<Troca> trocas;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void deleteItem(Troca item);
    }
    public AdapterTroca(List<Troca> trocas, OnItemClickListener listener) {
        this.trocas = trocas;
        this.listener = listener;
    }
    @NonNull
    @Override
    public AdapterTroca.TrocaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.troca_item, parent, false);
        return new AdapterTroca.TrocaViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull TrocaViewHolder holder, int position) {
        Troca troca = trocas.get(position);
        holder.dataTrocaTextViewHolder.setText(troca.getData());
        holder.quilometroTrocaTextViewHolder.setText(troca.getQuilometros() + " Km");
        holder.bind(troca, listener);
    }

    @Override
    public int getItemCount() {
        return  this.trocas.size();
    }

    public class TrocaViewHolder extends RecyclerView.ViewHolder{
        TextView dataTrocaTextViewHolder,  quilometroTrocaTextViewHolder;
        ImageView deleteTrocaImagemView;
        public TrocaViewHolder(@NonNull View itemView) {
            super(itemView);
            dataTrocaTextViewHolder = itemView.findViewById(R.id.dataTrocaTextViewHolder);
            quilometroTrocaTextViewHolder = itemView.findViewById(R.id.quilometroTrocaTextViewHolder);
            deleteTrocaImagemView = itemView.findViewById(R.id.deleteTrocaImagemView);
        }
        public void bind (final Troca item, OnItemClickListener listener){
            deleteTrocaImagemView.setOnClickListener( v -> listener.deleteItem(item));
        }
    }
}