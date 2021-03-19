package com.thepyprogrammer.randstudent.model.list

data class StudentList(
    var title: String,
    var students: MutableList<Student>
) {
    override fun toString(): String {
        return title
    }
}
