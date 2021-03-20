package org.example.model;

import java.util.Objects;

public class PriceVisualProperties {
    String regularPrice;
    String campaignPrice;
    VisualProperties visualPropRegularPrice;
    VisualProperties visualPropCampaignPrice;

    public String getRegularPrice() {
        return regularPrice;
    }

    public PriceVisualProperties withRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
        return this;
    }

    public String getCampaignPrice() {
        return campaignPrice;
    }

    public PriceVisualProperties withCampaignPrice(String campaignPrice) {
        this.campaignPrice = campaignPrice;
        return this;
    }

    public VisualProperties getPropRegularPrice() {
        return visualPropRegularPrice;
    }

    public PriceVisualProperties withVisualPropRegularPrice(VisualProperties visualPropRegularPrice) {
        this.visualPropRegularPrice = visualPropRegularPrice;
        return this;
    }

    public VisualProperties getPropCampaignPrice() {
        return visualPropCampaignPrice;
    }

    public PriceVisualProperties withVisualPropCampaignPrice(VisualProperties visualPropCampaignPrice) {
        this.visualPropCampaignPrice = visualPropCampaignPrice;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceVisualProperties priceVisualProperties = (PriceVisualProperties) o;
        return Objects.equals(regularPrice, priceVisualProperties.regularPrice) &&
                Objects.equals(campaignPrice, priceVisualProperties.campaignPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regularPrice, campaignPrice);
    }

    @Override
    public String toString() {
        return "Price{" +
                "regularPrice='" + regularPrice + '\'' +
                ", campaignPrice='" + campaignPrice + '\'' +
                ", visualPropRegularPrice=" + visualPropRegularPrice +
                ", visualPropCampaignPrice=" + visualPropCampaignPrice +
                '}';
    }
}
