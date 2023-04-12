package com.example.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class SelectQuizQuestionsCountActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var spinnerCategory: Spinner
    private val MAX_COUNT = 50
    private val questionListCount = arrayListOf<Int>()
    private var selectedQuestionCount = 10;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_count)
        initViews()
    }

    private fun initViews() {
        spinnerCategory = findViewById(R.id.spinner_category)
        spinnerCategory.onItemSelectedListener = this
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, getQuestionsCountList())
        spinnerCategory.adapter = adapter

        findViewById<AppCompatButton>(R.id.btnProceed).setOnClickListener {
            val intent  = Intent(this, MainActivity::class.java)
            intent.putExtra("QUESTION_COUNT", selectedQuestionCount)
            startActivity(intent)
            finish()
        }
    }

    private fun getQuestionsCountList(): List<Int> {
        questionListCount.clear()
        for (i in 10..MAX_COUNT step 10) {
            questionListCount.add(i)
        }
        return questionListCount
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedQuestionCount = questionListCount[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing
    }
}