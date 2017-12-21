#### 作者序：

本章乃由安卓巴士业余博主bear&&fightingMarmot合作编撰而成。坐标千年名城——姑苏。
此二人皆乃蓬勃朝气有理想、热血豪迈志比天、即将在除夕夜街头放飞梦想的有志青年，因此本文值得各位看官一阅，有钱捧个钱场，没钱捧个人场撒？

常言道，一重山水一重天，山重水重天外天，纷繁馥郁尘嚣上，我自归去笔耕田。人间四月繁花灿，隔山重水在眼前，相逢何必曾相识，聊以片语慰生平。人来人往，复又去来兮，相逢若是有缘，何若留存一眼面缘，点个赞？

#### 本文序：
本文阐述了一款名为“巴士天气”的入门级Android-APP的开发全程。本文中所述的APP旨在用Kotlin语言为Android初级学习者提供一次完整的APP开发实践。
在APP设计过程中作者融合了Activity的生命周期、信息传递、数据库、网络连接、接口调用、自定义数据结构、UI设计、Service后台服务、SharePreferences共享参数、引入第三方开源库等多方面知识，同时采取简洁开发策略，使用尽可能简单的UI界面来涵盖更多的内容，做到使APP在满足开发简洁的同时包含了更多元的知识。
经过前面的学习，相信大家对Android有了更加深入地了解，所谓学以致用，接下来我们将进入实战的课程。

完成一个完整的应用（Application，后面简称App），要明白做的是什么？需要什么？如何去做？Android知识广博我们不可能在短短的时间去掌握所有的知识，所以我们要根据自己所需，分析每个功能需要什么资源，用什么方式去写，然后带着问题回头去学习所需的知识最终解决问题。本章将以“巴士天气”为例，教会大家创建Android应用。

12.1 功能需求及技术可行性分析

在开始编码之前，我们需要先对App进行需求分析，想一想一款天气预报工具应该具备哪些功能。将这些功能全部整理出来之后，我们才好动手去一一实现。这里我整理了一下，巴士天气中至少应该具备以下功能：

可以罗列出全国所有的省、市县
可以查看全国任意城市的天气信息
可以自由切换城市，去查看其他城市的天气
后台自动更新
可以应用到多媒体功能
虽然看上去只有5个主要的功能点，但如果想要全部实现这些功能却需要用到UI、网络、数据存储、服务，多媒体等技术，因此还是非常考验你的综合应用能力的。不过好在这些技术在前面的章节中我们我们全部都学习过了，只要你学得用心，相信完成这些功能对你来说并不难。

分析完了需求之后，接下来就要进行技术可行性分析了。首先要考虑的问题就是全国的各省市，以及对应的天气数据源。正好安卓巴士有合作方提供了相关的Api可以满足。Mob.com到官网可以看到有两个Api刚好满足我们的需求：

根据城市名查询天气（http://apicloud.mob.com/v1/weather/query）
请求方式：GET

请求参数：

名称	类型	必填	说明
key	string	是	用户申请的appkey（可以直接用网站的测试AppKey-520520test）
city	string	是	城市名
province	string	是	当前城市的所属省市 如：北京-北京-通州、江苏-苏州-吴中（url编码）
Json返回实例：

{
  "msg": "success",
  "result": [
    {
      "airCondition": "良",
      "city": "北京",
      "coldIndex": "低发期",
      "updateTime": "20150908153820",
      "date": "2015-09-08",
      "distrct": "门头沟",
      "dressingIndex": "短袖类",
      "exerciseIndex": "适宜",
      "future": [
        {
          "date": "2015-09-09",
          "dayTime": "阵雨",
          "night": "阴",
          "temperature": "24°C/18°C",
          "week": "星期三",
          "wind": "无持续风向小于3级"
        },
        {
          "date": "2015-09-10",
          "dayTime": "阵雨",
          "night": "阵雨",
          "temperature": "22°C/15°C",
          "week": "星期四",
          "wind": "无持续风向小于3级"
        },
        {
          "date": "2015-09-11",
          "dayTime": "阴",
          "night": "晴",
          "temperature": "23°C/15°C",
          "week": "星期五",
          "wind": "北风3～4级无持续风向小于3级"
        },
        {
          "date": "2015-09-12",
          "dayTime": "晴",
          "night": "晴",
          "temperature": "26°C/13°C",
          "week": "星期六",
          "wind": "北风3～4级无持续风向小于3级"
        },
        {
          "date": "2015-09-13",
          "dayTime": "晴",
          "night": "晴",
          "temperature": "27°C/16°C",
          "week": "星期日",
          "wind": "无持续风向小于3级"
        },
        {
          "date": "2015-09-14",
          "dayTime": "晴",
          "night": "多云",
          "temperature": "27°C/16°C",
          "week": "星期一",
          "wind": "无持续风向小于3级"
        },
        {
          "date": "2015-09-15",
          "dayTime": "少云",
          "night": "晴",
          "temperature": "26°C/14°C",
          "week": "星期二",
          "wind": "南风3级南风2级"
        },
        {
          "date": "2015-09-16",
          "dayTime": "局部多云",
          "night": "少云",
          "temperature": "26°C/15°C",
          "week": "星期三",
          "wind": "南风3级南风2级"
        },
        {
          "date": "2015-09-17",
          "dayTime": "阴天",
          "night": "局部多云",
          "temperature": "26°C/15°C",
          "week": "星期四",
          "wind": "东南风2级"
        }
      ],
      "humidity": "湿度：46%",
      "province": "北京",
      "sunset": "18:37",
      "sunrise": "05:49",
      "temperature": "25℃",
      "time": "14:35",
      "washIndex": "不适宜",
      "weather": "多云",
      "week": "周二",
      "wind": "南风2级"
    }
  ],
  "retCode": "200"
}
城市列表查询接口（http://apicloud.mob.com/v1/weather/citys）
请求方式：GET

