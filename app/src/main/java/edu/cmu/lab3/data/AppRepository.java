package edu.cmu.lab3.data;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import edu.cmu.lab3.models.GroceryItem;

public class AppRepository {
    private static AppRepository mInstance;
    private AppDatabase mAppDatabase;
    //for background thread
    private Executor executor = Executors.newSingleThreadExecutor();

    private AppRepository(Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
    }

    public static AppRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppRepository(context);
        }
        return mInstance;
    }

    public LiveData<List<GroceryItem>> getAllItems() {
        return mAppDatabase.groceryItemDAO().getAllItems();
    }

    public void insertItem(final GroceryItem newItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.groceryItemDAO().insertItem(newItem);
            }
        });
    }

    public void deleteItem(final GroceryItem newItem) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.groceryItemDAO().deleteItem(newItem);
            }
        });
    }

    public void deleteAll() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.groceryItemDAO().deleteAll();
            }
        });
    }
}
