package com.example.eric.googlenowtest.model;

import android.content.Context;

/**
 * Created by eric on 7/17/17.
 */

public class Drink {
    public Names names;
    public Sugar sugar;
    public Ice ice;

    public Drink(Context context, VoiceOrderListener voiceOrderListener){
        names = new Names(context, voiceOrderListener);
        ice = new Ice(context, voiceOrderListener);
        sugar = new Sugar(context, voiceOrderListener);
    }

    public void takeVoiceOrder(String order){
        names.parseVoiceOrder(order);
        ice.parseVoiceOrder(order);
        sugar.parseVoiceOrder(order);
    }
}
