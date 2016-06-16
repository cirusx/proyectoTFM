package com.esei.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.ext.Provider;
import com.esei.model.Offer;
import com.esei.model.Subcategory;
import com.esei.model.Teacher;

@Provider
public class OfferReader extends MultipartMessageBodyReader<Offer> {


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
			offer.setOfferName(new String(bs));
			break;
		case "tinydescription":
			offer.setOfferTinyDescription(new String(bs));
			break;
		case "description":
			offer.setOfferDescription(new String(bs));
			break;
		case "subcategories":
			String subcategoryIds = new String(bs);
			String[] subcategoryIdsSplit = subcategoryIds.split(",");
			List<Subcategory> subcategories = new ArrayList<Subcategory>();  
			for (String subcategoryIdStr : subcategoryIdsSplit) {
				Subcategory subcategory = new Subcategory();
				if (subcategoryIdStr.compareTo("") != 0 ) {
					long subcategoryId = Long.valueOf(subcategoryIdStr).longValue();
					subcategory.setSubcategoryId(subcategoryId);
					subcategories.add(subcategory);
				}

			}
			offer.setOfferSubcategoryList(subcategories);
			break;
		case "id":
			String offerIdStr = new String(bs);
			Long offerId = Long.valueOf(offerIdStr);
			offer.setOfferId(offerId);
			break;
		case "teacher":
			Teacher teacher = new Teacher();
			String userIdStr = new String (bs);
			long userId = Long.valueOf(userIdStr).longValue();
			teacher.setUserId(userId);
			offer.setTeacher(teacher);
			break;
		case "image":
			offer.setOfferImage(bs);
			break;
		case "pdf":
			offer.setOfferPdf(bs);
			break;
		case "withLimit":
			String offerWithLimitString = new String(bs);
			if (offerWithLimitString.compareTo("0") == 0){
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