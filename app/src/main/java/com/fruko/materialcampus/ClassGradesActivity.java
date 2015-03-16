package com.fruko.materialcampus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import us.plxhack.InfiniteCampus.api.InfiniteCampusApi;
import us.plxhack.InfiniteCampus.api.course.Category;
import us.plxhack.InfiniteCampus.api.course.Course;

public class ClassGradesActivity extends Activity
{
    private ListView gradesList;

    private Course course;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classgrades);
        course = InfiniteCampusApi.userInfo.courses.get(getIntent().getIntExtra(ClassesActivity.SELECTED_COURSE_ID, 0));
        setTitle( course.getCourseName() + " - " +  new DecimalFormat("#.00").format(course.getPercent()) + "%");


        final ArrayList<String[]> gradesArray = new ArrayList<>();

        for (int i=0;i < course.gradeCategories.size();++i)
        {
            Category c = course.gradeCategories.get(i);

            for (int j=0;j < c.activities.size();++j)
            {
                us.plxhack.InfiniteCampus.api.course.Activity a = c.activities.get(j);

                String[] newArray = {a.name, new DecimalFormat("#.00").format(a.percentage) + "%"};
                gradesArray.add(newArray);
            }
        }

        gradesList = (ListView)findViewById( R.id.class_grades );

        gradesList.setAdapter(new ArrayAdapter<String[]>(this, R.layout.assignment_list_item, R.id.name, gradesArray)
        {
            public View getView(final int position, View convertView, ViewGroup parent)
            {
                View view;
                if (convertView == null) {
                    LayoutInflater infl = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = infl.inflate(R.layout.assignment_list_item, parent, false);
                }
                view = super.getView(position, convertView, parent);

                TextView name = (TextView) view.findViewById(R.id.name);
                name.setText(gradesArray.get(position)[0]);
                TextView grade = (TextView) view.findViewById(R.id.grade);
                grade.setText(gradesArray.get(position)[1]);
                return view;
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
