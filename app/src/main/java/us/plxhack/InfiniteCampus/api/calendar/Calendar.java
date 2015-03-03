package us.plxhack.InfiniteCampus.api.calendar;

import java.util.ArrayList;

import nu.xom.Element;

public class Calendar
{
	public String name;
	public String schoolID;
	public String calendarID;
	public String endYear;
	
	public ArrayList<ScheduleStructure> schedules = new ArrayList<ScheduleStructure>();
	public Calendar(Element calendar)
	{
		name = calendar.getAttributeValue("calendarName");
		schoolID = calendar.getAttributeValue("schoolID");
		calendarID = calendar.getAttributeValue("calendarID");
		endYear = calendar.getAttributeValue("endYear");
		for(int i = 0; i < calendar.getChildElements().size(); i++)
			schedules.add(new ScheduleStructure(calendar.getChildElements().get(i)));
	}
	
	public String getInfoString()
	{
		String returnString = "Information for Calendar \'" + name + "\':\nSchool ID: " + schoolID + "\nCalendar ID: " + calendarID + "\nEnding Year: " + endYear + "\n\n===Schedules===";
		
		for(ScheduleStructure s : schedules)
			returnString += "\n" + s.getInfoString();
		
		return returnString;
	}
}
