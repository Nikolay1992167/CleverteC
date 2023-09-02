package ru.clevertec.service.appsettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.InputStream;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppSettings {

    private Long interestRate = configuration.getLong("interestRate");
    private Long period = configuration.getLong("period");

    private static final Configuration configuration;

    static {
        final InputStream resourceAsStream = AppSettings.class.getResourceAsStream("/appsettings.yml");
        final YAMLConfiguration yamlConfiguration = new YAMLConfiguration();
        try {
            yamlConfiguration.read(resourceAsStream);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        configuration = yamlConfiguration;
    }
}
