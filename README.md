## 项目说明
① challegen-ping服务: 每秒向challenge-pong服务发送ping请求，并打印响应结果。
* 使用webflux的webclient实现请求的发送
* 使用@Scheduled实现每秒发送请求
* 使用FileLock实现限流策略: 多个JVM请求, 一秒内只能有2个请求能发送到pong

② challegen-ping服务: 接收ping请求，并根据限流策略，返回"429"状态码或"World"
* 使用webflux实现web请求处理
* 使用guava的RateLimiter实现限流策略: 一秒内只能处理1个请求
* 也可使用K8s.yaml里的ingress实现


### 框架

| 框架                       | 说明               | 版本  |                                                         
|--------------------------|-------------------|-------------|
| Spring Boot             | 应用开发框架         | 2.7.11      |
| Spring WebFlux          | Web 开发框架         | 2.7.11      |
| FileLock                  | NIO 文件锁     |       |
| guava                   | 限流控制器           | 32.1.3-jre       |
| mybatis-plus               | orm框架         | 3.5.3.1      |
| postgresql                   | 数据库连接工具         | 42.3.8      |
| Spring Spock            | 单元测试框架         | 2.4     |
| Maven Jacoco           | 代码覆盖率分析         | 0.8.7    |
| logback           | 日志记录框架         | 1.2.12    |