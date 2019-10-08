package ru.i_novus.ms.audit.exception;

import net.n2oapp.platform.i18n.Message;
import net.n2oapp.platform.i18n.UserException;

public class NotFoundException extends UserException {

    private static final Message NOT_FOUND_EXCEPTION = new Message("audit.apiException.AuditNotFound");

    public NotFoundException() {
        super(NOT_FOUND_EXCEPTION);
    }
}