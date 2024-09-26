package com.example.appa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.widget.Toast;

public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.ReferenceViewHolder> {

    private List<String> references;
    private List<ReferenceItem> items;

    public ReferenceAdapter(List<String> references, List<ReferenceItem> items) {
        this.references = references;
        this.items = items;
    }

    @NonNull
    @Override
    public ReferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reference, parent, false);
        return new ReferenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferenceViewHolder holder, int position) {
        String reference = references.get(position);
        holder.textViewReference.setText(reference);

        holder.buttonEdit.setOnClickListener(v -> {
        });

        holder.buttonDelete.setOnClickListener(v -> {
            references.remove(position);
            notifyItemRemoved(position);
            deleteItem(holder.buttonDelete.getContext(), items.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return references.size();
    }

    public static class ReferenceViewHolder extends RecyclerView.ViewHolder {
        TextView textViewReference;
        ImageButton buttonEdit;
        ImageButton buttonDelete;

        public ReferenceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewReference = itemView.findViewById(R.id.textView_reference);
            buttonEdit = itemView.findViewById(R.id.icon_button_edit);
            buttonDelete = itemView.findViewById(R.id.icon_button_delete);
        }
    }

    public void deleteItem(Context context, ReferenceItem item){
        String table = item.getTableName();
        Integer id = item.getId();
        if (table == null || table.isEmpty()) {
            Toast.makeText(context, "Error: table name is null or empty", Toast.LENGTH_SHORT).show();
            return; // Salir si el nombre de la tabla no es válido
        }

        AdminSQLite adminSQLite = new AdminSQLite(context, "db_course", null, '1');
        SQLiteDatabase db = adminSQLite.getReadableDatabase();

        try {
            String whereClause = "id = ?";
            String[] whereArgs = new String[]{String.valueOf(id)};

            int rowsAffected = db.delete(table, whereClause, whereArgs);

            if (rowsAffected > 0) {
                Toast.makeText(context, "Item with ID " + id + " deleted successfully from " + table, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No item found with ID " + id + " in " + table, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error deleting item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }

    }
}