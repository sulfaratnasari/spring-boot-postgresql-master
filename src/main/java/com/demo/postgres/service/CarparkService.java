package com.demo.postgres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.demo.postgres.entity.CarparkDataResponse;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class CarparkService {

    @Autowired
    public CarparkService() {

    }

    public CarparkDataResponse GetCarparkAvailability() {
        CarparkDataResponse carparkDataResponse = new CarparkDataResponse();
        RestTemplate rt = new RestTemplate();
        LocalDateTime timeNow = LocalDateTime.now();
        String url = "https://api.data.gov.sg/v1/transport/carpark-availability?date_time={date_time}";
        try {
            HashMap<String, Object> params = new HashMap<>(1);
            params.put("date_time", timeNow);
            carparkDataResponse = rt.getForObject(url, CarparkDataResponse.class, params);
        } catch (RestClientException e) {
            return null;
        }

        return carparkDataResponse;
    }

}
