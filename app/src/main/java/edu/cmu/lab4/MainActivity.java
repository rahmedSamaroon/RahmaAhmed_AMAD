package edu.cmu.lab4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.cmu.lab4.data.Repository;
import edu.cmu.lab4.data.adapters.BookListAdaptor;
import edu.cmu.lab4.models.Book;

public class MainActivity extends AppCompatActivity {

    private Repository<Book> repository;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the recycler view
        recyclerView = findViewById(R.id.items);

        //divider line between rows
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //set a layout manager on the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new Repository<>("Books", Book.class);

        recipeAdapter = new BookListAdaptor(this, repository);
        recyclerView.setAdapter(recipeAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //create a vertical linear layout to hold edit texts
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50,50,50,50);
                //create edit texts and add to layout

                final EditText authorEditText = new EditText(MainActivity.this);
                authorEditText.setHint("Book Author");
                layout.addView(authorEditText);

                final EditText titleEditText = new EditText(MainActivity.this);
                titleEditText.setHint("Book Title");
                titleEditText.setGravity(Gravity.START);
                titleEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE);
                titleEditText.setHeight(250);
                layout.addView(titleEditText);

                //create alert dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Add Book");
                dialog.setView(layout);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //get entered data
                        String title = titleEditText.getText().toString();
                        String author = authorEditText.getText().toString();
                        if (title.trim().length() > 0) {
                            //create new recipe item
                            Book book = new Book(title, author);
                            //add to Firebase
                            repository.insert(book);
                        }
                        Snackbar.make(view, "Item added", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                });
                //sets cancel action
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // cancel
                    }
                });
                //present alert dialog
                dialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recipeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recipeAdapter.stopListening();
    }
}
