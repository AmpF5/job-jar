package org.jobjar.feeder.infrastructure.clients;

import java.util.List;

public interface BaseHttpClient<T> {
    List<T> getRequest();
}
