package us.plxhack.InfiniteCampus.api.classbook;

import java.util.ArrayList;

import us.plxhack.InfiniteCampus.api.Student;
import nu.xom.Element;
import nu.xom.Elements;

public class PortalClassbook
{
	public String sectionID;
	public ArrayList<Student> students = new ArrayList<Student>();
	public PortalClassbook(Element classbookElement)
	{
		this.sectionID = classbookElement.getAttributeValue("sectionID");
		Elements e = classbookElement.getFirstChildElement("ClassbookDetail").getFirstChildElement("StudentList").getChildElements("Student");

		for(int i = 0; i < e.size(); i++)
        {
            Student s = new Student(e.get(i));

            students.add( s );
        }
	}
	
	public String getInfoString()
	{
		String str = "";
		for(Student s : students)
			for(Classbook c : s.classbooks)
			{
				str += c.getInfoString();
			}
		return str;
	}
}
