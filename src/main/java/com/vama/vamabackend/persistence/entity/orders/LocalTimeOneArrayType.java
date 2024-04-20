package com.vama.vamabackend.persistence.entity.orders;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalTime;

public class LocalTimeOneArrayType implements UserType {

    private final int[] SQL_TYPES = {Types.ARRAY};

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class returnedClass() {
        return LocalTime[].class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null) {
            return y == null;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        try {
             if(resultSet.getArray((names[0])).getArray() instanceof Time[]){
                 Time[] value = (Time[]) resultSet.getArray((names[0])).getArray();
                 return convertToNewFormat(value);
             }else {
                 return null;
             }
        }catch (NullPointerException e){
            return null;
        }
    }



    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            statement.setNull(index, SQL_TYPES[0]);
        } else {
            LocalTime[] castObject = (LocalTime[]) value;
            statement.setArray(index,
                    session.connection().createArrayOf("time", castObject));
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    public static LocalTime[] convertToNewFormat(Time[] times) {
        LocalTime[] newFormat = new LocalTime[times.length];
        for (int i = 0; i < times.length; i++) {
            newFormat[i] = times[i].toLocalTime();
        }
        return newFormat;
    }
}