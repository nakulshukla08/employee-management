package com.tech.employee_management.profile.outbound.async;

import com.tech.employee_management.events.profile.ProfileEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
@Slf4j
public class ProfileSpringEventsGateway implements AsyncGateway<ProfileEvent>{

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(ProfileEvent event) {

        log.info("Publishing employeeProfile event : {}", event);

        eventPublisher.publishEvent(event);
    }
}
