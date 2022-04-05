package WEBAPP_SFK.models;

import java.util.Map;

public class ExpressSales {
    private Map<String, Object> lastBranchOffice;
    private Map<String, Object> lastQuantityFruit;

    public ExpressSales(Map<String, Object> lastBranchOffice, Map<String, Object> lastQuantityFruit) {
        this.lastBranchOffice = lastBranchOffice;
        this.lastQuantityFruit = lastQuantityFruit;
    }

    public Map<String, Object> getLastBranchOffice() {
        return lastBranchOffice;
    }

    public void setLastBranchOffice(Map<String, Object> lastBranchOffice) {
        this.lastBranchOffice = lastBranchOffice;
    }

    public Map<String, Object> getLastQuantityFruit() {
        return lastQuantityFruit;
    }

    public void setLastQuantityFruit(Map<String, Object> lastQuantityFruit) {
        this.lastQuantityFruit = lastQuantityFruit;
    }
}
