package unlp.info.bd2.dto;

public class RouteSummaryDTO {

    private final String routeName;
    private final Long purchasesCount;
    private final Double averagePurchasePrice;

    public RouteSummaryDTO(String routeName, Long purchasesCount, Double averagePurchasePrice) {
        this.routeName = routeName;
        this.purchasesCount = purchasesCount;
        this.averagePurchasePrice = averagePurchasePrice;
    }

    public String getRouteName() {
        return routeName;
    }

    public Long getPurchasesCount() {
        return purchasesCount;
    }

    public Double getAveragePurchasePrice() {
        return averagePurchasePrice;
    }
}