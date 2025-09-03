package org.jobjar.jobjarapi.infrastructure.clients;

import java.util.List;

public interface BaseHttpClient<T> {
    List<T> getRequest();
}
