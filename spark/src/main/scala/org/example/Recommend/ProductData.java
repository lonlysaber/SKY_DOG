package org.example.Recommend;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

/*
* 这里有改动，将商品的数量改成了3个，方便检测*/
import java.util.*;

public class ProductData {
    public static List<String> mockData() throws InterruptedException {
        List<String> list = new ArrayList<>();

        List<String> provinceList = Arrays.asList("browse", "search", "goodevaluate","addToCart", "order",  "payment","dislike");//0.01,0.02,0.1,0.05,0.0.6,0.07,-0.2
        List<String> cityList = Arrays.asList("GID1","GID2","GID3","GID4","GID5","GID6","GID7","GID8","GID9");

        int len = provinceList.size();
        Random r = new Random();
        for(int i=0; i<r.nextInt(100); i++){
            Thread.sleep(10);
            int idx = r.nextInt(len);
            String behavior = provinceList.get(idx);
            String city = cityList.get(idx);
            String uid = "U" + r.nextInt(10);
            String aid = "Ad" + r.nextInt(3);
            String record = System.currentTimeMillis() + " " + uid + " " + aid + " " + behavior ;
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
                        ProducerRecord<String, String> record = new ProducerRecord<>("Recommend", null, elem);
                        producer.send(record);
                    }
            );
            Thread.sleep(1000);
        }
    }
}
