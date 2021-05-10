package com.tradeledger.cards.eligibility;

import com.tradeledger.cards.common.Applicant;
import com.tradeledger.cards.common.EligibilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("eligibility")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApplicationUIController {

    @Autowired
    private ApplicationUIService service;

    @PostMapping(path = "check", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public EligibilityResponse checkEligibility(@Valid @RequestBody Applicant applicant) {
        return service.checkEligibilityFor(applicant);
    }
}
