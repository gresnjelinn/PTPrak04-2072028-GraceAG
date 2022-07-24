package com.example.pt04prak2072028jdbc.dao;

import javafx.collections.ObservableList;

public interface DaoInterface<T> {
    ObservableList<T> getData();
    void insertData(T data);
    void deleteData(T data);
    void updateData(T data);
}
