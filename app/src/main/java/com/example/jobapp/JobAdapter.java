package com.example.jobapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {
    private List<Job> jobList;
    private Context context;
    private boolean isAdmin;
    private JobActionListener jobActionListener;

    public JobAdapter(List<Job> jobList, Context context, boolean isAdmin, JobActionListener jobActionListener) {
        this.jobList = jobList;
        this.context = context;
        this.isAdmin = isAdmin;
        this.jobActionListener = jobActionListener;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = isAdmin ? R.layout.activity_admin_job : R.layout.activity_user_job;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Job job = jobList.get(position);
        holder.bind(job);
        if (isAdmin) {
            holder.showAdminButtons();
        } else {
            holder.showUserButton();
        }
    }

    @Override
    public int getItemCount() {
        return jobList != null ? jobList.size() : 0;
    }

    public void updateJobs(List<Job> newJobList) {
        jobList = newJobList;
        notifyDataSetChanged();
    }

    class JobViewHolder extends RecyclerView.ViewHolder {
        TextView tvJobTitle, tvCompany, tvLocation, tvDescription;
        Button btnApply, btnEdit, btnDelete, btnSave;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnApply = itemView.findViewById(R.id.btnApply);

            if (isAdmin) {
                btnEdit = itemView.findViewById(R.id.btnEdit);
                btnDelete = itemView.findViewById(R.id.btnDelete);
                btnSave = itemView.findViewById(R.id.btnSave);

                if (btnEdit != null) {
                    btnEdit.setOnClickListener(v -> {
                        Job job = jobList.get(getAdapterPosition());
                        jobActionListener.onEditJob(job);
                    });
                }

                if (btnDelete != null) {
                    btnDelete.setOnClickListener(v -> {
                        Job job = jobList.get(getAdapterPosition());
                        jobActionListener.onDeleteJob(job.getId());

                    });
                }

                if (btnSave != null) {
                    btnSave.setOnClickListener(v -> {
                        Job job = jobList.get(getAdapterPosition());
                        jobActionListener.onSaveJob(job);
                    });
                }
            } else {
                if (btnApply != null) {
                    btnApply.setOnClickListener(v -> {
                        Job job = jobList.get(getAdapterPosition());
                        if (jobActionListener != null) {
                            jobActionListener.onApplyJob(job);
                        }
                    });
                }
            }
        }

        public void bind(Job job) {
            if (tvJobTitle != null) tvJobTitle.setText(job.getTitle());
            if (tvCompany != null) tvCompany.setText(job.getCompany());
            if (tvLocation != null) tvLocation.setText(job.getLocation());
            if (tvDescription != null) tvDescription.setText(job.getDescription());
        }

        public void showAdminButtons() {
            if (btnEdit != null) btnEdit.setVisibility(View.VISIBLE);
            if (btnDelete != null) btnDelete.setVisibility(View.VISIBLE);
            if (btnSave != null) btnSave.setVisibility(View.VISIBLE);
            if (btnApply != null) btnApply.setVisibility(View.GONE);
        }

        public void showUserButton() {
            if (btnApply != null) btnApply.setVisibility(View.VISIBLE);
            if (btnEdit != null) btnEdit.setVisibility(View.GONE);
            if (btnDelete != null) btnDelete.setVisibility(View.GONE);
            if (btnSave != null) btnSave.setVisibility(View.GONE);
        }
    }

    public interface JobActionListener {
        void onEditJob(Job job);
        void onDeleteJob(int jobId);
        void onSaveJob(Job job);
        void onApplyJob(Job job);
    }
}