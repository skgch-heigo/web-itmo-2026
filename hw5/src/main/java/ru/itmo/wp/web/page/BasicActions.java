package ru.itmo.wp.web.page;

import java.util.Map;

public interface BasicActions {
    default void action(Map<String, Object> view) {
        view.put("interfaceAction", "hiii");
    }
}
