package com.example.dan.footmapping;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mbtAdapter = null;
    private BluetoothSocket mbtSocket = null;
    private InputStream outStream = null;
    private static final String TAG = "bluetooth";
    private double userWeight = 0.0;
    private StringBuilder sb = new StringBuilder();
    private ConnectedThread mConnectedThread;
    private List<String> mArrayAdapter = null;



    private int sensorDAta = 0;

    Handler h;
    TextView txtArduino;

    TextView topleft;
    TextView topright;
    TextView midleft;
    TextView midright;
    TextView botleft;
    TextView botright;

    final int RECIEVE_MESSAGE = 1;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "00:15:FF:F2:19:5F";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtArduino = (TextView) findViewById(R.id.txtArduino);

        //Generate id of squares

        topleft = (TextView) findViewById(R.id.topleft);
        topright = (TextView) findViewById(R.id.topright);
        midleft = (TextView) findViewById(R.id.midleft);
        midright = (TextView) findViewById(R.id.midright);
        botleft = (TextView) findViewById(R.id.botleft);
        botright = (TextView) findViewById(R.id.botright);



        //Check if bluetooth is supported if it is turn it on
        mbtAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothState();
        getUserWeight();


        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:                                                   // if receive massage
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);                 // create string from bytes array
                        sb.append(strIncom);                                                // append string
                        int endOfLineIndex = sb.indexOf("\r\n");                            // determine the end-of-line
                        if (endOfLineIndex > 0) {                                            // if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex);               // extract string
                            sb.delete(0, sb.length());                                      // and clear
                            txtArduino.setText("Data from Arduino: " + sbprint);            // update TextView
                        }


                        //Log.d(TAG, "...String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
                        break;
                }
            }

        };
    }

    private void bluetoothState(){
        if (mbtAdapter == null) {
            Toast.makeText(getApplicationContext(),
                    "Bluetooth not supported", Toast.LENGTH_LONG).show();
        }else if (!mbtAdapter.isEnabled()){
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, 1);
        }

        //Check for queue for device
        Set<BluetoothDevice> pairDevices = mbtAdapter.getBondedDevices();
        // If there are paired devices
        if(pairDevices.size() > 0) {
            for (BluetoothDevice device : pairDevices) {
                //Add the name and address to an array adapter
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }

        // Create a Broadcast Reciever for ACTION_FOUND
        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                //When a device is found
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    //Get BT dev obj from intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    //Add name and address into listview
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        };

        //Register the bcast receiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); //unregister during onDestroy

    }

    private void getUserWeight() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Foot Mapper");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userWeight = Double.parseDouble(input.getText().toString());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord",
                        new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }


    @Override
    public void onResume(){
        super.onResume();

        BluetoothDevice device = mbtAdapter.getRemoteDevice(address);

        //use a MAC address and UUID to connect

        try {
            mbtSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }

        //Turn off discovery to reduce resources
        mbtAdapter.cancelDiscovery();

        //Connect to device
        try {
            mbtSocket.connect();
            Log.d(TAG, "Connecting");
        } catch (IOException e){
            try {
                mbtSocket.close();
            } catch (IOException e2) {
                Toast.makeText(getApplicationContext(), "Unable to close socket duing connection failure", Toast.LENGTH_LONG).show();
            }
        }

        //Create a data stream

        try{
            outStream = mbtSocket.getInputStream();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Input stream connection failure", Toast.LENGTH_LONG).show();
        }

    }


    private class ConnectedThread extends Thread {
        private final InputStream mInputStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tempIn = null;

            //get the input and output streams
            try {
                tempIn = socket.getInputStream();
            } catch (IOException e) { }

            mInputStream = tempIn;
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
        //Call from main activity to shutdown connection
        public void cancel() {
            try {
                mbtSocket.close();
            } catch (IOException e) {}
        }

    }


}

}
