package com.devheon.database;

import com.devheon.netty.common.constant.PropKey;
import com.devheon.database.config.vo.DBConnectionCofigVO;
import com.devheon.util.PropertyLoader;
import com.devheon.util.singleton.LogHelper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.sql.SQLException;

/**
 * <pre>
 * Description : 
 *     데이터베이스 커넥션 풀 - Singleton
 * ===============================================
 * Member fields : 
 *     DBConnectionCofigVO mDBConnectionCofigVO
 *     HikariDataSource mHikariDataSource
 *     Log4jdbcProxyDataSource mLog4jdbcProxyDataSource
 *     SqlSessionFactory mSqlSessionFactory
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2020-03-12
 * </pre>
 */
public class DBConnectionPool {
    /* Singleton */
    private static DBConnectionPool instance;
    public static DBConnectionPool getInstance() {
        if(instance == null)
            instance = new DBConnectionPool();
        return instance;
    }
    /* Singleton */

    /* Member fields */
    private DBConnectionCofigVO mDBConnectionCofigVO;
    private HikariDataSource mHikariDataSource;
    private Log4jdbcProxyDataSource mLog4jdbcProxyDataSource;
    private SqlSessionFactory mSqlSessionFactory;
    /* Member fields */

    /**
     * <pre>
     * Description
     *     DB 연결 객체를 가져온다.
     * ===============================================
     * Parameters
     *
     * Returns
     *     SqlSession
     * Throws
     *     SQLException
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-03-12
     * </pre>
     */
    public SqlSession getSqlSession() throws SQLException {
        try {
            return this.mSqlSessionFactory.openSession(this.mLog4jdbcProxyDataSource.getConnection());
        } catch (SQLException e) {
            LogHelper.getInstance().exception(e);
            throw e;
        }
    }

    /**
     * <pre>
     * Description
     *     DB 연결 객체를 반환한다.
     * ===============================================
     * Parameters
     *     SqlSession sqlSession
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-03-12
     * </pre>
     */
    public void closeSqlSession(SqlSession sqlSession) {
        sqlSession.close();
    }

    /**
     * <pre>
     * Description
     *     설정파일을 통해 커넥션 풀을 생성한다.
     * ===============================================
     * Parameters
     *     String propertyFileName
     * Returns
     *
     * Throws
     *     Exception
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-03-12
     * </pre>
     */
    public void setConnectionPool(String propertyFileName) throws Exception {
        /* Connection Config */
        this.mDBConnectionCofigVO = getDBConnectionConfigVO(propertyFileName);

        /* DataSource */
        this.mHikariDataSource = new HikariDataSource(getHikariConfig());
        this.mLog4jdbcProxyDataSource = new Log4jdbcProxyDataSource(this.mHikariDataSource);
        this.mLog4jdbcProxyDataSource.setLogFormatter(getLog4JdbcFormattion());

        /* SqlSessionFactory */
        this.mSqlSessionFactory = new SqlSessionFactoryBuilder().build(getMybatisConfiguration());
    }

    private DBConnectionCofigVO getDBConnectionConfigVO(String propertyFileName) throws Exception {
        PropertyLoader propertyLoader = new PropertyLoader(propertyFileName);

        DBConnectionCofigVO dbConnectionCofigVO;

        if(propertyLoader.load()) {
            final String driver        = propertyLoader.getPropertyValue(PropKey.DB_DRIVER.getKey());
            final String url           = propertyLoader.getPropertyValue(PropKey.DB_URL.getKey());
            final String username      = propertyLoader.getPropertyValue(PropKey.DB_USERNAME.getKey());
            final String password      = propertyLoader.getPropertyValue(PropKey.DB_PASSWORD.getKey());
            final String mapperPackage = propertyLoader.getPropertyValue(PropKey.DB_MAPPER_PACKAGE.getKey());
            final String strPoolSize   = propertyLoader.getPropertyValue(PropKey.DB_POOLSIZE.getKey());

            int connectionPoolSize = 5;
            try {
                connectionPoolSize = Integer.parseInt(strPoolSize);
            } catch (Exception e) {
                LogHelper.getInstance().getLogger().warn("Database Connection Pool set default(5)");
            }

            dbConnectionCofigVO = DBConnectionCofigVO.builder()
                    .driver(driver)
                    .url(url)
                    .username(username)
                    .password(password)
                    .mapperPackage(mapperPackage)
                    .connectionPoolSize(connectionPoolSize)
                    .build();

        } else
            throw new Exception("properties file cannot be loaded.");


        return dbConnectionCofigVO;
    }

    private HikariConfig getHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(mDBConnectionCofigVO.getConnectionPoolSize());
        hikariConfig.setDriverClassName(mDBConnectionCofigVO.getDriver());
        hikariConfig.setJdbcUrl(mDBConnectionCofigVO.getUrl());
        hikariConfig.setUsername(mDBConnectionCofigVO.getUsername());
        hikariConfig.setPassword(mDBConnectionCofigVO.getPassword());
        hikariConfig.setAutoCommit(true);
        hikariConfig.setAllowPoolSuspension(true);
        return hikariConfig;
    }

    private Log4JdbcCustomFormatter getLog4JdbcFormattion() {
        Log4JdbcCustomFormatter log4JdbcCustomFormatter = new Log4JdbcCustomFormatter();
        log4JdbcCustomFormatter.setSqlPrefix("[SQL] \n    ");
        log4JdbcCustomFormatter.setLoggingType(LoggingType.MULTI_LINE);
        return log4JdbcCustomFormatter;
    }

    private Configuration getMybatisConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addMappers(this.mDBConnectionCofigVO.getMapperPackage());
        configuration.setMapUnderscoreToCamelCase(true);
        return configuration;
    }

}
