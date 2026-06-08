package br.com.energymng;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.time.Duration;

@Slf4j
@Configuration
@EnableScheduling
@EnableRetry
@EnableSchedulerLock(defaultLockAtMostFor = "${spring.modulith.retry.lock-at-most-for:PT90S}")
@RequiredArgsConstructor
public class EventPublicationRetryScheduler {

    private static final Duration RETRY_OLDER_THAN = Duration.ofMinutes(1);

    private final IncompleteEventPublications incompleteEventPublications;

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
            JdbcTemplateLockProvider.Configuration.builder()
                .withJdbcTemplate(new JdbcTemplate(dataSource))
                .usingDbTime()
                .build()
        );
    }

    @Scheduled(
        fixedDelayString   = "${spring.modulith.retry.fixed-delay-ms:60000}",
        initialDelayString = "${spring.modulith.retry.initial-delay-ms:60000}"
    )
    @SchedulerLock(
        name            = "retryIncompletePublications",
        lockAtMostFor   = "${spring.modulith.retry.lock-at-most-for:PT90S}",
        lockAtLeastFor  = "${spring.modulith.retry.lock-at-least-for:PT45S}"
    )
    public void retryIncompletePublications() {
        log.debug("Checking for incomplete event publications older than {}…", RETRY_OLDER_THAN);
        try {
            incompleteEventPublications.resubmitIncompletePublicationsOlderThan(RETRY_OLDER_THAN);
        } catch (Exception e) {
            log.warn("Error during incomplete event publication retry", e);
        }
    }
}