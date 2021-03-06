package ch.bernmobil.vibe.shared;

import ch.bernmobil.vibe.shared.entity.Schedule;
import ch.bernmobil.vibe.shared.entity.UpdateHistory;
import ch.bernmobil.vibe.shared.mapping.JourneyMapping;
import ch.bernmobil.vibe.shared.mapping.StopMapping;
import ch.bernmobil.vibe.shared.mockdata.JourneyMapperMockData;
import ch.bernmobil.vibe.shared.mockdata.ScheduleMockData;
import ch.bernmobil.vibe.shared.mockdata.StopMapperMockData;
import ch.bernmobil.vibe.shared.mockdata.UpdateHistoryMockData;
import java.util.stream.Collectors;
import jooq.generated.entities.mappings.tables.records.JourneyMapperRecord;
import jooq.generated.entities.mappings.tables.records.StopMapperRecord;
import jooq.generated.entities.static_.tables.records.ScheduleRecord;
import jooq.generated.entities.static_.tables.records.UpdateHistoryRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

import java.sql.SQLException;
import java.util.*;

import static jooq.generated.entities.mappings.Tables.STOP_MAPPER;
import static jooq.generated.entities.mappings.tables.JourneyMapper.JOURNEY_MAPPER;
import static jooq.generated.entities.static_.Tables.UPDATE_HISTORY;
import static jooq.generated.entities.static_.tables.Schedule.SCHEDULE;


public class MockProvider implements MockDataProvider {

    private QueryCollector queryCollector;
    private boolean actLikeUpdateHistoryIsEmpty;
    private boolean actLikeUpdateHistoryhasValidCollision;
    private boolean actLikeUpdateHistoryhasInvalidCollision;

    public void cleanFlags() {
        actLikeUpdateHistoryIsEmpty = false;
        actLikeUpdateHistoryhasValidCollision = false;
        actLikeUpdateHistoryhasInvalidCollision = false;
    }

    public void actLikeUpdateHistoryIsEmpty() {
        this.actLikeUpdateHistoryIsEmpty = true;
    }

    public void actLikeUpdateHistoryhasValidCollision() {
        this.actLikeUpdateHistoryhasValidCollision = true;
    }

    public void actLikeUpdateHistoryhasInvalidCollision() {
        this.actLikeUpdateHistoryhasInvalidCollision = true;
    }

    @Override
    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {

        DSLContext create = DSL.using(SQLDialect.POSTGRES);
        MockResult[] mock = new MockResult[1];
        String sql = ctx.sql().toUpperCase();

        if(queryCollector != null) {
            queryCollector.queries.add(sql);
            queryCollector.bindings.add(new ArrayList<>(Arrays.asList(ctx.bindings())));
        }

        if (sql.startsWith("SELECT")){
            if(sql.contains("FROM SCHEDULE")) {
                List<Schedule> mockedSchedules = ScheduleMockData.getDataSource();
                Result<ScheduleRecord> result = create.newResult(SCHEDULE);
                for(Schedule mockSchedule : mockedSchedules) {
                    ScheduleRecord record = createScheduleRecord(create, mockSchedule);
                    result.add(record);
                }
                mock[0] = new MockResult(mockedSchedules.size(), result);
            } else if(sql.contains("FROM JOURNEY_MAPPER")) {
                List<JourneyMapping> mockedJourneyMappings = JourneyMapperMockData.getDataSource();
                Result<JourneyMapperRecord> result = create.newResult(JOURNEY_MAPPER);
                for(JourneyMapping mockJourneyMapping : mockedJourneyMappings) {
                    JourneyMapperRecord record = createJourneyMapperRecord(create, mockJourneyMapping);
                    result.add(record);
                }
                mock[0] = new MockResult(mockedJourneyMappings.size(), result);
            } else if(sql.contains("FROM STOP_MAPPER")) {
                List<StopMapping> mockedStopMappings = StopMapperMockData.getDataSource();
                Result<StopMapperRecord> result = create.newResult(STOP_MAPPER);
                for(StopMapping mockStopMapping : mockedStopMappings) {
                    StopMapperRecord record = createStopMapperRecord(create, mockStopMapping);
                    result.add(record);
                }
                mock[0] = new MockResult(mockedStopMappings.size(), result);
            } else if(sql.contains("FROM UPDATE_HISTORY") && !actLikeUpdateHistoryIsEmpty) {
                List<UpdateHistory> mockedUpdateHistoryEntries = getUpdateHistoryEntries(sql, ctx);



                Result<UpdateHistoryRecord> result = create.newResult(UPDATE_HISTORY);
                for(UpdateHistory mockUpdateHistory : mockedUpdateHistoryEntries) {
                    UpdateHistoryRecord record = createUpdateHistoryRecord(create, mockUpdateHistory);
                    result.add(record);
                }
                mock[0] = new MockResult(mockedUpdateHistoryEntries.size(), result);
            }
        }

        return mock[0] != null ? mock : new MockResult[0];
    }

