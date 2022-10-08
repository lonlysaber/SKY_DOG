package org.example

  import org.apache.spark.rdd.RDD
  import org.apache.spark.{SparkConf, SparkContext}

  object Demo1 {

    def main(args: Array[String]): Unit = {

      //1、实例化conf对象以及创建sc对象
      val conf = new SparkConf().setMaster("local").setAppName(Demo1.getClass.getSimpleName)
      val sc = new SparkContext(conf)

      //2、读取文件
      val fileRDD: RDD[String] = sc.textFile("F:\\数据\\agent.log")

      //3、切分、拼1
      val toOneRDD: RDD[((String, String), Int)] = fileRDD.map { x =>
        val strings: Array[String] = x.split(" ")
        ((strings(1), strings(4)), 1)
      }

      //4、聚合((province,add),sum)
      val sumRDD: RDD[((String, String), Int)] = toOneRDD.reduceByKey(_ + _)

      //5、将省份作为key，广告加点击数为value：(province,(add,sum))
      val mapRDD: RDD[(String, (String, Int))] = sumRDD.map(x => (x._1._1, (x._1._2, x._2)))

      //6、将同一个省份的所有广告进行分组聚合(province,List((add1,sum1),(add2,sum2)...))
      val groupRDD: RDD[(String, Iterable[(String, Int)])] = mapRDD.groupByKey()

      //7、对同一个省份所有广告的集合进行排序并取前3条
      val sortRDD: RDD[(String, List[(String, Int)])] = groupRDD.mapValues { x =>
        x.toList.sortWith((x, y) => x._2 > y._2).take(3)
      }

      //8、将数据拉取到Driver端并打印
      sortRDD.collect().foreach(println)

      sc.stop()
    }
  }



