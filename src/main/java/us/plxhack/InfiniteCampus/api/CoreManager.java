package us.plxhack.InfiniteCampus.api;

//import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.plxhack.InfiniteCampus.api.district.DistrictInfo;

public class CoreManager
{
	private ObjectMapper mapper = new ObjectMapper();
	private String cookies = "";
	private DistrictInfo distInfo;
    private String districtCode; // This is never actually used!!!

	public CoreManager(String districtCode)
	{
        this.districtCode = districtCode;
        try
        {
            distInfo = mapper.readValue(new URL("https://mobile.infinitecampus.com/mobile/checkDistrict?districtCode=" + districtCode), DistrictInfo.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public DistrictInfo getDistrictInfo()
	{
		return distInfo;
	}
	
	public boolean attemptLogin(String user, String pass, DistrictInfo distInfo)
	{
		try
		{
			URL loginURL = new URL(distInfo.getDistrictBaseURL() + "/verify.jsp?nonBrowser=true&username=" + user + "&password=" + pass + "&appName=" + distInfo.getDistrictAppName());
			String response = getContent(loginURL, true);
			if(response.trim().equalsIgnoreCase("<authentication>success</authentication>"))
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public String getContent(URL url, boolean altercookies)
	{
		String s = "";
		try
		{
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestProperty("Cookie", cookies); //Retain our sessoin
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String input;
			while ((input = br.readLine()) != null)
			{
				s += input + "\n";
			}
			br.close();
			
			StringBuilder sb = new StringBuilder();

			// find the cookies in the response header from the first request
			List<String> cookie = con.getHeaderFields().get("Set-Cookie");
			if (cookie != null) {
			    for (String cooki : cookie) {
			        if (sb.length() > 0) {
			            sb.append("; ");
			        }

			        // only want the first part of the cookie header that has the value
			        String value = cooki.split(";")[0];
			        sb.append(value);
			    }
			}
			if(altercookies)
				cookies = sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}
}