请求参数：

名称	类型	必填	说明
key	string	是	用户申请的appkey
（可以直接用网站的测试AppKey-520520test）

返回Json示例：

{
  "msg": "success",
  "result": [
    {
      "province":"安徽",
      "city": [
        {
          "city": "合肥",
          "district": [
            {
              "district": "合肥"
            },
            {
              "district": "长丰"
            },
            {
              "district": "肥东"
            },
            {
              "district": "肥西"
            },
            {
              "district": "巢湖"
            },
            {
              "district": "庐江"
            }
          ]
        },
        {
          "city": "蚌埠",
          "district": [
            {
              "district": "蚌埠"
            },
            {
              "district": "怀远"
            },
            {
              "district": "固镇"
            },
            {
              "district": "五河"
            }
          ]
        },
        {
          "city": "芜湖",
          "district": [
            {
              "district": "芜湖"
            },
            {
              "district": "繁昌"
            },
            {
              "district": "芜湖县"
            },
            {
              "district": "南陵"
            },
            {
              "district": "无为"
            }
          ]
        },
        {
          "city": "淮南",
          "district": [
            {
              "district": "淮南"
            },
            {
              "district": "凤台"
            },
            {
              "district": "潘集"
            }
          ]
        },
        {
          "city": "马鞍山",
          "district": [
            {
              "district": "马鞍山"
            },
            {
              "district": "当涂"
            },
            {
              "district": "含山"
            },
            {
              "district": "和县"
            }
          ]
        }
      ]
    }
  ]
}
数据源已经有了，下面整理了需要用到的知识点。

