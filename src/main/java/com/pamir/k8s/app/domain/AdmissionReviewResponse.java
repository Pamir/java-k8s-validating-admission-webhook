package com.pamir.k8s.app.domain;

import com.google.gson.annotations.SerializedName;

public class AdmissionReviewResponse {

   @SerializedName("status")
   private AdmissionReviewStatus admissionReviewStatus;
   private boolean allowed;

   public void setAdmissionReviewStatus(AdmissionReviewStatus admissionReviewStatus) {
      this.admissionReviewStatus = admissionReviewStatus;
   }

   public AdmissionReviewStatus getAdmissionReviewStatus() {
      return this.admissionReviewStatus;
   }

   public void setAllowed(boolean allowed) {
      this.allowed = allowed;
   }

   public boolean getAllowed() {
      return this.allowed;
   }

}