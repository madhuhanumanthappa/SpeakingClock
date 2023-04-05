/**
 *  @author MADHU H
 *  ClockConverter java class to convert the current time into words.
 */
package com.speakingclock.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClockConverterService {
	private Map<String, String> dozens;

	private Map<String, String> tenToNineteen;

	private Map<String, String> oneToNine;

	private final String MIDDAY = "It's midday";

	private final String MIDNIGHT = "It's midnight";

	private Pattern pattern = Pattern.compile("(([2][0-3]|[0-1])([0-9]|[1-9]):([0-5])([0-9]))");

	public ClockConverterService() {
		try {
			loadDozens();
			loadOneToNine();
			loadTenToNineteen();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public String convertToWords(String time) {

		String[] hoursAndMinutes = timeValidation(time);
		
		if (hoursAndMinutes.length == 4) {
			
			return checkHours(hoursAndMinutes) + " " +
					checkMinutes(hoursAndMinutes);
			
		} else {
			return "Given time is not valid!";
		}

	}

	private void loadDozens() throws IOException {

		dozens = new HashMap<String, String>();
		Properties properties = new Properties();

		properties.load(getClass().getResourceAsStream("dozens.properties"));

		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			dozens.put(key, value);
		}
	}

	private void loadTenToNineteen() throws IOException {

		tenToNineteen = new HashMap<String, String>();
		Properties properties = new Properties();

		properties.load(getClass().getResourceAsStream("ten-to-nineteen.properties"));

		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			tenToNineteen.put(key, value);
		}
	}

	private void loadOneToNine() throws IOException {
		oneToNine = new HashMap<String, String>();
		Properties properties = new Properties();

		properties.load(getClass().getResourceAsStream("one-to-nine.properties"));

		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			oneToNine.put(key, value);
		}
	}

	public String[] timeValidation(String time) {
		Matcher matcher = pattern.matcher(time);
		String[] listMatches = new String[4];
		int i = 0;
		if (matcher.find()) {
			listMatches[i++] = matcher.group();
		}
		
		return listMatches;
	}
	
	private String checkMinutes(String[] hoursAndMinutes) {
		String minutes = hoursAndMinutes[2]+hoursAndMinutes[3];
		
		int intMinutes = Integer.valueOf(minutes).intValue();
		
		if ("00".equals(minutes)) {
			return "";
		} else if (intMinutes > 9 && intMinutes < 20) {
			return tenToNineteen.get(minutes);
		} else {
			if (hoursAndMinutes[3] == "0") {
				return dozens.get(minutes);
			} else {
				return dozens.get(hoursAndMinutes[2]) + 
						" " + tenToNineteen.get(hoursAndMinutes[3]);
			}
		}
	}

	private String checkHours(String[] hoursAndMinutes) {
		String hours = hoursAndMinutes[0]+hoursAndMinutes[1];
		
		if ("00".equals(hours)) {
			return MIDNIGHT;
		} else if ("12".equals(hours)) {
			return MIDDAY;
		} else if ("0".equals(hoursAndMinutes[0])) {
			return oneToNine.get(hoursAndMinutes[1]);
		} else {
			return tenToNineteen.get(hours);
		}
	}
}
