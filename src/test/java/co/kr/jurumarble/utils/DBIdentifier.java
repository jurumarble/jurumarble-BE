package co.kr.jurumarble.utils;

import javax.persistence.EntityManager;
import java.util.List;

public enum DBIdentifier {
    H2("H2") {
        @Override
        void executeDBCleanUp(EntityManager entityManager, List<String> tableNames) {
            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
            for (String tableName : tableNames) {
                entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
                entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1").executeUpdate();
            }
            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        }
    },
    MYSQL("MySQL") {
        @Override
        void executeDBCleanUp(EntityManager entityManager, List<String> tableNames) {
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            for (String tableName : tableNames) {
                entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            }
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        }
    };

    private String value;

    DBIdentifier(String value) {
        this.value = value;
    }

    public static DBIdentifier getInstance(String dbName) {
        if (MYSQL.value.equals(dbName)) {
            return DBIdentifier.MYSQL;
        }
        return DBIdentifier.H2;
    }

    abstract void executeDBCleanUp(EntityManager entityManager, List<String> tableNames);
}
