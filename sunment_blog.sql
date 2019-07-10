/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50149
 Source Host           : localhost:3306
 Source Schema         : sunment_blog

 Target Server Type    : MySQL
 Target Server Version : 50149
 File Encoding         : 65001

 Date: 14/05/2019 16:34:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for archives
-- ----------------------------
DROP TABLE IF EXISTS `archives`;
CREATE TABLE `archives`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `archive_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '归档日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of archives
-- ----------------------------
INSERT INTO `archives` VALUES (1, '2019年03月');
INSERT INTO `archives` VALUES (2, '2019年04月');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章作者',
  `original_author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章原作者',
  `article_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `article_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章内容',
  `article_tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标签',
  `article_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章类型',
  `article_categories` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章分类',
  `article_abstract` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章摘要',
  `article_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原文链接  转载：则是转载的链接  原创：则是在本博客中的链接',
  `article_likes` int(11) NOT NULL COMMENT '文章点赞数',
  `publish_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布文章时间',
  `update_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章最后一次修改时间',
  `last_article_id` bigint(20) NULL DEFAULT NULL COMMENT '上一篇文章id',
  `next_article_id` bigint(20) NULL DEFAULT NULL COMMENT '下一篇文章id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (11, 1552901948, 'Joey', 'Joey', 'idea整合springboot多个服务，实现增删改查', '创建parent项目\n1. 打开IDEA，注意这里不要勾选模板，用模板创建过maven项目的小伙伴都知道模板创建项目非常慢，所以这里不要选模板，需要的文件夹我们后面自己来创建就可以了。所以这个页面直接点击下一步。\n\n\n\n\n\n\n可以把父工程的src文件夹删除\n\n\n父工程microservicecloud的pom文件:\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n     xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n<modelVersion>4.0.0</modelVersion>\n\n<groupId>com.atguigu.springcloud</groupId>\n<artifactId>microservicecloud</artifactId>\n<version>1.0-SNAPSHOT</version>\n<modules>\n    <module>microservicecloudapi</module>\n</modules>\n<packaging>pom</packaging>\n\n<properties>\n    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n    <maven.compiler.source>1.8</maven.compiler.source>\n    <maven.compiler.target>1.8</maven.compiler.target>\n    <junit.version>4.12</junit.version>\n    <log4j.version>1.2.17</log4j.version>\n    <lombok.version>1.16.18</lombok.version>\n</properties>\n\n<dependencyManagement>\n    <dependencies>\n        <dependency>\n            <groupId>mysql</groupId>\n            <artifactId>mysql-connector-java</artifactId>\n            <version>5.0.4</version>\n        </dependency>\n        <dependency>\n            <groupId>org.springframework.cloud</groupId>\n            <artifactId>spring-cloud-dependencies</artifactId>\n            <version>Dalston.SR1</version>\n            <type>pom</type>\n            <scope>import</scope>\n        </dependency>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-dependencies</artifactId>\n            <version>1.5.9.RELEASE</version>\n            <type>pom</type>\n            <scope>import</scope>\n        </dependency>\n\n        <dependency>\n            <groupId>com.alibaba</groupId>\n            <artifactId>druid</artifactId>\n            <version>1.0.31</version>\n        </dependency>\n\n        <dependency>\n            <groupId>org.mybatis.spring.boot</groupId>\n            <artifactId>mybatis-spring-boot-starter</artifactId>\n            <version>1.3.0</version>\n        </dependency>\n\n        <dependency>\n            <groupId>ch.qos.logback</groupId>\n            <artifactId>logback-core</artifactId>\n            <version>1.2.3</version>\n        </dependency>\n        <dependency>\n            <groupId>junit</groupId>\n            <artifactId>junit</artifactId>\n            <version>${junit.version}</version>\n            <scope>test</scope>\n        </dependency>\n\n        <dependency>\n            <groupId>log4j</groupId>\n            <artifactId>log4j</artifactId>\n            <version>${log4j.version}</version>\n        </dependency>\n\n    </dependencies>\n</dependencyManagement>\n\n</project>\n2. 创建子工程：\n\n\n\n\n\n\n子工程microservicecloud的pom文件：\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n     xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n<parent>\n    <artifactId>microservicecloud</artifactId>\n    <groupId>com.atguigu.springcloud</groupId>\n    <version>1.0-SNAPSHOT</version>\n</parent>\n<modelVersion>4.0.0</modelVersion>\n\n<artifactId>microservicecloud-api</artifactId>\n\n<dependencies>\n    <dependency>\n        <groupId>org.projectlombok</groupId>\n        <artifactId>lombok</artifactId>\n    </dependency>\n</dependencies>\n3. 创建子工程实体类：\n\n\npackage com.atguigu.springcloud.entities;\n\nimport lombok.Data;\nimport lombok.NoArgsConstructor;\nimport lombok.experimental.Accessors;\n\nimport java.io.Serializable;\n\n@SuppressWarnings(\"serial\")\n@NoArgsConstructor\n@Data\n@Accessors(chain=true)\npublic class Dept implements Serializable {\nprivate Long deptno; //主键\nprivate String dname; //部门名称\nprivate String db_source;//来自那个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库\n\npublic Dept(String dname) {\n    super();\n    this.dname = dname;\n}\n\n\n}\n4. 然后Install工程：\n\n\n5. 创建服务提供者microservicecloud-provider-dept-8001子项目：\n\n\nmicroservicecloud-provider-dept-8001子项目pom文件:\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n     xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n<parent>\n    <artifactId>microservicecloud</artifactId>\n    <groupId>com.atguigu.springcloud</groupId>\n    <version>1.0-SNAPSHOT</version>\n</parent>\n<modelVersion>4.0.0</modelVersion>\n<artifactId>microservicecloud-provider-dept-8001</artifactId>\n<dependencies>\n    <dependency>\n        <groupId>com.atguigu.springcloud</groupId>\n        <artifactId>microservicecloud-api</artifactId>\n        <version>${project.version}</version>\n    </dependency>\n    <dependency>\n        <groupId>junit</groupId>\n        <artifactId>junit</artifactId>\n    </dependency>\n    <dependency>\n        <groupId>mysql</groupId>\n        <artifactId>mysql-connector-java</artifactId>\n    </dependency>\n    <dependency>\n        <groupId>com.alibaba</groupId>\n        <artifactId>druid</artifactId>\n    </dependency>\n    <dependency>\n        <groupId>ch.qos.logback</groupId>\n        <artifactId>logback-core</artifactId>\n    </dependency>\n    <dependency>\n        <groupId>org.mybatis.spring.boot</groupId>\n        <artifactId>mybatis-spring-boot-starter</artifactId>\n    </dependency>\n    <dependency>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-jetty</artifactId>\n    </dependency>\n    <dependency>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-web</artifactId>\n    </dependency>\n    <dependency>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-test</artifactId>\n    </dependency>\n    <dependency>\n        <groupId>org.springframework</groupId>\n        <artifactId>springloaded</artifactId>\n    </dependency>\n    <dependency>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-devtools</artifactId>\n    </dependency>\n</dependencies>\n</project>\nmicroservicecloud-provider-dept-8001子项目application.yml文件:\n\n\nspring:\napplication:\n name: microservicecloud-dept\ndatasource:\n  type: com.alibaba.druid.pool.DruidDataSource\n  url: jdbc:mysql://localhost:3306/cloudDB01\n  username: root\n  password: 123456\n  dbcp2:\n    min-idle: 5\n    initial-size: 5\n    max-total: 5\n    max-wait-millis: 200\nserver:\n  port: 8081\nmybatis:\n  config-location: classpath:mybatis/mybatis.cfg.xml\n  type-aliases-package: com.atguigu.springcloud.entities\n  mapper-locations:\n  - classpath:mybatis/mapper/**/*.xml\n6. 创建clouddb01的mysql数据库\n\n\nCreate Table\n\nCREATE TABLE `dept` (\n  `deptno` bigint(20) NOT NULL AUTO_INCREMENT,\n  `dname` varchar(60) DEFAULT NULL,\n  `db_source` varchar(60) DEFAULT NULL,\n  PRIMARY KEY (`deptno`)\n) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8\n然后插入信息。\n7. microservicecloud-provider-dept-8001子项目创建mybaties文件夹再创建mybatis.cfg.xml文件\n?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE configuration\n        PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n        \"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n<configuration>\n    <settings>\n        <setting name=\"cacheEnabled\" value=\"true\"></setting><!-- 二级缓存开启 -->\n    </settings>\n</configuration>\nmicroservicecloud-provider-dept-8001子项目DeptDao文件\npackage com.atguigu.springcloud.dao;\n\nimport com.atguigu.springcloud.entities.Dept;\nimport org.apache.ibatis.annotations.Mapper;\n\nimport java.util.List;\n\n@Mapper\npublic interface DeptDao {\npublic boolean addDept(Dept dept);\n\n\npublic Dept findById(Long id);\n\n\npublic List<Dept> findAll();\n}\n8. microservicecloud-provider-dept-8001子项目创建mapper文件夹下面的DeptMapper.xml文件:\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n        <!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n<mapper namespace=\"com.atguigu.springcloud.dao.DeptDao\">\n\n<select id=\"findById\" resultType=\"Dept\" parameterType=\"Long\">\n    SELECT deptno,dname,db_source FROM dept WHERE deptno=#{deptno};\n</select>\n<select id=\"findAll\" resultType=\"Dept\">\n    SELECT deptno,dname,db_source FROM dept;\n</select>\n<insert id=\"addDept\" parameterType=\"Dept\">\n    INSERT INTO dept(dname,db_source) VALUES(#{dname},DATABASE());\n</insert>\n</mapper>\n9. microservicecloud-provider-dept-8001子项目创建service文件夹下面的DeptServiceImpl文件和它的实现DeptService\npackage com.atguigu.springcloud.service;\n\nimport com.atguigu.springcloud.entities.Dept;\n\nimport java.util.List;\n\npublic interface DeptService {\n    public boolean add(Dept dept);\n\n    public Dept get(Long id);\n\n    public List<Dept> list();\n}\nDeptServiceImpl\nimport org.springframework.stereotype.Service;\n\nimport java.util.List;\n\n@Service\npublic class DeptServiceImpl implements DeptService {\n\n    @Autowired\n    private DeptDao dao;\n\n    @Override\n    public boolean add(Dept dept) {\n        return dao.addDept(dept);\n    }\n\n    @Override\n    public Dept get(Long id) {\n        return dao.findById(id);\n    }\n\n    @Override\n    public List<Dept> list() {\n        return dao.findAll();\n    }\n}\n10. microservicecloud-provider-dept-8001子项目创建Controller文件夹下面的DeptController\npackage com.atguigu.springcloud.controller;\n\nimport com.atguigu.springcloud.entities.Dept;\nimport com.atguigu.springcloud.service.DeptService;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.*;\n\nimport java.util.List;\n\n@RestController\npublic class DeptController {\n    @Autowired\n    private DeptService service;\n\n    @RequestMapping(value = \"/dept/add\" ,method= RequestMethod.POST)\n    public boolean add(@RequestBody Dept dept) {\n        return service.add(dept);\n    }\n\n    @RequestMapping(value = \"/dept/get/{id}\" ,method= RequestMethod.GET)\n    public Dept get(@PathVariable(\"id\") Long id) {\n        return service.get(id);\n    }\n\n    @RequestMapping(value = \"/dept/list\" ,method= RequestMethod.POST)\n    public List<Dept> list() {\n        return service.list();\n    }\n}\n11. microservicecloud-provider-dept-8001子项目创建主启动类\npackage com.atguigu.springcloud;\n\nimport org.springframework.boot.SpringApplication;\nimport org.springframework.boot.autoconfigure.SpringBootApplication;\n@SpringBootApplication\npublic class DeptProvider8001_App {\n\n\n    public static void main(String[] args) {\n        SpringApplication.run(DeptProvider8001_App.class,args);\n    }\n}\n跑http://localhost:8081/dept/list这个路径,成功访问\n\n\n新建一个项目对外暴露提供服务microservicecloud-consumer-dept-80(参考第一个子工程的建立)\n\n\n服务消费者microservicecloud-consumer-dept-80的pom文件\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n    <parent>\n        <artifactId>microservicecloud</artifactId>\n        <groupId>com.atguigu.springcloud</groupId>\n        <version>1.0-SNAPSHOT</version>\n    </parent>\n    <modelVersion>4.0.0</modelVersion>\n\n    <artifactId>microservicecloud-consumer-dept-80</artifactId>\n    <description>部门微服务消费者</description>\n    <dependencies>\n        <dependency><!-- 自己定义的api -->\n            <groupId>com.atguigu.springcloud</groupId>\n            <artifactId>microservicecloud-api</artifactId>\n            <version>${project.version}</version>\n        </dependency>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-web</artifactId>\n        </dependency><!-- 修改后立即生效，热部署 -->\n        <dependency>\n            <groupId>org.springframework</groupId>\n            <artifactId>springloaded</artifactId>\n        </dependency>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-devtools</artifactId>\n        </dependency>\n    </dependencies>\n</project>\n创建服务消费者microservicecloud-consumer-dept-80的application.yml文件(只需要端口号就行)\n\n\n服务消费者microservicecloud-consumer-dept-80的com.atguigu.springcloud.cfgbeans包下ConfigBean的编写（类似spring里面的applicationContext.xml写入的注入Bean）\npackage com.atguigu.springcloud.cfgbeans;\n\nimport org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;\nimport org.springframework.web.client.RestTemplate;\n\n@Configuration\npublic class ConfigBean {\n    /*@Configuration配置 Configuration =applicationContext.xml\n    * @Bean\n    * public UserService getUserService()\n       {\n           return new UserServiceImpl()\n            }\n<bean id=\"userService\" class=\"com...UserServiceImpl\"\n@Configuration(ConfigBean) = applicationContext.xml\n    * */\n    @Bean\n    public RestTemplate getRestTemplate() {\n        return new RestTemplate();\n    }\n\n}\n服务消费者microservicecloud-consumer-dept-80的com.atguigu.springcloud.controller的DeptController_Consumer代码\npackage com.atguigu.springcloud.controller;\n\nimport com.atguigu.springcloud.entities.Dept;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.web.bind.annotation.PathVariable;\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\nimport org.springframework.web.client.RestTemplate;\n\nimport java.util.List;\n/*使用\n        使用restTemplate访问restful接口非常的简单粗暴无脑。\n        (url, requestMap, ResponseBean.class)这三个参数分别代表 \n        REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。*/\n@RestController\npublic class DeptController_Consumer {\n    private static final String REST_URL_PREFIX = \"http://localhost:8001\";\n\n    @Autowired\n    private RestTemplate restTemplate;\n\n    @RequestMapping(value = \"/consumer/dept/add\")\n    public boolean add(Dept dept) {\n        return restTemplate.postForObject(REST_URL_PREFIX + \"/dept/add\", dept, Boolean.class);\n    }\n\n    @RequestMapping(value = \"/consumer/dept/get/{id}\")\n    public Dept get(@PathVariable(\"id\") Long id) {\n        return restTemplate.getForObject(REST_URL_PREFIX + \"/dept/get/\" + id, Dept.class);\n    }\n\n    @SuppressWarnings(\"unchecked\")\n    @RequestMapping(value = \"/consumer/dept/list\")\n    public List<Dept> list() {\n        return restTemplate.getForObject(REST_URL_PREFIX + \"/dept/list\", List.class);\n    }\n}\n服务消费者microservicecloud-consumer-dept-80的启动类\n\n\npackage com.atguigu.springcloud;\n\nimport org.springframework.boot.SpringApplication;\nimport org.springframework.boot.autoconfigure.SpringBootApplication;\n\n@SpringBootApplication\npublic class DeptConsumer80_App {\n    public static void main(String[] args) {\n        SpringApplication.run(DeptConsumer80_App.class,args);\n    }\n}\n跑http://localhost/consumer/dept/list这个路径,成功访问\n\n\n', 'idea,springboot,原创', '原创', 'springboot', '创建parent项目打开IDEA，注意这里不要勾选模板，用模板创建过maven项目的小伙伴都知道模板创建项目非常慢，所以这里不要选模板，需要的文件夹我们后面自己来创建就可以了。所以这个页面直接点击下一步。可以把父工程的src文件夹删除父工程microservicecloud的pom文件:&lt;?xmlversion=”1.0”encoding=”UTF-8”?&gt;4.0.0com.atguigu.springcloud...', 'http://localhost:8080/jump/findArticle?articleId=1552901948&originalAuthor=Joey', 0, '2019-03-18', '2019-03-18', 0, 1552902135);
INSERT INTO `article` VALUES (12, 1552902135, 'Joey', 'Joey', '时间轮java实现', '时间轮java实现\n一、java调度方法：\n前言\n在开发高性能服务器中，定时器总是不可或缺的。 常见的定时器实现三种，分别是：排序链表，最小堆，时间轮。 之前用的定时器是基于最小堆的，如果程序中的定时器数量比较少，基于最小堆的定时器一般可以满足需求，且实现简单。\n\nTimer，ScheduledExecutorService\n时间复杂度 O(log(n)) 因为它们使用的 是 最小堆的对排序，每当有新任务的时候都需要堆堆进行插入， 堆排序插入的时间复杂度为 O(log(n))\n\nTimer 的问题：\n1、 如果任务执行时间过长，TimerTask会出现延迟执行的情况。比如，第一任务在1000ms执行了4000ms，第二个任务定时在2000ms开始执行。这里由于第一个任务要执行4000，所以第二个任务实际在5000ms开始执行。这是由于Timer是单线程，且顺序执行提交的任务\n\n\n\n2、 如果执行任务抛出异常，Timer是不会执行后面的任务的\n\n\n\nScheduledExecutorService 解决了这些问题\n任务一在1000ms执行，4000ms后结束。任务二在2000ms执行，4000ms后结束，任务二不会等任务一执行完成后执行，抛出异常也会执行任务二\n\n\n\njava调度算法时间复杂度\n\n实现方式	加入任务	取消任务	运行任务\n基于排序链表	O(n)	O(1)	O(1)\n基于最小堆	O(lgn)	O(1)	O(1)\n二、时间轮调度算法：\n比java调度算法更高效的算法，时间复杂度为O(1)\n1、如果执行任务抛出异常，会执行后面的任务的\n\n\n\n2、1s执行任务一，2s执行任务二\n\n\n\n3、1s执行多个任务\n\n\n\n算法对比\n实现方式	加入任务	取消任务	运行任务\n基于排序链表	O(n)	O(1)	O(1)\n基于最小堆	O(lgn)	O(1)	O(1)\n基于时间轮	O(1)	O(1)	O(1)\n简单时间轮算法原理：\n\n\n项目下载地址：\n\nhttps://download.csdn.net/download/a1229451567/10970874', '时间轮,原创', '原创', 'java', '时间轮java实现一、java调度方法：前言在开发高性能服务器中，定时器总是不可或缺的。常见的定时器实现三种，分别是：排序链表，最小堆，时间轮。之前用的定时器是基于最小堆的，如果程序中的定时器数量比较少，基于最小堆的定时器一般可以满足需求，且实现简单。Timer，ScheduledExecutorService时间复杂度O(log(n))因为它们使用的是最小堆的对排序，每当有新任务的时候都需要堆堆进行插入，堆排序插入的时间复杂度为O(log(n))...', 'http://localhost:8080/jump/findArticle?articleId=1552902135&originalAuthor=Joey', 0, '2019-03-18', '2019-03-18', 1552901948, 1552902210);
INSERT INTO `article` VALUES (13, 1552902210, 'Joey', 'Joey', 'kafka介绍', '  kafka介绍\n\n \n\n1.1.       主要功能\n\n根据官网的介绍，ApacheKafka®是一个分布式流媒体平台，它主要有3种功能：\n\n1：It lets you publish and subscribe to streams of records.发布和订阅消息流，这个功能类似于消息队列，这也是kafka归类为消息队列框架的原因\n\n2：It lets you store streams of records in a fault-tolerant way.以容错的方式记录消息流，kafka以文件的方式来存储消息流\n\n3：It lets you process streams of records as they occur.可以再消息发布的时候进行处理\n\n \n\n \n\n1.2.       使用场景\n\n1：Building real-time streaming data pipelines that reliably get data between systems or applications.在系统或应用程序之间构建可靠的用于传输实时数据的管道，消息队列功能\n\n2：Building real-time streaming applications that transform or react to the streams of data。构建实时的流数据处理程序来变换或处理数据流，数据处理功能\n\n \n\n1.3.       详细介绍\n\nKafka目前主要作为一个分布式的发布订阅式的消息系统使用，下面简单介绍一下kafka的基本机制\n\n1.3.1 消息传输流程\n\n\n\n \n\nProducer即生产者，向Kafka集群发送消息，在发送消息之前，会对消息进行分类，即Topic，上图展示了两个producer发送了分类为topic1的消息，另外一个发送了topic2的消息。\n\nTopic即主题，通过对消息指定主题可以将消息分类，消费者可以只关注自己需要的Topic中的消息\n\nConsumer即消费者，消费者通过与kafka集群建立长连接的方式，不断地从集群中拉取消息，然后可以对这些消息进行处理。\n\n　　　　从上图中就可以看出同一个Topic下的消费者和生产者的数量并不是对应的。\n\n1.3.2 kafka服务器消息存储策略\n\n\n\n　　　　谈到kafka的存储，就不得不提到分区，即partitions，创建一个topic时，同时可以指定分区数目，分区数越多，其吞吐量也越大，但是需要的资源也越多，同时也会导致更高的不可用性，kafka在接收到生产者发送的消息之后，会根据均衡策略将消息存储到不同的分区中。\n\n\n\n　　在每个分区中，消息以顺序存储，最晚接收的的消息会最后被消费。\n\n1.3.3 与生产者的交互\n\n \n\n\n　　　　生产者在向kafka集群发送消息的时候，可以通过指定分区来发送到指定的分区中\n\n　　　　也可以通过指定均衡策略来将消息发送到不同的分区中\n\n　　　　如果不指定，就会采用默认的随机均衡策略，将消息随机的存储到不同的分区中\n\n1.3.4  与消费者的交互\n\n\n\n　　　　在消费者消费消息时，kafka使用offset来记录当前消费的位置\n\n　　　　在kafka的设计中，可以有多个不同的group来同时消费同一个topic下的消息，如图，我们有两个不同的group同时消费，他们的的消费的记录位置offset各不项目，不互相干扰，所以一个分区可以给在不同组中的一个消费者消费。\n\n　　　　对于一个group而言，消费者的数量不应该多余分区的数量，因为在一个group中，每个分区至多只能绑定到一个消费者上，即一个消费者可以消费多个分区，一个分区只能给一个消费者消费\n\n　　　　因此，若一个group中的消费者数量大于分区数量的话，多余的消费者将不会收到任何消息。\n\n2.       Kafka安装与使用\n\n \n\n2.1.       下载\n\n　　你可以在kafka官网 http://kafka.apache.org/downloads下载到最新的kafka安装包，选择下载二进制版本的tgz文件，根据网络状态可能需要fq，这里我们选择的版本是0.11.0.1，目前的最新版\n\n \n\n2.2.       安装\n\nKafka是使用scala编写的运行与jvm虚拟机上的程序，虽然也可以在windows上使用，但是kafka基本上是运行在linux服务器上，因此我们这里也使用linux来开始今天的实战。\n\n　　首先确保你的机器上安装了jdk，kafka需要java运行环境，以前的kafka还需要zookeeper，新版的kafka已经内置了一个zookeeper环境，所以我们可以直接使用\n\n　　说是安装，如果只需要进行最简单的尝试的话我们只需要解压到任意目录即可，这里我们将kafka压缩包解压到/home目录\n\n \n\n2.3.       配置\n\n　　在kafka解压目录下下有一个config的文件夹，里面放置的是我们的配置文件\n\nconsumer.properites 消费者配置，这个配置文件用于配置于2.5节中开启的消费者，此处我们使用默认的即可\n\nproducer.properties 生产者配置，这个配置文件用于配置于2.5节中开启的生产者，此处我们使用默认的即可\n\nserver.properties kafka服务器的配置，此配置文件用来配置kafka服务器，目前仅介绍几个最基础的配置\n\n申明当前kafka服务器在集群中的唯一ID，需配置为integer,并且集群中的每一个kafka服务器的id都应是唯一的，我们这里采用默认配置即可\n申明此kafka服务器需要监听的端口号，如果是在本机上跑虚拟机运行可以不用配置本项，默认会使用localhost的地址，如果是在远程服务器上运行则必须配置，例如：\nlisteners=PLAINTEXT:// 192.168.180.128:9092。并确保服务器的9092端口能够访问\n\n3.zookeeper.connect 申明kafka所连接的zookeeper的地址 ，需配置为zookeeper的地址，由于本次使用的是kafka高版本中自带zookeeper，使用默认配置即可\n\nzookeeper.connect=localhost:2181\n\n2.4.       运行\n\nzookeeper\ncd进入kafka解压目录，输入\n\nbin/zookeeper-server-start.sh config/zookeeper.properties\n\n启动zookeeper成功后会看到如下的输出\n\n\n\n2.启动kafka\n\ncd进入kafka解压目录，输入\n\nbin/kafka-server-start.sh config/server.properties\n\n启动kafka成功后会看到如下的输出\n\n \n\n\n2.5.       第一个消息\n\n2.5.1   创建一个topic\n\nKafka通过topic对同一类的数据进行管理，同一类的数据使用同一个topic可以在处理数据时更加的便捷\n\n　　　　在kafka解压目录打开终端，输入\n\nbin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test\n\n　　　　创建一个名为test的topic\n\n \n\n        　在创建topic后可以通过输入\n\n            bin/kafka-topics.sh --list --zookeeper localhost:2181\n\n   来查看已经创建的topic\n\n2.4.2   创建一个消息消费者\n\n　　　在kafka解压目录打开终端，输入\n\nbin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning\n\n　　　可以创建一个用于消费topic为test的消费者\n\n \n\n\n\n \n\n         消费者创建完成之后，因为还没有发送任何数据，因此这里在执行后没有打印出任何数据\n\n         不过别着急，不要关闭这个终端，打开一个新的终端，接下来我们创建第一个消息生产者\n\n2.4.3         创建一个消息生产者\n\n　　　　在kafka解压目录打开一个新的终端，输入\n\nbin/kafka-console-producer.sh --broker-list localhost:9092 --topic test\n\n　　　　在执行完毕后会进入的编辑器页面\n\n\n\n \n\n在发送完消息之后，可以回到我们的消息消费者终端中，可以看到，终端中已经打印出了我们刚才发送的消息\n\n\n\n \n\n3.       使用java程序\n\n　　　　跟上节中一样，我们现在在java程序中尝试使用kafka\n\n3.1  创建Topic\n\npublic static void main(String[] args) {\n\n    //创建topic\n\n    Properties props = new Properties();\n\n    props.put(\"bootstrap.servers\", \"192.168.180.128:9092\");\n\n    AdminClient adminClient = AdminClient.create(props);\n\n    ArrayList<NewTopic> topics = new ArrayList<NewTopic>();\n\n    NewTopic newTopic = new NewTopic(\"topic-test\", 1, (short) 1);\n\n    topics.add(newTopic);\n\n    CreateTopicsResult result = adminClient.createTopics(topics);\n\n    try {\n\n        result.all().get();\n\n    } catch (InterruptedException e) {\n\n        e.printStackTrace();\n\n    } catch (ExecutionException e) {\n\n        e.printStackTrace();\n\n    }\n\n}\n\n　　使用AdminClient API可以来控制对kafka服务器进行配置，我们这里使用NewTopic(String name, int numPartitions, short 　　replicationFactor)的构造方法来创建了一个名为“topic-test”，分区数为1，复制因子为1的Topic.\n\n3.2  Producer生产者发送消息\n\npublic static void main(String[] args){\n\n    Properties props = new Properties();\n\n    props.put(\"bootstrap.servers\", \"192.168.180.128:9092\");\n\n    props.put(\"acks\", \"all\");\n\n    props.put(\"retries\", 0);\n\n    props.put(\"batch.size\", 16384);\n\n    props.put(\"linger.ms\", 1);\n\n    props.put(\"buffer.memory\", 33554432);\n\n    props.put(\"key.serializer\", \"org.apache.kafka.common.serialization.StringSerializer\");\n\n    props.put(\"value.serializer\", \"org.apache.kafka.common.serialization.StringSerializer\");\n\n\n    Producer<String, String> producer = new KafkaProducer<String, String>(props);\n\n    for (int i = 0; i < 100; i++)\n\n        producer.send(new ProducerRecord<String, String>(\"topic-test\", Integer.toString(i), Integer.toString(i)));\n\n\n    producer.close();\n\n\n}\n\n使用producer发送完消息可以通过2.5中提到的服务器端消费者监听到消息。也可以使用接下来介绍的java消费者程序来消费消息\n\n3.3 Consumer消费者消费消息\n\npublic static void main(String[] args){\n\n    Properties props = new Properties();\n\n    props.put(\"bootstrap.servers\", \"192.168.12.65:9092\");\n\n    props.put(\"group.id\", \"test\");\n\n    props.put(\"enable.auto.commit\", \"true\");\n\n    props.put(\"auto.commit.interval.ms\", \"1000\");\n\n    props.put(\"key.deserializer\", \"org.apache.kafka.common.serialization.StringDeserializer\");\n\n    props.put(\"value.deserializer\", \"org.apache.kafka.common.serialization.StringDeserializer\");\n\n    final KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);\n\n    consumer.subscribe(Arrays.asList(\"topic-test\"),new ConsumerRebalanceListener() {\n\n        public void onPartitionsRevoked(Collection<TopicPartition> collection) {\n\n        }\n\n        public void onPartitionsAssigned(Collection<TopicPartition> collection) {\n\n            //将偏移设置到最开始\n\n            consumer.seekToBeginning(collection);\n\n        }\n\n    });\n\n    while (true) {\n\n        ConsumerRecords<String, String> records = consumer.poll(100);\n\n        for (ConsumerRecord<String, String> record : records)\n\n            System.out.printf(\"offset = %d, key = %s, value = %s%n\", record.offset(), record.key(), record.value());\n\n    }\n\n}\n\n这里我们使用Consume API 来创建了一个普通的java消费者程序来监听名为“topic-test”的Topic，每当有生产者向kafka服务器发送消息，我们的消费者就能收到发送的消息。\n\n4.       使用spring-kafka\n\nSpring-kafka是正处于孵化阶段的一个spring子项目，能够使用spring的特性来让我们更方便的使用kafka\n\n4.1   基本配置信息\n\n与其他spring的项目一样，总是离不开配置，这里我们使用java配置来配置我们的kafka消费者和生产者。\n\npom文件\n<!--kafka start-->\n\n<dependency>\n\n    <groupId>org.apache.kafka</groupId>\n\n    <artifactId>kafka-clients</artifactId>\n\n    <version>0.11.0.1</version>\n\n</dependency>\n\n<dependency>\n\n    <groupId>org.apache.kafka</groupId>\n\n    <artifactId>kafka-streams</artifactId>\n\n    <version>0.11.0.1</version>\n\n</dependency>\n\n<dependency>\n\n    <groupId>org.springframework.kafka</groupId>\n\n    <artifactId>spring-kafka</artifactId>\n\n    <version>1.3.0.RELEASE</version>\n\n</dependency>\n\n我们在主目录下新建名为KafkaConfig的类\n\n@Configuration\n\n@EnableKafka\n\npublic class KafkaConfig {\n\n\n}\n\nTopic\n在kafkaConfig类中添加配置\n\n//topic config Topic的配置开始\n\n    @Bean\n\n    public KafkaAdmin admin() {\n\n        Map<String, Object> configs = new HashMap<String, Object>();\n\n        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,\"192.168.180.128:9092\");\n\n        return new KafkaAdmin(configs);\n\n    }\n\n\n    @Bean\n\n    public NewTopic topic1() {\n\n        return new NewTopic(\"foo\", 10, (short) 2);\n\n    }\n\n//topic的配置结束\n\n \n\nFactort及Template\n//producer config start\n\n    @Bean\n\n    public ProducerFactory<Integer, String> producerFactory() {\n\n        return new DefaultKafkaProducerFactory<Integer,String>(producerConfigs());\n\n    }\n\n    @Bean\n\n    public Map<String, Object> producerConfigs() {\n\n        Map<String, Object> props = new HashMap<String,Object>();\n\n        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, \"192.168.180.128:9092\");\n\n        props.put(\"acks\", \"all\");\n\n        props.put(\"retries\", 0);\n\n        props.put(\"batch.size\", 16384);\n\n        props.put(\"linger.ms\", 1);\n\n        props.put(\"buffer.memory\", 33554432);\n\n        props.put(\"key.serializer\", \"org.apache.kafka.common.serialization.IntegerSerializer\");\n\n        props.put(\"value.serializer\", \"org.apache.kafka.common.serialization.StringSerializer\");\n\n        return props;\n\n    }\n\n    @Bean\n\n    public KafkaTemplate<Integer, String> kafkaTemplate() {\n\n        return new KafkaTemplate<Integer, String>(producerFactory());\n\n    }\n\n//producer config end\n\n5.配置ConsumerFactory\n\n//consumer config start\n\n    @Bean\n\n    public ConcurrentKafkaListenerContainerFactory<Integer,String> kafkaListenerContainerFactory(){\n\n        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<Integer, String>();\n\n        factory.setConsumerFactory(consumerFactory());\n\n        return factory;\n\n    }\n\n\n    @Bean\n\n    public ConsumerFactory<Integer,String> consumerFactory(){\n\n        return new DefaultKafkaConsumerFactory<Integer, String>(consumerConfigs());\n\n    }\n\n\n\n    @Bean\n\n    public Map<String,Object> consumerConfigs(){\n\n        HashMap<String, Object> props = new HashMap<String, Object>();\n\n        props.put(\"bootstrap.servers\", \"192.168.180.128:9092\");\n\n        props.put(\"group.id\", \"test\");\n\n        props.put(\"enable.auto.commit\", \"true\");\n\n        props.put(\"auto.commit.interval.ms\", \"1000\");\n\n        props.put(\"key.deserializer\", \"org.apache.kafka.common.serialization.IntegerDeserializer\");\n\n        props.put(\"value.deserializer\", \"org.apache.kafka.common.serialization.StringDeserializer\");\n\n        return props;\n\n    }\n\n//consumer config end\n\n \n\n \n\n4.2  创建消息生产者\n\n//使用spring-kafka的template发送一条消息 发送多条消息只需要循环多次即可\n\npublic static void main(String[] args) throws ExecutionException, InterruptedException {\n\n    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(KafkaConfig.class);\n\n    KafkaTemplate<Integer, String> kafkaTemplate = (KafkaTemplate<Integer, String>) ctx.getBean(\"kafkaTemplate\");\n\n        String data=\"this is a test message\";\n\n        ListenableFuture<SendResult<Integer, String>> send = kafkaTemplate.send(\"topic-test\", 1, data);\n\n        send.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {\n\n            public void onFailure(Throwable throwable) {\n\n\n            }\n\n\n            public void onSuccess(SendResult<Integer, String> integerStringSendResult) {\n\n\n            }\n\n        });\n\n}\n\n \n\n4.3    创建消息消费者\n\n我们首先创建一个一个用于消息监听的类，当名为”topic-test”的topic接收到消息之后，我们的这个listen方法就会调用。\n\npublic class SimpleConsumerListener {\n\n    private final static Logger logger = LoggerFactory.getLogger(SimpleConsumerListener.class);\n\n    private final CountDownLatch latch1 = new CountDownLatch(1);\n\n\n    @KafkaListener(id = \"foo\", topics = \"topic-test\")\n\n    public void listen(byte[] records) {\n\n        //do something here\n\n        this.latch1.countDown();\n\n    }\n\n}\n\n         我们同时也需要将这个类作为一个Bean配置到KafkaConfig中\n\n@Bean\n\npublic SimpleConsumerListener simpleConsumerListener(){\n\n    return new SimpleConsumerListener();\n\n}\n\n默认spring-kafka会为每一个监听方法创建一个线程来向kafka服务器拉取消息', 'kafka,原创', '原创', 'java', 'kafka介绍1.1.主要功能根据官网的介绍，ApacheKafka®是一个分布式流媒体平台，它主要有3种功能：1：Itletsyoupublishandsubscribetostreamsofrecords.发布和订阅消息流，这个功能类似于消息队列，这也是kafka归类为消息队列框架的原因2：Itletsyoustorestreamsofrecordsinafault-tolerantway.以容错的方式记录消息流，kafka以文件的方式来存储消息流...', 'http://localhost:8080/jump/findArticle?articleId=1552902210&originalAuthor=Joey', 0, '2019-03-18', '2019-03-18', 1552902135, 1552902272);
INSERT INTO `article` VALUES (14, 1552902272, 'Joey', 'Joey', 'JAVA算法：啤酒算法', 'package com.stxy;\n\nimport java.util.Scanner;\n\n/**\n * 啤酒2元一瓶,4个瓶盖可以换一瓶啤酒,2个空瓶可以换一瓶啤酒，输入多少钱可以喝多少瓶？\n *\n *\n * @author Administrator\n *\n */\n\npublic class Test1 {\n\n    // 定义一个变量，最终可以喝多少瓶\n    private static int totalNum;\n    // 保存当前剩余瓶子盖子数量\n    private static int leftPingZi, leftGaiZi;\n\n    // 拿到钱能买多少瓶\n    public static void calcNum(int money) {\n        // 计算买多少瓶、5\n        int currentNum = money / 2;\n        // 购买多少瓶加到totalNum\n        totalNum += currentNum;\n        // 加上上一轮的瓶子盖子数量为当前数量\n        int currentLeftPingZi = leftPingZi + currentNum;\n        int currentLeftGaiZi = leftGaiZi + currentNum;\n\n        // 继续换钱去买酒,剩余的钱\n        int leftMoney = 0;\n        // 计算瓶子可以换多少钱\n        if (currentLeftPingZi > 1) {\n            // 多少钱\n            int pzMoney = (currentLeftPingZi / 2) * 2;\n            leftMoney += pzMoney;\n            // 计算剩余的瓶子\n            if (currentLeftPingZi % 2 != 0) {\n                leftPingZi = 1;\n            } else {\n                leftPingZi = 0;\n            }\n        } else {\n            leftPingZi = currentLeftPingZi;\n        }\n        // 计算盖子可以换多少\n        if (currentLeftGaiZi > 3) {\n            // 能换多少钱\n            int gzMoney = (currentLeftGaiZi / 4) * 2;\n            leftMoney += gzMoney;\n            // 计算出当前剩余盖子\n            leftGaiZi = currentLeftGaiZi - (gzMoney / 2) * 4;\n\n        } else {\n            leftGaiZi = currentLeftGaiZi;\n        }\n        if (leftMoney >= 2) {\n            calcNum(leftMoney);\n        }\n\n    }\n\n    public static void main(String[] args) {\n        Scanner s=new Scanner(System.in);\n        int money=s.nextInt();\n        calcNum(money);\n        System.out.println(\"瓶数\" + totalNum);\n        System.out.println(\"剩余瓶子数\" + leftPingZi);\n        System.out.println(\"剩余瓶子数\" + leftGaiZi);\n    }\n}', '算法,原创', '原创', 'java', 'packagecom.stxy;importjava.util.Scanner;/**啤酒2元一瓶,4个瓶盖可以换一瓶啤酒,2个空瓶可以换一瓶啤酒，输入多少钱可以喝多少瓶？@authorAdministrator/publicclassTest1{}...', 'http://localhost:8080/jump/findArticle?articleId=1552902272&originalAuthor=Joey', 0, '2019-03-18', '2019-03-18', 1552902210, 1552902321);
INSERT INTO `article` VALUES (15, 1552902321, 'Joey', 'Joey', 'kafka入门安装和使用', 'Kafka教程\n简介\n1.消息中间件介绍\n        消息中间件是在消息的传输过程中保存消息的容器。消息中间件在将消息从消息生产者到消费者时充当中间人的作用。队列的主要目的是提供路由并保证消息的传送；如果发送消息时接收者不可用，消息对列会保留消息，直到可以成功地传递它为止，当然，消息队列保存消息也是有期限的\n\n2.消息中间件特点\n采用异步处理模式：\n消息发送者可以发送一个消息而无须等待响应。消息发送者将消息发送到一条虚拟的通道（主题或者队列）上，消息接收者则订阅或者监听该通道。一条消息可能最终转发给一个或多个消息接收者，这些接收者都无需对消息发送者做出同步回应。整个过程是异步的。 比如用户信息注册。注册完成后过段时间发送邮件或者短信。\n应用程序和应用程序调用关系为松耦合关系：\n发送者和接收者不必要了解对方、只需要确认消息 发送者和接收者不必同时在线 比如在线交易系统为了保证数据的最终一致，在支付系统处理完成后会把支付结果放到信息中间件里通知订单系统修改订单支付状态。两个系统通过消息中间件解耦。\n3.Kafka介绍\n        Apache kafka是消息中间件的一种，举个例子，生产者消费者，生产者生产鸡蛋，消费者消费鸡蛋，生产者生产一个鸡蛋，消费者就消费一个鸡蛋，假设消费者消费鸡蛋的时候噎住了（系统宕机了），生产者还在生产鸡蛋，那新生产的鸡蛋就丢失了。再比如生产者很强劲（大交易量的情况），生产者1秒钟生产100个鸡蛋，消费者1秒钟只能吃50个鸡蛋，那要不了一会，消费者就吃不消了（消息堵塞，最终导致系统超时），消费者拒绝再吃了，”鸡蛋“又丢失了，这个时候我们放个篮子在它们中间，生产出来的鸡蛋都放到篮子里，消费者去篮子里拿鸡蛋，这样鸡蛋就不会丢失了，都在篮子里，而这个篮子就是”kafka“。 鸡蛋其实就是“数据流”，系统之间的交互都是通过“数据流”来传输的（就是tcp、http什么的），也称为报文，也叫“消息”。 消息队列满了，其实就是篮子满了，”鸡蛋“ 放不下了，那赶紧多放几个篮子，其实就是kafka的扩容。 现在知道kafka是干什么的了吧，它就是那个\"篮子\"。\n\n4.kafka名词解释\nproducer：生产者，就是它来生产“鸡蛋”的。\nconsumer：消费者，生出的“鸡蛋”它来消费。\ntopic：你把它理解为标签，生产者每生产出来一个鸡蛋就贴上一个标签（topic），消费者可不是谁生产的“鸡蛋”都吃的，这样不同的生产者生产出来的“鸡蛋”，消费者就可以选择性的“吃”了。\nbroker：就是篮子了。 \n\n一定要学会抽象的去思考，上面只是属于业务的角度，如果从技术角度，topic标签实际就是队列，生产者把所有“鸡蛋（消息）”都放到对应的队列里了，消费者到指定的队列里取。\n入门配置\n1.Ubuntu下安装和使用\n下载地址：http://kafka.apache.org/downloads\n\n\n把tgz包放到opt目录下面\n\n\n解压安装包到/opt/kafka目录下\n\n\n进入到/opt/kafka/kafka_2.11-2.0.0目录下启动kafka自带的zookeeper\n\n/bin/zookeeper-server-start.sh config/zookeeper.properties\n\n\n\n启动zookeeper成功，2181端口\n\n\n打开另一个终端查看2181端口已经被监听\n\n\n箭头就是zookeeper开启的服务\n\n\n用下面命令启动kafka,9092端口\n\nbin/kafka-server-start.sh config/server.properties\n\n\n\n再打开另一个终端查看9092端口已经被监听,zookeeper端口也把kafka服务加载进去\n\n\n输入jps命令发现服务都开启了\n\n\n创建一个主题，名字叫test1\n\nbin/kafka-topics.sh --zookeeper localhost:2181 --create --topic test1 --partitions 3 --replication-factor 1\n\n\n\n查看test1主题被创建成功,创建了三个Partition\n\n\n启动生产者（producer），在生产者中发送的消息，会存储在broker中，需要消费者接收这些消息。\n\nbin/kafka-console-producer.sh --broker-list localhost:9092 --topic test1\n\n\n\n启动消费者（consumer），消费者接收到，刚刚生产者输出的信息\n\nbin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test1\n\n\n\n\n\n查看生产者的历史信息\nbin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test1 --from-beginning\n\n\n\nkafka的默认端口是9092，在config/server.properties也可以修改这里去掉#自己定义\n\n\nzookeeper的默认端口是2181，在config/zookeeper.propertie也可以修改这里自己定义\n\n\n不用kafka自带的zookeeper,下载一个zookeeper来启动\n\n\n解压到opt下\n\n\n启动zookeeper\n\nbin/zkServer.sh start conf/zoo_sample.cfg\n\n\n\n查看状态\n\nbin/zkServer.sh status conf/zoo_sample.cfg\n\n\n\n启动成功\n\n\n然后像上面一样启动kafka就行了\n', 'kafka,原创', '原创', 'java', 'Kafka教程简介1.消息中间件介绍消息中间件是在消息的传输过程中保存消息的容器。消息中间件在将消息从消息生产者到消费者时充当中间人的作用。队列的主要目的是提供路由并保证消息的传送；如果发送消息时接收者不可用，消息对列会保留消息，直到可以成功地传递它为止，当然，消息队列保存消息也是有期限的2.消息中间件特点采用异步处理模式：消息发送者可以发送一个消息而无须等待响应。消息发送者将消息发送到一条虚拟的通道（主题或者队列）上，消息接收者则订阅或者监听该通道。一条消息可能最终转发给一个或多个消息接收者，这些接收者都无需对消息发送者做出同步回应。整个过程是异步的。比如用户信息注册。注册完成后过段时间发送邮件或者短信。...', 'http://localhost:8080/jump/findArticle?articleId=1552902321&originalAuthor=Joey', 1, '2019-03-18', '2019-03-18', 1552902272, 1552902381);
INSERT INTO `article` VALUES (16, 1552902381, 'Joey', 'Joey', 'Docker教程', 'Docker教程\n简介\n1.Docker是什么？\nDocker 是一个便携的应用容器，Docker 当你需要在容器内运行自己的应用（当然可以是任何应用），Docker 都提供了一个基础系统镜像作为运行应用时的基础系统。也就是说，只要是 Linux 系统上的应用都可以运行在 Docker 中，也可以实现虚拟化。\n\n2.Docker能干什么？\n更快速的交付和部署\n自动化测试和持续集成、发布。\n允许实例简单、快速地扩展。\n3.理解Docker\nDocker，可以说是一个终端命令行的虚拟机，但更准确的说法，其实应该是一个虚拟环境。比如，你想要在PC上无缝使用Linux么？那么虚拟机并不是你唯一的出路，你还有Docker！我更愿意称Docker为一个容器，当然这只是Docker的一个狭义解释，Docker不止是一个容器。\n\nDocker3个重要概念：\n\n   镜像（Image）\n\n镜像是静态的、可以被用户互相分享的文件。我们玩过双系统和虚拟机的人都知道，首先你需要一个.iso镜像，才能安装系统。Docker中的镜像也是这个东西，镜像是静态的，你不能对他操作，只能pull别人的镜像或者push自己的镜像。\n\n   容器（Container）\n\n镜像是静态不可操作的，只能被分享和下载，那什么是能被操作的呢？就是容器里！容器可以理解为镜像的动态状态，也就是我们虚拟机中系统装好后的状态，其实这么说是不对的，容器最符合的描述应该是Linux的iso文件的Live CD模式，比如我们玩双系统时都进入过Live CD模式，不安装系统而直接进入系统，很神奇是吧，Docker的容器就是这个概念，只不过更加轻量更加迅速便捷。但是Live CD的害处就是你关机后作出的修改安装的软件全部gg，容器也是一样，一旦被直接推出，之前安装的gcc啊vim啊啥的就会全部gg掉。如果要保存修改，就需要将当前容器封装成一个新的镜像，这样下次启动这个新的镜像后之前作出的修改还都在。\n\n   仓库（Repository）\n\nDocker中的仓库很像git的代码仓库，你可以pull自己之前push到自己仓库的镜像到本地，也可以pull别人push到公共仓库的镜像到自己本地。说白了就是百度云盘，你可以上传（push）自己做好环境的Docker上去，也可以下载（pull）自己云端的镜像到本地。同时，我们知道百度云最大的特点就是分享，类比Docker，如果你得到百度云分享链接（别人的镜像名字、标签和别人的用户名），你还可以下载（pull）别人分享的镜像到自己的本地，别人也可以下载（pull）你的镜像，因为Docker仓库都是公共的。当然，每个免费用户有一个名额把自己的一个镜像设为私有，也就是禁止被分享给别人，类比百度云上你自己保存的而没有被生成分享链接的资源。\n\n入门配置\n1.Ubuntu下安装\nCentOS6.8安装docker\n\nCentOS6.8在ROOT用户下用yum -y install docker命令被各种坑，无法启动，使用service docker start命令启动报“docker: 未被识别的服务”。\n\n安装docker的时候要执行 yum -y install docker-io 而不是 yum -y install docker！！！！\n\nsudo yum install docker-io\n\n\n\n启动docker\n\nservice docker start\n\n\n\n查看启动docker状态命令\n\nsudo service docker status\n\n将docker加入开机启动\n\nchkconfig docker on\n\n2.下载镜像\n拉取官方最新版的ubuntu镜像\n\nsudo docker pull ubuntu:latest\n\n查看下载的本地镜像仓库的镜像\n\nsudo docker imges \n\n3.springboot部署项目\n将打包的项目jar放到lnux文件夹上，在同级目录上用touch Dockerfile 命令创建Dockerfile文件\n\n文件里面内容为：\n\nFROM java:8\n\nMAINTAINER wyq\n\nADD springboot-helloworld-0.0.1-SNAPSHOT.jar app.jar EXPOSE 8080\n\nENTRYPOINT [\"java\",\"-jar\",\"/app.jar\"]\n\n文件内容解释：\n\n1、基镜像为JAVA 版本为8 ​\n\n2、作者名字 ​\n\n3、将项目添加到镜像中，并重命名为app.jar ​\n\n4、运行镜像文件，监听端口为8080 ​\n\n5、启动时运行java -jar app.jar\n\n\n\n然后构建镜像\n\nsudo docker build -t test/dockerdemo .\n\n\n\n运行镜像\n\n``` docker run -d --name dockerdemo -p 8081:8080 test/dockerdemo\n\n```\n\n\n\n然后在浏览器输入 http://127.0.0.1:8081/\n\n\n\n', 'docker,linux,原创', '原创', 'java', 'Docker教程简介1.Docker是什么？Docker是一个便携的应用容器，Docker当你需要在容器内运行自己的应用（当然可以是任何应用），Docker都提供了一个基础系统镜像作为运行应用时的基础系统。也就是说，只要是Linux系统上的应用都可以运行在Docker中，也可以实现虚拟化。2.Docker能干什么？更快速的交付和部署自动化测试和持续集成、发布。允许实例简单、快速地扩展。3.理解Docker...', 'http://localhost:8080/jump/findArticle?articleId=1552902381&originalAuthor=Joey', 3, '2019-03-18', '2019-03-18', 1552902321, 1555297882);

-- ----------------------------
-- Table structure for article_likes
-- ----------------------------
DROP TABLE IF EXISTS `article_likes`;
CREATE TABLE `article_likes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `liker_id` int(11) NOT NULL COMMENT '点赞人id',
  `original_author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原作者',
  `like_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of article_likes
