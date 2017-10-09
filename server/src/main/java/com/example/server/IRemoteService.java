package com.example.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.adil.IImoocAIDL;

/**
 * Created by winnie on 2017/9/30.
 */

public class IRemoteService extends Service {

    //客户端绑定service时会执行
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IImoocAIDL.Stub(){

        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.e("TAG","收到了来自客户端的请求" + num1 + "+" + num2 );
            return num1 + num2;
        }
    };
}

