package org.example.AbnormalPrompt

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

object Exportlogin {
  def main(args: Array[String]): Unit = {
    //创建sparkConf
    val sparkConf = new SparkConf().setMaster("local[*]")
      .setAppName("ks")
    //创建StreamingContext，时间间隔为设置10秒（采集周期）
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    // 设置检查点目录
    ssc.checkpoint("C:\\Users\\86131\\Desktop\\检查点\\login")
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
    val topics = Array("login")
    //创建Kafka数据源  每行的数据为 key：null value：37320774973(时间搓) 江西 南昌 用户ID 商品ID
    val linesDStream: InputDStream[ConsumerRecord[String, String]] =
      KafkaUtils.createDirectStream(ssc, PreferConsistent,
        Subscribe[String, String](topics, kafkaParams))
    val lineRDD = linesDStream.map(_.value())
    //数据格式转换成:(2022-9-16(日期)_省份_城市,count)
    val mapDS: DStream[(String, Int)] = lineRDD.map {
      line => {
        val fields: Array[String] = line.split(" ")
        //获取时间戳
        val timeStamp: Long = fields(0).toLong
        //根据时间戳创建日期对象
        val day: Date = new Date(timeStamp)
        //创建SimpleDateFormat,对日期对象进行转换
        val sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
        //将日期对象转换为字符串
        val str: String = sdf.format(day)
        //获取地区
        val user: String = fields(1)
        val login: String = fields(2)

        //封装元组
        (str + "_" + user + "_" + login, 1)
      }

    }


    val updateDS: DStream[(String, Int)] = mapDS.updateStateByKey(
      (seq: Seq[Int], buffer: Option[Int]) => {
        Option(seq.sum + buffer.getOrElse(0))
      }
    )
    val mapDS1 = updateDS.reduceByKey(_ + _)
    val mapDS2 = mapDS1.map(
      datas => {
        var a: String = null
        var line=datas._1.split("_")
        //错误超过五次就加入暂时冻结
        if (datas._2 >= 5) {
           a = line(1)
        }
        a
      }
    )

    mapDS2.print()


    ssc.start()
    ssc.awaitTermination()

    // 对上述的DS进行分组，按照天_地区进行分组
    //val groupDS: DStream[(String, Iterable[(String, Int)])] = mapDS2.groupByKey()

  }


}
