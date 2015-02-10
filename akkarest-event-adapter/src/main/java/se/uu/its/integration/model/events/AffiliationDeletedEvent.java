package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlRootElement;

import se.uu.its.integration.model.identity.Affiliation;

@XmlRootElement(name = "AffiliationEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class AffiliationDeletedEvent extends AffiliationEvent {

	private static final long serialVersionUID = -7891758741204300941L;

	/**
	 * Needed for JAXB.
	 */
	@SuppressWarnings("unused")
	private AffiliationDeletedEvent() {
	}		
		
	public AffiliationDeletedEvent(String producer, Affiliation affiliation) {
		super(producer, affiliation);
	}
	
	public AffiliationDeletedEvent(String producer, String affiliationIdentifier) {
		super(producer, affiliationIdentifier);
	}

}
