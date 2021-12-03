package com.example.westen.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.westen.Cliente;
import com.example.westen.Projeto;
import com.example.westen.R;

import org.w3c.dom.Text;

import java.util.List;

public class GridProjetosAdapter extends BaseAdapter {
    private int layout;
    private Context context;
    List<Cliente> listaClientes;
    List<Projeto> listaProjetos;

    public GridProjetosAdapter(Context context, int layout, List<Cliente> listaClientes, List<Projeto> listaProjetos) {
        this.layout = layout;
        this.context = context;
        this.listaClientes = listaClientes;
        this.listaProjetos = listaProjetos;
    }

    @Override
    public int getCount() { return listaProjetos.size(); }

    @Override
    public Object getItem(int position) {
        return listaProjetos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtClienteNome, txtProjetoServico;
        ImageView imgClienteLogotipo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GridProjetosAdapter.ViewHolder holder = new GridProjetosAdapter.ViewHolder();

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtClienteNome = (TextView) row.findViewById(R.id.nomeCliente_ProjetosLista);
            holder.txtProjetoServico = (TextView) row.findViewById(R.id.nomeServico_ProjetosLista);
            holder.imgClienteLogotipo = (ImageView) row.findViewById(R.id.imageCliente_ProjetosLista);
            row.setTag(holder);
        }
        else
        {
            holder = (GridProjetosAdapter.ViewHolder) row.getTag();
        }

        Cliente cliente = listaClientes.get(position);
        Projeto projeto = listaProjetos.get(position);

        holder.txtClienteNome.setText(cliente.getClienteNome());
        holder.txtProjetoServico.setText(projeto.getProjetoServico());

        byte[] imgLogotipo = cliente.getClienteImagem();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgLogotipo, 0, imgLogotipo.length);
        holder.imgClienteLogotipo.setImageBitmap(bitmap);

        return row;
    }
}