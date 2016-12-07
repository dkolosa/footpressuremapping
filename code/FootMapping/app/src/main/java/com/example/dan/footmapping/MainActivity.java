package com.example.dan.footmapping;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mbtAdapter = null;
    private BluetoothSocket mbtSocket = null;
    private InputStream outStream = null;


    private ConnectedThread mConnectedThread;
    private static final String TAG = "bluetooth";
    private double userWeight = 0.0;
    private StringBuilder sb = new StringBuilder();
    private List<String> mArrayAdapter = null;

//    private int[] sensorDAta = ;
    private int sensors = 5;

    Handler h;
    TextView txtArduino;

    TextView topleft;
    TextView topright;
    TextView midleft;
    TextView midright;
    TextView botleft;
    TextView botright;

    Button btnOn, btnOff;

    final int RECIEVE_MESSAGE = 1;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "20:16:08:16:14:78";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        txtArduino = (TextView) findViewById(R.id.txtArduino);

        //Generate id of squares

        topleft = (TextView) findViewById(R.id.topleft);
        topright = (TextView) findViewById(R.id.topright);
        midleft = (TextView) findViewById(R.id.midleft);
        midright = (TextView) findViewById(R.id.midright);
        botleft = (TextView) findViewById(R.id.botleft);
        botright = (TextView) findViewById(R.id.botright);

//        btnOn = (Button) findViewById(R.id.btm_on);
//        btnOff = (Button) findViewById(R.id.btn_off);



        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:                                                   // if receive massage
                        byte[] readBuf = (byte[]) msg.obj;