-- ----------------------------
INSERT INTO `article_likes` VALUES (10, 1552902381, 1, 'Joey', '2019-04-12 17:40');
INSERT INTO `article_likes` VALUES (11, 1552902381, 2, 'Joey', '2019-04-12 17:41');
INSERT INTO `article_likes` VALUES (12, 1552902321, 2, 'Joey', '2019-04-12 17:42');

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, 'java');
INSERT INTO `categories` VALUES (2, 'springboot');
INSERT INTO `categories` VALUES (5, '777');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `father_id` bigint(20) NOT NULL COMMENT '回复的父id 若是评论则为 0，则是评论中的回复则为对应评论的id',
  `article_id` bigint(20) NOT NULL COMMENT '评论的文章id',
  `comment_id` int(11) NOT NULL COMMENT '评论者id',
  `response_id` int(11) NOT NULL COMMENT '被回复者id',
  `original_author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论的文章的原作者',
  `comment_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论日期',
  `comment_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `likes` int(255) NOT NULL COMMENT '点赞数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (15, 0, 1552902381, 1, 1, 'Joey', '2019-04-12 17:40', '不错哦', 2);
INSERT INTO `comment` VALUES (16, 0, 1552902321, 1, 1, 'Joey', '2019-04-12 17:41', '很好', 0);
INSERT INTO `comment` VALUES (17, 0, 1552902321, 1, 1, 'Joey', '2019-04-12 17:41', '怎么做到的', 1);
INSERT INTO `comment` VALUES (18, 15, 1552902381, 2, 1, 'Joey', '2019-04-12 17:41', '当然啊', 0);
INSERT INTO `comment` VALUES (19, 17, 1552902321, 2, 1, 'Joey', '2019-04-12 17:42', '很简单', 0);
INSERT INTO `comment` VALUES (21, 0, 1552902210, 2, 1, 'Joey', '2019-04-12 17:42', '很详细', 0);
INSERT INTO `comment` VALUES (22, 15, 1552902381, 1, 1, 'Joey', '2019-04-26 09:48', '11', 0);
INSERT INTO `comment` VALUES (23, 15, 1552902381, 1, 2, 'Joey', '2019-05-07 19:23', ' 444', 0);

-- ----------------------------
-- Table structure for comment_likes
-- ----------------------------
DROP TABLE IF EXISTS `comment_likes`;
CREATE TABLE `comment_likes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `comment_id` int(11) NOT NULL COMMENT '评论者id',
  `liker_id` int(11) NOT NULL COMMENT '点赞人id',
  `original_author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '原作者',
  `like_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of comment_likes
