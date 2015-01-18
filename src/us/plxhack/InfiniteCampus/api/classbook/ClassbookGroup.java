package us.plxhack.InfiniteCampus.api.classbook;

import java.util.ArrayList;

import nu.xom.Element;

public class ClassbookGroup
{
	public String activityID;
	public String name;
	public float weight;
	public int seq;
	public boolean notGraded = false;
	//hidePortal
	//hasValidScore
	//composite
	//calcEclude
	public int termID;
	public int taskID;
	public float percentage;
	public String formattedPercentage;
	public String letterGrade;
	public float pointsEarned;
	public float totalPointsPossible;
	
	public ArrayList<ClassbookActivity> activities = new ArrayList<ClassbookActivity>();
	
	public ClassbookGroup(Element group)
	{
		activityID = group.getAttributeValue("activityID");
		name = group.getAttributeValue("name");
		weight = Float.parseFloat(group.getAttributeValue("weight"));
		seq = Integer.parseInt(group.getAttributeValue("seq"));
		notGraded = group.getAttributeValue("notGraded").equalsIgnoreCase("true");
		termID = Integer.parseInt(group.getAttributeValue("termID"));
		taskID = Integer.parseInt(group.getAttributeValue("taskID"));
		percentage = Float.parseFloat(group.getAttributeValue("percentage"));
		formattedPercentage = group.getAttributeValue("formattedPercentage");
		letterGrade = group.getAttributeValue("letterGrade");
		pointsEarned = Float.parseFloat(group.getAttributeValue("pointsEarned"));
		totalPointsPossible = Float.parseFloat(group.getAttributeValue("totalPointsPossible"));
		
		for(int i = 0; i < group.getFirstChildElement("activities").getChildElements("ClassbookActivity").size(); i++)
			activities.add(new ClassbookActivity(group.getFirstChildElement("activities").getChildElements("ClassbookActivity").get(i)));
		
		
		letterGrade = (letterGrade == null ? "?" : letterGrade);
		formattedPercentage = (formattedPercentage == null ? "?" : formattedPercentage);
	}
	
	public String getInfoString()
	{
		String str = name + (name.length() < 16 ? "\t" : "") + "\t(" + (weight > 0 ? "Weight: " + weight + ", " : "") + "Grade: " + letterGrade + ", " + formattedPercentage + "%)";
		
		for(ClassbookActivity a : activities)
			str += "\n\t" + a.getInfoString().replace("\n", "\n\t");
		
		return str;
	}
}
