package com.kaka.group.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public class CorrelatedPurchase {



        private String customerId;
        private List<String> itemsPurchased;
        private BigDecimal totalAmount;
        private LocalDate firstPurchaseTime;
        private LocalDate secondPurchaseTime;

        private CorrelatedPurchase(Builder builder) {
            customerId = builder.customerId;
            itemsPurchased = builder.itemsPurchased;
            totalAmount = builder.totalAmount;
            firstPurchaseTime = builder.firstPurchasedItem;
            secondPurchaseTime = builder.secondPurchasedItem;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public String getCustomerId() {
            return customerId;
        }

        public List<String> getItemsPurchased() {
            return itemsPurchased;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public LocalDate getFirstPurchaseTime() {
            return firstPurchaseTime;
        }

        public LocalDate getSecondPurchaseTime() {
            return secondPurchaseTime;
        }


        @Override
        public String toString() {
            return "CorrelatedPurchase{" +
                    "customerId='" + customerId + '\'' +
                    ", itemsPurchased=" + itemsPurchased +
                    ", totalAmount=" + totalAmount +
                    ", firstPurchaseTime=" + firstPurchaseTime +
                    ", secondPurchaseTime=" + secondPurchaseTime +
                    '}';
        }

        public static final class Builder {
            private String customerId;
            private List<String> itemsPurchased;
            private BigDecimal totalAmount;
            private LocalDate firstPurchasedItem;
            private LocalDate secondPurchasedItem;

            private Builder() {
            }

            public Builder withCustomerId(String val) {
                customerId = val;
                return this;
            }

            public Builder withItemsPurchased(List<String> val) {
                itemsPurchased = val;
                return this;
            }

            public Builder withTotalAmount(BigDecimal val) {
                totalAmount = val;
                return this;
            }

            public Builder withFirstPurchaseDate(LocalDate val) {
                firstPurchasedItem = val;
                return this;
            }

            public Builder withSecondPurchaseDate(LocalDate val) {
                secondPurchasedItem = val;
                return this;
            }

            public CorrelatedPurchase build() {
                return new CorrelatedPurchase(this);
            }
        }
    }


