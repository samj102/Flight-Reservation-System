

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!


public class FlightReservationSystem
{
	public static void main(String[] args)
	{
		try 
		{
			FlightManager manager = new FlightManager();


			ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations


			Scanner scanner = new Scanner(System.in);
			System.out.print(">");
			
				while (scanner.hasNextLine())
				{
					try {
					String inputLine = scanner.nextLine();
					if (inputLine == null || inputLine.equals("")) 
					{
						System.out.print("\n>");
						continue;
					}

					Scanner commandLine = new Scanner(inputLine);
					String action = commandLine.next();

					if (action == null || action.equals("")) 
					{
						System.out.print("\n>");
						continue;
					}

					else if (action.equals("Q") || action.equals("QUIT"))
						return;

					else if (action.equalsIgnoreCase("LIST"))
					{
						manager.printAllFlights(); 
					}
					else if (action.equalsIgnoreCase("RES"))
					{
						String flightNum = null;
						String passengerName = "";
						String passport = "";
						String seat = "";

						if (commandLine.hasNext())
						{
							flightNum = commandLine.next();
						}
						if (commandLine.hasNext())
						{
							passengerName = commandLine.next();
						}
						if (commandLine.hasNext())
						{
							passport = commandLine.next();
						}
						if (commandLine.hasNext())
						{
							seat = commandLine.next();
							Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seat);
							if (res != null)
							{
								myReservations.add(res);
								res.print();
							}
						}
					}
					else if (action.equalsIgnoreCase("CANCEL"))
					{
						String flightNum = null;
						String passengerName = "";
						String passport = "";

						if (commandLine.hasNext())
						{
							flightNum = commandLine.next();
						}
						if (commandLine.hasNext())
						{
							passengerName = commandLine.next();
						}
						if (commandLine.hasNext())
						{
							passport = commandLine.next();

							int index = myReservations.indexOf(new Reservation(flightNum, passengerName, passport));
							if (index >= 0)
							{
								manager.cancelReservation(myReservations.get(index));
								myReservations.remove(index);
							}
							else
								System.out.println("Reservation on Flight " + flightNum + " Not Found");
						}
					}
					else if (action.equalsIgnoreCase("SEATS"))
					{
						String flightNum = "";

						if (commandLine.hasNext())
						{
							flightNum = commandLine.next();
						}
							manager.printseats(flightNum);
					}
					else if (action.equalsIgnoreCase("MYRES"))
					{
						for (Reservation myres : myReservations)
							myres.print();
					}
					else if(action.equalsIgnoreCase("PASMAN")) 
					{

						String flightNum = "";

						if (commandLine.hasNext())
						{
							flightNum = commandLine.next();
						}
						manager.printManifest(flightNum);
					}
					System.out.print("\n>");
				}
					catch (FlightNotFoundException e)
					{
						System.out.println(e.getMessage());
						System.out.print("\n>");
					}
					catch (DuplicatePassengerException e) 
					{
						System.out.println(e.getMessage());
						System.out.print("\n>");
					}
					catch(PassengerNotFoundException e) 
					{
						System.out.println(e.getMessage());
						System.out.print("\n>");
					}
					catch(SeatExistenceException e) 
					{
						System.out.println(e.getMessage());
						System.out.print("\n>");
					}
					catch(SeatOccupiedException e) 
					{
						System.out.println(e.getMessage());
						System.out.print("\n>");
					}
					catch(FlightFullException e) 
					{
						System.out.println(e.getMessage());
						System.out.print("\n>");
					}
					catch(Exception e) 
					{
						System.out.println(e.getMessage());
						System.out.print("\n>");
					}
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.print("File not found");
		}
	}
}

