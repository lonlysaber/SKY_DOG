/*
package org.example

import java.text.SimpleDateFormat
import java.util.Date

import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


object RealTime_req2 {
  def main(args: Array[String]): Unit = {
    //创建配置文件
    val conf: SparkConf = new SparkConf().setAppName("RealTime_req2").setMaster("local[*]")
    //创建SparkStreaming执行上下文
    val ssc: StreamingContext = new StreamingContext(conf, Seconds(3))
    //设置检测点目录
    ssc.sparkContext.setCheckpointDir("./cp")
    //kafka参数声明
    val brokers = "hadoop201:9092,hadoop202:9092,hadoop203:9092"
    val topic = "my-ads-bak"
    val group = "bigdata"
    val deserialization = "org.apache.kafka.common.serialization.StringDeserializer"
    val kafkaParams = Map(
      ConsumerConfig.GROUP_ID_CONFIG -> group,
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> deserialization,
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> deserialization
    )
    //创建DS
    val kafkaDS: InputDStream[(String, String)] = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, Set(topic)
    )
    //从Kafka的kv值中取value msg = 1590136353874,华北,北京,103,1
    val dataDS: DStream[String] = kafkaDS.map(_._2)
    //定义窗口的大小以及滑动的步长
    val windowDS: DStream[String] = dataDS.window(Seconds(12), Seconds(3))
    //对结构进行转换
    val mapDS: DStream[(String, Int)] = windowDS.map {
      line => {
        val fields: Array[String] = line.split(",")
        val timeStmp: Long = fields(0).toLong
        val day: Date = new Date(timeStmp)
        //
        val sdf = new SimpleDateFormat("mm:ss")
        val time: String = sdf.format(day)
        (fields(4) + "_" + time, 1)
      }
    }
    //对数据进行聚合
    val resDS: DStream[(String, Int)] = mapDS.reduceByKey(_ + _)
    //打印输出
    resDS.print()
    //启动采集器
    ssc.start()
    //等待线程结束，关闭采集器
    ssc.awaitTermination()
  }
}
*/
