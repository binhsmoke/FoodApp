package com.example.baemin.Model.PlaceApiModels;

public class Prediction {
   private String description;
   private StructuredFormatting structured_formatting;

    public StructuredFormatting getStructured_formatting() {
        return structured_formatting;
    }

    public void setStruct_formatting(StructuredFormatting structured_formatting) {
        this.structured_formatting = structured_formatting;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
