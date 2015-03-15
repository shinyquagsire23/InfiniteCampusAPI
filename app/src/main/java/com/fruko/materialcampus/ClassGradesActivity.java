package com.fruko.materialcampus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import us.plxhack.InfiniteCampus.api.InfiniteCampusApi;
import us.plxhack.InfiniteCampus.api.course.Category;
import us.plxhack.InfiniteCampus.api.course.Course;

public class ClassGradesActivity extends Activity
{
    private ListView gradesList;
    private ArrayAdapter<String> gradeListElements;

    private Course course;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classgrades);

        course = InfiniteCampusApi.userInfo.courses.get(getIntent().getIntExtra(ClassesActivity.SELECTED_COURSE_ID, 0));
        setTitle( getIntent().getStringExtra(ClassesActivity.SELECTED_COURSE_NAME) );

        ArrayList<String> gradesArray = new ArrayList<String>();

        for (int i=0;i < course.gradeCategories.size();++i)
        {
            Category c = course.gradeCategories.get(i);

            for (int j=0;j < c.activities.size();++j)
            {
                us.plxhack.InfiniteCampus.api.course.Activity a = c.activities.get(j);

                gradesArray.add(a.name + " (" + a.letterGrade + ")");
            }
        }

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
