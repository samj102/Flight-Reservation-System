public class Reservation
{
	String flightNum;
	String flightInfo;
	String name;
	String passport;
	String seat;
	
	public Reservation(String flightNum, String info)
	{
		this.flightNum = flightNum;
		this.flightInfo = info;
	}
	
	public Reservation(String flightNum, String name, String passport)
	{
		this.flightNum = flightNum;
		this.name = name;
		this.passport = passport;
	}
	
	public Reservation(String flightNum, String info, String name, String passport, String seat)
	{
		this.flightNum = flightNum;
		this.flightInfo = info;
		this.name = name;
		this.passport = passport;
		this.seat = seat;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	
	public String getFlightInfo()
	{
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo)
	{
		this.flightInfo = flightInfo;
	}
	
	public boolean equals(Object other)
	{
		Reservation otherRes = (Reservation) other;
		return flightNum.equals(otherRes.flightNum)&&  name.equals(otherRes.name) && passport.equals(otherRes.passport); 
	}

	public void print()
	{
		if(seat.contains("+"))// if seat is first class it would print FCL at the end of it
		{
			System.out.println(flightInfo + " " + "Name: "+ name + " " + "Seat: " + seat+ " FCL ");
		}
		else 
		{
			System.out.println(flightInfo + " Name: "+ name + " Seat: " + seat);
		}
	}
}
