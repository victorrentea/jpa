package victor.training.jpa.perf;

import lombok.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapaDegenerata {
    public static void main(String[] args) {

        List<A> toate = new ArrayList<>();

        DataSource dataSource;
        JdbcTemplate jdbcTemplate = new JdbcTemplate(null);
        jdbcTemplate.query("SELECT 1", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {

            }
        });
        for (A elem : toate) {
//            elem.getCustomerId();
//            elem.getOrderIds()

        }

        Map<Long, List<Long>> customerIdToOrderIds = new HashMap<>();

        for (Long customerId : customerIdToOrderIds.keySet()) {
            List<Long> orderIds = customerIdToOrderIds.get(customerId);


            // mai departe cu Customer id si lista de order
        }
    }
}

@Value
class A {
    long customerId;
    long []orderIds;
}