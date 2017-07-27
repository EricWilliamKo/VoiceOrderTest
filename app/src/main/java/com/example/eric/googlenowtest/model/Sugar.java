package com.example.eric.googlenowtest.model;

import android.content.Context;

import com.example.eric.googlenowtest.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eric on 7/17/17.
 */

public class Sugar implements OrderParser{
    private String[] expression_sugars;
    private Context mcontext;
    private VoiceOrderListener voiceOrderListener;

    public Sugar(Context context, VoiceOrderListener voiceOrderListener){
        mcontext = context;
        expression_sugars = mcontext.getResources().getStringArray(R.array.expression_sugar);
        this.voiceOrderListener = voiceOrderListener;
    }

    @Override
    public void parseVoiceOrder(final String order) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean matched;
                Matcher matcher;
                for (String expression_sugar:expression_sugars) {
                    matcher = Pattern.compile(expression_sugar).matcher(order);
                    matched = matcher.find();
                    if(matched){
                        setValue(expression_sugar);
                    }
                }
            }
        }).start();
        return;
    }

    @Override
    public void setValue(String value) {
        voiceOrderListener.setSugar(value);
    }
}