-- ----------------------------
INSERT INTO `comment_likes` VALUES (2, 1552902381, 15, 2, 'Joey', '2019-04-12 17:41');
INSERT INTO `comment_likes` VALUES (3, 1552902321, 17, 2, 'Joey', '2019-04-12 17:42');
INSERT INTO `comment_likes` VALUES (4, 1552902381, 15, 1, 'Joey', '2019-04-26 09:16');

-- ----------------------------
-- Table structure for contact_admin
-- ----------------------------
DROP TABLE IF EXISTS `contact_admin`;
CREATE TABLE `contact_admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '联系管理员表id',
  `private_word` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送内容',
  `publisher_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送者',
  `replier_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复者',
  `reply_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复内容',
  `publisher_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of contact_admin
-- ----------------------------
INSERT INTO `contact_admin` VALUES (1, '77', '1', '1', '好好', '2019-03-31 23:33:00');

-- ----------------------------
-- Table structure for feed_back
-- ----------------------------
DROP TABLE IF EXISTS `feed_back`;
CREATE TABLE `feed_back`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈表id',
  `feed_back_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '反馈内容',
  `contact_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `person_id` int(11) NOT NULL COMMENT '反馈人',
  `feed_back_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '反馈时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of feed_back
-- ----------------------------
INSERT INTO `feed_back` VALUES (2, '没问题', '962721775', 1, '2019-03-29 11:53:49');
INSERT INTO `feed_back` VALUES (3, '没问题好不好', '962721775', 1, '2019-03-29 11:54:04');
INSERT INTO `feed_back` VALUES (4, 'sss', 'ddd', 1, '2019-04-09 10:38:51');

