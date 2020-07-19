package com.kaka.group;

import com.kaka.group.domain.Purchase;
import com.kaka.group.domain.PurchasePattern;
import com.kaka.group.domain.RewardAccumulator;
import com.kaka.group.mock.MockDataProducer;
import com.kaka.group.serdes.SampleSerdes;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Properties;


@Slf4j
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        log.info("Start the App for ETL");
        Properties prop = new Properties();
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG, "sample_app_2");
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        Serde<Purchase> purchaseSerde = SampleSerdes.PurchaseSerde();
        Serde<PurchasePattern> purchasePatternSerde = SampleSerdes.PurchasePatternSerde();
        Serde<RewardAccumulator> rewardAccumulatorSerde = SampleSerdes.RewardSerde();
        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String,Purchase> purchaseKStream = streamsBuilder.stream("transaction",
                Consumed.with(stringSerde, purchaseSerde))
                .mapValues(p -> Purchase.builder(p).maskCreditCard().build());


        KStream<String, PurchasePattern> patternKStream = purchaseKStream.mapValues(purchase -> PurchasePattern.
                builder(purchase).build());

        patternKStream.print(Printed.<String, PurchasePattern>toSysOut().withLabel("patterns"));
        patternKStream.to("patterns", Produced.with(stringSerde,purchasePatternSerde));


        KStream<String, RewardAccumulator> rewardsKStream = purchaseKStream.mapValues(purchase -> RewardAccumulator.
                builder(purchase).build());

        rewardsKStream.print(Printed.<String, RewardAccumulator>toSysOut().withLabel("rewards"));
        rewardsKStream.to("rewards", Produced.with(stringSerde,rewardAccumulatorSerde));



        purchaseKStream.print(Printed.<String, Purchase>toSysOut().withLabel("purchases"));
        purchaseKStream.to("purchases", Produced.with(stringSerde,purchaseSerde));


        // used only to produce data for this application, not typical usage
        MockDataProducer.producePurchaseData();

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(),prop);
        log.info("ZMart First Kafka Streams Application Started");
        kafkaStreams.start();
        Thread.sleep(650000);
        log.info("Shutting down the Kafka Streams Application now");
        kafkaStreams.close();
        MockDataProducer.shutdown();

    }
}
