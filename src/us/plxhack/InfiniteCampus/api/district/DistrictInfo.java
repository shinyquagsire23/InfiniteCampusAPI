package us.plxhack.InfiniteCampus.api.district;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistrictInfo
{
	private String districtAppName;
	private String districtBaseURL;
	private String districtName;
	private int districtNumber;
	private String stateCode;
	
	public String getDistrictAppName()
	{
		return districtAppName;
	}
	
	public String getDistrictBaseURL()
	{
		return districtBaseURL;
	}
	
	public String getDistrictName()
	{
		return districtName;
	}
	
	public int getDistrictNumber()
	{
		return districtNumber;
	}
	
	public String getDistrictStateCode()
	{
		return stateCode;
	}
}
