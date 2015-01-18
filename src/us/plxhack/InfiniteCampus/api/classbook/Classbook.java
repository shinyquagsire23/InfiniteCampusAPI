package us.plxhack.InfiniteCampus.api.classbook;

import java.util.ArrayList;

import nu.xom.Element;

public class Classbook
{
	public String termName;
	public String courseNumber;
	public String courseName;
	public String sectionNumber;
	public String teacherDisplay;
	
	public ArrayList<Curve> curves = new ArrayList<Curve>();
	public ArrayList<ClassbookTask> tasks = new ArrayList<ClassbookTask>();
	public Classbook(Element classbook)
	{
		termName = classbook.getAttributeValue("termName");
		courseNumber = classbook.getAttributeValue("courseNumber");
		courseName = classbook.getAttributeValue("courseName");
		sectionNumber = classbook.getAttributeValue("sectionNumber");
		teacherDisplay = classbook.getAttributeValue("teacherDisplay");
		
		for(int i = 0; i < classbook.getFirstChildElement("tasks").getChildElements("ClassbookTask").size(); i++)
			tasks.add(new ClassbookTask(classbook.getFirstChildElement("tasks").getChildElements("ClassbookTask").get(i)));
		for(int i = 0; i < classbook.getFirstChildElement("curves").getChildElements("Curve").size(); i++)
			curves.add(new Curve(classbook.getFirstChildElement("curves").getChildElements("Curve").get(i)));
	}
	
	public String getInfoString()
	{
		String str = "\nTasks for " + courseName + ", with teacher " + teacherDisplay + " and class ID " + courseNumber + ", " + termName;
		for(ClassbookTask t : tasks)
			str += "\n" + t.getInfoString();
		return str;
	}
}
