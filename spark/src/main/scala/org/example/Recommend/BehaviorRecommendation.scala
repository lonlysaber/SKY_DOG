package org.example.Recommend

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

/*
* 只取排名前三的商品，，后面不够可以再加。
* 商品的种类也只有三种，方便检测*/
object BehaviorRecommendation {
  def main(args: Array[String]): Unit = {
    //创建sparkConf
    val sparkConf = new SparkConf().setMaster("local[*]")
      .setAppName("ks")
    //创建StreamingContext，时间间隔为设置10秒（采集周期）
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    // 设置检查点目录
    ssc.checkpoint("C:\\Users\\86131\\Desktop\\检查点\\BehaviorRecommendation")
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
    val topics = Array("Recommend")
    //创建Kafka数据源  每行的数据为 key：null value：37320774973(时间搓) 江西 南昌 用户ID 商品ID
    val linesDStream: InputDStream[ConsumerRecord[String, String]] =
      KafkaUtils.createDirectStream(ssc, PreferConsistent,
        Subscribe[String, String](topics, kafkaParams))
    val lineRDD = linesDStream.map(_.value())

    val mapDS: DStream[(String, Double)] = lineRDD.map {
      line => {
        val fields: Array[String] = line.split(" ")
        val user: String = fields(1)
        val product: String = fields(2)
        //获取广告
        val Behavior: String = fields(3)
        var hobby = 0.0
        Behavior match {
          case "browse" => hobby = 0.01
          case "search" => hobby = 0.02
          case "goodevaluate" => hobby = 0.1
          case "addToCart" => hobby = 0.05
          case "order" => hobby = 0.06
          case "payment" => hobby = 0.07
          case "dislike" => hobby = 0.2
        }
         (user + "_" + product, hobby)
      }
    }

    val updateDS: DStream[(String, Double)] = mapDS.updateStateByKey(
      (seq: Seq[Double], buffer: Option[Double]) => {
        Option(seq.sum + buffer.getOrElse(0.0))
      }
    )
    val mapDS1: DStream[(String, Double)] = updateDS.reduceByKey(_+_)
    // 结构转换     (用户ID_商品ID,sum)  ==>  (用户ID, (商品ID,sum))
    val mapDS2: DStream[(String, (String, Double))] = mapDS1.map {
      case (key, sum) => {
        // 对 天_地区_广告 进行切割
        val fields: Array[String] = key.split("_")
        (fields(0) , (fields.last, sum))
      }
    }
    val mapDS3=mapDS2.groupByKey()
    val resDS: DStream[(String, List[(String, Double)])] = mapDS3.mapValues {
      datas => {
        val list: List[(String, Double)] = datas.toList // 将迭代器中的数据转换成List集合
        list.sortBy(_._2).reverse.take(3) // 降序排序取前三
      }
    }
    resDS.print()


    ssc.start()
    ssc.awaitTermination()

    // 对上述的DS进行分组，按照天_地区进行分组
    //val groupDS: DStream[(String, Iterable[(String, Int)])] = mapDS2.groupByKey()

  }

}
