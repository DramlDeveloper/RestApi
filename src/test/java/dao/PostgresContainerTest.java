package dao;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import rest.api.rest_service.dao.IPostDao;
import rest.api.rest_service.util.PropertiesUtil;

public class PostgresContainerTest {
    private static final String INIT_SQL = "script.sql";
    private static int containerPort = 5432;
    private static int localPort = 5432;
    public static IPostDao postDao;
 //   @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("postgres")
            .withUsername(PropertiesUtil.getProperty("db.username"))
            .withPassword(PropertiesUtil.getProperty("db.password"))
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
            ))
            .withInitScript(INIT_SQL);
}
