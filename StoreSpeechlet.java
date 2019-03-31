package com.techsophy.alexa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;

public class StoreSpeechlet implements Speechlet{
	
	private static final Logger log = LoggerFactory.getLogger(StoreSpeechlet.class);
	
	private static final String STORE_NAME = "UK Super Market.";
	
	

	@Override
	public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		// TODO Auto-generated method stub
	}

	@Override
	public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
		return getWelcomeResponse();
	}

	@Override
	public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
		
		Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;
        
        if ("ItemLocationIntent".equals(intentName)) {
        	return new ItemLocation().execute(intent, session);
        }
        else if("OfferIntent".equals(intentName)){
        	return new Offer().execute(intent, session);
        }
        else if("ItemAvailabilityIntent".equals(intentName)){
        	return new ItemAvailabilty().execute(intent, session);
        }
        else if("ItemPriceIntent".equals(intentName)){
        	return new ItemPrice().getItemPrice(session);
        }
        else{
        	throw new SpeechletException("Invalid Intent");
        }
	}

	@Override
	public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {
		// TODO Auto-generated method stub
		
	}
	
	
	private SpeechletResponse getWelcomeResponse() {
		String speechOutput = "<speak>"
				+ "Welcome to. "+STORE_NAME
				+ "How may I serve you. ";
		String repromptText =
				"I can lead you through. all the items available in the store. "
						+ " and different offers."

	                        + " How may I serve you.";

		return AlexaUtil.speechResponse(speechOutput, false, repromptText, false);
	}
	 
	 

}
