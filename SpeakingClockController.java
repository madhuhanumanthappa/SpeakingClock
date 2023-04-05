package com.speakingclock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.speakingclock.exceptions.InputTimeNotfoundException;
import com.speakingclock.service.SpeakingClockServiceImpl;

/**
 * @author MADHU H
 * SpeakingClockController is a RestController class to call the SpeakingClockServiceImpl class
 * and its implemented methods.
 */
@RestController
public class SpeakingClockController {
	
	  @Autowired
	  private SpeakingClockServiceImpl speakingClockService;
	  
	  @GetMapping("/speakingclock/{time}")
	  public String convertToWords(
	    @PathVariable String time
	  ) {
		  if ("" == time || "".equalsIgnoreCase(time) || time.isEmpty()) throw new InputTimeNotfoundException();

	    return speakingClockService.convertToWords(time);
	  }
	  
	  @GetMapping("/speakingclock/{time}")
	  public boolean timeValidation(
	    @PathVariable String time
	  ) {
		  if ("" == time || "".equalsIgnoreCase(time) || time.isEmpty()) throw new InputTimeNotfoundException();
	    return speakingClockService.timeValidation(time);
	  }
	  
	  @GetMapping("/speakingclock/{time}")
	  public String[] timeGrouping(
	    @PathVariable String time
	  ) {
		  if ("" == time || "".equalsIgnoreCase(time) || time.isEmpty()) throw new InputTimeNotfoundException();
	    return speakingClockService.timeGrouping(time);
	  }

}
