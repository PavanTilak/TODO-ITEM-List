package com.example.todolist.api

import com.example.todolist.model.ToDoListModel
import retrofit2.Call
import retrofit2.http.*

/**
https://jsonplaceholder.typicode.com/todos?userId=1
 */
interface IAwsToDoList {

    @GET("todos?userId=1")
    fun getTodoListData(): Call<List<ToDoListModel>>

    @POST("todos?userId=1")
    fun addTodoItem(@Body todoListItem : ToDoListModel) : Call<ToDoListModel>

    @PUT("todos?userId=1")
    fun updateTodoItem(@Body todoListItem : ToDoListModel) : Call<ToDoListModel>
    //fun addTodoItem(@Field("id") id:Int,@Field("userId") userId:Int ,@Field("title") title:String , @Field("completed")completed:Boolean): Call<ToDoListModel>
}
