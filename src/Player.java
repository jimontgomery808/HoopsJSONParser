
public class Player
{
	private String firstName;
	private String lastName;
	private String number;
	private String position;
	private String heightFeet;
	private String heightInches;
	private String weight;
	private String id;
	private String team;
	private Boolean isAllStar;
	
	public Player(String firstName, String lastName, String number, String position, String heightFeet,
			String heightInches, String weight, String id, String team, Boolean isAllStar)
	{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.number = number;
		this.position = position;
		this.heightFeet = heightFeet;
		this.heightInches = heightInches;
		this.weight = weight;
		this.id = id;
		this.team = team;
		this.isAllStar = isAllStar;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition(String position)
	{
		this.position = position;
	}

	public String getHeightFeet()
	{
		return heightFeet;
	}

	public void setHeightFeet(String heightFeet)
	{
		this.heightFeet = heightFeet;
	}

	public String getHeightInches()
	{
		return heightInches;
	}

	public void setHeightInches(String heightInches)
	{
		this.heightInches = heightInches;
	}

	public String getWeight()
	{
		return weight;
	}

	public void setWeight(String weight)
	{
		this.weight = weight;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTeam()
	{
		return team;
	}

	public void setTeam(String team)
	{
		this.team = team;
	}

	public Boolean isAllStar()
	{
		return isAllStar;
	}

	public void setIsAllStar(Boolean isAllStar)
	{
		this.isAllStar = isAllStar;
	}
	
	
}
