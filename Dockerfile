FROM maven:3.9-eclipse-temurin-21 AS compilacao
WORKDIR /projeto
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests clean package

FROM tomcat:11-jre21
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=compilacao /projeto/target/BACK_END_OLIUS-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
COPY iniciar-render.sh /usr/local/bin/iniciar-render.sh
RUN chmod +x /usr/local/bin/iniciar-render.sh
ENV JAVA_OPTS="-Xms64m -Xmx256m -XX:MaxMetaspaceSize=128m"
EXPOSE 10000
CMD ["/usr/local/bin/iniciar-render.sh"]
