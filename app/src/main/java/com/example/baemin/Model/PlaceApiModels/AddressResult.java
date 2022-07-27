package com.example.baemin.Model.PlaceApiModels;

import com.example.baemin.Model.PlaceApiModels.Prediction;

import java.util.ArrayList;

public class AddressResult {
    String status;

    ArrayList<Prediction> predictions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(ArrayList<Prediction> predictions) {
        this.predictions = predictions;
    }
}
