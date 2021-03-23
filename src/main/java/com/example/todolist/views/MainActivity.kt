package com.example.todolist.views

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapter.ToDoListAdapter
import com.example.todolist.api.CheckboxState
import com.example.todolist.model.ToDoListModel
import com.example.todolist.utilities.Utils.checkNetworkState
import com.example.todolist.viewmodels.TodoListViewModel
import java.util.*
import kotlin.Comparator

class MainActivity : AppCompatActivity() , CheckboxState {

    private var mSearchInbox: EditText? = null
    private var mSearchBtn: Button? = null
    private var mRecyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var mRecyclerAdapter: RecyclerView.Adapter<*>? = null
    private var mEmptyView: TextView? = null
    private var mRestaurantsList: MutableList<ToDoListModel>? = null
    private val TAG = "MainActivity"
    private var mProgressBar: ProgressBar? = null
    lateinit var mViewModel: TodoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                this.application).create(TodoListViewModel::class.java)

        initViewIds()
        setupViewModelObservers()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViewIds() {
        mRecyclerView = findViewById(R.id.recycler_view)
        mEmptyView = findViewById(R.id.emptyStringView)
        mSearchBtn = findViewById(R.id.searchBtn)
        mProgressBar = findViewById(R.id.progressBar)
        mSearchBtn!!.setOnTouchListener { v, event ->

            if(checkNetworkState(this)) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.background = getDrawable(R.drawable.ic_add_dim)
                    }
                    MotionEvent.ACTION_UP -> {
                        v.background = getDrawable(R.drawable.ic_add)
                        val todoItem:String = mSearchInbox?.text.toString()
                        if (todoItem.isNotEmpty()) {
                            mProgressBar!!.visibility = View.VISIBLE
                            hideSoftKeyboard()
                            refreshUI()

                            //code to get id
                            val id: Int? = mRestaurantsList?.size
                            if (id != null) {
                                mViewModel.addTodoItemApi(ToDoListModel(1,id+1,todoItem,false))
                            }
                        } else {
                            Toast.makeText(this@MainActivity, resources.getText(R.string.empty_msg), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this@MainActivity, resources.getText(R.string.network_error), Toast.LENGTH_LONG).show()
            }
            true
        }
        mSearchInbox = findViewById(R.id.searchText)
    }

    private fun setupViewModelObservers() {
        mViewModel.getTodoList().observe(this,androidx.lifecycle.Observer {
            mProgressBar!!.visibility = View.GONE
            mSearchInbox?.isEnabled = true
            mSearchInbox?.setText("")
            Collections.sort(it, Comparator<ToDoListModel> { obj1, obj2 ->
                //Sort list Descending order
                (obj2.id)?.compareTo(obj1.id)
            })

            if(it != null) {
                mRestaurantsList = it
                mRecyclerAdapter = ToDoListAdapter(it, this , this)
                mLayoutManager = LinearLayoutManager(this)

                mRecyclerView!!.layoutManager = mLayoutManager
                mRecyclerView!!.visibility = View.VISIBLE
                mEmptyView!!.visibility = View.GONE
                mRecyclerView!!.adapter = mRecyclerAdapter
            } else {
                mRecyclerView!!.visibility = View.GONE
                mEmptyView!!.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity, "No Response received from backend", Toast.LENGTH_SHORT).show()
            }
        })
        loadTodoList();
    }

    private fun loadTodoList() {
        if(checkNetworkState(this)) {
            mProgressBar!!.visibility = View.VISIBLE
            mViewModel.getTodoListApiCall()
        } else {
            Toast.makeText(this@MainActivity, resources.getText(R.string.network_error), Toast.LENGTH_LONG).show()
        }
    }
    private fun hideSoftKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

    private fun refreshUI() {
        mRecyclerView?.visibility = View.GONE
        mProgressBar?.visibility = View.VISIBLE
        mSearchInbox?.isEnabled = false
    }

    override fun checkboxState(isChecked:Boolean, checkPosition:Int) {
        refreshUI()
        mViewModel.updateTodoItemApi(isChecked,checkPosition)
    }
}