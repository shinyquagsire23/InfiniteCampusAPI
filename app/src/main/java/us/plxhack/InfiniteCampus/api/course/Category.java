package us.plxhack.InfiniteCampus.api.course;

import java.util.ArrayList;

public class Category
{
    public String name;

    public float percentage;
    public float earnedPoints, totalPoints;
    public float weight;
    public char letterGrade;

    public ArrayList<Activity> activities;

    public Category( String name )
    {
        this.name = name;
        activities = new ArrayList<Activity>();
    }
}
