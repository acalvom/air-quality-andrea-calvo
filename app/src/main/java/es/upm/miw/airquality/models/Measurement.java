
package es.upm.miw.airquality.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Measurement {

    @SerializedName("parameter")
    @Expose
    private String parameter;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("lastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("sourceName")
    @Expose
    private String sourceName;
    @SerializedName("averagingPeriod")
    @Expose
    private AveragingPeriod averagingPeriod;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public AveragingPeriod getAveragingPeriod() {
        return averagingPeriod;
    }

    public void setAveragingPeriod(AveragingPeriod averagingPeriod) {
        this.averagingPeriod = averagingPeriod;
    }

}
