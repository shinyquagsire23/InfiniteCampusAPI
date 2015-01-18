package us.plxhack.InfiniteCampus.api.classbook;

import java.util.ArrayList;

import nu.xom.Element;

public class ClassbookTask
{
	public String taskID;
	public String name;
	public float weight;
	public boolean isWeighted = false;
	// hasValidGroup
	// hasValidWeightedGroup
	// locked
	public boolean gradeBookPosted = false;
	public int taskSeq;
	public int termID;
	public String termName;
	public int termSeq;
	public float totalPointsPossible;
	public float pointsEarned;
	public float percentage;
	public String letterGrade;
	public String formattedPercentage;
	public int curveID;

	public ArrayList<ClassbookTask> tasks = new ArrayList<ClassbookTask>();
	public ArrayList<ClassbookGroup> groups = new ArrayList<ClassbookGroup>();

	public ClassbookTask(Element task)
	{

		name = task.getAttributeValue("name");
		taskID = task.getAttributeValue("taskID");
		weight = Float.parseFloat(task.getAttributeValue("weight"));
		isWeighted = task.getAttributeValue("isWeighted").equalsIgnoreCase("true");
		gradeBookPosted = task.getAttributeValue("gradeBookPosted").equalsIgnoreCase("true");
		taskSeq = Integer.parseInt(task.getAttributeValue("taskSeq"));
		termID = Integer.parseInt(task.getAttributeValue("termID"));
		termName = task.getAttributeValue("termName");
		termSeq = Integer.parseInt(task.getAttributeValue("termSeq"));
		totalPointsPossible = Float.parseFloat(task.getAttributeValue("totalPointsPossible"));
		pointsEarned = Float.parseFloat(task.getAttributeValue("pointsEarned"));
		percentage = Float.parseFloat(task.getAttributeValue("percentage"));
		letterGrade = task.getAttributeValue("letterGrade");
		formattedPercentage = task.getAttributeValue("formattedPercentage");
		
		try
		{
			for (int i = 0; i < task.getFirstChildElement("groups").getChildElements("ClassbookGroup").size(); i++)
				groups.add(new ClassbookGroup(task.getFirstChildElement("groups").getChildElements("ClassbookGroup").get(i)));
		}
		catch(Exception e){}
		
		try
		{
			curveID = Integer.parseInt(task.getAttributeValue("curveID"));
		
			for (int i = 0; i < task.getFirstChildElement("tasks").getChildElements("ClassbookTask").size(); i++)
				tasks.add(new ClassbookTask(task.getFirstChildElement("tasks").getChildElements("ClassbookTask").get(i)));
		}
		catch (Exception e)
		{
		}
		
		letterGrade = (letterGrade == null ? "?" : letterGrade);
		formattedPercentage = (formattedPercentage == null ? "?" : formattedPercentage);
	}

	public String getInfoString()
	{
		String str =  "Task: " + name + ", " + termName + " " + letterGrade + " " + formattedPercentage + "%";
		for(ClassbookTask t : tasks)
			str +=  "\n\t" + t.getInfoString().replace("\n", "\n\t");
		for(ClassbookGroup b : groups)
			str += "\n\t" + b.getInfoString().replace("\n", "\n\t");
		return str;
	}
}
