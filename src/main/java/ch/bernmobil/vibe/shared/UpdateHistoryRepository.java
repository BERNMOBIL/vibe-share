package ch.bernmobil.vibe.shared;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import static ch.bernmobil.vibe.shared.QueryBuilder.Predicate;
import static ch.bernmobil.vibe.shared.QueryBuilder.PreparedStatement;
import static ch.bernmobil.vibe.shared.UpdateManager.Status;

@Repository
public class UpdateHistoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public UpdateHistoryRepository(@Qualifier("StaticDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UpdateHistoryEntry findByTimestamp(Timestamp timestamp) {
        return queryForObject(new QueryBuilder()
                .select(UpdateHistoryContract.TABLE_NAME)
                .where(Predicate.equals(UpdateHistoryContract.TIME, String.format("'%s'", timestamp)))
                .getQuery());
    }

    public UpdateHistoryEntry findLastSuccessUpdate() {
        Predicate successStatus = Predicate.equals(UpdateHistoryContract.STATUS, "'" + Status.SUCCESS + "'");
        String query = new QueryBuilder()
                .select(UpdateHistoryContract.TABLE_NAME)
                .where(successStatus)
                .orderby(UpdateHistoryContract.TIME + " DESC")
                .limit("1")
                .getQuery();
        return queryForObject(query);
    }

    public UpdateHistoryEntry findLastUpdate() {
        String query = new QueryBuilder()
                .select(UpdateHistoryContract.TABLE_NAME)
                .orderby(UpdateHistoryContract.TIME + " DESC")
                .limit("1")
                .getQuery();

        return queryForObject(query);
    }

    public List<UpdateHistoryEntry> findLatestNUpdates(int num) {
        String query = new QueryBuilder()
                .select(UpdateHistoryContract.TABLE_NAME)
                .orderby(UpdateHistoryContract.TIME + " DESC")
                .limit(String.format("%d", num))
                .getQuery();

        return jdbcTemplate.query(query, new UpdateRowMapper());
    }

    public void insert(UpdateHistoryEntry updateHistoryEntry) {
        String query = new PreparedStatement()
                .Insert(UpdateHistoryContract.TABLE_NAME,
                        UpdateHistoryContract.TIME,
                        UpdateHistoryContract.STATUS)
                .getQuery();

        jdbcTemplate.update(query,
                new Object[]{updateHistoryEntry.getTime(), updateHistoryEntry.getStatus()},
                new int[]{Types.TIMESTAMP, Types.VARCHAR});
    }

    public void update(UpdateHistoryEntry element) {
        jdbcTemplate.update("UPDATE update_history SET status = ?, timestamp = ? WHERE id = ?",
                new Object[]{element.getStatus(), element.getTime(), element.getId()},
                new int[]{Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER});
    }

    //TODO: document
    private UpdateHistoryEntry queryForObject(String query) {
        try {
            return jdbcTemplate.queryForObject(query, new UpdateRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private class UpdateRowMapper implements RowMapper<UpdateHistoryEntry> {
        public UpdateHistoryEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt(UpdateHistoryContract.ID);
            Timestamp time = rs.getTimestamp(UpdateHistoryContract.TIME);
            Status status = Status.valueOf(rs.getString(UpdateHistoryContract.STATUS));
            return new UpdateHistoryEntry(id, time, status);
        }
    }
}
