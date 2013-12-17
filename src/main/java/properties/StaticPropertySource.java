package properties;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class StaticPropertySource extends
		org.springframework.core.env.PropertySource<Map> {

	private final Map<String,Object> props;

	public StaticPropertySource(String name, JdbcTemplate jdbcTemplate) {
		super(name);
		
		
		List<KeyValue>  list = jdbcTemplate.query(
				"select prop_key, prop_value from properties where env is null",
				new RowMapper<KeyValue>() {
					public KeyValue mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						KeyValue ret = new KeyValue();
						ret.setKey(rs.getString("prop_key"));
						ret.setValue(rs.getString("prop_value"));
						return ret;
					}
				});

		
		props=new HashMap<String, Object>(list.size());
		for (KeyValue kv: list) {
			props.put(kv.getKey(), kv.getValue());
		}
		
		
	}

	@Override
	public Object getProperty(String name) {
		return props.get(name);
	}
}
