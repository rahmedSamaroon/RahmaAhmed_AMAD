package edu.cmu.lab4.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.cmu.lab4.R;

public class BookViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleTextView;
    private final TextView authorTextView;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title);
        authorTextView = itemView.findViewById(R.id.author);
    }

    public void setTitle(String title) {
        this.titleTextView.setText(title);
    }

    public void setAuthor(String author) {
        this.authorTextView.setText(author);
    }
}
