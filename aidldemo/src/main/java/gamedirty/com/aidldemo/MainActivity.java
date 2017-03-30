package gamedirty.com.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Messenger client;

    Messenger s2c = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.i("info","activity接收到消息:"+msg.arg1);


        }
    });

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            client = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, OntherProcessService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        String pid = String.valueOf(android.os.Process.myPid());
        Log.i("info", "activity的进程名称是:" + pid);
        findViewById(R.id.button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Message message = new Message();
        message.replyTo = s2c;
        message.arg1 = 10;
        try {
            client.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