    private List<UpdateHistory> getUpdateHistoryEntries(String sql, MockExecuteContext ctx) {
        List<UpdateHistory> mockedUpdateHistoryEntries;
        if(actLikeUpdateHistoryhasValidCollision) {
            mockedUpdateHistoryEntries = UpdateHistoryMockData.getDataSourceWithValidUpdateCollision();
        } else if(actLikeUpdateHistoryhasInvalidCollision) {
            mockedUpdateHistoryEntries = UpdateHistoryMockData.getDataSourceWithInvalidUpdateCollision();
        } else {
            mockedUpdateHistoryEntries = UpdateHistoryMockData.getDataSource();
        }

        if(sql.contains("ORDER BY TIME DESC")) {
            mockedUpdateHistoryEntries = mockedUpdateHistoryEntries.stream()
                .sorted(Comparator.comparing(UpdateHistory::getTime)).collect(Collectors.toList());
            Collections.reverse(mockedUpdateHistoryEntries);
        }

        if(sql.contains("LIMIT ?")) {
            int numRecords = (int) ctx.bindings()[ctx.bindings().length-1];
            mockedUpdateHistoryEntries = mockedUpdateHistoryEntries.subList(0, numRecords);
        }
        return mockedUpdateHistoryEntries;
    }

    private ScheduleRecord createScheduleRecord(DSLContext create, Schedule mockSchedule) {
        ScheduleRecord record = create.newRecord(SCHEDULE);
        record.setValue(SCHEDULE.ID, mockSchedule.getId());
        record.setValue(SCHEDULE.JOURNEY, mockSchedule.getJourney());
        record.setValue(SCHEDULE.STOP, mockSchedule.getStop());
        record.setValue(SCHEDULE.PLATFORM, mockSchedule.getPlatform());
        record.setValue(SCHEDULE.PLANNED_ARRIVAL, mockSchedule.getPlannedArrival());
        record.setValue(SCHEDULE.PLANNED_DEPARTURE, mockSchedule.getPlannedDeparture());
        return record;
    }

    private UpdateHistoryRecord createUpdateHistoryRecord(DSLContext create, UpdateHistory mockUpdateHistory) {
        UpdateHistoryRecord record = create.newRecord(UPDATE_HISTORY);
        record.setValue(UPDATE_HISTORY.TIME, mockUpdateHistory.getTime());
        record.setValue(UPDATE_HISTORY.STATUS, mockUpdateHistory.getStatus().toString());
        return record;
    }

    private StopMapperRecord createStopMapperRecord(DSLContext create, StopMapping mockStopMapping) {
        StopMapperRecord record = create.newRecord(STOP_MAPPER);
        record.setValue(STOP_MAPPER.ID, mockStopMapping.getId());
        record.setValue(STOP_MAPPER.GTFS_ID, mockStopMapping.getGtfsId());
        return record;
    }

    private JourneyMapperRecord createJourneyMapperRecord(DSLContext create, JourneyMapping mockJourneyMapping) {
        JourneyMapperRecord record = create.newRecord(JOURNEY_MAPPER);
        record.setValue(JOURNEY_MAPPER.ID, mockJourneyMapping.getId());
        record.setValue(JOURNEY_MAPPER.GTFS_SERVICE_ID, mockJourneyMapping.getGtfsServiceId());
        record.setValue(JOURNEY_MAPPER.GTFS_TRIP_ID, mockJourneyMapping.getGtfsTripId());
        return record;
    }

    void useQueryCollector(QueryCollector queryCollector) {
        this.queryCollector = queryCollector;
    }

    static class QueryCollector {
        private List<String> queries = new ArrayList<>();
        private List<List<Object>> bindings = new ArrayList<>();

        List<String> getQueries() {
            return queries;
        }

        List<List<Object>> getBindings() {
            return bindings;
        }
    }
}

