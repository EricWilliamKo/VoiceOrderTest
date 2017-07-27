package com.example.eric.googlenowtest.model;

import android.content.Context;

import com.example.eric.googlenowtest.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eric on 7/17/17.
 */

public class Names implements OrderParser{
    private String[] expression_names;
    private Context mcontext;
    private VoiceOrderListener voiceOrderListener;

    public Names(Context context, VoiceOrderListener voiceOrderListener){
        mcontext = context;
        this.voiceOrderListener = voiceOrderListener;
        expression_names = mcontext.getResources().getStringArray(R.array.expression_name);
    }

    @Override
    public void parseVoiceOrder(final String order) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean matched;
                Matcher matcher;
                for (String expression_name:expression_names) {
                    matcher = Pattern.compile(expression_name).matcher(order);
                    matched = matcher.find();
                    if(matched){
                        setValue(expression_name);
                    }
                }
            }
        }).start();
        return;
    }

    @Override
    public void setValue(String value) {
        voiceOrderListener.setName(value);
    }
}
