import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;  
  
public class TestTollCalculator {  
	
	@BeforeClass  
    public static void setUpBeforeClass() throws Exception {  
        System.out.println("before class");  
    }   
    
	/**
	 * Only one entry during normal traffic hours.
	 * 
	 */
	
	@Test  
    public void testOnlyOneEntryInNoramTraffic(){
    	Vehicle vehicle = new Car();
    	ArrayList<Date> dateList = new ArrayList<Date>();
		
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 06);
		cal.set(Calendar.MINUTE, 01);
		cal.set(Calendar.SECOND, 01);
		dateList.add(cal.getTime());
		System.out.println(vehicle.getType() + " Entry 1 at " + cal.getTime());
		
		TollCalculator tollCalculatorObj = new TollCalculator(); 
		Date[] dateArray = new Date[dateList.size()]; 
		
		 
		int tollFee = tollCalculatorObj.getTollFee(vehicle, dateList.toArray(dateArray));
        
		System.out.println(tollFee);
    }
	
	/**
	 * One Entry during rush hour
	 */
	@Test  
    public void testOnlyOneEntryDuringRushHour(){
		
		/* Rush Hour Timings are
		 * 
		 *  7 to 7.59 
		 *  
		 *  15.30 to 16.59
		 *   
		 */
		int expectedTollFeeDuringRushHour = 18;
		
		Vehicle vehicle = new Car();
		
    	ArrayList<Date> dateList = new ArrayList<Date>();
		
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 7);
		cal.set(Calendar.MINUTE, 35);
		cal.set(Calendar.SECOND, 10);
		dateList.add(cal.getTime());
		System.out.println(vehicle.getType() + " Entry 1 During Rush Hour at [ " + cal.getTime() +" ]");
		
		TollCalculator tollCalculatorObj = new TollCalculator(); 
		Date[] dateArray = new Date[dateList.size()]; 
		
		 
		int tollFee = tollCalculatorObj.getTollFee(vehicle, dateList.toArray(dateArray));
        
		Assert.assertEquals(expectedTollFeeDuringRushHour,tollFee);  
		
		System.out.println(tollFee);
    }
	
	/**
	 * This test case will test for same vehicle,
	 * which is passed multiple times during rush hour and within 1hr.
	 * 
	 */
	@Test  
    public void testMultipleEntriesDuringRushHour(){
		
		/* Rush Hour Timings are
		 * 
		 *  7 to 7.59 
		 *  
		 *  15.30 to 16.59
		 *   
		 */
		int expectedTollFeeDuringRushHour = 18;
		
		Vehicle vehicle = new Car();
		
    	ArrayList<Date> dateList = new ArrayList<Date>();
		
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 7);
		cal.set(Calendar.MINUTE, 5);
		cal.set(Calendar.SECOND, 10);
		dateList.add(cal.getTime());
		System.out.println(vehicle.getType() + " Entry 1 During Rush Hour at [ " + cal.getTime() +" ]");
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 7);
		cal.set(Calendar.MINUTE, 25);
		cal.set(Calendar.SECOND, 12);
		dateList.add(cal.getTime());
		System.out.println(vehicle.getType() + " Entry 2 During Rush Hour at [ " + cal.getTime() +" ]");
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 7);
		cal.set(Calendar.MINUTE, 55);
		cal.set(Calendar.SECOND, 12);
		dateList.add(cal.getTime());
		System.out.println(vehicle.getType() + " Entry 3 During Rush Hour at [ " + cal.getTime() +" ]");
		
		
		TollCalculator tollCalculatorObj = new TollCalculator(); 
		Date[] dateArray = new Date[dateList.size()]; 
		
		 
		int tollFee = tollCalculatorObj.getTollFee(vehicle, dateList.toArray(dateArray));
        
		Assert.assertEquals(expectedTollFeeDuringRushHour,tollFee);  
		
		System.out.println(tollFee);
    }
    
    /**
	 * This test case will test for same vehicle,
	 * which is passed two times during two different rush hours.
	 * 
	 */
	@Test  
    public void testMultipleEntriesDuringDifferentRushHours(){
		
		/* Rush Hour Timings are
		 * 
		 *  7 to 7.59 
		 *  
		 *  15.30 to 16.59
		 *   
		 */
		int expectedTollFeeDuringRushHour = 36;
		
		Vehicle vehicle = new Car();
		
    	ArrayList<Date> dateList = new ArrayList<Date>();
		
    	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 7);
		cal.set(Calendar.MINUTE, 5);
		cal.set(Calendar.SECOND, 10);
		dateList.add(cal.getTime());
		System.out.println(vehicle.getType() + " Entry 1 During Rush Hour at [ " + cal.getTime() +" ]");
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 15);
		cal.set(Calendar.MINUTE, 45);
		cal.set(Calendar.SECOND, 12);
		dateList.add(cal.getTime());
		System.out.println(vehicle.getType() + " Entry 2 During Rush Hour at [ " + cal.getTime() +" ]");
		 
		
		TollCalculator tollCalculatorObj = new TollCalculator(); 
		Date[] dateArray = new Date[dateList.size()]; 
		
		 
		int tollFee = tollCalculatorObj.getTollFee(vehicle, dateList.toArray(dateArray));
        
		Assert.assertEquals(expectedTollFeeDuringRushHour,tollFee);  
		
		System.out.println(tollFee);
    }
    
    /**
	 * This test case will test for same vehicle,
	 * 
	 * which is passed multiple times within same day.
	 * 
	 */
	@Test  
    public void testMaximumDayTollFee(){
		
		/* Rush Hour Timings are
		 * 
		 *  7 to 7.59 
		 *  
		 *  15.30 to 16.59
		 *   
		 */
		int maxDayTollFee = 60;
		
		Vehicle vehicle = new Car();
		TollCalculator tollCalculatorObj = new TollCalculator();
		 
		ArrayList<Date> dateList = prepareInput(vehicle);
		Date[] dateArray = new Date[dateList.size()];
		
		 
		int tollFee = tollCalculatorObj.getTollFee(vehicle, dateList.toArray(dateArray));
		
		System.out.println(tollFee);	
		
		Assert.assertEquals(maxDayTollFee,tollFee);
    } 
    
    /**
     * This method will prepare the test data
     * @param v
     * @return
     */
    private ArrayList<Date> prepareInput(Vehicle v) {
		ArrayList<Date> dateList = new ArrayList<Date>();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 6);
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
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 12);
		cal.set(Calendar.SECOND, 59);
		dateList.add(cal.getTime());
		System.out.println(v.getType() + " Entry 5>" + cal.getTime());
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 15);
		cal.set(Calendar.MINUTE, 12);
		cal.set(Calendar.SECOND, 59);
		dateList.add(cal.getTime());
		System.out.println(v.getType() + " Entry 6>" + cal.getTime());
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 20);
		cal.set(Calendar.MINUTE, 12);
		cal.set(Calendar.SECOND, 59);
		dateList.add(cal.getTime());
		System.out.println(v.getType() + " Entry 7>" + cal.getTime());
		
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 22);
		cal.set(Calendar.MINUTE, 12);
		cal.set(Calendar.SECOND, 59);
		dateList.add(cal.getTime());
		System.out.println(v.getType() + " Entry 8>" + cal.getTime());	
		
		
		return dateList;

	}
}  
