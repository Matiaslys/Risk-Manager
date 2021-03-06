package ui;

public class RiskUI {
    Integer id;
    String description;
    Double probability;
    Double consequence;
    Double exposure;
    Boolean strategy;
    Integer connectedStrategy;

    public RiskUI() {
        description = "";
        probability = 0.0;
        consequence = 0.0;
        exposure = 0.0;
        strategy = false;
    }

    public RiskUI(Integer id, String description, Double probability, Double consequence, Double exposure, Boolean strategy, Integer connectedStrategy) {
        this.id = id;
        this.description = description;
        this.probability = probability;
        this.consequence = consequence;
        this.exposure = exposure;
        this.strategy = strategy;
        this.connectedStrategy = connectedStrategy;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Double getConsequence() {
        return consequence;
    }

    public void setConsequence(Double consequence) {
        this.consequence = consequence;
    }

    public Double getExposure() {
        return exposure;
    }

    public void setExposure(Double exposure) {
        this.exposure = exposure;
    }

    public Boolean getStrategy() {
        return strategy;
    }

    public void setStrategy(Boolean strategy) {
        this.strategy = strategy;
    }
}
