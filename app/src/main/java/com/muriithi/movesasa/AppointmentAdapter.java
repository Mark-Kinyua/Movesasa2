package com.muriithi.movesasa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class AppointmentAdapter extends FirestoreRecyclerAdapter<AppointmentHelper, AppointmentAdapter.AppointmentHolder> {

    public AppointmentAdapter(@NonNull FirestoreRecyclerOptions<AppointmentHelper> options) {
        super(options);
    }


    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_records, parent, false);
        return new AppointmentHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull AppointmentHolder holder, int position, @NonNull AppointmentHelper model) {
        holder.list_old_loc.setText(model.getOld_loc());
        holder.list_new_loc.setText(model.getNew_loc());
        holder.list_date.setText(model.getDate());
        holder.list_time.setText(model.getTime());
    }

    class AppointmentHolder extends RecyclerView.ViewHolder{
        TextView list_old_loc;
        TextView list_new_loc;
        TextView list_date;
        TextView list_time;
        TextView list_desc;

        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);
            list_old_loc = itemView.findViewById(R.id.old_loc);
            list_new_loc = itemView.findViewById(R.id.new_loc);
            list_date = itemView.findViewById(R.id.date);
            list_time = itemView.findViewById(R.id.doc_time);
            list_desc = itemView.findViewById(R.id.desc);
        }
    }
}
