package com.tradeledger.cards.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class EligibilityResponse {

    @JsonProperty("eligibleCards")
    Set<String> eligibleCards;
}
