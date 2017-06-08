package ch.bernmobil.vibe.shared;

import ch.bernmobil.vibe.shared.mapping.JourneyMapping;
import ch.bernmobil.vibe.shared.mapping.StopMapping;
import ch.bernmobil.vibe.shared.mockdata.JourneyMapperMockData;
import ch.bernmobil.vibe.shared.mockdata.ScheduleMockData;
import ch.bernmobil.vibe.shared.mockdata.StopMapperMockData;
import jooq.generated.entities.mappings.tables.records.JourneyMapperRecord;
import jooq.generated.entities.mappings.tables.records.StopMapperRecord;
import jooq.generated.entities.static_.tables.records.ScheduleRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jooq.generated.entities.mappings.Tables.STOP_MAPPER;
import static jooq.generated.entities.mappings.tables.JourneyMapper.JOURNEY_MAPPER;
import static jooq.generated.entities.static_.tables.Schedule.SCHEDULE;


public class MockProvider implements MockDataProvider {

    private QueryCollector queryCollector;

    @Override
    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {

        DSLContext create = DSL.using(SQLDialect.POSTGRES);
        MockResult[] mock = new MockResult[1];
        String sql = ctx.sql().toUpperCase();

        if (sql.startsWith("SELECT")){
            if(sql.contains("FROM SCHEDULE")) {
                List<ch.bernmobil.vibe.shared.entitiy.Schedule> mockedSchedules = ScheduleMockData.getDataSource();
                Result<ScheduleRecord> result = create.newResult(SCHEDULE);
                for(ch.bernmobil.vibe.shared.entitiy.Schedule mockSchedule : mockedSchedules) {
                    ScheduleRecord record = create.newRecord(SCHEDULE);
                    record.setValue(SCHEDULE.ID, mockSchedule.getId());
                    record.setValue(SCHEDULE.JOURNEY, mockSchedule.getJourney());
                    record.setValue(SCHEDULE.STOP, mockSchedule.getStop());
                    record.setValue(SCHEDULE.PLATFORM, mockSchedule.getPlatform());
                    record.setValue(SCHEDULE.PLANNED_ARRIVAL, mockSchedule.getPlannedArrival());
                    record.setValue(SCHEDULE.PLANNED_DEPARTURE, mockSchedule.getPlannedDeparture());
                    result.add(record);
                }
                mock[0] = new MockResult(mockedSchedules.size(), result);
            } else if(sql.contains("FROM JOURNEY_MAPPER")) {
                List<JourneyMapping> mockedJourneyMappings = JourneyMapperMockData.getDataSource();
                Result<JourneyMapperRecord> result = create.newResult(JOURNEY_MAPPER);
                for(JourneyMapping mockJourneyMapping : mockedJourneyMappings) {
                    JourneyMapperRecord record = create.newRecord(JOURNEY_MAPPER);
                    record.setValue(JOURNEY_MAPPER.ID, mockJourneyMapping.getId());
                    record.setValue(JOURNEY_MAPPER.GTFS_SERVICE_ID, mockJourneyMapping.getGtfsServiceId());
                    record.setValue(JOURNEY_MAPPER.GTFS_TRIP_ID, mockJourneyMapping.getGtfsTripId());

                    result.add(record);
                }
                mock[0] = new MockResult(mockedJourneyMappings.size(), result);
            } else if(sql.contains("FROM STOP_MAPPER")) {
                List<StopMapping> mockedStopMappings = StopMapperMockData.getDataSource();
                Result<StopMapperRecord> result = create.newResult(STOP_MAPPER);
                for(StopMapping mockStopMapping : mockedStopMappings) {
                    StopMapperRecord record = create.newRecord(STOP_MAPPER);
                    record.setValue(STOP_MAPPER.ID, mockStopMapping.getId());
                    record.setValue(STOP_MAPPER.GTFS_ID, mockStopMapping.getGtfsId());

                    result.add(record);
                }
                mock[0] = new MockResult(mockedStopMappings.size(), result);
            }
        } else if(sql.startsWith("TRUNCATE TABLE") || sql.startsWith("DELETE")) {
            queryCollector.queries.add(sql);
            queryCollector.bindings = new ArrayList<>(Arrays.asList(ctx.bindings()));
            return new MockResult[0];
        }
        else {
            return new MockResult[0];
        }

        return mock;
    }

    public void useQueryCollector(QueryCollector queryCollector) {
        this.queryCollector = queryCollector;
    }
}

class QueryCollector {
    public List<String> queries = new ArrayList<>();
    public List<Object> bindings = new ArrayList<>();
}