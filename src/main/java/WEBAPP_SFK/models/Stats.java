package WEBAPP_SFK.models;

import java.util.Map;

public class Stats{
    private Map<String, Object> wasteFruits;
    private Map<String, Object> tempLimit;
    private Map<String, Object> humLimit;
    private Map<String, Object> overripeFruits;
    private Map<String, Object> fruitSuply;


    public Stats(Map<String, Object> wasteFruits) {
        this.wasteFruits = wasteFruits;
    }

    public Map<String, Object> getWasteFruits() {
        return wasteFruits;
    }

    public void setWasteFruits(Map<String, Object> wasteFruits) {
        this.wasteFruits = wasteFruits;
    }


}
