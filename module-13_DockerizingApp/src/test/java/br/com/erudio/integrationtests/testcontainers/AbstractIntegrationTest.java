package br.com.erudio.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		// Alteração mínima: especificar versão do MySQL e adicionar configuração básica
		static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.28")
				.withDatabaseName("testdb")
				.withUsername("testuser")
				.withPassword("testpass");

		private static void startContainers() {
			Startables.deepStart(Stream.of(mysql)).join();
			// Adição mínima: pequena pausa para garantir que o MySQL está pronto
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		private static Map<String, String> createConnectionConfiguration() {
			return Map.of(
					"spring.datasource.url", mysql.getJdbcUrl(),
					"spring.datasource.username", mysql.getUsername(), // Corrigido de 'name' para 'username'
					"spring.datasource.password", mysql.getPassword()
			);
		}

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			MapPropertySource testContainers = new MapPropertySource(
					"testContainers",
					(Map) createConnectionConfiguration()
			);
			environment.getPropertySources().addFirst(testContainers);
		}
	}
}