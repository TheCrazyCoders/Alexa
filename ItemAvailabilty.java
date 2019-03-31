package com.techsophy.alexa;

import java.util.List;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.SimpleCard;

public class ItemAvailabilty {

	public SpeechletResponse execute(Intent intent,Session session) {
		String repromptText = "You can ask, who's there";
		
		String item = intent.getSlot("item").getValue();
		List<String> avialability;
		
		String speechOutput = "Requested "+item +" is Available.";
		avialability = getItemAvailabilty(item);
		if(avialability == null || avialability.size()==0){
			speechOutput = "Sorry, "+item +", please contact store staff. ";
		}
		
		SimpleCard card = new SimpleCard();
        card.setTitle("Alfresco Services");
        card.setContent(speechOutput);
		SpeechletResponse response = AlexaUtil.speechResponse(speechOutput, false,
                repromptText, false);
        response.setCard(card);
	return response;
	}

	private List<String> getItemAvailabilty(String item) {
		// TODO Auto-generated method stub
		return null;
	}

}
