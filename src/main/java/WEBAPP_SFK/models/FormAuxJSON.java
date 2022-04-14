package WEBAPP_SFK.models;

public class FormAuxJSON {
    private int idNotificationSelect;
    private int shelfId;
    private String fruitType;
    private int discountPercentage;
    private int quantity;
    private String inspectionType;
    private int id;

    public FormAuxJSON() {
    }

    public FormAuxJSON(int idNotificationSelect, int shelfId, String fruitType, int discountPercentage, int quantity, String inspectionType, int id) {
        this.idNotificationSelect = idNotificationSelect;
        this.shelfId = shelfId;
        this.fruitType = fruitType;
        this.discountPercentage = discountPercentage;
        this.quantity = quantity;
        this.inspectionType = inspectionType;
        this.id = id;
    }

    public int getIdNotificationSelect() {
        return idNotificationSelect;
    }

    public void setIdNotificationSelect(int idNotificationSelect) {
        this.idNotificationSelect = idNotificationSelect;
    }

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public String getFruitType() {
        return fruitType;
    }

    public void setFruitType(String fruitType) {
        this.fruitType = fruitType;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
