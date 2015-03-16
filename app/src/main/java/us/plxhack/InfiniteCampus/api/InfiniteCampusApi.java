package us.plxhack.InfiniteCampus.api;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import us.plxhack.InfiniteCampus.api.course.Activity;
import us.plxhack.InfiniteCampus.api.course.Category;
import us.plxhack.InfiniteCampus.api.district.DistrictInfo;
import us.plxhack.InfiniteCampus.api.course.Course;

public class InfiniteCampusApi
{
	public static DistrictInfo districtInfo;
	public static Student userInfo;
	private static boolean isLoggedIn = false;
	
	private static URL getFormattedURL( String document )
	{
		try
		{
			return new URL( getFormattedURLString( document ) );
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getFormattedURLString( String document )
	{
		return districtInfo.getDistrictBaseURL() + document;
	}
	
	public static boolean isLoggedIn( )
	{
		return isLoggedIn;
	}
	
	public static boolean login( String districtCode, String username, String password )
	{
		//Get District Information from district code
		CoreManager core = new CoreManager(districtCode);
		districtInfo = core.getDistrictInfo();
				
		//Try to log in with given district info, username, and pass
		if (!core.attemptLogin(username, password, core.getDistrictInfo()))
			return false;

		try
		{
            Builder builder = new Builder();
            {
                //Get User information from formatted web page
                URL infoURL = getFormattedURL("prism?x=portal.PortalOutline&appName=" + core.getDistrictInfo().getDistrictAppName());
                Document doc = builder.build(new ByteArrayInputStream(core.getContent(infoURL, false).getBytes()));
                Element root = doc.getRootElement();

                userInfo = new Student(root.getFirstChildElement("PortalOutline").getFirstChildElement("Family").getFirstChildElement("Student"), core.getDistrictInfo());
            }
	
			//Get classbook information from formatted web page
			URL infoURL2 = getFormattedURL("prism?&x=portal.PortalClassbook-getClassbookForAllSections&mode=classbook&personID=" + userInfo.personID + "&structureID=" + userInfo.calendars.get(0).schedules.get(0).id + "&calendarID=" + userInfo.calendars.get(0).calendarID);
			Document doc2 = builder.build(new ByteArrayInputStream(core.getContent(infoURL2, false).getBytes()));

            Element root = doc2.getRootElement().getFirstChildElement("SectionClassbooks");
            ArrayList<Element> courseElements = new ArrayList<Element>();

            for (int i=0;i < root.getChildCount();++i)
            {
                Element portalClassbook = root.getChildElements().get(i);
                Element studentList = portalClassbook.getFirstChildElement("ClassbookDetail").getFirstChildElement("StudentList");

                if (studentList.getChildCount() != 0)
                    courseElements.add( portalClassbook.getFirstChildElement("ClassbookDetail").getFirstChildElement("StudentList").getChildElements().get(0).getFirstChildElement("Classbook") );
            }

            ArrayList<Course> courses = new ArrayList<Course>();

            for (int i=0;i < courseElements.size();++i)
            {
                Element e = courseElements.get(i);

                Course c = new Course( Integer.valueOf( e.getAttributeValue("courseNumber") ), e.getAttributeValue("courseName"), e.getAttributeValue("teacherDisplay") );

                Elements tasks = e.getFirstChildElement("tasks").getChildElements();
                Element finalTask = null;

                for (int j=0;j < tasks.size();++j)
                {
                    if (tasks.get(j).getAttributeValue("name").equalsIgnoreCase("final"))
                        finalTask = tasks.get(j);
                }

                c.percentage = Float.valueOf(finalTask.getAttributeValue("percentage"));
                c.letterGrade = finalTask.getAttributeValue("letterGrade").charAt(0);

                Element finalTaskGroups = finalTask.getFirstChildElement("tasks").getFirstChildElement("ClassbookTask").getFirstChildElement("groups");

                for (int j=0;j < finalTaskGroups.getChildCount();++j)
                {
                    Element groupElement = finalTaskGroups.getChildElements().get(j);

                    Category category = new Category( groupElement.getAttributeValue("name") );
                    category.percentage = Float.valueOf( groupElement.getAttributeValue("percentage") );
                    category.earnedPoints = Float.valueOf( groupElement.getAttributeValue("pointsEarned") );
                    category.totalPoints = Float.valueOf( groupElement.getAttributeValue("totalPointsPossible") );
                    category.weight = Float.valueOf( groupElement.getAttributeValue("weight") );
                    category.letterGrade = groupElement.getAttributeValue("letterGrade").charAt(0);

                    Element activities = groupElement.getFirstChildElement("activities");

                    for (int k=0;k < activities.getChildCount();++k)
                    {
                        Element activityElement = activities.getChildElements().get(k);

                        Activity a = new Activity( activityElement.getAttributeValue("name") );
                        a.percentage = Float.valueOf( activityElement.getAttributeValue("percentage") );
                        a.earnedPoints = Float.valueOf( activityElement.getAttributeValue("weightedScore") );
                        a.totalPoints = Float.valueOf( activityElement.getAttributeValue("weightedTotalPoints") );
                        a.dueDate = activityElement.getAttributeValue("dueDate");

                        String letterGrade = activityElement.getAttributeValue("letterGrade");
                        if (letterGrade != null)
                            a.letterGrade = letterGrade;
                        else
                            a.letterGrade = "N/A";

                        category.activities.add( a );
                    }

                    category.sort();

                    c.gradeCategories.add( category );
                }

                courses.add( c );
            }

            userInfo.courses = new ArrayList<Course>( );

            //alphabetically sort course list
            for (int i=0;i < courses.size();++i)
            {
                if (userInfo.courses.size() == 0)
                {
                    userInfo.courses.add( courses.get(i) );
                }
                else
                {
                    int placement = -1;

                    for (int j=0;j < userInfo.courses.size();++j)
                    {
                        if (courses.get(i).getCourseName().compareTo(userInfo.courses.get(j).getCourseName()) < 0 && placement == -1)
                        {
                            placement = j;
                            break;
                        }
                    }

                    if (placement != -1)
                        userInfo.courses.add( placement, courses.get(i) );
                    else
                        userInfo.courses.add( courses.get(i) );
                }
            }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		isLoggedIn = true;
		return true;
	}
	
	public static void printDebugInfo()
	{
		System.out.println("District Information:");
		System.out.println("District: " + districtInfo.getDistrictName());
		System.out.println("State: " + districtInfo.getDistrictStateCode());
		System.out.println("Base URL: " + districtInfo.getDistrictBaseURL());
		System.out.println("District App Name: " + districtInfo.getDistrictAppName());
	
		if (isLoggedIn( ))
		{
			System.out.println(userInfo.getInfoString());
		}
		else
		{
			System.out.println("\nNot logged in");
		}
	}
}