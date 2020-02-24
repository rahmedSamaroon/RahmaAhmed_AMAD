package edu.cmu.lab3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.cmu.lab3.adapters.GroceryListAdapter;
import edu.cmu.lab3.models.GroceryItem;
import edu.cmu.lab3.models.GroceryViewModel;

public class MainActivity extends AppCompatActivity {

    private DialogInterface.OnClickListener addListener;
    private DialogInterface.OnClickListener cancelListener;
    private GroceryListAdapter adapter;
    private RecyclerView recyclerView;
    private List<GroceryItem> items = new ArrayList<>();
    private GroceryViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewModelProvider.AndroidViewModelFactory factory = new ViewModelProvider.AndroidViewModelFactory(this.getApplication());
        model = new ViewModelProvider(this, factory).get(GroceryViewModel.class);
        recyclerView = findViewById(R.id.items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Observer<List<GroceryItem>> observer = new Observer<List<GroceryItem>>() {
            @Override
            public void onChanged(List<GroceryItem> list) {
                items.clear();
                items.addAll(list);

                if (adapter == null) {
                    adapter = new GroceryListAdapter(model, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.setList(items);
                    adapter.notifyDataSetChanged();
                }
            }
        };

        model.getData().observe(this, observer);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setView(R.layout.grocery_item)
                        .setTitle(MainActivity.this.getString(R.string.dialog_title))
                        .create();

                addListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText grocery_item = alertDialog.findViewById(R.id.grocery_item);
                        EditText quantity = alertDialog.findViewById(R.id.quantity);

                        if (!grocery_item.getText().toString().isEmpty()) {
                            GroceryItem item = new GroceryItem(grocery_item.getText().toString(),
                                    Integer.parseInt(quantity.getText().toString()));


                            items.add(item);
                            model.insertItem(item);

                            Snackbar.make(view, "Item added", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                };


                cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                };

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Add", addListener);
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", cancelListener);
                alertDialog.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_delete) {
            items.clear();
            model.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
