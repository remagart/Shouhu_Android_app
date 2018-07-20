package com.example.jfmamjjasond.shouhu;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Homepage extends android.support.v4.app.Fragment {
    private ImageButton imageButton;
    private AnimationDrawable animwark,animrun;
    private  int checkbtn =0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_homepage, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageButton = getView().findViewById(R.id.imageButton);
        Resources res = getResources();
        //animwark = (AnimationDrawable) res.getDrawable(R.drawable.wark);
        animrun = (AnimationDrawable) res.getDrawable(R.drawable.water);
        imageButton.setImageDrawable(animrun);
        imageButton.setAdjustViewBounds(true);
        imageButton.setMaxWidth(400);
        imageButton.setMaxHeight(800);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbtn ==0){

                    animrun.start();
                    checkbtn = checkbtn +1;
                }else {
                    animrun.stop();
                    checkbtn = checkbtn -1;
                }
            }
        });
    }

    public void findView(){

    }
}
