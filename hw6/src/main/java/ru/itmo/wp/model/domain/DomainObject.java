package ru.itmo.wp.model.domain;

import java.util.Date;

public interface DomainObject {
    void setId (long id);
    void setCreationTime (Date creationTime);
}
