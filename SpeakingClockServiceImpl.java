package com.speakingclock.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

/**
 *  @author MADHU H
 *  SpeakingClockServiceImpl class to provide implementation for SpeakingClockService interface methods.
 */

@Service
public class SpeakingClockServiceImpl implements SpeakingClockService {

	private Map<String, String> dozens;

	private Map<String, String> tenToNineteen;

	private Map<String, String> oneToNine;

	private final String MIDDAY = "midday";

	private final String MIDNIGHT = "midnight";

	private String expValidation = "(2[0-3]|[01]?[0-9]):([0-5]?[0-9])";
	private String expGroup = "([0-2])([0-9]):([0-5])([0-9])";

	public SpeakingClockServiceImpl() {
		try {
			loadDozens();
			loadOneToNine();
			loadTenToNineteen();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public String convertToWords(String time) {
		String resultString = "";

		if (time != null && timeValidation(time)) {
			String[] hoursAndMinutes = timeGrouping(time);

			if (hoursAndMinutes.length == 4) {

				resultString = "It's " + checkHours(hoursAndMinutes[0], hoursAndMinutes[1]) + " "
						+ checkOther(hoursAndMinutes[2], hoursAndMinutes[3]);

			}
		} else {
			System.out.println(time + " is not a valid time!");
			resultString = "Given time is not valid!";
		}

		return resultString.trim();
	}

	private void loadDozens() throws IOException {

		dozens = new HashMap<String, String>();
		Properties properties = getPropertiesFor("dozens.properties");

		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			dozens.put(key, value);
		}
	}

	private void loadTenToNineteen() throws IOException {

		tenToNineteen = new HashMap<String, String>();
		Properties properties = getPropertiesFor("ten_to_nineteen.properties");

		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			tenToNineteen.put(key, value);
		}
	}

	private void loadOneToNine() throws IOException {
		oneToNine = new HashMap<String, String>();
		Properties properties = getPropertiesFor("one_to_nine.properties");

		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			oneToNine.put(key, value);
		}
	}
	
	@Override
	public boolean timeValidation(String time) {
		Pattern pattern = Pattern.compile(expValidation);
		Matcher matcher = pattern.matcher(time);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String[] timeGrouping(String time) {
		Pattern pattern = Pattern.compile(expGroup);
		Matcher matcher = pattern.matcher(time);
		List<String> tokens = new LinkedList<String>();
		if (matcher.matches() && matcher.groupCount() == 4) {
			for (int i = 1; i < 5; i++) {
				tokens.add(matcher.group(i));
			}
		}

		return tokens.toArray(new String[tokens.size()]);
	}

	private String checkHours(String timeLeft, String timeRight) {
		String sTime = timeLeft + timeRight;

		if ("00".equals(sTime)) {
			return MIDNIGHT;
		} else if ("12".equals(sTime)) {
			return MIDDAY;
		} else {
			return checkOther(timeLeft, timeRight);
		}
	}

	private String checkOther(String timeLeft, String timeRight) {
		String sTime = timeLeft + timeRight;
		int intTime = Integer.valueOf(sTime).intValue();

		if (intTime < 10) {
			return oneToNine.get(sTime);
		} else if (intTime > 9 && intTime < 20) {
			return tenToNineteen.get(sTime);
		} else {
			return dozens.get(timeLeft + "0") + " " + oneToNine.get(timeRight);
		}
	}

	private Properties getPropertiesFor(String fileName) throws IOException {
		Properties properties = new Properties();
		properties.load(getClass().getClassLoader().getResourceAsStream(fileName));

		return properties;
	}
}
