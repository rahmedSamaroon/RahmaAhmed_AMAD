package edu.cmu.lab3.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import edu.cmu.lab3.R;
import edu.cmu.lab3.models.GroceryItem;
import edu.cmu.lab3.models.GroceryViewModel;

public class GroceryListAdapter extends
        RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {

    private static final String TAG = GroceryListAdapter.class.getSimpleName();

    private GroceryViewModel model;
    private Context context;
    private List<GroceryItem> list;

    public GroceryListAdapter(GroceryViewModel model, Context context) {
        this.model = model;
        this.context = context;
        this.list = model.getData().getValue();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grocery_item_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GroceryItem item = list.get(position);
        View.OnCreateContextMenuListener menuListener = new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                //set the menu title
                menu.setHeaderTitle("Delete " + item.getItem());
                //add the choices to the menu
                menu.add(1, 1, 1, "Yes").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int position = holder.getAdapterPosition();
                        GroceryItem removeItem = list.remove(position);
                        model.deleteItem(removeItem);
                        Snackbar.make(v, "Item removed", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return false;
                    }
                });
                menu.add(2, 2, 2, "No");
            }
        };
        holder.bind(item, menuListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<GroceryItem> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView item;
        public final TextView item_quantity;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            item_quantity = itemView.findViewById(R.id.item_quantity);
        }

        public void bind(final GroceryItem model, final View.OnCreateContextMenuListener listener) {

            item.setText(model.getItem());
            item_quantity.setText(String.valueOf(model.getQuantity()));

            itemView.setOnCreateContextMenuListener(listener);
        }
    }
}