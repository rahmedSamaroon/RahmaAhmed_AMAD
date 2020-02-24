package edu.cmu.lab3.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import edu.cmu.lab3.models.GroceryItem;

@Dao
public interface GroceryItemDAO {
    @Query("SELECT * FROM GroceryItems")
    LiveData<List<GroceryItem>> getAllItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(GroceryItem item);

    @Delete
    void deleteItem(GroceryItem item);

    @Query("DELETE FROM GroceryItems")
    int deleteAll();
}

