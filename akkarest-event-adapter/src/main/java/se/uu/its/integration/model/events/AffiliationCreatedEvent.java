package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.Affiliation;

@XmlRootElement(name="AffilisationEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class AffiliationCreatedEvent extends AffiliationEvent {

	private static final long serialVersionUID = 6781440173238582222L;

	/**
	 * Needed for JAXB.
	 */	
	@SuppressWarnings("unused")
	private AffiliationCreatedEvent() {
	}	
	
	public AffiliationCreatedEvent(String producer, String affiliationIdentifier) {
		super(producer, affiliationIdentifier);
	}
	
	public AffiliationCreatedEvent(String producer, Affiliation affiliation) {
		super(producer, affiliation);
	}

}
