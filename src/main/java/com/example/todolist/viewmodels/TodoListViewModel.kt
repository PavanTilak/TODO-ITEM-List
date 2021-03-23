package com.example.todolist.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.api.IAwsToDoList
import com.example.todolist.model.ToDoListModel
import com.example.todolist.utilities.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoListViewModel  : ViewModel() , Callback<MutableList<ToDoListModel>> {

    private var mRestaurantsList: MutableLiveData<MutableList<ToDoListModel>> = MutableLiveData()
    lateinit var mTodoList: MutableList<ToDoListModel>

    fun getTodoListApiCall() {
        val retrofit = Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val todoListApi = retrofit.create(IAwsToDoList::class.java)
        val call = todoListApi.getTodoListData()
        call.enqueue(this as (Callback<List<ToDoListModel>>))
    }

    fun addTodoItemApi(todoItem : ToDoListModel) {
        val retrofit = Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val todoListApi = retrofit.create(IAwsToDoList::class.java)
        val call = todoListApi.addTodoItem(todoItem)
        call.enqueue(object : Callback<ToDoListModel?> {
            override fun onResponse(call: Call<ToDoListModel?>, response: Response<ToDoListModel?>) {
                val todoListItem: ToDoListModel? = response.body()
                mTodoList?.add(todoItem)
                mRestaurantsList.postValue(mTodoList)
                Log.e("TodoListViewModel"," request response id= "+todoListItem?.id)
            }
            override fun onFailure(call: Call<ToDoListModel?>, t: Throwable) {
                Log.e("TodoListViewModel"," request failed= "+t.stackTrace)
            }
        })
    }

    fun updateTodoItemApi(isChecked:Boolean, checkPosition:Int) {
        val retrofit = Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val todoListApi = retrofit.create(IAwsToDoList::class.java)
        var todoListItem: ToDoListModel = mTodoList.get(checkPosition)
        todoListItem.completed = isChecked
        val call = todoListApi.updateTodoItem(todoListItem)

        call.enqueue(object : Callback<ToDoListModel?> {
            override fun onResponse(call: Call<ToDoListModel?>, response: Response<ToDoListModel?>) {
                val todoListItem: ToDoListModel? = response.body()
                mTodoList.get(checkPosition).completed = isChecked
                mRestaurantsList.postValue(mTodoList)
                Log.e("TodoListViewModel"," request response id= "+todoListItem?.id)
            }
            override fun onFailure(call: Call<ToDoListModel?>, t: Throwable) {
                Log.e("TodoListViewModel"," request failed= "+t.stackTrace)
            }
        })
    }

    override fun onResponse(call: Call<MutableList<ToDoListModel>>, response: Response<MutableList<ToDoListModel>>) {
        if (response.isSuccessful) {

            val listModel = response.body()
            if (listModel != null) {
                 mTodoList = listModel
                if (mTodoList != null && mTodoList!!.size > 0) {
                    mRestaurantsList.postValue(mTodoList)
                }
            }
        } else {
            mRestaurantsList.postValue(null)
        }
    }

    override fun onFailure(call: Call<MutableList<ToDoListModel>>, t: Throwable) {
        Log.e("TodoListViewModel"," request failed= "+t.stackTrace)
        mRestaurantsList.postValue(null)
    }

    fun getTodoList() : MutableLiveData<MutableList<ToDoListModel>> {
        return mRestaurantsList
    }
}