-- ----------------------------
-- Table structure for leave_message
-- ----------------------------
DROP TABLE IF EXISTS `leave_message`;
CREATE TABLE `leave_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言表id',
  `page_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言页的HTML名字',
  `father_id` int(255) NOT NULL COMMENT '留言的父id 若是留言则为 0，则是留言中的回复则为对应留言的id',
  `leaver_id` int(11) NOT NULL COMMENT '留言者',
  `response_id` int(11) NOT NULL COMMENT '被回复者',
  `leave_message_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言日期',
  `likes` int(11) NOT NULL COMMENT '留言点赞',
  `leave_message_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of leave_message
-- ----------------------------
INSERT INTO `leave_message` VALUES (1, 'jump/categories', 0, 1, 1, '2019-03-28 17:23', 2, '有过');
INSERT INTO `leave_message` VALUES (2, 'jump/categories', 1, 2, 1, '2019-03-28 18:06', 0, '杨哥');
INSERT INTO `leave_message` VALUES (3, 'jump/categories', 1, 1, 2, '2019-03-28 19:23', 0, ' 对对对');
INSERT INTO `leave_message` VALUES (4, 'jump/categories', 0, 3, 1, '2019-03-28 19:25', 0, '777');
INSERT INTO `leave_message` VALUES (5, 'jump/categories', 1, 3, 1, '2019-03-28 19:25', 0, ' 对对对');
INSERT INTO `leave_message` VALUES (6, 'jump/categories', 1, 3, 2, '2019-03-28 19:25', 0, ' 打死你');
INSERT INTO `leave_message` VALUES (7, 'jump/archives', 0, 1, 1, '2019-03-29 10:24', 1, '不错啊');
INSERT INTO `leave_message` VALUES (8, 'jump/tags', 0, 1, 1, '2019-03-29 10:25', 1, '很好');
INSERT INTO `leave_message` VALUES (9, 'jump/tags', 8, 1, 1, '2019-03-29 10:25', 0, '顶你');

