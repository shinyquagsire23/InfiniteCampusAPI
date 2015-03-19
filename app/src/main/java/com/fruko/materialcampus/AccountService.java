package com.fruko.materialcampus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountService extends Service
{
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
