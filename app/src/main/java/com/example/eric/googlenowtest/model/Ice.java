package com.example.eric.googlenowtest.model;

import android.content.Context;

import com.example.eric.googlenowtest.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eric on 7/17/17.
 */

public class Ice implements OrderParser{
    private String[] expression_ices;
    private Context mcontext;
    private VoiceOrderListener voiceOrderListener;

    public Ice(Context context, VoiceOrderListener voiceOrderListener){
        mcontext = context;
        this.voiceOrderListener = voiceOrderListener;
        expression_ices = mcontext.getResources().getStringArray(R.array.expression_ice);
    }

    @Override
    public void parseVoiceOrder(final String order) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean matched;
                Matcher matcher;
                for (String expression_ice:expression_ices) {
                    matcher = Pattern.compile(regularize(expression_ice)).matcher(order);
                    matched = matcher.find();
                    if(matched){
                        setValue(expression_ice);
                    }
                }
            }
        }).start();
        return;
    }

    @Override
    public void setValue(String value) {
        voiceOrderListener.setIce(value);
    }

    public String regularize(String input){
        String output = ".*" + input + ".*";
        return output;
    }
}
