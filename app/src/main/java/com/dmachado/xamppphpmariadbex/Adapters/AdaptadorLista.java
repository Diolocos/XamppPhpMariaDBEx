package com.dmachado.xamppphpmariadbex.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dmachado.xamppphpmariadbex.Model.UserM;
import com.dmachado.xamppphpmariadbex.R;

import java.util.List;

public class AdaptadorLista extends ArrayAdapter<UserM> {

    private Context context;
    private List<UserM> listUsers;

    public AdaptadorLista(@NonNull Context c, List<UserM> listaUsers) {
        super(c, R.layout.custom_list_item, listaUsers);

        this.context = c;
        this.listUsers = listaUsers;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, null, true);

        TextView nome = view.findViewById(R.id.txt_nome);
        TextView email = view.findViewById(R.id.txt_email);
        TextView username = view.findViewById(R.id.txt_username);

        nome.setText(listUsers.get(position).getFullname());
        username.setText(listUsers.get(position).getUsername());
        email.setText(listUsers.get(position).getEmail());



        return view;
    }
}
