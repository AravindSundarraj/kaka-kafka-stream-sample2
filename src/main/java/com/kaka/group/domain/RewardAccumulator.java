
package com.kaka.group.domain;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import org.apache.commons.lang.builder.ToStringBuilder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.kafka.common.serialization.IntegerSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "customerId",
    "totalRewardPoints",
    "currentRewardPoints",
    "daysFromLastPurchase",
    "purchaseTotal"
})
@ToString
@NoArgsConstructor
public class RewardAccumulator {

    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("totalRewardPoints")
    private Integer totalRewardPoints;
    @JsonProperty("currentRewardPoints")
    private Integer currentRewardPoints;
    @JsonProperty("daysFromLastPurchase")
    private Integer daysFromLastPurchase;
    @JsonProperty("purchaseTotal")
    private BigDecimal purchaseTotal;

    private RewardAccumulator(String customerId, BigDecimal purchaseTotal, int rewardPoints) {
        this.customerId = customerId;
        this.purchaseTotal = purchaseTotal;
        this.currentRewardPoints = rewardPoints;
        this.totalRewardPoints = rewardPoints;

    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getPurchaseTotal() {
        return purchaseTotal;
    }

    public int getCurrentRewardPoints() {
        return currentRewardPoints;
    }

    public int getTotalRewardPoints() {
        return totalRewardPoints;
    }

    public void addRewardPoints(int previousTotalPoints) {
        this.totalRewardPoints += previousTotalPoints;
    }

    @Override
    public String toString() {
        return "RewardAccumulator{" +
                "customerId='" + customerId + '\'' +
                ", purchaseTotal=" + purchaseTotal +
                ", totalRewardPoints=" + totalRewardPoints +
                ", currentRewardPoints=" + currentRewardPoints +
                ", daysFromLastPurchase=" + daysFromLastPurchase +
                '}';
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RewardAccumulator)) return false;

        RewardAccumulator that = (RewardAccumulator) o;

        if (Double.compare(that.purchaseTotal, purchaseTotal) != 0) return false;
        if (totalRewardPoints != that.totalRewardPoints) return false;
        if (currentRewardPoints != that.currentRewardPoints) return false;
        if (daysFromLastPurchase != that.daysFromLastPurchase) return false;
        return customerId != null ? customerId.equals(that.customerId) : that.customerId == null;
    }*/

/*    @Override
    public int hashCode() {
        int result;
        long temp;
        result = customerId != null ? customerId.hashCode() : 0;
        temp = Double.doubleToLongBits(purchaseTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + totalRewardPoints;
        result = 31 * result + currentRewardPoints;
        result = 31 * result + daysFromLastPurchase;
        return result;
    }*/

    public static Builder builder(Purchase purchase){return new Builder(purchase);}

    public static final class Builder {
        private String customerId;
        private BigDecimal purchaseTotal;
        private int daysFromLastPurchase;
        private int rewardPoints;

        private Builder(Purchase purchase){
            this.customerId = purchase.getLastName()+","+purchase.getFirstName();
            this.purchaseTotal = purchase.getPrice().multiply( BigDecimal.valueOf(purchase.getQuantity()));
            this.rewardPoints = (int) Integer.valueOf(purchaseTotal.intValue());
        }


        public RewardAccumulator build(){
            return new RewardAccumulator(customerId, purchaseTotal, rewardPoints);
        }

    }

}
