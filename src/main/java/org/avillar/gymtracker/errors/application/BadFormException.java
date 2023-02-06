package org.avillar.gymtracker.errors.application;

import org.avillar.gymtracker.base.domain.BaseEntity;

public class BadFormException extends RuntimeException {

    public <T extends BaseEntity> BadFormException(final String msg) {
        super(msg);
    }
}
