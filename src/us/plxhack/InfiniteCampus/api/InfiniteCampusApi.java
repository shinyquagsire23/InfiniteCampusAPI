package us.plxhack.InfiniteCampus.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import us.plxhack.InfiniteCampus.api.classbook.ClassbookManager;
import us.plxhack.InfiniteCampus.api.district.DistrictInfo;

public class InfiniteCampusApi
{
	public static String districtCode = "XKTDKL";
	public static String icURL = "https://ic.d214.org";
	public static DistrictInfo districtInfo;
	public static Student userInfo;
	public static ClassbookManager classbookInfo;
	
	public static boolean login( String username, String password )
	{
		File f = new File("grades.txt");
		if (f.exists())
			f.delete();

		CoreManager core = new CoreManager(districtCode);
		districtInfo = core.getDistrictInfo();
		System.out.println("Found District Information:");
		System.out.println("District: " + districtInfo.getDistrictName());
		System.out.println("State: " + districtInfo.getDistrictStateCode());
		System.out.println("Base URL: " + districtInfo.getDistrictBaseURL());
		System.out.println("District App Name: " + districtInfo.getDistrictAppName());
		
		System.out.println("Attempting login...");

		System.out.println("Logging into user " + username + "...");
		boolean successfulLogin = core.attemptLogin(username, password, core.getDistrictInfo());
		System.out.println(successfulLogin ? "Login success!" : "Login failed!");
		
		if (!successfulLogin)
			return false;

		try
		{
			URL infoURL = new URL(icURL + "/campus/prism?x=portal.PortalOutline&appName=" + core.getDistrictInfo().getDistrictAppName());
			Builder builder = new Builder();
			Document doc = builder.build(new ByteArrayInputStream(core.getContent(infoURL, false).getBytes()));
			Element root = doc.getRootElement();
			userInfo = new Student(root.getFirstChildElement("PortalOutline").getFirstChildElement("Family").getFirstChildElement("Student"), core.getDistrictInfo());
			System.out.println("\n");
			System.out.println(userInfo.getInfoString());
	
			URL infoURL2 = new URL(icURL + "/campus/prism?&x=portal.PortalClassbook-getClassbookForAllSections&mode=classbook&personID=" + userInfo.personID + "&structureID=" + userInfo.calendars.get(0).schedules.get(0).id + "&calendarID=" + userInfo.calendars.get(0).calendarID);
			System.out.println(icURL + "/campus/prism?&x=portal.PortalClassbook-getClassbookForAllSections&mode=classbook&personID=" + userInfo.personID + "&structureID=" + userInfo.calendars.get(0).schedules.get(0).id + "&calendarID=" + userInfo.calendars.get(0).calendarID);
			Document doc2 = builder.build(new ByteArrayInputStream(core.getContent(infoURL2, false).getBytes()));
			classbookInfo = new ClassbookManager(doc2.getRootElement().getFirstChildElement("SectionClassbooks"));
			System.out.println(classbookInfo.getInfoString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}