package com.none.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class CallReceiver extends PhonecallReceiver  {

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {

        Log.e("Incominggggggg","-----"+number);
        Toast.makeText(ctx, "Incoming "     , Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.e("outgoing","-----"+number);

    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.e("incomingend","-----"+number);

    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.e("outgoingend","-----"+number);

    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Log.e("missedcall","-----"+number);

    }

}