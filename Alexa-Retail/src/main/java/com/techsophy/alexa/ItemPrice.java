package com.techsophy.alexa;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.SimpleCard;

public class ItemPrice {

	public SpeechletResponse getItemPrice(Session session) {
		String repromptText = "You can ask, who's there";
		
		String speechOutput = "";
		SimpleCard card = new SimpleCard();
        card.setTitle("Alfresco Services");
        card.setContent(speechOutput);
		SpeechletResponse response = AlexaUtil.speechResponse(speechOutput, false,
                repromptText, false);
        response.setCard(card);
	return response;
		
	}

}
