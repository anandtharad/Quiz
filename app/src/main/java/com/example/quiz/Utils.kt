package com.example.quiz

import android.content.Context
import android.content.Intent

class Utils {

    companion object {
        @JvmStatic
        fun UserDetailIntent(context: Context): Intent {
            return Intent(context, SelectQuizQuestionsCountActivity::class.java)
        }

        fun getAppendedBaseUrl(questionCount: String): String {
            return "https://opentdb.com/api.php?amount=$questionCount&category=11&type=boolean";
        }
    }
}