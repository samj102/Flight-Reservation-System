import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeMap;

public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum Type {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};

	private String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int numPassengers;
	protected Type type;
	protected ArrayList<Passenger> manifest;
	protected TreeMap<String, Passenger> seatMap; //A map for storing seat numbers and their passenger.
	protected ArrayList<String> seats;// an arraylist used to help fill the seatMap
	protected ArrayList<String> FCLseatsList;
	
	protected Random random = new Random();
	
	private String errorMessage = "";
	  
	public String getErrorMessage()
	{
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public Flight()
	{
		this.flightNum = "";
		this.airline = "";
		this.dest = "";
		this.origin = "Toronto";
		this.departureTime = "";
		this.flightDuration = 0;
		this.aircraft = null;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>(new Stringcomparator());
		seats = new ArrayList<String>();
		FCLseatsList = new ArrayList<String>();
		generateSeatNumbers();//intializing and filling the map and arraylist
	}
	
	public Flight(String flightNum)
	{
		this.flightNum = flightNum;
	}
	
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		numPassengers = 0;
		status = Status.ONTIME;
		type = Type.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>(new Stringcomparator());
		seats = new ArrayList<String>();
		FCLseatsList = new ArrayList<String>();//list to store first class seats
		generateSeatNumbers();//intializing and filling the map and arraylist
	}
	
	public Type getFlightType()
	{
		return type;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	
	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	
	public int getNumPassengers()
	{
		return numPassengers;
	}
	public void setNumPassengers(int numPassengers)
	{
		this.numPassengers = numPassengers;
	}
	
	public void generateSeatNumbers() // a method used to fill the arraylist seats 	and the seatMap with seats
	{
		String[] rowletter = {"A","B","C","D"};
		int numrow = aircraft.getTotalSeats()/4;
		String seatnumber ="";
		boolean firstclass = false;
		int FCLseats = aircraft.getNumFirstClassSeats();
		if(FCLseats!=0)
		{
			firstclass = true;
		}
		for(String s : rowletter) // a loop to determine which row is currently being filled		
		{ 
			if(firstclass == true) //if plane has first class seats
			{
				for(int l = 0;l<(FCLseats/4);l++) //filling in first class seats
				{
					seatnumber = (l+1)+s+"+";
					seats.add(seatnumber);
					FCLseatsList.add((l+1)+s);//adding seats without "+" to prevent duplicate seats
				}
			}
			for(int k = 0;k<numrow;k++)// using this loop to fill each individual row 
			{
				seatnumber = (k+1)+s;
				seats.add(seatnumber);
				
			}
		}
		for (int i = 0 ;i<seats.size();i++) // loop to fill the seat map
		{
			if(!FCLseatsList.contains(seats.get(i))) //this condition prevents duplicate seats(e.g. 1A and 1A+) from being added to seat map
			{
				seatMap.put(seats.get(i), null);
			}
		}
		
	}
	
	public void printAllSeats() 
	{
		String[] rowletter = {"A","B","C","D"};//an array to help print the seats in order
		for(String s : rowletter)
		{
			for(String key : seatMap.keySet()) 
			{
				if(key.contains(s) && seatMap.get(key)==null)//checking if a seat is empty and which row it is in
				{
					System.out.print(key+"  ");
				}
				else if((key.contains(s) && seatMap.get(key)!=null)) 
				{
					System.out.print("XX  ");// if a seat is occupied it prints this instead
				}
			}
			System.out.println();
		}
	}	
	
	public void assignSeat(Passenger p, String seat) throws Exception
	{ 
		boolean assigned = false;
		for(String key:seatMap.keySet()) 
		{
			if(key.equals(seat))
			{
				if( seatMap.get(key)==null) //checking if seat is vacant
				{
					seatMap.put(key, p);
					assigned = true;
				}
				else
				{
					throw new SeatOccupiedException("Seat " + seat + " is reserved by someone else" );
				}
			}
		}
		if (assigned == false)// if a seat wasn't assigned and is't a seat
		{
			throw new SeatExistenceException("Seat " + seat + " doesn't exist");
		}
		
	}
	
	public void cancelSeat(Passenger p) throws Exception
	{
		
		if (manifest.indexOf(p) == -1) 
		{
			throw new PassengerNotFoundException( "Passenger " + p.getName() + " " + p.getPassport() + " Not Found");													
		}
		for(String key : seatMap.keySet()) 
		{
			if(p.equals(seatMap.get(key))) 
			{
				seatMap.put(key, null);
				manifest.remove(p);
			}
		}
		
		if (numPassengers > 0) numPassengers--;
	}
	
	public void reserveSeat(Passenger p)throws Exception
	{
		if (numPassengers >= aircraft.getNumSeats())
		{
			throw new FlightFullException("Flight " + flightNum + " Full");
		}
		
	
		if (manifest.indexOf(p) >=  0)
		{
			throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport());
		}
		assignSeat(p, p.getSeat()); 
		manifest.add(p);
		numPassengers++;	
	}
	

	public void printPassengerManifest() 
	{
		for(Passenger p : manifest) 
		{
			System.out.println(p.toString());
		}
	}
	public boolean equals(Object other)
	{
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}
	
	public String toString()
	{
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
	}
	
	
	public class Stringcomparator implements Comparator<String> // comparator class to sort the tree map according to the numbers and letters in the keys
	{
		public int compare(String s1, String s2)
		{
			if(s1.contains("+")) 
			{
				if(s2.contains("+")) 
				{
					char let1 = s1.charAt(s1.length()-2);//extracting the letter from seatnumber
					char let2 = s2.charAt(s2.length()-2);
					String num1 = s1.substring(0, s1.length()-2);//extracting the number from seatnumber
					String num2 = s2.substring(0, s2.length()-2);
					if(let1==let2) 
					{
						return Integer.parseInt(num1)-Integer.parseInt(num2);
					}
					else if(let1<let2) 
					{
						return 1;
					}
					else 
					{
						return -1;
					}
					
				}
				else 
				{
					return -1;
				}
			}
			else if(s2.contains("+"))
			{
				return 1;
			}
			else 
			{
				char let1 = s1.charAt(s1.length()-1);//extracting the letter from seatnumber
				char let2 = s2.charAt(s2.length()-1);
				String num1 = s1.substring(0, s1.length()-1);//extracting the number from seatnumber
				String num2 = s2.substring(0, s2.length()-1);
				if(let1==let2) 
				{
					return Integer.parseInt(num1)-Integer.parseInt(num2);
				}
				else if(let1<let2) 
				{
					return -1;
				}
				else 
				{
					return 1;
				}
				
			}
		}
	}	
}


class DuplicatePassengerException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public DuplicatePassengerException() {};
	public DuplicatePassengerException(String message) 
	{
		super(message);
	}
}
class SeatExistenceException extends RuntimeException
{

	private static final long serialVersionUID = 1L;
	public SeatExistenceException() {}
	public SeatExistenceException(String message) 
	{
		super(message);
	}
}
class SeatOccupiedException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public SeatOccupiedException() {}
	public SeatOccupiedException(String message) 
	{
		super(message);
	}
}
class FlightFullException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public FlightFullException() {}
	public FlightFullException(String message) 
	{
		super(message);
	}
}
class PassengerNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public PassengerNotFoundException() {}
	public PassengerNotFoundException(String message) 
	{
		super(message);
	}
}
