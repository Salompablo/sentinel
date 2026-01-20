package com.psp.sentinel.config;

import com.psp.sentinel.model.entity.ServerEntity;
import com.psp.sentinel.model.enums.ServerStatus;
import com.psp.sentinel.repository.ServerRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ServerRepository serverRepository;

    public DataSeeder(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public void run(String... args) throws Exception {

            if (serverRepository.count() == 0) {
                Faker faker = new Faker();
                List<ServerEntity> servers = new ArrayList<>();

                for (int i = 0; i < 20; i++) {

                    String randomName = faker.computer().platform().toLowerCase() + "-" +
                            faker.internet().domainWord() + "-" +
                            faker.number().digits(3);

                    ServerEntity server = ServerEntity.builder()
                            .name(randomName)
                            .ipAddress(faker.internet().ipV4Address())
                            .region(faker.address().countryCode())
                            .status(faker.options().option(ServerStatus.class))
                            .build();

                    servers.add(server);
                }
                serverRepository.saveAll(servers);
                System.out.println("✅ DataSeeder: " + servers.size() + " servers generated with Datafaker");
            } else {
                System.out.println("Database already has servers in");
            }
        }
}
