package com.techsophy.alexa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.SimpleCard;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemLocation  {
	
	public SpeechletResponse execute(Intent intent, Session session){
		String repromptText = "You can ask for location of items.";
		
		String item = intent.getSlot("item").getValue();
		List<String> locations;
		
		String speechOutput;
		try {
			speechOutput = "You can find "+item +" at.";
			locations = getItemLocation(item);
			if(locations == null || locations.size()==0){
				speechOutput = "Sorry, we could not find required location, please contact store staff. ";
			}
			else{
				for(int i=0; i<locations.size(); i++){
					if(i!=0 && i==locations.size()-1){
						speechOutput+=" and. "+locations.get(i);
					}
					else{
						speechOutput+= " "+locations.get(i);
					}
				}
			}
		} catch (IOException e) {
			speechOutput = " Sorry,  ";
		}
		SimpleCard card = new SimpleCard();
        card.setTitle("Alfresco Services");
        card.setContent(speechOutput);
		SpeechletResponse response = AlexaUtil.speechResponse(speechOutput, false,
                repromptText, false);
        response.setCard(card);
        
        return response;
		
	}
	
	public List<String> getItemLocation(String item) throws ClientProtocolException, IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(AlexaUtil.URI+"/item/"+item);

		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-type", "application/json");

		

		CloseableHttpResponse httpRes = client.execute(httpGet);

		String jsonString = IOUtils.toString(httpRes.getEntity().getContent());
		JsonNode jsonObject = new ObjectMapper().readTree(jsonString);
		
		List<String> locations = new ArrayList<>();
		
		if (jsonObject.isArray()) {
		    for (final JsonNode objNode : jsonObject) {
		        JsonNode locNode = objNode.get("location");
		        String loc = "shelf. " + locNode.get("shelf").asText() + " .of block. "+ locNode.get("block").asText() ; 
		        locations.add(loc);
		    }
		}
		
		return locations;
	}

	
}					
	
	



		
	
						        
