package com.tech.employee_management.profile.outbound.async;


import com.tech.employee_management.events.profile.BaseEvent;

public interface AsyncGateway<T extends BaseEvent> {

    void publish(T event);

}
