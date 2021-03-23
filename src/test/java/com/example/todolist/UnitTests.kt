package com.example.todolist

import com.example.todolist.model.ToDoListModel
import com.example.todolist.model.ListModel

import org.junit.Test

import java.util.ArrayList

import org.junit.Assert.assertNotNull

class UnitTests {


    private val listModel: ListModel
        get() {

            val list = ArrayList<ToDoListModel>()
            list.add(toDoListModel)

            return ListModel(list)
        }

    private val toDoListModel: ToDoListModel
        get() = ToDoListModel("A really great dude", 125, 18)


    @Test
    fun metaDataNullCheck() {
        assertNotNull(listModel)
    }

    @Test
    fun isCorrectDictionaryModel() {
        val dictionaryModel = toDoListModel
        AssertHelper.assertDictionaryEquals(dictionaryModel, getSecondDictionaryModel(dictionaryModel))
    }

    @Test
    fun isCorrectListModel() {
        AssertHelper.assertListEquals(listModel, getSecondListModel(listModel))
    }

    private fun getSecondListModel(imageMetadata: ListModel): ListModel {
        return ListModel(
                imageMetadata.list)
    }

    private fun getSecondDictionaryModel(toDoListModel: ToDoListModel): ToDoListModel {
        return ToDoListModel(toDoListModel.definition, toDoListModel.thumbs_up, toDoListModel.thumbs_down)
    }
}
