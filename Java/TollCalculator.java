package Java;

import java.util.*;
import java.util.concurrent.*;

public class TollCalculator {
	

	/**
	 * Calculate the total toll fee for one day
	 *
	 * @param vehicle
	 *            - the vehicle
	 * @param dates
	 *            - date and time of all passes on one day
	 * @return - the total toll fee for that day
	 */
	public int getTollFee(Vehicle vehicle, Date... dates) {
		Date intervalStart = dates[0];
		int totalFee = 0;
		int tmpTripFee = 0;
		for (Date date : dates) {  

			TimeUnit timeUnit = TimeUnit.MINUTES;
			long diffInMillies = date.getTime() - intervalStart.getTime();
			long minutes = timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);

			if (minutes > 60) {
				intervalStart = date;
				diffInMillies = date.getTime() - intervalStart.getTime();
				minutes = timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS); 
				tmpTripFee = 0;
			}
			
			int endFee = getTollFee(date, vehicle);
			int startFee = getTollFee(intervalStart, vehicle);
			
			// System.out.println(totalFee +" entry > "+date+" minutes
			// "+minutes);
			// System.out.println("startFee "+startFee);
			// System.out.println("endFee "+endFee);

			if (minutes <= 60) {
				if (endFee > startFee) {					
					tmpTripFee = endFee;
					totalFee = endFee;
				} else if (startFee == endFee && tmpTripFee != 0) {
					tmpTripFee = endFee;
				} else {
					totalFee += Math.max(startFee, endFee);
					tmpTripFee = endFee;
				}
			} 
		}
		if (totalFee > 60) {
			totalFee = 60;
		}
		return totalFee;
	}

	private boolean isTollFreeVehicle(Vehicle vehicle) {
		if (vehicle == null)
			return false;
		String vehicleType = vehicle.getType();
		return vehicleType.equals(TollFreeVehicles.MOTORBIKE.getType())
				|| vehicleType.equals(TollFreeVehicles.TRACTOR.getType())
				|| vehicleType.equals(TollFreeVehicles.EMERGENCY.getType())
				|| vehicleType.equals(TollFreeVehicles.DIPLOMAT.getType())
				|| vehicleType.equals(TollFreeVehicles.FOREIGN.getType())
				|| vehicleType.equals(TollFreeVehicles.MILITARY.getType());
	}

	public int getTollFee(final Date date, Vehicle vehicle) {
		if (isTollFreeDate(date) || isTollFreeVehicle(vehicle))
			return 0;
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		if (hour == 6 && minute >= 0 && minute <= 29)
			return 8;
		else if (hour == 6 && minute >= 30 && minute <= 59)
			return 13;
		else if (hour == 7 && minute >= 0 && minute <= 59)
			return 18;
		else if (hour == 8 && minute >= 0 && minute <= 29)
			return 13;
		else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59)
			return 8;
		else if (hour == 15 && minute >= 0 && minute <= 29)
			return 13;
		else if (hour == 15 && minute >= 30 || hour == 16 && minute <= 59)
			return 18;
		else if (hour == 17 && minute >= 0 && minute <= 59)
			return 13;
		else if (hour == 18 && minute >= 0 && minute <= 29)
			return 8;
		else if ((hour == 18 && minute >= 30) || (hour <= 23 && minute <= 59))
			return 8;
		else if ((hour >= 00 && hour <= 5) && minute <= 59)
			return 8;
		else
			return 0;
	}

	private Boolean isTollFreeDate(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
			return true;

		if (year == 2022) {
			if (month == Calendar.JANUARY && day == 1 || month == Calendar.MARCH && (day == 28 || day == 29)
					|| month == Calendar.APRIL && (day == 1 || day == 30)
					|| month == Calendar.MAY && (day == 1 || day == 8 || day == 9)
					|| month == Calendar.JUNE && (day == 5 || day == 6 || day == 21) || month == Calendar.JULY
					|| month == Calendar.NOVEMBER && day == 1
					|| month == Calendar.DECEMBER && (day == 24 || day == 25 || day == 26 || day == 31)) {
				return true;
			}
		}
		return false;
	}

	private enum TollFreeVehicles {
		MOTORBIKE("Motorbike"), TRACTOR("Tractor"), EMERGENCY("Emergency"), DIPLOMAT("Diplomat"), FOREIGN(
				"Foreign"), MILITARY("Military");
		private final String type;

		TollFreeVehicles(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
	
	
	public static void main(String[] ar) {
		Vehicle v = new Car();
		TollCalculator tollCalculatorObj = new TollCalculator();
		 
		ArrayList<Date> dateList = tollCalculatorObj.prepareInput(v);
		Date[] dateArray = new Date[dateList.size()];
		
		//get toll fee
		int tollFee = tollCalculatorObj.getTollFee(v, dateList.toArray(dateArray));
		System.out.println("\n");
		System.out.format("Total Toll Fee %d SEK for %s ", tollFee, v.getType());

	}

	private ArrayList<Date> prepareInput(Vehicle v) {
		ArrayList<Date> dateList = new ArrayList<Date>();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 06);
		cal.set(Calendar.MINUTE, 01);
		cal.set(Calendar.SECOND, 01);
		dateList.add(cal.getTime());
		System.out.println(v.getType() + " Entry 1>" + cal.getTime());

		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 7);
		cal.set(Calendar.MINUTE, 25);
		cal.set(Calendar.SECOND, 59);
		dateList.add(cal.getTime());
		System.out.println(v.getType() + " Entry 2>" + cal.getTime());
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 7);
		cal.set(Calendar.MINUTE, 45);
		cal.set(Calendar.SECOND, 50);
		dateList.add(cal.getTime());
		System.out.println(v.getType() + " Entry 3>" + cal.getTime());
		
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 12);
		cal.set(Calendar.SECOND, 59);
		dateList.add(cal.getTime());
		System.out.println(v.getType() + " Entry 4>" + cal.getTime());
		
		
		return dateList;

	}
}
