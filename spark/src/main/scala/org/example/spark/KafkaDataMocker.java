package org.example.spark;



import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

/**
 * @author 32098
 * 数据生成
 * 数据生成
 * 数据生成 时间搓，省份，城市 UID PID
 * 向kafka发送数据
 */
public class KafkaDataMocker {
    public static List<String> mockData() throws InterruptedException {
        List<String> list = new ArrayList<>();

        List<String> provinceList = Arrays.asList("江西", "辽宁", "浙江", "广东", "湖南", "湖北", "吉林", "黑龙江", "福建");
        List<String> cityList = Arrays.asList("南昌","沈阳","杭州","广州","长沙","武汉","长春","哈尔滨","厦门");

        int len = provinceList.size();
        Random r = new Random();
        for(int i=0; i<r.nextInt(100); i++){
            Thread.sleep(10);
            int idx = r.nextInt(len);
            String province = provinceList.get(idx);
            String city = cityList.get(idx);
            String uid = "U" + r.nextInt(10);
            String aid = "Ad" + r.nextInt(20);
            String record = System.currentTimeMillis() + " " + province + " " + city + " " + uid + " " + aid;
            list.add(record);
        }
        return list;
    }

    public static void main(String[] args) throws InterruptedException {
        HashMap<String, Object> pros = new HashMap<>(3);
        pros.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.60.128:9092");
        pros.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        pros.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(pros);
        int ite = 0;
        while (true){
            ite = ite + 1;
            System.out.println("################"+ ite +"################");
            List<String> records = mockData();
            records.forEach(
                    elem -> {
                        System.out.println(elem);
                        ProducerRecord<String, String> record = new ProducerRecord<>("HotRecommend", null, elem);
                        producer.send(record);
                    }
            );
            Thread.sleep(1000);
        }
    }
}

/*
启动Kafka服务器：kafka-server-start.sh config/server.properties
启动Kafka消费者(console)：kafka-console-consumer.sh --bootstrap-server master:9092 --topic storm-topic --from-beginning
 */

