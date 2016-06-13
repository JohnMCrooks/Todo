package com.crooks;

/**
 * Created by johncrooks on 5/23/16.
 */
public class ToDoItem {
    Boolean isDone;
    String text;
    int id;

    public ToDoItem(int id, Boolean isDone, String text) {
        this.id = id;
        this.isDone = isDone;
        this.text = text;

    }

}
