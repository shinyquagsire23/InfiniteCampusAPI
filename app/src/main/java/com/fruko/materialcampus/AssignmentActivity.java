package com.fruko.materialcampus;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import us.plxhack.InfiniteCampus.api.InfiniteCampusApi;
import us.plxhack.InfiniteCampus.api.course.Category;
import us.plxhack.InfiniteCampus.api.course.Course;

/**
 * Created by student on 3/18/2015.
 */
public class AssignmentActivity extends ActionBarActivity
{
    private ListView gradesList;

    private Course course;
    private us.plxhack.InfiniteCampus.api.course.Activity assignment;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        course = InfiniteCampusApi.userInfo.courses.get(getIntent().getIntExtra(ClassGradesActivity.SELECTED_COURSE_ID, 0));
        int counter = 0;
        for(int i = 0; i < course.gradeCategories.size(); i++)
        {
            for(int j = 0; j < course.gradeCategories.get(i).activities.size(); j++)
            {
                if(counter == getIntent().getIntExtra(ClassGradesActivity.SELECTED_ASSIGNMENT_ID, 0))
                {
                    assignment = InfiniteCampusApi.userInfo.courses.get(getIntent().getIntExtra(ClassGradesActivity.SELECTED_COURSE_ID, 0)).gradeCategories.get(i).activities.get(j);
                }
                counter++;
            }
        }
        setTitle(assignment.name + " - " +  assignment.percentage);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TextView percent = (TextView) findViewById(R.id.percent);
        percent.setText(Float.toString(assignment.percentage) + "%");
        TextView points = (TextView) findViewById(R.id.points);
        points.setText(Float.toString(assignment.earnedPoints));
        TextView total = (TextView) findViewById(R.id.total);
        total.setText(Float.toString(assignment.totalPoints));
        TextView grade = (TextView) findViewById(R.id.grade);
        grade.setText(assignment.letterGrade);
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
