package edu.cmu.lab3.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GroceryItems")
public class GroceryItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String item;
    private int quantity;

    public GroceryItem(String item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

