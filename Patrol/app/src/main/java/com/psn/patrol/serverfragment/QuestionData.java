package com.psn.patrol.serverfragment;


import com.psn.patrol.bean.Question;

import java.util.ArrayList;

/**
 * Author: shinianPan on 2017/3/28.
 * email : snow_psn@163.com
 */

public class QuestionData {

    private static ArrayList<Question> questions=new ArrayList<>();

    public static ArrayList<Question> getQuestions() {
        return questions;
    }

    public static void setQuestions(ArrayList<Question> ques) {
        questions = ques;
    }

    public static void allDel(){
        questions.clear();
    }
}
