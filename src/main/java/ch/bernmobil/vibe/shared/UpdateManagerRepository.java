package ch.bernmobil.vibe.shared;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UpdateManagerRepository {
    private JdbcTemplate jdbcTemplate;

    public UpdateManagerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void truncate(String tableName) {
        jdbcTemplate.update(new QueryBuilder().truncate(tableName).getQuery());
    }

    public void deleteUpdatesWithInvalidTimestamp(String table, List<Timestamp> lastUpdates) {
        ArrayList<QueryBuilder.Predicate> predicates = new ArrayList<>();
        for (Timestamp timestamp : lastUpdates) {
            predicates.add(QueryBuilder.Predicate.notEquals("update", String.format("'%s'", timestamp)));
        }
        QueryBuilder.Predicate predicate = QueryBuilder.Predicate.joinAnd(predicates);
        jdbcTemplate.update(new QueryBuilder()
                .delete(table)
                .where(predicate)
                .getQuery());
    }

    public void deleteUpdatesWithInvalidTimestamp(String[] tables, List<Timestamp> lastUpdates) {
        for (String table : tables) {
            deleteUpdatesWithInvalidTimestamp(table, lastUpdates);
        }
    }

    public void deleteByUpdateTimestamp(String table, Timestamp updateTimestamp, String timestampColumn) {
        jdbcTemplate.update(new QueryBuilder()
                .delete(table)
                .where(QueryBuilder.Predicate.equals(timestampColumn, String.format("'%s'", updateTimestamp)))
                .getQuery());
    }

    public void deleteByUpdateTimestamp(String[] tables, Timestamp updateTimestamp) {
        for (String table : tables) {
            deleteByUpdateTimestamp(table, updateTimestamp, "update");
        }
    }
}