网络请求-HttpURLConnection
数据存储-SharedPreference,GreenDao
后台更新-Service
多媒体播放-MediaPlayer
UI-SurfaceView,三级联动布局
以上知识点如果大家还不熟悉的话，可以跳到前面的章节。
从本节开始，我们就要真正地动手编码了。
### AndroidStudio项目搭建
#### 1. 项目结构树
合理的项目结构也非常重要，这里需要在com.apkbus.weather包下面再建几个包。如下图：
![image.png](http://upload-images.jianshu.io/upload_images/4469838-9053518dde48c738.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- activity：存放应用所有的Activity
- api：存放网络请求相关的类
- base：存放Application，以及BaseActivity等父类
- db：存放数据库相关的表模型以及工具类
- entry：存放数据模型
- fragment：存放fragment
- service：存放service
- sharedPreference:存放sharedPreference的相关key模型
- utils：存放工具类
- weheel——widget:三级联动控件
#### 2. Gradle配置
项目父级`build.gradle`的设置如下：

```
buildscript {
    ext.kotlin_version = '1.1.51'
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.1'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

```
内部`build.gradle`的设置如下：

```
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'org.greenrobot.greendao'

android {
    compileSdkVersion 26
    buildToolsVersion '26.0.2'
    defaultConfig {
        applicationId "com.apkbus.weather"
        minSdkVersion 19
        targetSdkVersion 26
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        greendao {
            schemaVersion 1
//            这个地方是自动生成的配置文件存放在哪个位置的
            targetGenDir 'src/main/java'
        }
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    implementation 'com.android.support:appcompat-v7:26.1.0'
    implementation 'com.android.support:design:26.1.0'
    implementation 'com.android.support.constraint:constraint-layout:1.0.2'
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jre7:$kotlin_version"
    implementation 'org.greenrobot:greendao:3.2.2'
    compile 'net.zetetic:android-database-sqlcipher:3.5.1@aar'
    compile 'com.google.code.gson:gson:2.8.1'
    compile 'com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.31'
}
```
以上分别是配置GreenDao，Kotlin，Gjson以及recycleView的第三方adapter帮助库
### 数据库
#### 1. 建表
回顾前面的Api文档，可以看到省市区的结构，为查询效率我们可以建三张表。在db的包下面再新建个包`bean`如下：


![image.png](http://upload-images.jianshu.io/upload_images/4469838-10ea7fe5962d77c6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 省：

```
@Entity(indexes = arrayOf(Index(value = "provinceName")))
class ProvinceBean {
    @Id(autoincrement = true)
    var id: Long = 0
    var provinceName: String? = null
    var level = LEVEL_PROVINCE

    @Generated(hash = 1999810692)
    constructor(id: Long, provinceName: String, level: Int) {
        this.id = id
        this.provinceName = provinceName
        this.level = level
    }

    @Generated(hash = 1410713511)
    constructor()
}
```
- 市

```
@Entity(indexes = arrayOf(Index(value = "cityName"), Index(value = "provinceName")))
class CityBean {
    @Id(autoincrement = true)
    var id: Long = 0
    var cityName: String? = null
    var provinceName: String? = null
    var level = LEVEL_CITY

    @Generated(hash = 83209856)
    constructor(id: Long, cityName: String, provinceName: String, level: Int) {
        this.id = id
        this.cityName = cityName
        this.provinceName = provinceName
        this.level = level
    }

    @Generated(hash = 273649691)
    constructor()
}
```
- 区

```
@Entity(indexes = arrayOf(Index(value = "cityName"), Index(value = "districtName")))
class DistrictBean {
    @Id(autoincrement = true)
    var id: Long = 0
    var cityName: String? = ""
    var districtName: String? = ""
    var level = LEVEL_DISTRICT

    @Generated(hash = 903270775)
    constructor(id: Long, cityName: String, districtName: String, level: Int) {
        this.id = id
        this.cityName = cityName
        this.districtName = districtName
        this.level = level
    }

    @Generated(hash = 326445391)
    constructor()
}
```
用level来区分省市区，方便后面根据level来查对应的数据表：

```
val LEVEL_PROVINCE = 0//省 直辖市 自治区
val LEVEL_CITY = 1//市
val LEVEL_DISTRICT = 2//区县
```
#### 2. 初始化GreenDao以及编写数据库帮助类
GreenDao初始化，在db包下面新建`MyOpenHelper`类配置GreenDao的属性：

```
class MyOpenHelper : DaoMaster.DevOpenHelper {
    constructor(context: Context, name: String) : super(context, name)

    constructor(context: Context, name: String, factory: SQLiteDatabase.CursorFactory) : super(context, name, factory)

    override fun onUpgrade(db: Database?, oldVersion: Int, newVersion: Int) {
        /*此处不用super，因为父类中包含了
       dropAllTables(db, true);
        onCreate(db);
        需要自己定制升级
        */
        //       MigrationHelper.getInstance().dropAndCreate(db);
    }
}
```
为使GreenDao为单例，需要在Application中声明，所以我们需要先自定义Applation。在base包下面新建`MyApplication`

```
class MyApplication : Application() {
    var daoSession: DaoSession? = null

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        val help = MyOpenHelper(this, if (ENCRYPTED) "bear-db-encrypted" else "bear-db")
        val db = if (ENCRYPTED) help.getEncryptedWritableDb("admin") else help.writableDb
        val master = DaoMaster(db)
        prnt("当前数据库版本号-->" + master.schemaVersion)
        daoSession = master.newSession()
    }

    companion object {
        lateinit var INSTANCE: MyApplication
        val ENCRYPTED = true
    }
}
```

然后在`AndroidManifest.xml`的`<application>`节点下加入或者修改
```
 android:name=".base.MyApplication"
```
build一下程序，使得GreenDao插件生成模版代码。build完毕后项目结构树如下：
![image.png](http://upload-images.jianshu.io/upload_images/4469838-d4bd8be501b942b3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

因为三张表只会进行插入操作，所有我们需要把ProvinceBeanDao，CityBeanDao，DistrictBeanDao中的hasKey方法返回为false

```
 @Override
    public boolean hasKey(ProvinceBean entity) {
      return false;
    }

```
这样数据库表以及GreenDao框架就初始化完毕了。
### 1. 网络层搭建
网路请求有`GET`和`POST`两种方式，本节采用`HttpURLConnection`来搭建网络层，然后用`Gson`进行数据解析。

为方便使用我们需要对其进行简单的封装。前面的学习我们知道网络请求需要新建线程，一般返回的结果会影响UI线程（主线程），所以我们先要定义一个`Callback`接口在收到结果后传回主线程。

下面新建两个类如图：

![image.png](http://upload-images.jianshu.io/upload_images/4469838-2eb473ff2873362d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- ApiCallBack：回调接口
- ApiHelper：封装网络请求

具体代码如下：

ApiCallBack：
```
interface ApiCallBack {
    //请求成功，result为json字符串
    fun onSuccess(result: String)
    //请求失败，msg为错误信息，可以自定义
    fun onError(msg: String)
}
```
ApiHelper

```
class ApiHelper {
    companion object {
        //前面Api文档中的测试使用的key
        var KEY = "520520test"
        val BASE_URL = "http://apicloud.mob.com/v1"

        fun get(mActivity: Activity?, method: String, params: HashMap<String, String>, callBack: ApiCallBack?) {
            Thread(Runnable {
                try {
                    val tempParams = StringBuilder()
                    var pos = 0
                    for (key in params.keys) {
                        if (pos > 0) {
                            tempParams.append("&")
                        }
                        tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params[key], "utf-8")))
                        pos++
                    }
                    val requestUrl = BASE_URL + method + "?" + tempParams.toString()
                    //新建一个URL对象
                    val url = URL(requestUrl)
                    //打开链接
                    val urlConn = url.openConnection() as HttpURLConnection
                    //设置链接主机超时时间
                    urlConn.connectTimeout = 5 * 1000
                    //设置从主机读取数据超时时间
                    urlConn.readTimeout = 5 * 1000
                    //设置是否使用缓存
                    urlConn.useCaches = true
                    //设置请求方式为get
                    urlConn.requestMethod = "GET"
                    //urlConn设置请求头信息
                    //设置请求中的媒体类型信息。
                    urlConn.setRequestProperty("Content-Type", "application/json")
                    //设置客户端与服务连接类型
                    urlConn.addRequestProperty("Connection", "Keep-Alive")
                    // 开始连接
                    urlConn.connect()
                    if (urlConn.responseCode == HttpsURLConnection.HTTP_OK) {
                        //获取返回数据
                        val result = streamToString(urlConn.inputStream)
                        if (mActivity == null) {
                            callBack?.onSuccess(result)
                        } else if (!TextUtils.isEmpty(result) && !mActivity.isFinishing) {
                            mActivity.runOnUiThread {
                                if (callBack != null && !TextUtils.isEmpty(result))
                                    callBack.onSuccess(result)
                            }
                        }
                    } else {
                        if (mActivity == null) {
                            callBack?.onError("返回码：" + urlConn.responseCode)
                        } else if (!mActivity.isFinishing) {
                            mActivity.runOnUiThread {
                                callBack?.onError("返回码：" + urlConn.responseCode)
                            }
                        }
                    }
                    urlConn.disconnect()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }).start()
        }

        fun post(url: String, paramsMap: HashMap<String, String>, callBack: ApiCallBack?) {
            Thread(Runnable {
                try {
                    val baseUrl = BASE_URL + url
                    //合成参数
                    val tempParams = StringBuilder()
                    var pos = 0
                    for (key in paramsMap.keys) {
                        if (pos > 0) {
                            tempParams.append("&")
                        }
                        tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap[key], "utf-8")))
                        pos++
                    }
                    val params = tempParams.toString()
                    // 请求的参数转换为byte数组
                    val postData = params.toByteArray()
                    // 新建一个URL对象
                    val url1 = URL(baseUrl)
                    // 打开一个HttpURLConnection连接
                    val urlConn = url1.openConnection() as HttpURLConnection
                    // 设置连接超时时间
                    urlConn.connectTimeout = 5 * 1000
                    //设置从主机读取数据超时
                    urlConn.readTimeout = 5 * 1000
                    // Post请求必须设置允许输出 默认false
                    urlConn.doOutput = true
                    //设置请求允许输入 默认是true
                    urlConn.doInput = true
                    // Post请求不能使用缓存
                    urlConn.useCaches = false
                    // 设置为Post请求
                    urlConn.requestMethod = "POST"
                    //设置本次连接是否自动处理重定向
                    urlConn.instanceFollowRedirects = true
                    // 配置请求Content-Type
                    urlConn.setRequestProperty("Content-Type", "application/json")
                    // 开始连接
                    urlConn.connect()
                    // 发送请求参数
                    val dos = DataOutputStream(urlConn.outputStream)
                    dos.write(postData)
                    dos.flush()
                    dos.close()
                    // 判断请求是否成功
                    if (urlConn.responseCode == HttpURLConnection.HTTP_OK) {
                        // 获取返回的数据
                        val result = streamToString(urlConn.inputStream)
                        if (!TextUtils.isEmpty(result)) {
                            callBack?.onSuccess(result)
                        }
                    } else {
                        callBack?.onError("返回码：" + urlConn.responseCode)
                    }
                    // 关闭连接
                    urlConn.disconnect()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }).start()
        }

        /**
         * 将输入流转换成字符串
         *
         * @param is 从网络获取的输入流
         */
        private fun streamToString(ios: InputStream): String {
            return try {
                val baos = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var len = ios.read(buffer)
                while (len != -1) {
                    baos.write(buffer, 0, len)
                    len = ios.read(buffer)
                }
                baos.close()
                ios.close()
                val byteArray = baos.toByteArray()
                String(byteArray)
            } catch (e: Exception) {
                "Exception"
            }
        }


        /**
         * 获取天气详情
         */
        fun getWeatherDetail(activity: Activity?, province: String?, city: String?, callBack: ApiCallBack?) {
            var params = HashMap<String, String>()
            params.put("key", "520520test")
            params.put("province", province!!)
            params.put("city", city!!)
            get(activity, "/weather/query", params, callBack)
        }
    }
}
```

如上代码，封装了`get`和`post`两种请求方式，另外可以看到需要传递`activity`参数，这个是为了在服务器返回数据后借助Activity的`runOnUiThread`方法把结果通过`callback`传递到UI主线程中。当然，如果不需要传回UI主线程中去，`activity`可以传null。

网络请求返回的结果是二进制流，需要转换为字符串，如上述方法`streamToString(ios: InputStream)`。

最后的`getWeatherDetail(activity: Activity?, province: String?, city: String?, callBack: ApiCallBack?)`方法就是后面要用到的获取天气详情的接口。

### 2. 数据解析
现有与服务器传输的数据格式一般有`xml`，`json`两种。因为`xml`的读取速度缓慢且比较耗内存，特别是涉及到大量数据时。目前大部分应用都采用json的格式来传输数据。

`json`解析可以采用Android自带`org`包下面的`JsonObject`解析。或者使用Google提供的`Gson`来解析。下面举例说明两种方式的用法：


```
    //解析message字段
        //1.采用JsonObject解析
        var jsonStr = "{\"message\":\"this is a message\"}"
        try {
            var jsonObject = JSONObject(jsonStr)
            var message = jsonObject.getString("message")
        } catch (e : JSONException) {
            e.printStackTrace()
        }
        
        
        //2.采用Gson解析，需要先声明一个与Json字符串对应的实体类，通过反射的机制解析为对象，通过句柄获取对应的字段值。
        //声明实体类
     class Result {
        var message : String?= null
        }
        //解析过程
        var result = Gson().fromJson(jsonStr, Result::class.java)
        if (result != null) {
            var message = result.message
        }
    
```
从上面简单的例子可以看出，JsonObject的优势是不用把所有的字段都解析出来可以直接解析需要的字段，不需要建立多余的Model。但是如果结构比较复杂就会写非常多的代码，且不好维护。Gson的优点是代码量少，效率非常高且有现成的插件可以一键生成Model。

> 可以在Android Studio中plugin搜索JsonFormat插件，安装重启后可以使用快捷键`alt`+`s`（windows）， `command`+`s`(mac os)命令呼出操作界面，将对应的Json字符串放进去就可以自动生成Json model。

下面介绍本项目中Gson的用法。

### 3. 工具类封装
工具类的包结构树如下：

![image.png](http://upload-images.jianshu.io/upload_images/4469838-aa239975caf2a454.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- GsonUtils：用于Json解析

根据前面的Api文档，可以看到服务器返回的结果是Json字符串，这里我们使用Gson来进行解析，同样的也对其进行简单的封装：

```
object GsonUtils {
    /**
     * 将json字符串转化为一个对象
     *
     * @param json     ：json字符串
     * @param classOfT ：对象的Class
     * @param <T>      要转化的对象
     * @return null 或者 一个T类型的对象
    </T> */
    fun <T> jsonToClass(json: String, classOfT: Class<T>): T? {
        var t: T? = null
        try {
            t = Gson().fromJson(json, classOfT)
        } catch (e: Exception) {
            println("json to class【" + classOfT + "】 解析失败  " + e.message)
        }
        return t
    }

    /**
     * 将json字符串转化为一个对象列表
     *
     * @param json    ：json字符串
     * @param typeOfT ：对象列表的 type
     * @param <T>     要转化的对象
     * @return null 或者 一个对象列表
    </T> */
    fun <T> jsonToList(json: String, typeOfT: Type): List<T>? {
        var items: List<T>? = null
        if (!TextUtils.isEmpty(json)) {
            try {
                items = Gson().fromJson<List<T>>(json, typeOfT)
            } catch (e: Exception) {
                println("json to list 解析失败  " + e.message)
            }

        }
        return items
    }

    fun <T> jsonToList(json: String): List<T>? {
        var items: List<T>? = null
        if (!TextUtils.isEmpty(json)) {
            try {
                items = Gson().fromJson<List<T>>(json, object : TypeToken<List<T>>() {

                }.type)
            } catch (e: Exception) {
                println("json to list 解析失败  " + e.message)
            }
        }
        return items
    }


    /**
     * 将一个对象转化成json字符串
     *
     * @param object
     * @return
     */
    fun toJson(`object`: Any): String {
        var jsonStr = ""
        try {
            jsonStr = Gson().toJson(`object`)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return jsonStr
    }
}
```
- CityUtils：用于获取省市区数据并存在数据库中，另外提供数据库查询方法

为了节省用户的流量，全国各省市区的数据只需要获取一遍即可。根据不同的level分别把省市区存入三张表中，并且体提供关键字查询方法，具体代码如下：
```
object CityUtils {
    val LEVEL_PROVINCE = 0//省 直辖市 自治区
    val LEVEL_CITY = 1//市
    val LEVEL_DISTRICT = 2//区县
    //从服务器中获取全国个省市区的数据
    fun getAllCities(activity: Activity) {
        //为节省流量，此接口只使用一次，当本地数据库中存在省市区数据时，不进行网络请求
        if (MyApplication.INSTANCE.daoSession!!.provinceBeanDao.queryBuilder().count() > 0) {
            return
        }
        val params = HashMap<String, String>()
        params.put("key", ApiHelper.KEY)
        ApiHelper.get(activity, "/weather/citys", params, object : ApiCallBack {
            override fun onSuccess(result: String) {
                //利用Gson解析返回结果
                val bean = GsonUtils.jsonToClass(result, CityResult::class.java)
                var provinceId=0
                var cityId=0
                var districtId=0
                val pList=ArrayList<ProvinceBean>()
                val cList=ArrayList<CityBean>()
                val dList=ArrayList<DistrictBean>()
                if (bean?.result != null && bean.result!!.isNotEmpty()) {
                    for (resultBean in bean.result!!) {
                        val province = ProvinceBean()
                        province.provinceName = resultBean.province
                        province.id= provinceId.toLong()
                        pList.add(province)
                        provinceId++
                        if (resultBean.city != null && resultBean.city!!.isNotEmpty()) {
                            for (cityBean in resultBean.city!!) {
                                val city = CityBean()
                                city.cityName = cityBean.city
                                city.provinceName = resultBean.province
                                city.id=cityId.toLong()
                                cList.add(city)

                                cityId++
                                if (cityBean.district != null && cityBean.district!!.isNotEmpty()) {
                                    for (districtBean in cityBean.district!!) {
                                        val district = DistrictBean()
                                        district.cityName = cityBean.city
                                        district.districtName = districtBean.district
                                        district.id=districtId.toLong()
                                        dList.add(district)
                                        districtId++
                                    }
                                }
                            }
                        }
                    }
                    //存入数据库
                    MyApplication.INSTANCE.daoSession?.provinceBeanDao?.saveInTx(pList)
                    MyApplication.INSTANCE.daoSession?.cityBeanDao?.saveInTx(cList)
                    MyApplication.INSTANCE.daoSession?.districtBeanDao?.saveInTx(dList)
                }
            }

            override fun onError(msg: String) {}
        })
    }
    //根据省市区的levle以及关键字key来分别查询对应的数据列表
    fun <T> getListData(key: String, level: Int): ArrayList<T> {
        val list = ArrayList<T>()
        when (level) {
            LEVEL_PROVINCE -> {
                val province = MyApplication.INSTANCE.daoSession?.provinceBeanDao?.loadAll()
                list.addAll(province as Collection<T>)
            }
            LEVEL_CITY -> {
                val city = MyApplication.INSTANCE.daoSession?.cityBeanDao
                        ?.queryBuilder()?.where(CityBeanDao.Properties.ProvinceName.eq(key))?.build()?.list()
                list.addAll(city as Collection<T>)
            }
            LEVEL_DISTRICT -> {
                val district = MyApplication.INSTANCE.daoSession?.districtBeanDao
                        ?.queryBuilder()?.where(DistrictBeanDao.Properties.CityName.eq(key))?.build()?.list()
                list.addAll(district as Collection<T>)
            }
        }
        return list
    }
}
```
- Utils：其它工具类整合

一些常用的工具类，目前先仅放了`showToast`,后面可以自行扩展

```
fun showToast(context: BaseActivity?, msg:String){
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
}
```
- SharedPreferenceUtils:用于统一管理应用中用到的`SharedPreference`，后面用到时会接着介绍。

前面几节框架搭建完后，从本节开始就要着手画界面啦。没错要开始使用Android 的核心组件`Activity`。

开始写本节的视频启动页之前，需要先写`BaseActivity`去配置应用所有`Activity`的通用属性,方法等。后面的所有的`Activity`都必须继承`BaseActivity`。

在base包下面，新建`BaseActivity`
,代码如下：

```
abstract class BaseActivity:AppCompatActivity(){
     lateinit var mActivity:BaseActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity=this
    }
}
```
目前，仅声明了一个通用的成员变量`mActivity`,后续如需要还可自行扩展。

编写完`BaseActivity`后，现在开始编写视频启动页。

视频启动页的交互：
1. 开启应用后全屏自动播放视频
2. 能够直接跳过视频直接进入App主界面
3. 视频播放完自动进入App主界面

明确交互之后，先把视频资源放到资源文件中，将视频命名为`loading.mp4`，放到`src`的`raw`文件中，如果没有就需要新建`raw`文件。如下图：
![image.png](http://upload-images.jianshu.io/upload_images/4469838-ec31c3cdacbae7f0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

然后在activity的包下新建`SplashActivity`

```
class SplashActivity : BaseActivity(), SurfaceHolder.Callback {

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        try {
            media = MediaPlayer.create(mActivity, R.raw.loading)
            media?.setDisplay(p0)
            media?.start()
            media?.setOnCompletionListener { btn.performClick() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private var media: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)
        btn.setOnClickListener({
           //跳转至主界面 MainActivity.action(mActivity, "苏州", "江苏")
            finish()
        })

    }
    //activity销毁前，释放媒体资源
    override fun onDestroy() {
        super.onDestroy()
        media?.release()
        media = null
    }

}
```

布局文件`activity_splash.xml`

```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <Button
        android:id="@+id/btn"
        android:layout_gravity="right"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="跳过"/>
    <SurfaceView
        android:id="@+id/surfaceView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</FrameLayout>

```

`SplashActivity`需要在`AndroidManifest.xml`的`<application>`节点下声明，且设置为启动页。


```
<activity
        android:name=".activity.SplashActivity"
            android:label="@string/app_name"
            android:theme="@style/Theme.AppCompat.NoActionBar">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
</activity>
```

### 12.5.1 设计概述
#### 1）结构设计概述：
本APP旨在教学，设计初衷是方便学习分享，故UI部分仅设计了：

①两个交互活动（Activity），涉及了Activity的生命周期、信息传递、跳转等知识点；

②一个碎片控件（Fragment），涉及了Fragment的生命周期、调用接口取值赋值等知识点；

UI中用到了如TextView、Button、RecyclerView和WheelView。前二者是Android原生的，RecyclerView则是官方在Support-v7支持库中补充的，最后者是我们遴选第三方库选用的一款轻量控件，适于研究学习。

为RecyclerView赋值，我们用到了BaseQuickAdapter，这是一款为自定义循环布局赋值的简洁而高效的适配器，我们将在项目中简单介绍其使用方法。
#### 2）界面设计介绍：
开屏动画结束后主线程来到了MainActivity，如下两张图是其布局图和效果图：

![image.png](http://upload-images.jianshu.io/upload_images/4990900-2c486e653cf94ee2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](http://upload-images.jianshu.io/upload_images/4990900-6ab1cab2a72538d9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
（布局详见activity_main.xml）

MainActivity绑定的布局中只包含了名为“container”的全屏线性父布局（布局图中白色部分），更改背景图、加载显示天气的碎片布局均是在“container”上实现的。

CityWeatherFragment继承了父布局的全部显示区，并囊括了所有最上层子控件，其布局如下：

![image.png](http://upload-images.jianshu.io/upload_images/4990900-3b166fa8c450231b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

（布局详见fragment_city_weather.xml）

点击用于显示地区的TextView控件会跳转到用于选择地址的活动（ChooseLocationActivity），其布局图和效果图如下：

![image.png](http://upload-images.jianshu.io/upload_images/4990900-feb2488ac05110ea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](http://upload-images.jianshu.io/upload_images/4990900-4c5ae9319ed08e63.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

（布局详见activity_choose_location.xml）

该activity使用本例中的核心控件——WheelView实现了省-市-县区三级联动地址选择，并在选择结束后跳转回MainActivity时将选择的地址组合同时返回。
### 12.5.2 实现过程
①建立MainActivity.kt，并在代码中绑定布局activity_main.xml

②建立CityWeatherFragment.kt，以及其布局fragment_city_weather.xml

③用activity_main中的container布局控件加载CityWeatherFragment类的实例

④实现CityWeatherFragment类中的事物逻辑，添加对地址选择页面的跳转方法

⑤建立ChooseLocationActivity.kt，并绑定布局activity_choose_location.xml

以往大多文字教程都冗杂且枯燥，故笔者在此不再卖弄笔墨絮叨太多，具体代码释义见码中注释，就是这样，喵。
### 12.5.3 细节处理
①文案细节：未来数日天气预报显示文案以拉取到的条数为准；地区选择结果判断是否为三级地址后再行显示；对每一条要显示的数据都做判空显示默认文案处理，若全为空则显示样式如下：

![image.png](http://upload-images.jianshu.io/upload_images/4990900-664b0bd5960b43cb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这样即使哪一个环节出现差错传丢了地址值，也会避免因为产生Kotlin的NullPointerException而导致程序闪退（不要以为Kotlin加入了空安全就不会空闪，图样图森破T_T）。

②设计细节：Activity基类增加了一层第三方SwipeBackActivity类继承，它实现了滑动退出功能，使得所有继承了本例基类Activity的类都多了一层可滑退操作，可左右下滑动退出也可控制关闭（因为第三方类内容不是很多，所以直接粘到了library目录下，相应的资源文件也一并粘贴到了res文件夹下的相对位置）。
### 12.5.4 注意事项
①新建Android组件实现要在AndroidManifast.xml文件中先行注册才能使用，不然会闪，如Activity、Service；

②RecyclerView要想显示内容还要在添加LayoutManager，有时候辛辛苦苦地写好了适配器类却忘记指给RecyclerView，这些都是非常2的问题；

③接口返回字符串转换自定义数据类时保证字段名称、元素结构要完全一致，至少是只多不少，否则不会什么也抓不到；

④用系统FragmentManager将建立的CityWeatherFragment对象添加到界面上的过程中要注意对于上传结果的上传（commot()方法）。


如何实现后台自动刷新数据呢？

首先分析一下，既然是后台刷新那就需要用到Android的`Service`组件，自动刷新则需要定时器`AlermManager`来指定时间去启动`service`任务。自动获取的数据如何保存呢？这里我们使用`SharedPreference`来存储返回数据。

### 1. SharedPreferenceUtils
前面在utils包下面有提到这个工具类，这里先看代码：

```
/**
 * 获取保存天气的sp
 * key值见{@link com.apkbus.weather.sharedPreference.WeatherSpKey}
 */
fun getWeatherDataSp():SharedPreferences{
    return MyApplication.INSTANCE.getSharedPreferences("weatherData", Activity.MODE_PRIVATE)
}
```
这个类的主要作用是罗列出应用中所有的`sharedpreference`。为了方便维护，给每一个`sharedpreference`生成一个key Object。这里`WeatherDataSp`中缓存的是当前用户选择的省，区内容，以及后台service自动拉去的天气数据。

在sharedPreference包下面，新建`WeatherSpKey`如下：

```
class WeatherSpKey{
    companion object {
        /**
         * 获取天气后的json字符串
         * 【value String】
         */
         var data:String?=null

        /**
         * 当前选择的省份
         * 【value String】
         */
        var  provinceName="provinceName"
        /**
         * 当前选择的城市
         * 【value String】
         */
        var  cityName="cityName"
    }

}
```

### 2. UpdateService
下面开始编写后台服务，这里我们使用`service`的子类`IntentService`。在service包下面新建`UpdateService`如下：

```
class UpdateService : IntentService("updateWeather") {

    override fun onHandleIntent(p0: Intent?) {
        val sp = getWeatherDataSp()
        var pName: String = sp.getString(WeatherSpKey.provinceName, "北京")
        var cName: String = sp.getString(WeatherSpKey.cityName, "北京")

        ApiHelper.getWeatherDetail(null, pName, cName, object : ApiCallBack {
            override fun onSuccess(result: String) {
                getWeatherDataSp().edit().putString(WeatherSpKey.data, result).apply()
            }

            override fun onError(msg: String) {
            }

        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //设置定时器
        var manager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //调用间隔时8小时
        val refreshTime = 8 * 60 * 60 * 1000

        var triggerAtTime = SystemClock.elapsedRealtime() + refreshTime
        val intent = Intent(this, UpdateService::class.java)
        var pi = PendingIntent.getService(this, 0, intent, 0)
        manager.cancel(pi)
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi)
        println("调用时间：" + System.currentTimeMillis().toString())
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
    //启动service的静态方法
        fun startUpdateService(activity: Activity) {
            var intent = Intent(activity, UpdateService::class.java)
            activity.startService(intent)
        }
    }

}
```
在service调用时先走的是`onStartCommand`方法，所以在这里我们设置定时器，为减少用户流量的损耗，不能频发的后台刷新数据，所以这里设置了间隔时间为8小时。每8小时会启动service任务，此时具体的任务在`onHandleIntent`方法中实现，我们需要借助`SharedPreferenceUtils`拿到保存天气数据的`sharedPreference`,然后取出里面缓存的当前用户选择的省，区，然后调用api包下面的`ApiHelper`中的`getWeatherDetail`的方法，正如前面章节介绍，此处会将数据存储在sharedPreference中，不涉及到UI界面，所以这个地方不需要传入`activity`参数进入主线程。


定时拉到数据后，主页面优先显示缓存中的数据。这样就完成了后台自动刷新数据的功能了。当然还差一步就是启动找个服务，这里我们在MainActivity的`onCreate`方法中启动service，调用：

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         ······
        UpdateService.startUpdateService(mActivity)
    }
```

### 12.7.1 项目小结
本项目旨在用Kotlin语言为Android学习者提供一次完整的APP开发实践，在本例中我们结合了Activity的生命周期、信息传递、数据库、网络连接、调用接口、自定义数据结构、UI设计、Service后台服务、SharePreferences应用共享参数、引入第三方开源库等多方面知识，尽量使得APP在满足简洁设计的同时能包含多元的知识点。

但由于开发者能力有限，所带来的知识想必难以面面俱到，敬请各位谅解与支持。
### 12.7.2 可扩展性说明
虽然“巴士天气”的实现达到了预期的教学目的，但作为一款产品还远不够优秀，在此提供以下优化思路，供使用者进阶学习：

①开屏动画可设计成只显示一次，或每日首次打开APP时显示；

②界面背景可设计成联网获取以及定时更换、手动更换等；

③主页面可同时包含多个地区的天气，并实现添加、删除等功能（详见小米天气）；

④优化转轮View，将之设计成循环显示样式；

⑤将界面设计成你喜欢的样子。

大功告成，学会如何制作一款简单的APP之后，又要如何将其打包并上传到安卓应用市场呢？请阅读后续章节的内容。























