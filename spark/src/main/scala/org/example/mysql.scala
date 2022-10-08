package org.example

import java.util.Properties

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object mysql {
  //写，将数据写入到数据库
  def saveToMysql(table:String,df:DataFrame):Unit={
    val props=new Properties();
    props.setProperty("user","root")
    props.setProperty("password","root")
    val url="jdbc:mysql://192.168.60.128:3306/db_spark?useSSL=false&serverTimezone=UTC"
    df.write.mode(SaveMode.Append).jdbc(url,table,props)
  }
  def readToMysql(table:String,spark:SparkSession):DataFrame={
    val props=new Properties()
    props.setProperty("user","root")
    props.setProperty("password","root")
    val url="jdbc:mysql://192.168.60.128:3306/db_spark?useSSL=false&serverTimezone=UTC"
    val df=spark.read.jdbc(url,table,props)
    return  df
  }

}
