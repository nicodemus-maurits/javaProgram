import java.util.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;



public class Measurement {
	ArrayList<Long> rcvList = new ArrayList<Long>();
	ArrayList<Long> localList = new ArrayList<Long>();
	long rcv, local;
	Path file;
	long currentFileCreationTime;
	final long dayInMilliseconds =  86400000;
	
	//Constructor
	Measurement()
	{
		newFile();
	}
	
	private void newFile()
	{
		currentFileCreationTime = System.currentTimeMillis();
		Date localTime = new Date(currentFileCreationTime);
		System.out.println(localTime);
		List<String> lines = Arrays.asList("Received Time; Local Time;");
		file = Paths.get("../measurements/Meas_" + currentFileCreationTime + ".csv");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Add Measurements to Lists
	public void addMeasurement(String msg) {
		local = System.currentTimeMillis();
		String subs;
		int i = 0;
		Long upper = 1500000000000L;
		Long lower = 1400000000000L;
		
		do
		{			
			try{
				subs= msg.substring(0+i,13+i);
				rcv = Long.parseLong(subs, 10);
			}
			catch(Exception e)
			{
				rcv = 0;
				break;
			}
			i++;
		} while ( (rcv < lower) || (rcv > upper) );

		/*
		rcvList.add(rcv);
		localList.add(local);
		saveToCsv();
		System.out.println(msg);
		*/
		
		saveToCsv(rcv, local);
	}
	
	
	//Save Last list entry into csv
	private void saveToCsv()
	{
		if (System.currentTimeMillis() - currentFileCreationTime > dayInMilliseconds)
		{
			newFile();
		}
		String all = rcvList.get(rcvList.size()-1).toString() + ";" + localList.get(localList.size()-1).toString() + ";";
		List<String> lines = Arrays.asList(all);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveToCsv(long rcv, long local)
	{
		if (System.currentTimeMillis() - currentFileCreationTime > dayInMilliseconds)
		{
			newFile();
		}
		String all = Long.toString(rcv) + ";" + Long.toString(local) + ";";
		
		List<String> lines = Arrays.asList(all);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String createMeasurement() {
		return String.valueOf(System.currentTimeMillis());
	}

}
