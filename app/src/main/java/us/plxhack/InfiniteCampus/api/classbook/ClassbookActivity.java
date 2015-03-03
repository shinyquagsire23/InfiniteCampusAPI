package us.plxhack.InfiniteCampus.api.classbook;

import nu.xom.Element;

public class ClassbookActivity
{
	public String activityID;
	public String name;
	public String abbrev;
	public String dueDate;
	public String assignedDate;
	public float totalPoints;
	public boolean active;
	public boolean notGraded;
	//hidePortal
	//seq
	public float weight;
	public String scoringType;
	public boolean validScore;
	public String scoreID;
	public float score;
	public boolean late;
	public boolean missing;
	public boolean incomplete;
	public boolean turnedIn;
	public boolean exempt;
	public boolean cheated;
	public boolean dropped;
	public float percentage;
	public String letterGrade;
	public float weightedScore;
	public float weightedTotalPoints;
	public float weightedPercentage;
	public int numericScore;
	public boolean wysiwygSubmission;
	public boolean onlineAssessment;
	
	public ClassbookActivity(Element activity)
	{
		activityID = activity.getAttributeValue("activityID");
		name = activity.getAttributeValue("name");
		abbrev = activity.getAttributeValue("abbrev");
		dueDate = activity.getAttributeValue("dueDate");
		assignedDate = activity.getAttributeValue("assignedDate");
		totalPoints = Float.parseFloat(activity.getAttributeValue("totalPoints"));
		active = activity.getAttributeValue("active").equalsIgnoreCase("true");
		notGraded = activity.getAttributeValue("notGraded").equalsIgnoreCase("true");
		weight = Float.parseFloat(activity.getAttributeValue("weight"));
		scoringType = activity.getAttributeValue("scoringType");
		validScore = activity.getAttributeValue("validScore").equalsIgnoreCase("validScore");
		scoreID = activity.getAttributeValue("scoreID");
		score = Float.parseFloat(activity.getAttributeValue("score"));
		late = activity.getAttributeValue("late").equalsIgnoreCase("true");
		missing = activity.getAttributeValue("missing").equalsIgnoreCase("true");
		incomplete = activity.getAttributeValue("incomplete").equalsIgnoreCase("true");
		turnedIn = activity.getAttributeValue("turnedIn").equalsIgnoreCase("true");
		cheated = activity.getAttributeValue("cheated").equalsIgnoreCase("true");
		dropped = activity.getAttributeValue("dropped").equalsIgnoreCase("true");
		percentage = Float.parseFloat(activity.getAttributeValue("percentage"));
		letterGrade = activity.getAttributeValue("letterGrade");
		weightedScore = Float.parseFloat(activity.getAttributeValue("weightedScore"));
		weightedTotalPoints = Float.parseFloat(activity.getAttributeValue("weightedTotalPoints"));
		weightedPercentage = Float.parseFloat(activity.getAttributeValue("weightedPercentage"));
		numericScore = Integer.parseInt(activity.getAttributeValue("numericScore"));
		wysiwygSubmission = activity.getAttributeValue("wysiwygSubmission").equalsIgnoreCase("true");
		onlineAssessment = activity.getAttributeValue("onlineAssessment").equalsIgnoreCase("true");
	}
	
	public String getInfoString()
	{
		return name + "\t\t" + letterGrade + " " + weightedPercentage + "%" + "\t(" + score + "/" + (int)totalPoints + ")";
	}
}
