package edu.cmu.lab4.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import androidx.annotation.NonNull;
import edu.cmu.lab4.R;
import edu.cmu.lab4.data.Repository;
import edu.cmu.lab4.holders.BookViewHolder;
import edu.cmu.lab4.models.Book;

public class BookListAdaptor extends FirestoreRecyclerAdapter<Book, BookViewHolder> {

    private Context context;
    private Repository<Book> repository;

    public BookListAdaptor(Context context, Repository<Book> repository) {
        super(repository.getOptions());
        this.context = context;
        this.repository = repository;
    }

    @Override
    protected void onBindViewHolder(@NonNull final BookViewHolder holder, int i, @NonNull final Book book) {
         holder.setAuthor(book.getAuthor());
         holder.setTitle(book.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get recipe that was pressed
                int position = holder.getAdapterPosition();
                //get snapshot
                DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(position);
                //get recipe url
                Book item = documentSnapshot.toObject(Book.class);
                //create new intent
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //add url to intent
                String url = String.format("https://www.google.com/search?tbm=bks&q=%s+%s", item.getTitle(), item.getAuthor());
                intent.setData(Uri.parse(url));
                //start intent
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                //set the menu title
                menu.setHeaderTitle("Delete " + book.getTitle());
                //add the choices to the menu
                menu.add(1, 1, 1, "Yes").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //get recipe that was pressed
                        int position = holder.getAdapterPosition();
                        //get document id
                        String id = getSnapshots().getSnapshot(position).getId();
                        //delete from repository
                        repository.delete(id);

                        Snackbar.make(v, "Item removed", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        return false;
                    }
                });
                menu.add(2, 2, 2, "No");
            }
        });
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(itemView);
    }
}
