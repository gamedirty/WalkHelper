package gamedirty.com.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sovnem on 2017/3/30,20:09.
 */

public class OntherProcessService extends Service {


    Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            Log.i("info", "service接收到消息:" + msg.arg1);
            try {
                Message m = new Message();
                m.replyTo = messenger;
                m.arg1 = 1109;
                msg.replyTo.send(m);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String pid = String.valueOf(android.os.Process.myPid());
        Log.i("info", "服务的进程名称是:" + pid);
        return messenger.getBinder();
    }
}
