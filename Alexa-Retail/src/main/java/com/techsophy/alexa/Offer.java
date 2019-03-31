package com.techsophy.alexa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.SimpleCard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Offer {

	
	public SpeechletResponse execute(Intent intent, Session session){
		
		String repromptText = "You can ask for running offers in the store";
		
		List<String> offer = null;
		try {
			String type = intent.getSlot("offer").getValue();
			offer = getOffer(type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    String speechOutput;
		if(offer == null || offer.size()==0){
			speechOutput = "Sorry, we don't have any offer today. ";
		}
		else{
			speechOutput = " We have "+offer.size()+" offers ";
			for(int i=0; i<offer.size(); i++){
				
				if(i!=0 && i==offer.size()-1){
					speechOutput+=" and "+offer.get(i);
				}
				else{
					speechOutput+= " "+offer.get(i);
				}
			}
		}
		SimpleCard card = new SimpleCard();
        card.setTitle("Alfresco Services");
        card.setContent(speechOutput);
		SpeechletResponse response = AlexaUtil.speechResponse(speechOutput, false,
                repromptText, false);
        response.setCard(card);
        return response;
	}
	
	public List<String> getOffer(String offer) throws JsonProcessingException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		
		offer = offer == null || offer.trim().isEmpty() ? "day" : offer ;
		
		HttpGet httpGet = new HttpGet(AlexaUtil.URI+"/offer/"+offer);

		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");

		CloseableHttpResponse httpRes = client.execute(httpGet);

		String jsonString = IOUtils.toString(httpRes.getEntity().getContent());
		JsonNode jsonObject = new ObjectMapper().readTree(jsonString);
		
		List<String> offerList = new ArrayList<>();
		
		if (jsonObject.isArray()) {
			
		    for (final JsonNode offerNode : jsonObject) {
		        String o =  "discount " + offerNode.get("discount").asText() +" percent " + offerNode.get("conditions").asText()  ; 
		        offerList.add(o);
		       
		       // SpeechletResponse offer = getOffer(jsonString, session);
				   
		       // offer = "You have "+count+" offer";
		}
		
		
	}
		return offerList;
    }
}

