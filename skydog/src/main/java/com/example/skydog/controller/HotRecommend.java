package com.example.skydog.controller;


import com.example.skydog.module.vo.ResultVO;
import com.example.skydog.module.vo.UserVisit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

@Api(tags = "数据埋点控制器")
@RestController
@RequestMapping("/spark")
public class HotRecommend {



    @PostMapping("/saveEventTrack")
    @ApiOperation("数据埋点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userVisit", value = "数据对象", dataType = "UserVisit"),
    })
    public void saveEventTrack(@RequestBody UserVisit userVisit) throws Exception{
//        HashMap<String, Object> pros = new HashMap<>(3);
//        pros.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "https://59733751ac.goho.co:443");
//        pros.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//        pros.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
//        KafkaProducer<String, String> producer = new KafkaProducer<>(pros);
        //将对象转换成字符串发送到kafka
        String elem=userVisit.toString();
//        ProducerRecord<String, String> record = new ProducerRecord<>("HotRecommend", null, elem);
//        producer.send(record);

        String path = "F:\\test\\test.txt";
        String word = elem;
        System.out.println(word);
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path,true)));
        out.write(word+"\n");
        out.close();

    }


}
