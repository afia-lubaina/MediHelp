package com.example.medihelp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DocViewHolder> {


    class DocViewHolder extends RecyclerView.ViewHolder {
        private Doctor doctor;

        public DocViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void setDetails(Doctor doctor) {
//            this.doctor = doctor;
            ((TextView)itemView.findViewById(R.id.tvDocName)).setText(doctor.getName());
            ((TextView)itemView.findViewById(R.id.tvDocSpeciality)).setText(doctor.getSpeciality());
            ((TextView)itemView.findViewById(R.id.tvDocLocation)).setText(doctor.getLocation());
        }
    }
    private List<Doctor> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public RecyclerAdapter(List<Doctor> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DocViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.doctor_card, viewGroup, false);

        return new DocViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DocViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.setDetails(localDataSet.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}


