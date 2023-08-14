package com.tech.employee_management.events.profile;

import java.time.LocalDateTime;
import java.util.UUID;


public class BaseEvent {

    protected UUID eventId;
    protected LocalDateTime timestamp;
}
