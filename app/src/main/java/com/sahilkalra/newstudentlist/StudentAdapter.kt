package com.sahilkalra.newstudentlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sahilkalra.newstudentlist.model.Student

class StudentAdapter(var studentList: ArrayList<Student>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var sRollNo: TextView = view.findViewById(R.id.sRollNo)
        var sName: TextView = view.findViewById(R.id.sName)
        var sAddress: TextView = view.findViewById(R.id.sAddress)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_student_details, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.sRollNo.text = student.roll_no
        holder.sName.text = student.name
        holder.sAddress.text = student.address
    }

    override fun getItemCount(): Int {
        return studentList.size;

    }
}