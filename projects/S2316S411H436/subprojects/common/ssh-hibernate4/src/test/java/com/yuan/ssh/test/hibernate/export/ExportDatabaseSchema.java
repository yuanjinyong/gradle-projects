package com.yuan.ssh.test.hibernate.export;


/**
 * 用于生成数据库表结构：<br>
 * 可作为Hibernate的一个工具类，<br>
 * 将读取Hibernate配置文件，将对象模型生成关系模型（即生成数据库表结构），<br>
 * 要求：生成数据表结构之前要求已经存在数据库。
 */
public class ExportDatabaseSchema {

    /**
     * @param args
     */
    public static void main(String[] args) {

        /*
         * org.hibernate.cfg.Configuration类的作用：
         * 用于读取Hibernate配置文件(hibernate.cfg.xml或hiberante.properties)的，加载配置信息。
         * 而new Configuration()默认是读取hibernate.properties的，
         * 所以使用new Configuration().configure();来读取hibernate.cfg.xml配置文件。
         */
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration().configure();// 方式一：读取hibernate.cfg.xml
        //org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();// 方式二：读取hibernate.properties

        /*
         * org.hibernate.tool.hbm2ddl.SchemaExport工具类：
         * 需要传入Configuration参数，此工具类可以将类导出生成数据库表
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
