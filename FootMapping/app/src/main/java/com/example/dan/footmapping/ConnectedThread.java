package com.example.dan.footmapping;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by dan on 12/10/16.
 */

public class ConnectedThread extends Thread {
    private final InputStream mInputStream;
    private final OutputStream mOutputstream;

    public ConnectedThread(BluetoothSocket socket) {
        InputStream tempIn = null;
        OutputStream tempOut = null;

        //get the input and output streams
        try {
            tempIn = socket.getInputStream();
            tempOut = socket.getOutputStream();
        } catch (IOException e) { }

        mInputStream = tempIn;
        mOutputstream = tempOut;
    }

    public void run() {
        byte[] buffer = new byte[256];
        int bytes; // bytes returned

        //Listen to input until failure
        while (true) {
            try {
                //Read from input
                bytes = mInputStream.read(buffer);
                h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();
            } catch (IOException e){
                break;
            }
        }

    }

    public void write(String message) {
        Log.d(TAG, "...Data to send: " + message + "...");
        byte[] msgBuffer = message.getBytes();
        try {
            mOutputstream.write(msgBuffer);
        } catch (IOException e) {
            Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
        }
    }

    //Call from main activity to shutdown connection
    public void cancel() {
        try {
            mbtSocket.close();
        } catch (IOException e) {}
    }
}
