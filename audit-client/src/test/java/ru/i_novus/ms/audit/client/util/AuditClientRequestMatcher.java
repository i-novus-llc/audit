package ru.i_novus.ms.audit.client.util;

import org.mockito.ArgumentMatcher;
import ru.i_novus.ms.audit.client.model.AuditClientRequest;
import ru.i_novus.ms.audit.client.model.AuditClientRequestParam;

import java.util.Arrays;
import java.util.Objects;

/**
 * Вспомогательный класс-матчер для mockito, toString() необходим для корректного отображения diff при падении теста
 */
public class AuditClientRequestMatcher implements ArgumentMatcher<AuditClientRequest> {

    private AuditClientRequest expected;

    public AuditClientRequestMatcher(AuditClientRequest auditClientRequest) {
        expected = auditClientRequest;
    }

    @Override
    public boolean matches(AuditClientRequest request) {
        return compareParams(request.getEventType(), expected.getEventType()) &&
                compareParams(request.getObjectType(), expected.getObjectType()) &&
                compareParams(request.getObjectId(), expected.getObjectId()) &&
                compareParams(request.getObjectName(), expected.getObjectName()) &&
                compareParams(request.getUserId(), expected.getUserId()) &&
                compareParams(request.getUsername(), expected.getUsername()) &&
                compareParams(request.getSourceWorkstation(), expected.getSourceWorkstation()) &&
                compareParams(request.getSourceApplication(), expected.getSourceApplication()) &&
                compareParams(request.getHostname(), expected.getHostname()) &&
                compareParams(request.getSender(), expected.getSender()) &&
                compareParams(request.getReceiver(), expected.getReceiver()) &&
                Objects.equals(request.getEventDate(), expected.getEventDate()) &&
                Objects.equals(request.getAuditType(), expected.getAuditType()) &&
                Objects.equals(request.getContext(), expected.getContext());
    }

    @Override
    public String toString() {
        return expected.toString();
    }

    private boolean compareParams(AuditClientRequestParam left, AuditClientRequestParam right) {
        if (right == null) {
            return left == null;
        } else {
            return Objects.equals(left.getValue(), right.getValue()) &&
                    Objects.equals(Arrays.toString(left.getArgs()), Arrays.toString(right.getArgs()));
        }
    }
}
