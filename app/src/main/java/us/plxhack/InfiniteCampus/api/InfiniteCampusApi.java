package us.plxhack.InfiniteCampus.api;

import java.io.ByteArrayInputStream;
import java.net.URL;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import us.plxhack.InfiniteCampus.api.classbook.ClassbookManager;
import us.plxhack.InfiniteCampus.api.district.DistrictInfo;

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
			//Get User information from formatted web page
			URL infoURL = getFormattedURL("prism?x=portal.PortalOutline&appName=" + core.getDistrictInfo().getDistrictAppName());
			Builder builder = new Builder();
			Document doc = builder.build(new ByteArrayInputStream(core.getContent(infoURL, false).getBytes()));
			Element root = doc.getRootElement();
			
			userInfo = new Student(root.getFirstChildElement("PortalOutline").getFirstChildElement("Family").getFirstChildElement("Student"), core.getDistrictInfo());
	
			//Get classbook information from formatted web page
			URL infoURL2 = getFormattedURL("prism?&x=portal.PortalClassbook-getClassbookForAllSections&mode=classbook&personID=" + userInfo.personID + "&structureID=" + userInfo.calendars.get(0).schedules.get(0).id + "&calendarID=" + userInfo.calendars.get(0).calendarID);
			Document doc2 = builder.build(new ByteArrayInputStream(core.getContent(infoURL2, false).getBytes()));
			
			ClassbookManager classbookInfo = new ClassbookManager(doc2.getRootElement().getFirstChildElement("SectionClassbooks"));

            //please excuse this ungodly mess
            for (int i=0; i < classbookInfo.portalclassbooks.size(); ++i)
            {
                for (int j=0;j < classbookInfo.portalclassbooks.get(i).students.size();++j)
                {
                    for (int k=0;k < classbookInfo.portalclassbooks.get(i).students.get(j).classbooks.size();++k)
                    {
                        userInfo.classbooks.add( classbookInfo.portalclassbooks.get(i).students.get(j).classbooks.get(k));
                    }
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