package com.kaka.group.serdes;


import com.kaka.group.domain.CorrelatedPurchase;
import com.kaka.group.domain.Purchase;
import com.kaka.group.domain.PurchasePattern;
import com.kaka.group.domain.RewardAccumulator;
import com.kaka.group.serializer.JsonDeserializer;
import com.kaka.group.serializer.JsonSampleDeSerializer;
import com.kaka.group.serializer.JsonSampleSerializer;
import com.kaka.group.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class SampleSerdes {

/*    public static Serde<PurchasePattern> PurchasePatternSerde() {
        return new PurchasePatternsSerde();
    }

    public static Serde<RewardAccumulator> RewardAccumulatorSerde() {
        return new RewardAccumulatorSerde();
    }*/


    public static Serde<CorrelatedPurchase> correlatedPurchaseSerde() {
        return new CorrelatedPurchaseSerde();
    }


    public static final class CorrelatedPurchaseSerde extends Serdes.WrapperSerde<CorrelatedPurchase> {
        public CorrelatedPurchaseSerde() {
            super(new JsonSampleSerializer<CorrelatedPurchase>(), new JsonSampleDeSerializer<CorrelatedPurchase>(CorrelatedPurchase.class));
        }
    }

    public static Serde<Purchase> PurchaseSerde() {
        return new PurchaseSerde();
    }


    public static final class PurchaseSerde extends Serdes.WrapperSerde<Purchase> {
        public PurchaseSerde() {
            super(new JsonSampleSerializer<Purchase>(), new JsonSampleDeSerializer<Purchase>(Purchase.class));
        }
    }

    public static Serde<RewardAccumulator> RewardSerde() {
        return new RewardSerde();
    }


    public static final class RewardSerde extends Serdes.WrapperSerde<RewardAccumulator> {
        public RewardSerde() {
            super(new JsonSampleSerializer<RewardAccumulator>(), new JsonSampleDeSerializer<RewardAccumulator>(RewardAccumulator.class));
        }
    }

    public static Serde<PurchasePattern> PurchasePatternSerde() {
        return new PurchasePatternSerde();
    }


    public static final class PurchasePatternSerde extends Serdes.WrapperSerde<PurchasePattern> {
        public PurchasePatternSerde() {
            super(new JsonSampleSerializer<PurchasePattern>(), new JsonSampleDeSerializer<PurchasePattern>(PurchasePattern.class));
        }
    }
}
