package com.fruko.materialcampus;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;

public class AccountController
{
    private static String accountType = "com.fruko.materialcampus";
    private Context context;

    public AccountController( Context c )
    {
        this.context = c;
    }

    public boolean saveAccount( String username, String password, String districtCode )
    {
        Account newAccount = new Account( username, accountType );
        Bundle data = new Bundle( 1 );
        data.putString( "district", districtCode );

        AccountManager am = AccountManager.get( context );

        return am.addAccountExplicitly( newAccount, password, data );
    }

    public AccountDataContainer getAccount( int which )
    {
        AccountManager am = AccountManager.get( context );
        Account[] accounts = am.getAccountsByType( accountType );

        if (accounts.length == 0)
            return null;
        else
        {
            Account a = accounts[which];
            String password = am.getPassword( a );
            String username = a.name;
            String district = am.getUserData( a, "district" );

            return new AccountDataContainer( username, password, district );
        }
    }

    public AccountDataContainer getFirstAccount( )
    {
        return getAccount( 0 );
    }

    public class AccountDataContainer
    {
        public String username, password, district;

        public AccountDataContainer( String _u, String _p, String _d )
        {
            username = _u;
            password = _p;
            district = _d;
        }
    }
}
