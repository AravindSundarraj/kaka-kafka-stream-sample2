package com.kaka.group.partitioner;

import com.kaka.group.domain.Purchase;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.StreamPartitioner;

@Slf4j
public class RewardPartitioner implements StreamPartitioner<String ,Purchase> {

    @Override
    public Integer partition(String var1 ,String key, Purchase value, int numPartitions) {

       Integer i =  value.getCustomerId().hashCode() % numPartitions;
       log.warn("value.getCustomerId() = > {} {}" ,value.getCustomerId() , value.getCustomerId().hashCode());
        log.warn("Get the Partition Number : {} & numPartitions {}"  ,  i , numPartitions);

        return i;
    }
}
