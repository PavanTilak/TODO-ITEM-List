package com.example.todolist.model

open class ToDoListModel {

        var userId: Int
        var id: Int
        var title: String
        var completed:Boolean
        /**
         * [
        {
        "UserId": 2,
        "Id": 21,
        "Title": "takes pains to meet some pleasure"
        "Completed" false
        }
        {
        "UserId": 2,
        "Id": 22;
        "Title": "there is no difference between the life and the hardships"
        "Completed": true
        }
        {
        "UserId": 2,
        "Id": 23,
        "Title": "And so we have the most trouble he wants"
        "Completed" false
        }]
         */
        //var definition: String, var thumbs_up: Int, var thumbs_down: Int)
        constructor(userId: Int,  id: Int,  title: String,  completed:Boolean) {
                this.userId = userId
                this.id = id
                this.title = title
                this.completed = completed
        }
}
