package org.example

  import org.apache.spark.{SparkConf, SparkContext}
  import org.apache.spark.rdd.RDD

  object Demo2 {
    def main(args: Array[String]): Unit = {

      //1、实例化conf对象以及创建sc对象
      val conf = new SparkConf().setMaster("local").setAppName(Demo2.getClass.getSimpleName)
      val sc = new SparkContext(conf)

      //2、读取文件
      val fileRDD: RDD[String] = sc.textFile("F:\\数据\\agent.log")

      //3、切分
      val mapRDD: RDD[(String, String)] = fileRDD.map(x => {
        val strings: Array[String] = x.split(" ")
        (strings(1), strings(4))
      })

      //4、根据省份进行分组(province,List(add1,add2,...))
      val groupRDD: RDD[(String, Iterable[String])] = mapRDD.groupByKey()

      //5、处理List
      val result: RDD[(String, List[(String, Int)])] = groupRDD.map(x => {
        //将广告拼1，并分组取出大小
        val stringToInt: Map[String, Int] = x._2.map((_, 1)).groupBy(_._1).map(y => {
          val size: Int = y._2.toList.size
          (y._1, size)
        })

        //根据广告数量降序排序并取出前三
        val tuples: List[(String, Int)] = stringToInt.toList.sortBy(-_._2).take(3)
        (x._1, tuples)
      })

      //6、打印
      result.foreach(println)

      sc.stop()

    }



}
