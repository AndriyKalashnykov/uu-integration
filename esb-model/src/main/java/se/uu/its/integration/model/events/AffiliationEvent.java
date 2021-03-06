package se.uu.its.integration.model.events;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import se.uu.its.integration.model.identity.Affiliation;

@XmlRootElement(name = "AffiliationEvent", namespace = "http://www.uu.se/schemas/integration/2015/Events")
public class AffiliationEvent extends UUEvent {

	private static final long serialVersionUID = 1379421315450514035L;

	@XmlElementRef(type = Affiliation.class, namespace = "http://www.uu.se/schemas/integration/2015/Identity")
	private Affiliation affiliation;
	
	@XmlElementRef(type = AffiliationEventData.class, namespace = "http://www.uu.se/schemas/integration/2015/Events")
	private AffiliationEventData affiliationEventData;
	
	protected AffiliationEvent() {
	}
	
	protected AffiliationEvent(String producer, String producerReferenceId, String affilationIdentifier) {
		super(producer, producerReferenceId);
		this.affiliation = new Affiliation(affilationIdentifier);
	}
	
	protected AffiliationEvent(String producer, String producerReferenceId, Affiliation affiliation) {
		super(producer, producerReferenceId);
		this.affiliation = affiliation;
	}
	
	protected AffiliationEvent(String producer, String producerReferenceId, Affiliation identity, AffiliationEventData identityEventData) {
		super(producer, producerReferenceId);
		this.affiliation = identity;
		this.affiliationEventData = identityEventData;
	}
	
	public Affiliation getIdentity(){
		return affiliation;
	}
		
}
