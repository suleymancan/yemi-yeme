# yemi-yeme
Clickbait detection extension for Turkish news on social media.

### Makine Öğrenmesi Modelinin Tanıtılması
İlk olarak pom.xml dosyasına spark-mllib_2.11 bağımlılığı eklenir. <br>
Apache Spark kullanılarak üretilen [model](https://github.com/suleymancan/yemi-yeme/tree/master/src/main/resources/static/model/prizma/YemiYemeModel), klasör yapısında kaydedilmektedir. Bu klasör yapısında Apache Spark'ın ihtiyaç duyduğu bilgiler yer almaktadır. Modeli kullanmak için yapılması gereken, klasör yolunu parametre olarak verilmesidir. 
application.yml konfigurasyon dosyasında, app.model-path keyi ile modelin bulunduğu klasör pathi belirtilmiştir. </br>
Spark Context ve model [SparkConfig](https://github.com/suleymancan/yemi-yeme/blob/master/src/main/java/com/suleymancanblog/yemiyeme/config/SparkConfig.java) sınıfında oluşturulmuştur.

### Servis Katmanı
[ClickbaitService](https://github.com/suleymancan/yemi-yeme/blob/master/src/main/java/com/suleymancanblog/yemiyeme/clickbait/ClickbaitService.java) haber başlığından özellik çıkarımı ve makine öğrenmesi modelini kullanarak gelen haber başlığının sınıfını tahmin etme işlerini yapmaktadır. <br/>
Gelen haber başlığından özellik çıkarımı işlemi, [prizma](https://github.com/suleymancan/yemi-yeme/tree/master/src/main/java/com/suleymancanblog/yemiyeme/prizma) paketinde, [Prizma](https://code.google.com/archive/p/prizma-text-classification/) programının kaynak kodları aracılığıyla yapılmaktadır. Ayırt edici olarak tespit edilen özellikler, [PrizmaFeature](https://github.com/suleymancan/yemi-yeme/blob/master/src/main/java/com/suleymancanblog/yemiyeme/clickbait/PrizmaFeature.java) sınıfı kullanılarak sarmalanmıştır. Sınıf etiketleri enum[https://github.com/suleymancan/yemi-yeme/blob/master/src/main/java/com/suleymancanblog/yemiyeme/clickbait/NewsLabel.java] olarak tutulmaktadır.


### Controller Katmanı
[ClickbaitRestController](https://github.com/suleymancan/yemi-yeme/blob/master/src/main/java/com/suleymancanblog/yemiyeme/clickbait/ClickbaitRestController.java) temel olarak şu işleri yapmaktadır:
Haber başlığını parametre olarak al.<br/>
Haber başlığının özelliklerini çıkar.<br/>
Modele sorgu gerçekleştir.<br/>
Sorgu sonucunu dön. (CLICKBAIT / NOT CLICKBAIT)<br/>
```java
  @CrossOrigin
	@GetMapping
	public String test(@RequestParam String source) {
		final PrizmaFeature prizmaFeature = clickbaitService.createNewsFeature(source);
		final NewsLabel predict = clickbaitService.predict(prizmaFeature);
		return predict.getNewsLabel();
	}
```
