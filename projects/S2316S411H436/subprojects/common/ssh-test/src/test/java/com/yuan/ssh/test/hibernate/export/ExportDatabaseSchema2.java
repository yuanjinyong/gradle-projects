package com.yuan.ssh.test.hibernate.export;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;


/**
 * 用于生成数据库表结构：<br>
 * 可作为Hibernate的一个工具类，<br>
 * 将读取Hibernate配置文件，将对象模型生成关系模型（即生成数据库表结构），<br>
 * 要求：生成数据表结构之前要求已经存在数据库。
 */
public class ExportDatabaseSchema2 {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 加载Spring配置
        String configLocation = "classpath:applicationContext.xml";// Spring配置文件
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);

        // 参考《Spring3.x企业应用开发实战》的P118-120页
        LocalSessionFactoryBean sessionFactory = (LocalSessionFactoryBean) ctx.getBean("&sessionFactory");

        // 从整合好的Spring中获取Hibernate的配置
        org.hibernate.cfg.Configuration cfg = sessionFactory.getConfiguration();

        /*
         * org.hibernate.tool.hbm2ddl.SchemaExport工具类：
         * 需要传入Configuration参数，
         * 此工具类可以将类导出生成数据库表
         */
        org.hibernate.tool.hbm2ddl.SchemaExport export = new org.hibernate.tool.hbm2ddl.SchemaExport(cfg);

        /*
         * 开始导出：
         * 第一个参数：script，是否打印DDL信息，
         * 第二个参数：export，是否导出到数据库中生成表(注意：如果表已经存在，也会重新生成表，表中的原有数据就会被清除)
         */
        export.create(true, true);

    }

}
