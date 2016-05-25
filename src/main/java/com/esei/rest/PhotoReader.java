package com.esei.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.ext.Provider;

import com.esei.model.Offer;


@Provider
public class PhotoReader extends MultipartMessageBodyReader<Offer> {


	static final String FIRST_QUARTER_LIMIT_DATETIME = "2020/01/19 07:00";
	static final String SECOND_QUARTER_LIMIT_DATETIME = "2020/05/19 07:00";
	
	private Offer offer;

	@Override
	protected void init() {
		offer = new Offer();
	}

	@Override
	protected void add(String name, byte[] bs){

		switch(name) {
		case "name":
			//offer.setText(new String(bs));
			offer.setOfferName(new String(bs));

			break;
		case "description":
			//offer.setText(new String(bs));
			offer.setOfferDescription(new String(bs));

			break;        
		case "content":
			offer.setContent(bs);
			break;

		case "withLimit":
			String offerWithLimitString = new String(bs);
			if (offerWithLimitString == "0"){
				offer.setOfferWithLimit(false);
			} else{
				offer.setOfferWithLimit(true);
			}
			break;      
		case "timeLimit":
			String offerTimeLimitString = new String(bs);
			Date datetime;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			
			if (offerTimeLimitString.compareTo("1") == 0) {
				
				try {
					datetime = dateFormat.parse(FIRST_QUARTER_LIMIT_DATETIME);
					offer.setOfferTimeLimit(datetime);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				
			}else if (offerTimeLimitString.compareTo("2") == 0){
				try {
					datetime = dateFormat.parse(SECOND_QUARTER_LIMIT_DATETIME);
					offer.setOfferTimeLimit(datetime);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				}else{
					offer.setOfferTimeLimit(null);
					
				}
			break;  
		}
	}

	@Override
	protected Offer build() {
		return this.offer;
	}
}