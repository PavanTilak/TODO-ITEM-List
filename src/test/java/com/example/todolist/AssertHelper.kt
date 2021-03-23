package com.example.todolist


import com.example.todolist.model.ToDoListModel
import com.example.todolist.model.ListModel

import junit.framework.Assert.assertEquals

object AssertHelper {
    fun assertListEquals(listModel1: ListModel, listModel2: ListModel) {
        assertEquals(listModel1.list[0].definition, listModel2.list[0].definition)
        assertEquals(listModel1.list[0].thumbs_up, listModel2.list[0].thumbs_up)
        assertEquals(listModel1.list[0].thumbs_down, listModel2.list[0].thumbs_down)
    }

    fun assertDictionaryEquals(toDoListModel1: ToDoListModel, toDoListModel2: ToDoListModel) {
        assertEquals(toDoListModel1.definition, toDoListModel2.definition)
        assertEquals(toDoListModel1.thumbs_up, toDoListModel2.thumbs_up)
        assertEquals(toDoListModel1.thumbs_down, toDoListModel2.thumbs_down)
    }
}
