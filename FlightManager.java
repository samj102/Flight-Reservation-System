import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;



public class FlightManager
{
	Map<String , Flight> flights = new TreeMap<String, Flight>();
	Map<String, Integer> flightTimes = Map.of("Dallas", 3, "New York", 1, "London",  7, "Paris", 8, "Tokyo", 16);

	ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  
	ArrayList<String> flightNumbers = new ArrayList<String>();

	String errMsg = null;
	Random random = new Random();


	public FlightManager() throws FileNotFoundException
	{
		// Create some aircraft types with max seat capacities
		airplanes.add(new Aircraft(85, "Boeing 737"));
		airplanes.add(new Aircraft(180,"Airbus 320"));
		airplanes.add(new Aircraft(37, "Dash-8 100"));
		airplanes.add(new Aircraft(4, "Bombardier 5000"));
		airplanes.add(new Aircraft(592, 14, "Boeing 747"));

		File flightsdata = new File("flights.txt");
		Scanner input = new Scanner(flightsdata);
		while(input.hasNextLine()) 
		{
			String line = input.nextLine();
			String [] flightinfo = line.split(" ");
			String airline = flightinfo[0];
			airline = airline.replaceAll("_", " ");
			String flightNum = generateFlightNumber(airline);
			String dest = flightinfo[1];
			dest = dest.replaceAll("_", " ");
			String dept = flightinfo[2];
			int cap = Integer.parseInt(flightinfo[3]);
			Aircraft craft = new Aircraft();
			for(int i = 0;i<airplanes.size();i++) 
			{
				if(airplanes.get(i).getNumSeats()==cap) 
				{
					craft = airplanes.get(i);
					break;
				}
				else if (airplanes.get(i).getNumSeats()>cap && airplanes.get(i).getNumSeats()<(cap*3))// this condition is to prevent a aircraft too many extra seats for this flight to be chosen
				{
					craft = airplanes.get(i);
					break;
				}
			}
			int dur = flightTimes.get(dest);
			if(dur>8) // if the flight Duration is longer than 8 hours it is considered longhual
			{
				craft = airplanes.get(4);// since longhual flights have first class seats they use the only craft with first class seats
				LongHaulFlight flight = new LongHaulFlight(flightNum, airline, dest, dept, dur, craft);
				flights.put(flightNum,flight);
			}
			else
			{
				Flight flight = new Flight(flightNum, airline, dest, dept, dur, craft);	
				flights.put(flightNum,flight);
			}
		}
	}

	private String generateFlightNumber(String airline)
	{
		int flight = random.nextInt(200) + 101;
		String [] words = airline.split(" ");

		String flightNum = words[0].charAt(0)+""+words[1].charAt(0)+""+flight;
		return flightNum;
	}

	public String getErrorMessage()
	{
		return errMsg;
	}

	public void printAllFlights()
	{
		for (String key : flights.keySet()) 
		{
			System.out.println(flights.get(key));

		}
	}

	public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seat) throws Exception
	{
		Flight current =  flights.get(flightNum);
		if(current==null)
		{
			throw new FlightNotFoundException( "Flight " + flightNum + " Not Found");
		}
		Passenger p = new Passenger(name, passport, seat);
		current.reserveSeat(p);
		return new Reservation(flightNum, current.toString(), name, passport, seat);
	}

	public void cancelReservation(Reservation res) throws Exception
	{
		Flight current =  flights.get(res.getFlightNum());
		if(current==null)
		{
			throw new FlightNotFoundException( "Flight " + res.getFlightNum() + " Not Found");

		}
		Passenger p = new Passenger(res.name, res.passport, res.seat);
		current.cancelSeat(p);
	}
	public void printseats(String flightNum) throws FlightNotFoundException
	{
		Flight current =  flights.get(flightNum); 
		if(current==null)
		{
			throw new FlightNotFoundException( "Flight " + flightNum + " Not Found");
		}
		current.printAllSeats();
	}
	public void printManifest(String flightNum) throws FlightNotFoundException
	{
		Flight current =  flights.get(flightNum);
		if(current==null)
		{
			throw new FlightNotFoundException( "Flight " + flightNum + " Not Found");
		}
		else 
		{
			current.printPassengerManifest();
		}
	}
}
class FlightNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	public FlightNotFoundException() {}
	public FlightNotFoundException(String message) 
	{
		super(message);
	}
}
