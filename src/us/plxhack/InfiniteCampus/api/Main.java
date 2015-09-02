package us.plxhack.InfiniteCampus.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import us.plxhack.InfiniteCampus.api.classbook.ClassbookManager;
import us.plxhack.InfiniteCampus.api.district.DistrictInfo;
import nu.xom.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main
{
	static PrintWriter out;

	public static void main(String[] args) throws Exception
	{
		Main main = new Main();
		File f = new File("grades.txt");
		if (f.exists())
			f.delete();
		try
		{
			out = new PrintWriter(new BufferedWriter(new FileWriter("grades.txt")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		
		System.out.println("Please enter your district code:");
		String districtCode = main.getInput();

		CoreManager core = new CoreManager(districtCode);
		print("Found District Information:");
		print("District: " + core.getDistrictInfo().getDistrictName());
		print("State: " + core.getDistrictInfo().getDistrictStateCode());
		print("Base URL: " + core.getDistrictInfo().getDistrictBaseURL());
		print("District App Name: " + core.getDistrictInfo().getDistrictAppName());

		print("Attempting login...");
		System.out.println("Username: ");
		String username = main.getInput();

		System.out.println("Password: ");
		String passwordString = main.getInput();
		System.out.println(passwordString);

		print("Logging into user " + username + "...");
		boolean successfulLogin = core.attemptLogin(username, passwordString, core.getDistrictInfo());
		print(successfulLogin ? "Login success!" : "Login failed!");
		if (!successfulLogin)
		{
			print("\nPress any key to exit...");
			System.in.read();
			return;
		}

		URL infoURL = new URL(core.getDistrictInfo().getDistrictBaseURL() + "/prism?x=portal.PortalOutline&appName=" + core.getDistrictInfo().getDistrictAppName());
		Builder builder = new Builder();
		Document doc = builder.build(new ByteArrayInputStream(core.getContent(infoURL, false).getBytes()));
		Element root = doc.getRootElement();
		Student user = new Student(root.getFirstChildElement("PortalOutline").getFirstChildElement("Family").getFirstChildElement("Student"), core.getDistrictInfo());
		print("\n");
		print(user.getInfoString());

		URL infoURL2 = new URL(core.getDistrictInfo().getDistrictBaseURL() + "/prism?&x=portal.PortalClassbook-getClassbookForAllSections&mode=classbook&personID=" + user.personID + "&structureID=" + user.calendars.get(0).schedules.get(0).id + "&calendarID=" + user.calendars.get(0).calendarID);
		print(core.getDistrictInfo().getDistrictBaseURL() + "/prism?&x=portal.PortalClassbook-getClassbookForAllSections&mode=classbook&personID=" + user.personID + "&structureID=" + user.calendars.get(0).schedules.get(0).id + "&calendarID=" + user.calendars.get(0).calendarID);
		Document doc2 = builder.build(new ByteArrayInputStream(core.getContent(infoURL2, false).getBytes()));
		ClassbookManager manager = new ClassbookManager(doc2.getRootElement().getFirstChildElement("SectionClassbooks"));
		print(manager.getInfoString());
		out.close();

		print("\nUser info dump successful!\nPress any key to exit...");
		System.in.read();
	}

	public static void print(String s)
	{
		System.out.println(s);
		out.println(s);
	}
	
	private String getInput() {
	     String inputString = "";
		try{
		     BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		     inputString = bufferRead.readLine();
		 }
		 catch(IOException ex)
		 {
		    ex.printStackTrace();
		 }
		
		return inputString;
	}
}
