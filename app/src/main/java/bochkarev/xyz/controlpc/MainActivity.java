package bochkarev.xyz.controlpc;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText ips = (EditText) findViewById(R.id.ipstr);
        final EditText ports = (EditText) findViewById(R.id.portstr);

        Button send_pr = (Button) findViewById(R.id.btnpr);
        send_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyClientTask Client = new MyClientTask(ips.getText().toString(),Integer.valueOf(String.valueOf(ports.getText())),"computer");
                Client.execute();
            }
        });

        Button send_calc = (Button)findViewById(R.id.btncalc);
        send_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyClientTask Client = new MyClientTask(ips.getText().toString(),Integer.valueOf(String.valueOf(ports.getText())),"calc");
                Client.execute();
            }
        });
    }
    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String comm;

        MyClientTask(String addr, int port, String command){
            dstAddress = addr;
            dstPort = port;
            comm = command;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Socket socket = null;
            try {
                socket = new Socket(dstAddress, dstPort);
                OutputStream out = new DataOutputStream(socket.getOutputStream());
                out.write(comm.getBytes());
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(null);
        }

    }
}
