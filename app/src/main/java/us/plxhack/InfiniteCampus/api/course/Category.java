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

    public void sort()
    {
        ArrayList<Activity> tempList = new ArrayList<Activity>();

        for (int i=0;i < activities.size();++i)
        {
            if (tempList.size() == 0)
            {
                tempList.add( activities.get(i) );
            }
            else
            {
                int placement = -1;

                for (int j=0;j < tempList.size();++j)
                {
                    if (activities.get(i).name.compareTo(tempList.get(j).name) < 0 && placement == -1)
                    {
                        placement = j;
                        break;
                    }
                }

                if (placement != -1)
                    tempList.add( placement, activities.get(i) );
                else
                    tempList.add( activities.get(i) );
            }
        }

        activities = tempList;
    }
}