-- ----------------------------
-- Table structure for leave_message_likes
-- ----------------------------
DROP TABLE IF EXISTS `leave_message_likes`;
CREATE TABLE `leave_message_likes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言点赞表id',
  `page_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言页的HTML名字',
  `father_id` int(11) NOT NULL COMMENT '留言的父id 若是留言则为 0，则是留言中的回复则为对应留言的id',
  `liker_id` int(11) NOT NULL COMMENT '点赞人',
  `like_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of leave_message_likes
-- ----------------------------
INSERT INTO `leave_message_likes` VALUES (1, 'jump/categories', 1, 1, '2019-03-28 19:01');
INSERT INTO `leave_message_likes` VALUES (4, 'jump/categories', 1, 3, '2019-03-28 19:24');
INSERT INTO `leave_message_likes` VALUES (5, 'jump/archives', 7, 1, '2019-03-29 10:24');
INSERT INTO `leave_message_likes` VALUES (6, 'jump/tags', 8, 1, '2019-03-29 10:25');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_ACCOUNT');
INSERT INTO `role` VALUES (2, 'ROLE_ADMIN');
INSERT INTO `role` VALUES (3, 'ROLE_SUPERADMIN');

-- ----------------------------
-- Table structure for sec_account
-- ----------------------------
DROP TABLE IF EXISTS `sec_account`;
CREATE TABLE `sec_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号码',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `gender` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别',
  `true_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实名字',
  `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生日',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `personal_profile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人简介',
  `avatar_images_url` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片路径',
  `last_logintime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录时间',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '管理员通过注册的员工（0：不通过   1：通过）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sec_account
-- ----------------------------
INSERT INTO `sec_account` VALUES (1, '17724272557', 'Joey', 'a3caed36f0fe5a01e5f144db8927235e', '男', '2', '2020-08-21', '962721775@qq.com', '8', 'https://oyy-sunment.oss-cn-shenzhen.aliyuncs.com/photo/account/avatar/欧泳洋/1554644037.jpeg', '2019-05-10 15:58:45', 1);
INSERT INTO `sec_account` VALUES (2, '18316083089', 'Leo', 'a3caed36f0fe5a01e5f144db8927235e', '男', '3', '2020-08-21', '662', '8', 'https://oyy-sunment.oss-cn-shenzhen.aliyuncs.com/photo/account/avatar/欧泳洋/1553253606.jpeg', '2019-04-12 17:41:39', 1);
INSERT INTO `sec_account` VALUES (3, '13542685572', 'Michael', 'a3caed36f0fe5a01e5f144db8927235e', '男', '3', '2020-08-21', '662', '8', 'https://oyy-sunment.oss-cn-shenzhen.aliyuncs.com/photo/account/avatar/michael/1553692518.jpeg', '2019-03-28 19:24:55', 0);
INSERT INTO `sec_account` VALUES (5, '17724272567', 'Admin', 'a3caed36f0fe5a01e5f144db8927235e', '男', NULL, NULL, NULL, NULL, 'https://oyy-sunment.oss-cn-shenzhen.aliyuncs.com/static/images/%E7%94%B7%E7%94%9F%E9%BB%98%E8%AE%A4%E5%A4%B4%E5%83%8F.jpg', '2019-04-25 19:49:25', 1);

-- ----------------------------
-- Table structure for sec_account_role
-- ----------------------------
DROP TABLE IF EXISTS `sec_account_role`;
CREATE TABLE `sec_account_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sec_account_role
-- ----------------------------
INSERT INTO `sec_account_role` VALUES (1, 1);
INSERT INTO `sec_account_role` VALUES (1, 3);
INSERT INTO `sec_account_role` VALUES (2, 1);
INSERT INTO `sec_account_role` VALUES (3, 1);
INSERT INTO `sec_account_role` VALUES (4, 1);
INSERT INTO `sec_account_role` VALUES (5, 1);
INSERT INTO `sec_account_role` VALUES (5, 3);

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名字',
  `tag_size` int(11) NOT NULL COMMENT '标签大小',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES (5, 'spring', 20);
