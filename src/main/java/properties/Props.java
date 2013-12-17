package properties;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

public class Props {

	public static void main(String... args) throws IOException {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
		ConfigurableEnvironment configurableEnvironment = classPathXmlApplicationContext.getEnvironment();
		getSomeProperties(configurableEnvironment);
		
		// load bootstrap properties
		Properties p = new Properties();
		p.load(ClassLoader.getSystemResourceAsStream("bootstrap.properties"));
		PropertiesPropertySource bootstrapPropertySource = new PropertiesPropertySource(
				"bootstrap.properties PropertiesPropertySource", p);
		
		
		
		//use  bootstrap properties as first		
		MutablePropertySources ps = configurableEnvironment.getPropertySources();
		ps.addFirst(bootstrapPropertySource);
		
		getSomeProperties(configurableEnvironment);

		//use db StaticPropertySource as last
		ps.addLast(new StaticPropertySource("db StaticPropertySource", classPathXmlApplicationContext.getBean(
				"jdbcTemplate", JdbcTemplate.class)));

		getSomeProperties(configurableEnvironment);
	}

	private static void getSomeProperties(ConfigurableEnvironment configurableEnvironment) {
		System.out.println(configurableEnvironment.getProperty("bootstrap.key1"));
		System.out.println(configurableEnvironment.getProperty("db.key1"));
	}
}
