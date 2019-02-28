package com.adipurnomo.quizbolaadhi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends Activity {



    Button mTrueButton, mFalseButton;
    TextView mQuestionText;
    ImageView ImageSoal;
    //index array
    int mIndex;
    // score index
    int mScore;

    int soalTanyaIdRes;
    int soalImageId;
    TextView mScoreText;
    ProgressBar mProgressBar;
    //iklan
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private boolean loadingIklan=true;
    private Integer hitung=0;
    private BenarSalah[] mQuestionBank = new BenarSalah[] {
            new BenarSalah(R.string.question_1,R.drawable.andikvirman, true),
            new BenarSalah(R.string.question_2,R.drawable.bimasakti, true),
            new BenarSalah(R.string.question_3,R.drawable.egimaulana, true),
            new BenarSalah(R.string.question_4,R.drawable.evandimas, true),
            new BenarSalah(R.string.question_5,R.drawable.fakhrudinaryanto, true),
            new BenarSalah(R.string.question_6,R.drawable.firmanutina, false),
            new BenarSalah(R.string.question_7,R.drawable.gonzalez, true),
            new BenarSalah(R.string.question_8,R.drawable.hamkahamzah, false),
            new BenarSalah(R.string.question_9,R.drawable.hansamuyama, true),
            new BenarSalah(R.string.question_10,R.drawable.ihmedsofyan, true),
            new BenarSalah(R.string.question_11,R.drawable.indrasafri, false),
            new BenarSalah(R.string.question_12,R.drawable.irfanbachdim, false),
            new BenarSalah(R.string.question_13,R.drawable.kimkur,true),
            new BenarSalah(R.string.question_14,R.drawable.lilypali,true),
            new BenarSalah(R.string.question_15,R.drawable.maman,true),
            new BenarSalah(R.string.question_16,R.drawable.sadil,true),
            new BenarSalah(R.string.question_17,R.drawable.samsiralam,true),
            new BenarSalah(R.string.question_18,R.drawable.bepe,true),
    };

    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        }else {
            mScore = 0;
            mIndex = 0;
        }
        setContentView(R.layout.activity_main);
        //iklan
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest1);


        mQuestionText = findViewById(R.id.question_text_view);
        ImageSoal = findViewById(R.id.imageSoalText);
        mScoreText = findViewById(R.id.score);
        mScoreText.setText("Score: "+mScore + "/"+mQuestionBank.length);
        mProgressBar = findViewById(R.id.progress_bar);

        ImageSoal.setImageResource(R.drawable.andikvirman);
        //BenarSalah pertanyaan = mQuestionBank[mIndex];
        soalTanyaIdRes =mQuestionBank[mIndex].getmQuestionID();
        mQuestionText.setText(soalTanyaIdRes);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                updateQuestion();

               // Toasty.success(getApplicationContext(), "Anda Benar !", Toast.LENGTH_SHORT, true).show();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                updateQuestion();

               // Toasty.error(getApplicationContext(), "Maaf Coba Lagi.", Toast.LENGTH_SHORT, true).show();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey",mScore);
        outState.putInt("IndexKey",mIndex);
    }

    public void loadInterstitial() {
        hitung++;
        if (loadingIklan){
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
            AdRequest adRequest = new AdRequest.Builder()
                    .setRequestAgent("android_studio:ad_template").build();
            mInterstitialAd.loadAd(adRequest);
            loadingIklan=false;
        }
        if (hitung%30==0){
            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                loadingIklan=true;
            }
        }
    }
    private void updateQuestion()
    {
        mIndex =( mIndex+1) % mQuestionBank.length;
        if (mIndex == 0){
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View DialogScore = LayoutInflater.from(this).inflate(R.layout.dialog_score,viewGroup,false);

            AlertDialog.Builder alertScore =new AlertDialog.Builder(this);
            TextView score = DialogScore.findViewById(R.id.scoreDialog);
            score.setText("Skor Anda "+mScore+" Poin!");
          /*  alertScore.setTitle("Tamat");
            alertScore.setCancelable(false);
            alertScore.setMessage("Skor Anda "+ mScore + "poin!");*/
        /*    alertScore.setPositiveButton("Tutup Aplikasi ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });*/
            Button tutupButton =DialogScore.findViewById(R.id.buttonOk);
            tutupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new  Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            });
            alertScore.setView(DialogScore);
            AlertDialog alertDialog = alertScore.create();
            alertDialog.show();

        }
        soalTanyaIdRes = mQuestionBank[mIndex].getmQuestionID();
        mQuestionText.setText(soalTanyaIdRes);
        soalImageId  = mQuestionBank[mIndex].getmImageID();
        ImageSoal.setImageResource(soalImageId);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreText.setText("Score "+mScore + "/"+mQuestionBank.length);
        //mScoreText.setText(""+mScore);
    }
    private void checkAnswer(boolean userSelection){
        boolean correctAnswer = mQuestionBank[mIndex].ismAnswer();
        if (userSelection == correctAnswer)
        {
            mScore +=10;
            //Toasty.success(getApplicationContext(), "Anda Benar !", Toast.LENGTH_SHORT, true).show();
            //Toast.makeText(this, "Anda Benar", Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(this, "Anda Salah", Toast.LENGTH_SHORT).show();
            //Toasty.error(getApplicationContext(), "Maaf Anda Salah", Toast.LENGTH_SHORT, true).show();
        }
    }


}
