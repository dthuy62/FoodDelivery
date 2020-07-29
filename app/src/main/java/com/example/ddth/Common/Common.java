package com.example.ddth.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.ddth.Model.Users;
import com.example.ddth.Model.Ratting;

public class Common {
    public static Users currentUser;

    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if(networkInfo != null)
            {
                for(int i = 0;i<networkInfo.length;i++)
                {
                    if(networkInfo[i].getState() == NetworkInfo.State.CONNECTED )
                        return true;
                }
            }
        }
        return false;
    }

    public static final String DELETE = "Delete";

    public static final String INTENT_FOOD_ID = "foodId";

}
