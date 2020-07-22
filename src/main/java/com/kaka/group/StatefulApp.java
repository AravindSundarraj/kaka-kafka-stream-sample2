package com.kaka.group;

import com.kaka.group.domain.Purchase;
import com.kaka.group.domain.PurchasePattern;
import com.kaka.group.domain.RewardAccumulator;
import com.kaka.group.mock.MockDataProducer;
import com.kaka.group.partitioner.RewardPartitioner;
import com.kaka.group.serdes.SampleSerdes;
import com.kaka.group.transformer.RewardTransformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

import java.util.Properties;

@Slf4j
public class StatefulApp {
    public static void main(String args[]) throws Exception {

        Properties prop = new Properties();
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG, "sample_app_3");
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        Serde<Purchase> purchaseSerde = SampleSerdes.PurchaseSerde();

       // RewardPartitioner rewardPartitioner = new RewardPartitioner();
        MockDataProducer.producePurchaseData();

        Serde<PurchasePattern> purchasePatternSerde = SampleSerdes.PurchasePatternSerde();
        Serde<RewardAccumulator> rewardAccumulatorSerde = SampleSerdes.RewardSerde();
        Serde<String> stringSerde = Serdes.String();

        StreamsConfig streamConfig = new StreamsConfig(prop);

        StreamsBuilder streamsBuilder = new StreamsBuilder();


        KStream<String, Purchase> purchaseKStream = streamsBuilder.stream("transaction-renew",
                Consumed.with(stringSerde, purchaseSerde))
                .mapValues(p -> Purchase.builder(p).maskCreditCard().build());



        // adding State to processor
        String rewardsStateStoreName = "rewardsPointsStore";
        RewardPartitioner streamPartitioner = new RewardPartitioner();

        KeyValueBytesStoreSupplier storeSupplier = Stores.inMemoryKeyValueStore(rewardsStateStoreName);
        StoreBuilder<KeyValueStore<String, Integer>> storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, Serdes.String(), Serdes.Integer());

        streamsBuilder.addStateStore(storeBuilder);

        KStream<String, Purchase> transByCustomerStream = purchaseKStream.through( "customer_transactions", Produced.with(stringSerde, purchaseSerde, streamPartitioner));


        KStream<String, RewardAccumulator> statefulRewardAccumulator = transByCustomerStream.
                transformValues(() ->  new RewardTransformer(rewardsStateStoreName),
                rewardsStateStoreName);

        statefulRewardAccumulator.print(Printed.<String, RewardAccumulator>toSysOut().withLabel("rewards"));
        statefulRewardAccumulator.to("rewards", Produced.with(stringSerde, rewardAccumulatorSerde));

        // used only to produce data for this application, not typical usage



        log.info("Starting Adding State Example");
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(),prop);
        log.info("ZMart Adding State Application Started");
        kafkaStreams.cleanUp();
        kafkaStreams.start();
        Thread.sleep(65000);
        log.info("Shutting down the Add State Application now");
        kafkaStreams.close();
        MockDataProducer.shutdown();

    }
}
