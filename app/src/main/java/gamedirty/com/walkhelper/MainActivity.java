
package gamedirty.com.walkhelper;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.TextView;

public class MainActivity extends Activity implements Callback {

    private static final String TAG="nsc";
    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVER = 1;//返回服务
    public static final int REQUEST_SERVER = 2;//取消服务
    private long TIME_INTERVAL = 500;

    private TextView text;
    private Handler delayHandler;
    private Messenger messenger;
    private Messenger mGetReplyMessenger = new Messenger(new Handler(this));

    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // TODO Auto-generated method stub

        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            try{
                messenger = new Messenger(service);
                Message msg = Message.obtain(null,MSG_FROM_CLIENT);
                msg.replyTo = mGetReplyMessenger;//replyTo消息管理器
                messenger.send(msg);//发送消息出去
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();//初始化ui和启动线程
        setupService();//启动服务
    }

    /**
     * 启动服务
     */
    private void setupService() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(this,StepService.class);
        //使用这个ServiceConnection，客户端可以绑定到一个service，通过把它传给bindService()
        //第一个bindService()的参数是一个明确指定了要绑定的service的Intent．
        //第二个参数是ServiceConnection对象．
        //第三个参数是一个标志，它表明绑定中的操作．它一般应是BIND_AUTO_CREATE，这样就会在service不存在时创建一个．其它可选的值是BIND_DEBUG_UNBIND和BIND_NOT_FOREGROUND,不想指定时设为0即可．。
        bindService(intent, conn, BIND_AUTO_CREATE);//BIND_AUTO_CREATE =1
        startService(intent);
    }

    private void initUI() {
        // TODO Auto-generated method stub
        text = (TextView)findViewById(R.id.text);
        delayHandler = new Handler(this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        // TODO Auto-generated method stub
        switch(msg.what){
            case MSG_FROM_SERVER:
                text.setText(msg.getData().getInt("step")+"");//显示记步数
                //延时500ms发送值为REQUEST_SERVER 消息
                delayHandler.sendEmptyMessageDelayed(REQUEST_SERVER, TIME_INTERVAL);
                break;
            case REQUEST_SERVER:
                try{
                    Message message = Message.obtain(null,MSG_FROM_CLIENT);//发送消息
                    message.replyTo = mGetReplyMessenger;
                    messenger.send(message);
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unbindService(conn);//解除服务的绑定
    }


}
