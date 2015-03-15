package us.plxhack.InfiniteCampus.api.course;

public class Activity
{
    public String name;

    public float percentage;
    public float earnedPoints, totalPoints;
    public String letterGrade;

    public String dueDate;

    public Activity( String name )
    {
        this.name = name;
    }
}
