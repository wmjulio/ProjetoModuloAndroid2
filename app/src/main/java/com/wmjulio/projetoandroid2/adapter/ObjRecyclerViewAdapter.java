package com.wmjulio.projetoandroid2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wmjulio.projetoandroid2.MapsActivity;
import com.wmjulio.projetoandroid2.ObjActivity;
import com.wmjulio.projetoandroid2.R;
import com.wmjulio.projetoandroid2.model.Obj;

import java.util.List;

public class ObjRecyclerViewAdapter extends RecyclerView.Adapter<ObjRecyclerViewAdapter.ViewHolder> {

    private List<Obj> objList;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public ObjRecyclerViewAdapter(List<Obj> objList, Context context, FirebaseFirestore firestoreDB) {
        this.objList = objList;
        this.context = context;
        this.firestoreDB = firestoreDB;
    }

    @Override
    public ObjRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_obj, parent, false);

        return new ObjRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ObjRecyclerViewAdapter.ViewHolder holder, int position) {
        final int itemPosition = position;
        final Obj obj = objList.get(itemPosition);

        holder.title.setText(obj.getNome());
        holder.desc.setText(obj.getDesc());

        holder.ltTexts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details(obj);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(obj);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(obj.getId(), itemPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;
        ImageView edit;
        ImageView delete;
        LinearLayout ltTexts;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvTitle);
            desc = view.findViewById(R.id.tvDesc);

            edit = view.findViewById(R.id.ivEdit);
            delete = view.findViewById(R.id.ivDelete);

            ltTexts = view.findViewById(R.id.ltTexts);
        }
    }

    private void update(Obj obj) {
        Intent intent = new Intent(context, ObjActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("UpdateObjId", obj.getId());
        intent.putExtra("UpdateObjNome", obj.getNome());
        intent.putExtra("UpdateObjDesc", obj.getDesc());
        intent.putExtra("UpdateObjLat", obj.getLat());
        intent.putExtra("UpdateObjLon", obj.getLon());
        context.startActivity(intent);
    }

    private void delete(String id, final int position) {
        firestoreDB.collection("objs")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        objList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, objList.size());
                        Toast.makeText(context, "Objs has been deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void details(Obj obj) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("DetailsObjId", obj.getId());
        intent.putExtra("DetailsObjNome", obj.getNome());
        intent.putExtra("DetailsObjDesc", obj.getDesc());
        intent.putExtra("DetailsObjLat", obj.getLat());
        intent.putExtra("DetailsObjLon", obj.getLon());
        context.startActivity(intent);
    }
}
