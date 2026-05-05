FROM openjdk:17-jdk-slim

LABEL maintainer="LivePulse <support@livepulse.com.cn>"
LABEL description="Google Ads Connector - 推送客户匹配数据到 Google Ads 平台"

# 设置工作目录
WORKDIR /app

# 复制 JAR 文件
COPY target/livepulse-connector-google-ads-open.jar /app/app.jar

# 暴露端口
EXPOSE 23009

# 设置 JVM 参数
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
