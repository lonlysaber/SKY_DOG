package org.example

import java.sql.{Connection, DriverManager, PreparedStatement}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming_DataToMysql {

  def main(args: Array[String]): Unit = {
    //spark配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming_DataToMysql")

    //创建实时数据分析环境对象
    //采集周期，以指定的时间为周期采集实时数据
    val streamingContext: StreamingContext = new StreamingContext(sparkConf,Seconds(3))

    //从指定的端口中采集数据
    val socketLineDStream: ReceiverInputDStream[String] = streamingContext.socketTextStream("xxx.xxx.xx.x",9999)

    //将采集的数据进行分解（扁平化）
    val wordDStream: DStream[String] = socketLineDStream.flatMap(line=>line.split(" "))

    //将数据进行结构转变便于统计分析
    val mapDStream: DStream[(String, Int)] = wordDStream.map((_,1))

    //将转换结构后的数据进行聚合处理
    val wordToSumDStream: DStream[(String, Int)] = mapDStream.reduceByKey(_+_)

    //将结果打印出来
    wordToSumDStream.print()

    //使用foreachRDD()方法将数据写入外部数据库mysql中
    wordToSumDStream.foreachRDD(
      rdd =>{
        rdd.foreach(
          record =>{
            Class.forName("com.mysql.jdbc.Driver").newInstance()
            val connection: Connection = DriverManager.getConnection("jdbc:mysql://xxx.xxx.xx.x:3306/sparksqltest?useUnicode=true&characterEncoding=utf8&useSSL=false","root","000000")
            val pstmt: PreparedStatement = connection.prepareStatement("insert into data (name ,count) values (?,?)")
            pstmt.setString(1,record._1.toString)
            pstmt.setInt(2,record._2)
            pstmt.executeUpdate()
            connection.close()
          }
        )
      }
    )
    //启动采集器
    streamingContext.start()
    //Driver等待采集器的执行
    streamingContext.awaitTermination()
  }
}
