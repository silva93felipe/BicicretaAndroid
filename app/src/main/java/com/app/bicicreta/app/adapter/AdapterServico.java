package com.app.bicicreta.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Servico;
import com.app.bicicreta.app.utils.MoedaUtil;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterServico extends RecyclerView.Adapter<AdapterServico.ServicoViewHolder>{
    private List<Servico> servicos;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Servico item);
    }
    public AdapterServico(List<Servico> servicos, OnItemClickListener listener) {
        this.servicos = servicos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ServicoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.servico_item, parent, false);
        return new AdapterServico.ServicoViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicoViewHolder holder, int position) {
        Servico servico = servicos.get(position);
        holder.dataServico.setText(servico.getDataServico());
        holder.nomePeca.setText(servico.getDescricao());
        holder.valorServico.setText(MoedaUtil.convertToBR(servico.getValor()));
        holder.nomeBicicleta.setText(servico.getModeloBicicleta());
        holder.quilometros.setText(servico.getQuilometros() + " Km");
        holder.bind(servico, listener);
    }

    @Override
    public int getItemCount() {
        return servicos.size();
    }

    public class ServicoViewHolder extends RecyclerView.ViewHolder{
        TextView dataServico;
        TextView valorServico;
        TextView quilometros;
        TextView nomeBicicleta;
        TextView nomePeca;
        public ServicoViewHolder(@NonNull View itemView) {
            super(itemView);
            dataServico = itemView.findViewById(R.id.dataServicoViewHolder);
            valorServico = itemView.findViewById(R.id.valorServicoViewHolder);
            quilometros = itemView.findViewById(R.id.quilometroServicoViewHolder);
            nomeBicicleta = itemView.findViewById(R.id.nomeBicicletaServicoViewHolder);
            nomePeca = itemView.findViewById(R.id.descricaoServicoViewHolder);
        }
        public void bind (final Servico item, OnItemClickListener listener){
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
