package com.app.bicicreta.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bicicreta.R;
import com.app.bicicreta.app.model.Bicicleta;
import com.app.bicicreta.app.model.Peca;
import com.app.bicicreta.app.utils.MoedaUtil;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterPeca extends RecyclerView.Adapter<AdapterPeca.PecaViewHolder>{
    private List<Peca> pecas;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void deleteItem(Peca item);
        void editItem(Peca item);
    }
    public AdapterPeca(List<Peca> pecas, OnItemClickListener listener) {
        this.pecas = pecas;
        this.listener = listener;
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
        holder.valor.setText(MoedaUtil.convertToBR(peca.getValor()));
        holder.quilometros.setText(peca.getQuilometros() + " Km");
        holder.bind(peca, listener);
    }

    @Override
    public int getItemCount() {
        return pecas.size();
    }

    public class PecaViewHolder extends RecyclerView.ViewHolder{
        TextView dataCompra,  valor,  quilometros, nomePeca;
        ImageView editPecaImagemView, deletePecaImagemView;
        public PecaViewHolder(@NonNull View itemView) {
            super(itemView);
            dataCompra = itemView.findViewById(R.id.dataServicoViewHolder);
            valor = itemView.findViewById(R.id.valorServicoViewHolder);
            quilometros = itemView.findViewById(R.id.quilometroServicoViewHolder);
            nomePeca = itemView.findViewById(R.id.descricaoServicoViewHolder);
            editPecaImagemView = itemView.findViewById(R.id.editPecaImagemView);
            deletePecaImagemView = itemView.findViewById(R.id.deletePecaImagemView);
        }
        public void bind (final Peca item, OnItemClickListener listener){
            editPecaImagemView.setOnClickListener( v -> listener.editItem(item));
            deletePecaImagemView.setOnClickListener( v -> listener.deleteItem(item));
        }
    }
}
