//// DemandService.java
//package com.dynamicpricing.service;
//
//import org.springframework.stereotype.Service;
//import java.net.URI;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.net.http.*;
//
//import org.json.JSONObject;
//
//@Service
//public class DemandService {
//
//    public double getDemandScore(String productName) {
//        try {
//            String url = "http://localhost:8001/get-demand?product=" + URLEncoder.encode(productName, StandardCharsets.UTF_8);
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .build();
//
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            JSONObject obj = new JSONObject(response.body());
//            return obj.getDouble("demand");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0.5;
//        }
//    }
//}