//                        ByteBuffer wrapped = ByteBuffer.wrap(readBuf);
//                        sensorDAta = wrapped.getInt();

                        String strIncom = new String(readBuf, 0, msg.arg1);                 // create string from bytes array

                        sb.append(strIncom);                                                // append string
                        int endOfLineIndex = sb.indexOf("~");                            // determine the end-of-line

                        if (endOfLineIndex > 0) {                                            // if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex);               // extract string
                            sb.delete(0, sb.length());                                      // and clear
                            // update TextView

                            sbprint = sbprint.replaceFirst(Pattern.quote("#"), "");

                            if(sbprint.startsWith("+"))
                            {
                                sbprint.replaceFirst(Pattern.quote("+"), "");
                            }

                            if(sbprint.contains("++")) {
                                sbprint.replace(Pattern.quote("++"), "+0+");
                            }
                            if(sbprint.contains("+++")) {
                                sbprint.replace(Pattern.quote("+++"), "+0+0+");
                            }

                            String[] parts = sbprint.split(Pattern.quote("+"));

//                            topleft.setText(parts[0]);
//                            topright.setText(parts[1]);
//                            midleft.setText(parts[2]);
//                            midright.setText(parts[3]);
//                            botleft.setText(parts[4]);
//                            botright.setText(parts[5]);



                            int sensorData = (parts[0].equals("")) ? 0 : Integer.parseInt(parts[0]);
                            //Log.d(TAG, "String:" + sensorData);

                            if (sensorData <= 40){
                                topleft.setBackgroundColor(Color.BLUE);
                            } else if (sensorData > 40 && sensorData < 80) {
                                topleft.setBackgroundColor(Color.GREEN);
                            } else if (sensorData > 80 && sensorData < 120) {
                                topleft.setBackgroundColor(Color.RED);}
                            else if ( sensorData >= 120) {
                                topleft.setBackgroundColor(Color.MAGENTA);
                            }

//                            txtArduino.setText(sbprint);

                            sensorData = (parts[1].equals("")) ? 0 : Integer.parseInt(parts[1]);
                            if (sensorData <= 40){
                                topright.setBackgroundColor(Color.BLUE);
                            } else if (sensorData > 40 && sensorData <= 80) {
                                topright.setBackgroundColor(Color.GREEN);
                            } else if (sensorData > 80 && sensorData < 120) {
                                topright.setBackgroundColor(Color.RED);}
                            else if ( sensorData >= 120) {
                                topright.setBackgroundColor(Color.MAGENTA);
                            }

                            sensorData = (parts[2].equals("")) ? 0 : Integer.parseInt(parts[2]);
                            if (sensorData <= 40){
                                midleft.setBackgroundColor(Color.BLUE);
                            } else if (sensorData > 80 && sensorData <= 80) {
                                midleft.setBackgroundColor(Color.GREEN);
                            } else if (sensorData > 80 && sensorData < 120) {
                                midleft.setBackgroundColor(Color.RED);}
                            else if ( sensorData >= 120) {
                                midleft.setBackgroundColor(Color.MAGENTA);
                            }


//                            sensorData = Integer.parseInt(parts[3]);
                            sensorData = (parts[3].equals("")) ? 0 : Integer.parseInt(parts[3]);
                            if (sensorData <= 40){
                                midright.setBackgroundColor(Color.BLUE);
                            } else if (sensorData > 40 && sensorData <= 80) {
                                midright.setBackgroundColor(Color.GREEN);
                            } else if (sensorData > 80 && sensorData < 120) {
                                midright.setBackgroundColor(Color.RED);}
                            else if ( sensorData >= 120) {
                                midright.setBackgroundColor(Color.MAGENTA);
                            }

//                            sensorData = Integer.parseInt(parts[4]);
                            sensorData = (parts[4].equals("")) ? 0 : Integer.parseInt(parts[4]);
                            if (sensorData <= 40){
                                botleft.setBackgroundColor(Color.BLUE);
                            } else if (sensorData > 40 && sensorData <= 80) {
                                botleft.setBackgroundColor(Color.GREEN);
                            } else if (sensorData > 80 && sensorData < 120) {
                                botleft.setBackgroundColor(Color.RED);}
                            else if ( sensorData >= 120) {
                                botleft.setBackgroundColor(Color.MAGENTA);
                            }

                             sensorData = (parts[5].equals("")) ? 0 : Integer.parseInt(parts[5]);
                            if (sensorData <= 40){
                                botright.setBackgroundColor(Color.BLUE);
                            } else if (sensorData > 40 && sensorData <= 80) {
                                botright.setBackgroundColor(Color.GREEN);
                            } else if (sensorData > 80 && sensorData < 120) {
                                botright.setBackgroundColor(Color.RED);}
                            else if ( sensorData >= 120) {
                                botright.setBackgroundColor(Color.MAGENTA);
                            }
                        }

                        //Convert the colors
                        // blue : 0-341, green: 342-682, red: 683-1023


                        break;
                }
            }
        };

        //Check if bluetooth is supported if it is turn it on
        mbtAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothState();
        getUserWeight();


//        btnOn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btnOn.setEnabled(false);
//                btnOff.setEnabled(true);
//                mConnectedThread.write("1");
//            }
//        });
//
//        btnOff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btnOff.setEnabled(false);
//                btnOn.setEnabled(true);
//                mConnectedThread.write("0");
//            }
//        });

    }


    private void bluetoothState(){
        if (mbtAdapter == null) {
            Toast.makeText(getApplicationContext(),
                    "Bluetooth not supported", Toast.LENGTH_LONG).show();
        }else if (!mbtAdapter.isEnabled()){
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, 1);
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

//        //Check for queue for device
//        Set<BluetoothDevice> pairDevices = mbtAdapter.getBondedDevices();
//        // If there are paired devices
//        if(pairDevices.size() > 0) {
//            for (BluetoothDevice device : pairDevices) {
//                //Add the name and address to an array adapter
//                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//            }
//        }

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
            Toast.makeText(getApplicationContext(), "Socket created", Toast.LENGTH_LONG).show();

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
            Toast.makeText(getApplicationContext(), "Getting input data", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Input stream connection failure", Toast.LENGTH_LONG).show();
        }

        mConnectedThread = new ConnectedThread(mbtSocket);
        mConnectedThread.start();

    }


    private class ConnectedThread extends Thread {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                getUserWeight();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
