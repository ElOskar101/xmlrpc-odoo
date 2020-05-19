package com.l3soft.odoo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.l3soft.odoo.R;
import com.l3soft.odoo.models.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    List<Contact> contactsList;
    Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, email, phone, address;
        private ImageView profile;

        public ViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.name);
            email = v.findViewById(R.id.email);
            phone = v.findViewById(R.id.phone);
            address = v.findViewById(R.id.address);
            profile = v.findViewById(R.id.profile);
        }
    }


    public ContactAdapter(List<Contact> contactsList, Activity activity) {
        this.activity = activity;
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.name.setText(contactsList.get(i).getName());
        h.email.setText(contactsList.get(i).getEmail());
        h.phone.setText(contactsList.get(i).getPhone());
        h.address.setText(contactsList.get(i).getAddress());
        Glide.with(activity).load(R.drawable.ic_user).into(h.profile);
    }



    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}
