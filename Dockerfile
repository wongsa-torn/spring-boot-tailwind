# STEP 1: ใช้ JDK image เป็น base
FROM openjdk:17-jdk-slim

# STEP 2: ตั้ง working directory
WORKDIR /app

# STEP 3: คัดลอก JAR ที่ build แล้วไปใน container
COPY target/tailwind-0.0.1-SNAPSHOT.jar app.jar

# STEP 4: คำสั่งที่ใช้รันแอป
ENTRYPOINT ["java", "-jar", "app.jar"]