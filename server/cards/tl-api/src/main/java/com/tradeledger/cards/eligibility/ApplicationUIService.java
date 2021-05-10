package com.tradeledger.cards.eligibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeledger.cards.common.Applicant;
import com.tradeledger.cards.common.EligibilityResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class ApplicationUIService {

    @Value("${checkEligibility.url}")
    private String checkEligibilityURL;

    public EligibilityResponse checkEligibilityFor(Applicant applicant) {
        EligibilityResponse response = null;
        try {
            URL url = new URL(checkEligibilityURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            String jsonString = getJsonObject(applicant);
            OutputStream os = connection.getOutputStream();
            os.write(jsonString.getBytes(StandardCharsets.UTF_8));
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String inputLine;
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer result = new StringBuffer();
                while ((inputLine = in .readLine()) != null) {
                    result.append(inputLine);
                }
                response = new ObjectMapper().readValue(result.toString(), EligibilityResponse.class);
            }else{

                throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,"Data is incorrect");
            }
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"Something wrong happened");
        }
        return response;
    }

    private String getJsonObject(Object applicant) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(applicant);
    }
}
