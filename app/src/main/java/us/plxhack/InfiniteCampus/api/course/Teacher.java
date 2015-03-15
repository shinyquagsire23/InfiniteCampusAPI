package us.plxhack.InfiniteCampus.api.course;

public class Teacher
{
    private String firstName, lastName;

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public Teacher( String commaSeparated )
    {
        int commaPos = commaSeparated.indexOf(',');
        lastName = commaSeparated.substring(0,commaPos);
        firstName = commaSeparated.substring(commaPos+2);
    }
}