INSERT INTO `tags` VALUES (6, '原创', 20);
INSERT INTO `tags` VALUES (7, '框架', 20);
INSERT INTO `tags` VALUES (8, 'idea', 18);
INSERT INTO `tags` VALUES (9, 'springboot', 18);
INSERT INTO `tags` VALUES (10, '时间轮', 20);
INSERT INTO `tags` VALUES (11, 'kafka', 20);
INSERT INTO `tags` VALUES (12, '算法', 20);
INSERT INTO `tags` VALUES (13, 'docker', 20);
INSERT INTO `tags` VALUES (14, 'linux', 20);
INSERT INTO `tags` VALUES (15, '88', 20);
INSERT INTO `tags` VALUES (16, 'java', 20);
INSERT INTO `tags` VALUES (17, '转载', 20);
INSERT INTO `tags` VALUES (18, '美女', 15);

-- ----------------------------
-- Table structure for visitor
-- ----------------------------
DROP TABLE IF EXISTS `visitor`;
CREATE TABLE `visitor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `visitor_num` bigint(20) NOT NULL COMMENT '访客人数',
  `page_name` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当前页的name 或者 文章名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of visitor
-- ----------------------------
INSERT INTO `visitor` VALUES (1, 520, 'totalVisitor');
INSERT INTO `visitor` VALUES (2, 213, 'visitorVolume');
INSERT INTO `visitor` VALUES (11, 0, 'jump/findArticle?articleId=1552901948&originalAuthor=Joey');
INSERT INTO `visitor` VALUES (12, 0, 'jump/findArticle?articleId=1552902135&originalAuthor=Joey');
INSERT INTO `visitor` VALUES (13, 1, 'jump/findArticle?articleId=1552902210&originalAuthor=Joey');
INSERT INTO `visitor` VALUES (14, 0, 'jump/findArticle?articleId=1552902272&originalAuthor=Joey');
INSERT INTO `visitor` VALUES (15, 3, 'jump/findArticle?articleId=1552902321&originalAuthor=Joey');
INSERT INTO `visitor` VALUES (16, 11, 'jump/findArticle?articleId=1552902381&originalAuthor=Joey');
INSERT INTO `visitor` VALUES (17, 1, 'jump/findArticle?articleId=1555296561&originalAuthor=Joey');
INSERT INTO `visitor` VALUES (18, 1, 'jump/findArticle?articleId=1555297768&originalAuthor=Joey');
INSERT INTO `visitor` VALUES (19, 1, 'jump/findArticle?articleId=1555297882&originalAuthor=77');
INSERT INTO `visitor` VALUES (20, 1, 'jump/findArticle?articleId=1555297894&originalAuthor=Joey');

-- ----------------------------
-- Table structure for weather
-- ----------------------------
DROP TABLE IF EXISTS `weather`;
CREATE TABLE `weather`  (
  `id` bigint(11) NOT NULL COMMENT 'id',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市天气编码',
  `city_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of weather
-- ----------------------------
INSERT INTO `weather` VALUES (1, '101280101', '广州');

SET FOREIGN_KEY_CHECKS = 1;
