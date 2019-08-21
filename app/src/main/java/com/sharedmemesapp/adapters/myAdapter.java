package com.sharedmemesapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharedmemesapp.R;
import com.sharedmemesapp.entities.Imagen;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    public LinkedList<Imagen> imagenes;
    private int layout;
    private Context context;

    public myAdapter(LinkedList<Imagen> imagenes, int layout) {
        this.imagenes = imagenes;
        this.layout = layout;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        context = parent.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(imagenes.get(position));
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewTitulo)
        TextView titulo;

        @BindView(R.id.imageViewImagen)
        ImageView imgView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Imagen imagen) {

            titulo.setText(imagen.getName());
            Picasso.get().load(imagen.getUrl()).into(imgView);
        }
    }
}
