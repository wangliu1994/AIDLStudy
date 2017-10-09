package com.example.testaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adil.IImoocAIDL;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText num1;
    private EditText num2;
    private Button button;
    private TextView text;

    private IImoocAIDL iImoocAIDL;

    private ServiceConnection conn = new ServiceConnection() {

        //绑定服务，回调onBind()方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iImoocAIDL = IImoocAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iImoocAIDL = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindService();
        initView();

    }

    private void initView() {
        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        button = (Button) findViewById(R.id.button);
        text = (TextView) findViewById(R.id.text);

        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int num11 = Integer.parseInt(num1.getText().toString());
        int num22 = Integer.parseInt(num2.getText().toString());

        try {
            int res = iImoocAIDL.add(num11,num22);
            text.setText(num11 +"+"+ num22 +"="+ res);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindService() {

        Intent intent = new Intent();
        //绑定服务端的service
        intent.setAction("com.example.server.IRomoteService");
        //新版本（5.0后）必须显式intent启动 绑定服务
        intent.setComponent(new ComponentName("com.example.server","com.example.server.IRemoteService"));
        //绑定的时候服务端自动创建
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
