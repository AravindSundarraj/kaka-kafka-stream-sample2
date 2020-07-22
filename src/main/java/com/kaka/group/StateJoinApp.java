package com.kaka.group;

import com.kaka.group.domain.CorrelatedPurchase;
import com.kaka.group.domain.Purchase;
import com.kaka.group.joiner.PurchaseJoiner;
import com.kaka.group.mock.MockDataProducer;
import com.kaka.group.serdes.SampleSerdes;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.Properties;
import java.util.function.Consumer;

@Slf4j
public class StateJoinApp {

    public static void main(String args[])throws Exception{

        log.info("Start the Join Project :" );
        // Step 1: create the properties
        Properties prop = new Properties();
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG, "sample_app_2");
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        Serde<Purchase> purchaseSerde = SampleSerdes.PurchaseSerde();

        Serde<CorrelatedPurchase> correlatedPurchaseSerde = SampleSerdes.correlatedPurchaseSerde();

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        /*KStream<> kPurchase = StreamsBuilder.stream("transaction-renew" ,
                Consumed.with(Serdes.String(), purchaseSerde))
                .map( (k,v) -> {});*/

        // Step2 : create the stream for topic transaction-renew
/*        KStream<String, Purchase> purchaseKStream = streamsBuilder.stream("transaction-renew",
                Consumed.with(Serdes.String(), purchaseSerde))
                .mapValues(p -> Purchase.builder(p).maskCreditCard().build());*/


 /*       KeyValueMapper<String, Purchase, KeyValue<String,Purchase>> custIdCCMasking = (k, v) -> {
            Purchase masked = Purchase.builder(v).maskCreditCard().build();
            return new KeyValue<>(masked.getCustomerId(), masked);
        };*/

        KStream<String, Purchase> purchaseKStream = streamsBuilder.stream("transaction-renew",
                Consumed.with(Serdes.String(), purchaseSerde))
                .map( (k , p)

                        ->{ Purchase pp = Purchase.builder(p).maskCreditCard().build();
                        String s = pp.getCustomerId();
                        return new KeyValue<>(s , pp);

                });

        // Step 3 Create a Branch processors

        Predicate<String , Purchase> coffee = (k , v) -> v.getDepartment().
                equalsIgnoreCase("coffee");

        Predicate<String , Purchase> isElectronics = (k , v) -> v.getDepartment().
                equalsIgnoreCase("electronics");

        KStream<String , Purchase>[] branches = purchaseKStream.selectKey((k,v) -> v.getCustomerId()).
                branch(coffee , isElectronics);


             branches[0].to("coffee", Produced.with(Serdes.String(), purchaseSerde));

        branches[1].to("electronics", Produced.with(Serdes.String(), purchaseSerde));

        ValueJoiner<Purchase, Purchase, CorrelatedPurchase> purchaseJoiner = new PurchaseJoiner();

        JoinWindows twentyMinuteWindow =  JoinWindows.of(Duration.ofMinutes(20));

        KStream<String, CorrelatedPurchase> joinedKStream = branches[0].join(branches[1],
                purchaseJoiner,
                twentyMinuteWindow,
                Joined.with(Serdes.String(),
                        purchaseSerde,
                        purchaseSerde));

        joinedKStream.print(Printed.<String, CorrelatedPurchase>toSysOut().withLabel("joined KStream"));

        joinedKStream.to("join-topic" , Produced.with(Serdes.String() , correlatedPurchaseSerde));
        // used only to produce data for this application, not typical usage
        MockDataProducer.producePurchaseData();

        log.info("Starting Join Examples");
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), prop);
        kafkaStreams.start();
        Thread.sleep(65000);
        log.info("Shutting down the Join Examples now");
        kafkaStreams.close();
        MockDataProducer.shutdown();
    }
}
