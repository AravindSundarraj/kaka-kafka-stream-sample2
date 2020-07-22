package com.kaka.group.joiner;

import com.kaka.group.domain.CorrelatedPurchase;
import com.kaka.group.domain.Purchase;
import org.apache.kafka.streams.kstream.ValueJoiner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseJoiner implements ValueJoiner<Purchase, Purchase, CorrelatedPurchase> {

    @Override
    public CorrelatedPurchase apply(Purchase purchase, Purchase otherPurchase) {

        CorrelatedPurchase.Builder builder = CorrelatedPurchase.newBuilder();

        LocalDate purchaseDate = purchase != null ? purchase.getPurchaseDate() : null;
        BigDecimal price = purchase != null ? purchase.getPrice() : new BigDecimal("0.0");
        String itemPurchased = purchase != null ? purchase.getItemPurchased() : null;

        LocalDate otherPurchaseDate = otherPurchase != null ? otherPurchase.getPurchaseDate() : null;
        BigDecimal otherPrice = otherPurchase != null ? otherPurchase.getPrice() : new BigDecimal("0.0");
        String otherItemPurchased = otherPurchase != null ? otherPurchase.getItemPurchased() : null;

        List<String> purchasedItems = new ArrayList<>();

        if (itemPurchased != null) {
            purchasedItems.add(itemPurchased);
        }

        if (otherItemPurchased != null) {
            purchasedItems.add(otherItemPurchased);
        }

        String customerId = purchase != null ? purchase.getCustomerId() : null;
        String otherCustomerId = otherPurchase != null ? otherPurchase.getCustomerId() : null;

        builder.withCustomerId(customerId != null ? customerId : otherCustomerId)
                .withFirstPurchaseDate(purchaseDate)
                .withSecondPurchaseDate(otherPurchaseDate)
                .withItemsPurchased(purchasedItems)
                .withTotalAmount(price.add(otherPrice));

        return builder.build();
    }
}
