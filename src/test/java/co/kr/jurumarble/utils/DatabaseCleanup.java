package co.kr.jurumarble.utils;

import com.google.common.base.CaseFormat;
import org.hibernate.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseCleanup implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
                .map(e -> getTableName(e.getJavaType()))
                .collect(Collectors.toList());
    }

    public String getTableName(Class<?> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        }
        // Handle the case where @Table annotation is not present, or return a default table name if needed
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName());
    }

    @Transactional
    public void execute() throws SQLException {
        entityManager.flush();
        DatabaseMetaData metaData = entityManager.unwrap(Session.class).doReturningWork(Connection::getMetaData);
        String databaseProductName = metaData.getDatabaseProductName();

        DBIdentifier instance = DBIdentifier.getInstance(databaseProductName);
        instance.executeDBCleanUp(entityManager,tableNames);
    }
}