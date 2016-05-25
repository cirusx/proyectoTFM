package com.esei.rest;

import javax.ws.rs.ext.Provider;

import com.esei.model.Offer;

@Provider
public class PhotoReader extends MultipartMessageBodyReader<Offer> {


	private Offer offer;

	@Override
	protected void init() {
		offer = new Offer();
	}

	@Override
	protected void add(String name, byte[] bs) {
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
		}
	}

	@Override
	protected Offer build() {
		return this.offer;
	}
}