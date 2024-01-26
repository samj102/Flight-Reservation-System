public class Passenger
{
	private String name;
	private String passport;
	private String seat;
	private String seatType;
	
	public Passenger(String name, String passport, String seat)//, String seatType)
	{
		this.name = name;
		this.passport = passport;
		this.seat = seat;
		if(seat.contains("+")) 
		{
			this.seatType = "First Class" ;
		}
		else 
		{
			this.seatType = "Economy Class";
		}
	}
	
	public Passenger(String name, String passport)
	{
		this.name = name;
		this.passport = passport;
	}
	
	public boolean equals(Object other)
	{
		if(other!=null) 
		{
			Passenger otherP = (Passenger) other;
			return name.equals(otherP.name) && passport.equals(otherP.passport);
		}
		else 
		{
			return false;
		}
	}

	public String getSeatType()
	{
		return seatType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getSeat()
	{
		return seat;
	}

	public void setSeat(String seat)
	{
		this.seat = seat;
	}
	
	public String toString()
	{
		return "Name: " +name + " Passport Number: " + passport + " Seat Number: " + seat;
	}
}
