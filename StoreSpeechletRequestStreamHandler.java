package com.techsophy.alexa;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public class StoreSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler{

	private static final Set<String> supportedApplicationIds;
	
	static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add("amzn1.ask.skill.db04d955-8b2d-403d-bb06-2d3f3edd1c34");
    }
	
	public StoreSpeechletRequestStreamHandler(){
		super(new StoreSpeechlet(), supportedApplicationIds);
	}
	
	public StoreSpeechletRequestStreamHandler(Speechlet speechlet, Set<String> supportedApplicationIds) {
		super(speechlet, supportedApplicationIds);
	}

}
