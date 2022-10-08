package org.example.spark

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
/*重点声明滑动窗口长度为1小时，但是演示将其缩短为12秒，与项目连接需要改为3600
 滑动步长为3秒

* */
object minutesHoursCount {
  def main(args: Array[String]): Unit = {
    //创建sparkConf
    val sparkConf = new SparkConf().setMaster("local[*]")
      .setAppName("ks")
    //创建StreamingContext，时间间隔为设置10秒（采集周期）
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    // 设置检查点目录
    ssc.checkpoint("C:\\Users\\86131\\Desktop\\检查点\\minuteHoursCount")
    /*
    配置kafka消费者参数
    bootstrap.servers kafka的服务节点和端口号，用于连接kafka
    key.deserializer 反序列化
    value.deserializer 指定反序列化的类
    group.id 消费者组id，id相同的消费者在同一个组
    enable.auto.commit kafka是否自动提交偏移量，这里填否，交由spark管理
     */
    Logger.getLogger("org").setLevel(Level.WARN)
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "192.168.60.128:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "HotRecommend",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    //定义一个主题数组，内可包含多个主题，此处只有一个
    val topics = Array("HotRecommend")
    //创建Kafka数据源  每行的数据为 key：null value：37320774973(时间搓) 江西 南昌 用户ID 商品ID
    val linesDStream: InputDStream[ConsumerRecord[String, String]] =
      KafkaUtils.createDirectStream(ssc, PreferConsistent,
        Subscribe[String, String](topics, kafkaParams))
    val lineRDD = linesDStream.map(_.value())

    // 定义窗口大小以及滑动的步长
    val windowDS: DStream[String] = lineRDD.window(Seconds(30), Seconds(3))    // 为了方便演示定义窗口大小为 12s,滑动步长为 3s

    // 对上述的DS的结构进行转换 （advId_hhmm, 1）
    val mapDS: DStream[(String, Int)] = windowDS.map {
      line => {
        // 用逗号 "," 进行切割
        val fields: Array[String] = line.split(" ")
        // 获取时间戳
        val timeStamp: Long = fields(0).toLong
        // 将时间戳转换为时间对象
        val date = new Date(timeStamp)
        // 创建一个SimpleDateFormat对象对日期进行格式的转换
        val sdf = new SimpleDateFormat("hh:mm")//更改：("mm:ss")这是分钟秒
        val dateStr: String = sdf.format(date)
        // 返回想要的格式
        (fields.last + "_" + dateStr, 1)
      }
    }

    // 对上述的DS进行聚合处理
    val resDS: DStream[(String, Int)] = mapDS.reduceByKey(_ + _)

    // 打印输出结果
    resDS.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
