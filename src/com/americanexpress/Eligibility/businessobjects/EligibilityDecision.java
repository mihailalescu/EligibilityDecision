package com.americanexpress.Eligibility.businessobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum EligibilityDecision {
	APPROVED, PENDING_MANUAL, DENIED;
}
