package edu.cmu.lab3.models;

import android.app.Application;
import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import edu.cmu.lab3.data.AppRepository;

public class GroceryViewModel extends AndroidViewModel {
    private LiveData<List<GroceryItem>> itemList;
    private Context context;
    private AppRepository appRepository;

    public GroceryViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        appRepository = AppRepository.getInstance(context);
        itemList = appRepository.getAllItems();
    }

    public LiveData<List<GroceryItem>> getData() {
        return itemList;
    }

    public void insertItem(GroceryItem newItem) {
        itemList.getValue().add(newItem);
        appRepository.insertItem(newItem);
    }

    public void deleteItem(GroceryItem item) {
        appRepository.deleteItem(item);
    }

    public void deleteAll() {
        appRepository.deleteAll();
    }
}