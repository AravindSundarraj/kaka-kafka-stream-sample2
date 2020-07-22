package com.kaka.group.transformer;

import com.kaka.group.domain.Purchase;
import com.kaka.group.domain.RewardAccumulator;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Objects;

public class RewardTransformer implements ValueTransformer<Purchase, RewardAccumulator> {

    private KeyValueStore<String , Integer> stateStore;
    private String storeName;
    private ProcessorContext processorContext;

    public RewardTransformer(String storeName) {
        Objects.requireNonNull(storeName,"Store Name can't be null");
        this.storeName = storeName;
    }
    @Override
    public void init(ProcessorContext processorContext) {

        this.processorContext = processorContext;
        stateStore = (KeyValueStore) this.processorContext.getStateStore(storeName);
    }

    @Override
    public RewardAccumulator transform(Purchase purchase) {
        RewardAccumulator rewardAccumulator = RewardAccumulator.builder(purchase).build();
        Integer accumulatedSoFar = stateStore.get(rewardAccumulator.getCustomerId());

        if (accumulatedSoFar != null) {
            rewardAccumulator.addRewardPoints(accumulatedSoFar);
        }
        stateStore.put(rewardAccumulator.getCustomerId(), rewardAccumulator.getTotalRewardPoints());

        return rewardAccumulator;
    }

    @Override
    public void close() {

    }
}
