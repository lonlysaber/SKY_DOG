package org.example

import java.util.Properties

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}



object read {

  def readToMysql(table: String, spark: SparkSession): DataFrame = {
    val props = new Properties()
    props.setProperty("user", "root")
    props.setProperty("password", "root")
    val url = "jdbc:mysql://127.0.0.1/sparksqltest?useSSL=false&serverTimezone=UTC"
    val df = spark.read.jdbc(url, table, props)
    return df
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.WARN)
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Dateset")
    val sc = new SparkContext(sparkConf)
    //创建sparkSession
    val spark = SparkSession.builder().config(sparkConf).getOrCreate();

    val df = readToMysql("data", spark)
    df.createOrReplaceTempView("temp")
    val clickcategoryIDDS=spark.sql(
      """
        |select name ,sum(count) from temp
        |group by name
        |order by sum(count) desc
        |""".stripMargin)
    clickcategoryIDDS.show()













  }
}
