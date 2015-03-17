package com.fruko.materialcampus;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import us.plxhack.InfiniteCampus.api.InfiniteCampusApi;
import us.plxhack.InfiniteCampus.api.course.Course;

public class ClassesActivity extends ActionBarActivity
{
    public final static String SELECTED_COURSE_ID = "com.fruko.materialcampus.SELECTED_COURSE_ID";

    private ListView classList;
    private boolean updateList = false;

    private void getCourseList()
    {
        final ArrayList<String[]> classNameArray = new ArrayList<>();

        for (int i=0;i < InfiniteCampusApi.userInfo.courses.size(); ++i)
        {
            Course course = InfiniteCampusApi.userInfo.courses.get(i);
            String[] newArray = {course.getCourseName(), new DecimalFormat("#.00").format(course.getPercent()) + "%", course.getTeacherName()};
            classNameArray.add(newArray);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        classList = (ListView)findViewById( R.id.class_list );

        classList.setAdapter(new ArrayAdapter<String[]>(this, R.layout.class_list_item, R.id.name, classNameArray) {
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    LayoutInflater infl = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    convertView = infl.inflate(R.layout.class_list_item, parent, false);
                }
                view = super.getView(position, convertView, parent);

                TextView name = (TextView) view.findViewById(R.id.name);
                name.setText(classNameArray.get(position)[0]);
                TextView grade = (TextView) view.findViewById(R.id.grade);
                grade.setText(classNameArray.get(position)[1]);
                TextView teacher = (TextView) view.findViewById(R.id.teacher);
                teacher.setText(classNameArray.get(position)[2]);
                return view;
            }
        });

        final Activity a = this;

        classList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( a, ClassGradesActivity.class );
                intent.putExtra( SELECTED_COURSE_ID, position );
                startActivity( intent );
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        setTitle( InfiniteCampusApi.userInfo.firstName + ' ' + InfiniteCampusApi.userInfo.lastName );
        getCourseList();

        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout)findViewById( R.id.class_swipe );
        swipeView.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                new UserRefreshTask( swipeView ).execute();
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

    @Override
    public void onBackPressed()
    {
        moveTaskToBack( true );
    }

    private class UserRefreshTask extends AsyncTask<Void, Void, Boolean>
    {
        private SwipeRefreshLayout swipeLayout;

        public UserRefreshTask( SwipeRefreshLayout swipelayout )
        {
            swipeLayout = swipelayout;
        }

        @Override
        protected Boolean doInBackground( Void... params)
        {
            return InfiniteCampusApi.relogin();
        }

        protected void onPostExecute( Boolean result )
        {
            getCourseList();
            swipeLayout.setRefreshing( false );
        }
    }
}
