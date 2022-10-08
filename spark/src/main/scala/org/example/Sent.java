package org.example;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Sent {
    @Test
    public void send() throws InterruptedException {
        Properties pro=new Properties();
        pro.put("bootstrap.servers","192.168.60.128:9092");
        pro.put("acks","all");
        pro.put("retries",0);
        pro.put("batch.size",16384);
        pro.put("linger.ms",2);
        pro.put("buffer.memory",33554432);
        pro.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        pro.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer kp=new KafkaProducer(pro);
        for(int i=0;i<3;i++){
            ProducerRecord pr=new ProducerRecord("HotRecommend","key",
                    "1590136353874"+"_"+"省份"+(i+15)+"_"+"城市"+i+"_"+"用户ID"+(i+i)+"_"+"商品ID"+i+10);//必须要有字符串
            kp.send(pr);
            Thread.sleep(1000);//没有这个无法执行发送任务
        }
    }
    //目前有bug，一直运行
    @Test
    public void receive(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.60.128:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put(" auto.offset.reset","earliest");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> constomer=new KafkaConsumer<String,String> (props);
        constomer.subscribe(Arrays.asList("HotRecommend"));
        while(true){
            ConsumerRecords<String,String> cus=
                    constomer.poll(Duration.ofSeconds(1));
            for(ConsumerRecord cr:cus){
                System.out.println(cr.key()+"----"+cr.value());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Sent sent=new Sent();
        sent.send();
    }
}
