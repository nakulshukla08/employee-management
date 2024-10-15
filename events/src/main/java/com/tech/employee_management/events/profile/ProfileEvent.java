package com.tech.employee_management.events.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ProfileEvent extends BaseEvent{

    private EventType type;
    private EmployeeState employeeState;

    enum EventType {
        CREATED, UPDATED, DELETED
    }
}
