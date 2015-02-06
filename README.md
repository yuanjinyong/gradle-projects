Gradle项目群


所有项目共用gradlew目录下的配置文件和批处理脚本。


一、gradlew\g.bat会自动设置环境变量和Path:
1、设置GRALE_HOME用户环境变量为gradlew目录；
2、设置GRADLE_USER_HOME用户环境变量为gradlew\.gradle；
3、把gradlew目录添加到用户的path环境变量中。


二、gradlew\gradle\wrapper\gradle-wrapper.properties文件中的distributionUrl地址，改为内网地址。

