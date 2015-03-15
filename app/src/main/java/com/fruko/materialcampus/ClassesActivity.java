package com.fruko.materialcampus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import us.plxhack.InfiniteCampus.api.InfiniteCampusApi;

public class ClassesActivity extends Activity
{
    public final static String SELECTED_COURSE_ID = "com.fruko.materialcampus.SELECTED_COURSE_ID";
    public final static String SELECTED_COURSE_NAME = "com.fruko.materialcampus.SELECTED_COURSE_NAME";

    private ListView classList;
    private ArrayAdapter<String> classListElements;

    private String formatClassName( String className )
    {
        String ret = "";

        for (int i=0;i < className.length();++i)
        {
            if (i > 0
                    && className.charAt(i) >= 65 //between capital A
                    && className.charAt(i) <= 90 //and capital Z
                    && !(className.charAt(i-1) >= 32 && className.charAt(i-1) <= 47) //uppercase if the previous character is not a special character or a space
                    && !(className.charAt(i-1) == 'A' && className.charAt(i) == 'P' && (i == className.length() || (className.charAt(i+1) >= 32 && className.charAt(i+1) <= 47))) ) //we want uppercase for AP followed by space/special
            {
                ret += (char)(className.charAt(i)+32);
            }
            else
            {
                ret += className.charAt(i);
            }
        }

        return ret;
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        setTitle( InfiniteCampusApi.userInfo.firstName + ' ' + InfiniteCampusApi.userInfo.lastName );

        ArrayList<String> classNameArray = new ArrayList<String>();

        for (int i=0;i < InfiniteCampusApi.userInfo.classbooks.size(); ++i)
        {
            classNameArray.add( formatClassName (InfiniteCampusApi.userInfo.classbooks.get(i).courseName ) );
        }

        classListElements = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, classNameArray);

        classList = (ListView)findViewById( R.id.class_list );
        classList.setAdapter( classListElements );

        final Activity a = this;
        final ArrayList<String> cA = classNameArray;

        classList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);

                Intent intent = new Intent( a, ClassGradesActivity.class );
                intent.putExtra( SELECTED_COURSE_ID, position );
                intent.putExtra( SELECTED_COURSE_NAME, cA.get( position ) );
                startActivity( intent );
            }
        });
    }

    protected void onStart()
    {
        super.onStart();
    }

    protected void onRestart()
    {
        super.onRestart();
    }

    protected void onResume()
    {
        super.onResume();
    }

    protected void onPause()
    {
        super.onPause();
    }

    protected void onStop()
    {
        super.onStop();
    }

    protected void onDestroy()
    {
        super.onDestroy();
    }
}
