package org.example

import java.text.SimpleDateFormat
import java.util.{Date, Properties}
import java.sql.{Connection, DriverManager, PreparedStatement}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SaveMode
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

//时间搓，省份，城市，用户，商品 每一份采集一次然后保存到数据库 (id  省份_城市_商品   count)
object day {
  def main(args: Array[String]): Unit = {
    //创建sparkConf
    val sparkConf=new SparkConf().setMaster("local[*]")
      .setAppName("ks")
    //创建StreamingContext，时间间隔为设置60秒（采集周期）
    val ssc=new StreamingContext(sparkConf,Seconds(10))
    /*
    配置kafka消费者参数
    bootstrap.servers kafka的服务节点和端口号，用于连接kafka
    key.deserializer 反序列化
    value.deserializer 指定反序列化的类
    group.id 消费者组id，id相同的消费者在同一个组
    enable.auto.commit kafka是否自动提交偏移量，这里填否，交由spark管理
     */
    Logger.getLogger("org").setLevel(Level.WARN)
    val kafkaParams = Map[String,Object](
      "bootstrap.servers"->"192.168.60.128:9092",
      "key.deserializer"->classOf[StringDeserializer],
      "value.deserializer"->classOf[StringDeserializer],
      "group.id"->"HotRecommend",
      "enable.auto.commit"->(false:java.lang.Boolean)
    )
    //定义一个主题数组，内可包含多个主题，此处只有一个
    val topics = Array("HotRecommend")
    //创建Kafka数据源
    val linesDStream:InputDStream[ConsumerRecord[String,String]]=
      KafkaUtils.createDirectStream(ssc,PreferConsistent,
        Subscribe[String,String](topics,kafkaParams))
    val lineRDD=linesDStream.map(_.value())


    val mapDS: DStream[(String, Int)] = lineRDD.map {
      line => {
        val fields: Array[String] = line.split("_")
        //获取时间戳
        val timeStamp: Long = fields(0).toLong
        //根据时间戳创建日期对象
        val day: Date = new Date(timeStamp)
        //创建SimpleDateFormat,对日期对象进行转换
        val sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
        //将日期对象转换为字符串
        val str: String = sdf.format(day)
        //获取地区
        val area: String = fields(1)
        val city:String=fields(2)
        //获取广告
        val adv: String = fields(4)
        //封装元组
        (str + "_" + area + "_" + city +"_" + adv, 1)
      }
    }
    val resultRDD=mapDS.reduceByKey(_+_)
    //resultRDD.saveAsTextFiles("C:\\Users\\86131\\Desktop\\%d{yyyy-MM-dd}.log")
    //将每个周期的数据写到数据里面
    resultRDD.foreachRDD(
      rdd =>{
        rdd.foreach(
          record =>{
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            var a:Int=0

            val connection: Connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sparksqltest?useUnicode=true&characterEncoding=utf8&useSSL=false","root","root")
            val pstmt: PreparedStatement = connection.prepareStatement("insert into data(name,count) values (?,?)")
            pstmt.setString(1,record._1.toString)
            pstmt.setInt(2,record._2)
            pstmt.executeUpdate()
            connection.close()
          }
        )
      }
    )
    //启动采集器
    ssc.start()
    //等待线程结束，关闭采集器
    ssc.awaitTermination()


  }
}
