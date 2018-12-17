package ru.i_novus.ms.audit.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;


public class JsonbType implements UserType {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class<JsonObject> returnedClass() {
        return JsonObject.class;
    }

    @Override
    public boolean equals(final Object x, final Object y) {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(final Object x) {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor implementor, Object o) throws SQLException {
        final String json = resultSet.getString(strings[0]);
        if (json == null) {
            return null;
        }
        try {
            return MAPPER.readTree(json).asText();
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor implementor) throws SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.OTHER);
            return;
        }
        try {
            preparedStatement.setObject(i, MAPPER.writeValueAsString(o), Types.OTHER);
        } catch (IOException ex) {
            throw new PersistenceException("Failed to convert JsonObject to String: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Object deepCopy(Object value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(final Object value) {
        return (Serializable) this.deepCopy(value);
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner) {
        return deepCopy(cached);
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) {
        return deepCopy(original);
    }
}