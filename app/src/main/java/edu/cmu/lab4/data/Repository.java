package edu.cmu.lab4.data;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Repository<T> {


    private FirebaseFirestore db;
    private CollectionReference reference;
    private Class<T> type;

    public Repository(String collectionPath, Class<T> type) {
        this.type = type;
        db = FirebaseFirestore.getInstance();
        reference = db.collection(collectionPath);
    }

    public FirestoreRecyclerOptions<T> getOptions(){
        FirestoreRecyclerOptions<T> options = new FirestoreRecyclerOptions.Builder<T>()
                .setQuery(reference, type)
                .build();
        return options;
    }

    public void insert(T item){
        reference.add(item);
    }

    public void delete(String id){
        reference.document(id).delete();
    }
}