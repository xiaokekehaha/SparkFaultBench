package sql

import org.apache.spark.sql.SparkSession
import sql.BaseUtils._
/**
  * Created by lenovo on 2016/8/23 0023.
  */

object Scan {


  def main(args: Array[String]): Unit = {

    val warehouseLocation = System.getProperty("user.dir")
    val spark = SparkSession.builder()
      .appName("Scan")
      .config("spark.some.config.option", "some-value")
      .config("spark.sql.warehouse.dir",warehouseLocation)
      .master("local[2]").getOrCreate()
    doScanSQL(spark)
  }
  private def doScanSQL(spark:SparkSession):Unit={
    import spark.implicits._
    val rankingsDF = getRankingsDF(spark)

    rankingsDF.createOrReplaceTempView("rankings")
    val scanDF = spark.sql("SELECT * From rankings where pagerank > 20")
    scanDF.show()

    val uservisitsDF =getUservisitsDF(spark)

    uservisitsDF.createOrReplaceTempView("uservisits")
    val scanDF2 = spark.sql("SELECT * from uservisits")
    scanDF2.show()
  }

}
