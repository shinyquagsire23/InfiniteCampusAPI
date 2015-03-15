package com.fruko.materialcampus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import us.plxhack.InfiniteCampus.api.InfiniteCampusApi;
import us.plxhack.InfiniteCampus.api.classbook.Classbook;
import us.plxhack.InfiniteCampus.api.classbook.ClassbookTask;

public class ClassGradesActivity extends Activity
{
    private ListView gradesList;
    private ArrayAdapter<String> gradeListElements;

    private Classbook course;

    void getGradesRecursive( ClassbookTask task, ArrayList<String> gradesArray )
    {
        if (task.groups.size() != 0)
        {
            for (int i=0;i < task.groups.size();++i)
            {
                System.out.println( task.groups.get(i).name );

                for (int j=0;j < task.groups.get(i).activities.size();++j)
                {
                    System.out.println( task.groups.get(i).activities.get(j).name );
                    gradesArray.add( task.groups.get(i).activities.get(j).name );
                }
            }
        }
        else if (task.tasks.size() != 0)
        {
            for (int i=0;i < task.tasks.size();++i)
            {
                getGradesRecursive( task.tasks.get(i), gradesArray );
            }
        }
    }

    void getGrades( Classbook course, ArrayList<String> gradesArray )
    {
        for (int i=0;i < course.tasks.size();++i)
        {
            getGradesRecursive( course.tasks.get(i), gradesArray );
        }
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classgrades);

        course = InfiniteCampusApi.userInfo.classbooks.get( getIntent().getIntExtra(ClassesActivity.SELECTED_COURSE_ID, 0) );
        setTitle( getIntent().getStringExtra(ClassesActivity.SELECTED_COURSE_NAME) );

        ArrayList<String> gradesArray = new ArrayList<String>();
        getGrades( course, gradesArray );

        System.out.println("done getting grades");

        gradeListElements = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, gradesArray);

        gradesList = (ListView)findViewById( R.id.class_grades );
        gradesList.setAdapter( gradeListElements );
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
