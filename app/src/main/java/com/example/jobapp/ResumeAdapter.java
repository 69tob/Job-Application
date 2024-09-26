package com.example.jobapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResumeAdapter extends RecyclerView.Adapter<ResumeAdapter.ResumeViewHolder> {

    private Cursor cursor;
    private Context context;
    private DatabaseHelper databaseHelper;

    public ResumeAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ResumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resume, parent, false);
        return new ResumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumeViewHolder holder, int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String filePath = cursor.getString(cursor.getColumnIndexOrThrow("file_path"));

            holder.tvApplicantName.setText(name);
            holder.tvResumeFilePath.setText(filePath);

            holder.btnDownload.setOnClickListener(v -> {
                // Implement download functionality here
                FileDownloader.downloadFile(context, filePath, name);
            });

            holder.btnDelete.setOnClickListener(v -> {
                // Delete the resume from the database
                boolean isDeleted = databaseHelper.deleteResume(id);
                if (isDeleted) {
                    // Update the cursor and notify the adapter
                    Cursor newCursor = databaseHelper.getAllResumes();
                    changeCursor(newCursor);
                    Toast.makeText(context, "Resume deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete resume", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public void changeCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }

    class ResumeViewHolder extends RecyclerView.ViewHolder {
        TextView tvApplicantName, tvResumeFilePath;
        Button btnDownload, btnDelete;

        ResumeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvApplicantName = itemView.findViewById(R.id.tvApplicantName);
            tvResumeFilePath = itemView.findViewById(R.id.tvResumeFilePath);
            btnDownload = itemView.findViewById(R.id.btnDownload);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
