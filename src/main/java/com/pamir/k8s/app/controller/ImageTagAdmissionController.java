package com.pamir.k8s.app.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pamir.k8s.app.domain.AdmissionReviewResponse;
import com.pamir.k8s.app.domain.AdmissionReviewStatus;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "control")
@RequestMapping("controller")
public class ImageTagAdmissionController {

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String validate(@RequestBody String admissionReview) {

        JsonElement jsonElement = JsonParser.parseString(admissionReview);
        JsonObject jsonAdmissionReview = jsonElement.getAsJsonObject();
        JsonArray containers = jsonAdmissionReview.getAsJsonObject("request").getAsJsonObject("object")
                .getAsJsonObject("spec").getAsJsonArray("containers");

        AdmissionReviewStatus status = new AdmissionReviewStatus();
        AdmissionReviewResponse response = new AdmissionReviewResponse();
        response.setAdmissionReviewStatus(status);

        boolean trustedRepo = true;
        for (int i = 0; i < containers.size(); ++i) {
            JsonObject container = containers.get(i).getAsJsonObject();
            String imageTag = container.get("image").getAsString();
            if (!imageTag.startsWith("nginx")) {
                trustedRepo = false;
            }
        }

        if (!trustedRepo) {
            response.setAllowed(false);
            status.setDescription("All images repository should be from nginx");
        } else {
            response.setAllowed(true);
        }

        jsonAdmissionReview.add("response", new Gson().toJsonTree(response));
        return jsonAdmissionReview.toString();
    }
}
