package com.speakingclock.service;

/**
 * @author MADHU H
 * SpeakingClockService interface with the contract methods that 
 * needs to be implemented in the SpeakingClockServiceImpl class.
 */
public interface SpeakingClockService {

	public abstract String convertToWords(String time);
	public abstract boolean timeValidation(String time);
	public abstract String[] timeGrouping(String time);
	
}
