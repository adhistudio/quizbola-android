package com.adipurnomo.quizbolaadhi;

public class BenarSalah {
    private int mQuestionID;
    private int mImageID;
    private boolean mAnswer;

    public BenarSalah(int mQuestionID, int mImageID, boolean mAnswer) {
        this.mQuestionID = mQuestionID;
        this.mImageID = mImageID;
        this.mAnswer = mAnswer;
    }

    public int getmQuestionID() {
        return mQuestionID;
    }

    public void setmQuestionID(int mQuestionID) {
        this.mQuestionID = mQuestionID;
    }

    public int getmImageID() {
        return mImageID;
    }

    public void setmImageID(int mImageID) {
        this.mImageID = mImageID;
    }

    public boolean ismAnswer() {
        return mAnswer;
    }

    public void setmAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}
