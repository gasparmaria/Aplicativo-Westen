package com.example.westen.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.westen.Funcionario;
import com.example.westen.R;

import java.util.List;

public class ListViewFuncionarioAdapter extends BaseAdapter {
    private int layout;
    private Context context;
    List<Funcionario> listaFuncionarios;

    public ListViewFuncionarioAdapter(Context context, int layout, List<Funcionario> listaFuncionarios) {
        this.layout = layout;
        this.context = context;
        this.listaFuncionarios = listaFuncionarios;
    }

    @Override
    public int getCount() {
        return listaFuncionarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFuncionarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtFuncionarioCodigo, txtFuncionarioNome, txtFuncionarioCargo;
        ImageButton btnFuncionarioEditar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            //holder.txtFuncionarioCodigo.setText(String.valueOf(position));
            holder.txtFuncionarioNome = (TextView) row.findViewById(R.id.txtNome);
            holder.txtFuncionarioCargo = (TextView) row.findViewById(R.id.txtComplemento);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        Funcionario Funcionario = listaFuncionarios.get(position);

        //holder.txtFuncionarioCodigo.setText(String.valueOf(position));
        holder.txtFuncionarioNome.setText(Funcionario.getFuncionarioNome());
        holder.txtFuncionarioCargo.setText(Funcionario.getFuncionarioCargo());

        return row;
    }
}
