package org.example;

import org.openqa.selenium.support.Color;

import java.awt.*;
import java.util.Objects;

public class VisualProperties {
    Color color;
    Color backgroundColor;
    Float fontSize;


    public Color getColor() {
        return color;
    }

    public VisualProperties withColor(Color color) {
        this.color = color;
        return this;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public VisualProperties withBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public Float getFontSize() {
        return fontSize;
    }

    public VisualProperties withFontSize(String fontSize) {
        this.fontSize = Float.parseFloat(fontSize.replaceAll("px",""));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisualProperties that = (VisualProperties) o;
        return Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return "VisualProperties{" +
                "color='" + color + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", fontSize=" + fontSize +
                '}';
    }
}
