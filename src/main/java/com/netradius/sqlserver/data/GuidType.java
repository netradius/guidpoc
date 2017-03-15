package com.netradius.sqlserver.data;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractStandardBasicType;
import org.hibernate.type.LiteralType;
import org.hibernate.type.SingleColumnType;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A type mapping {@link java.sql.Types#BINARY} and {@link UUID}
 *
 * @author Abhinav Nahar
 */
public class GuidType extends AbstractStandardBasicType<UUID>
		implements SingleColumnType<UUID>, LiteralType<UUID> {

	public static final GuidType INSTANCE = new GuidType();

	public GuidType() {
		super( BinaryTypeDescriptor.INSTANCE, GuidTypeDescriptor.INSTANCE );
	}

	@Override
	public final int sqlType() {
		return 0;
	}

	@Override
	public final void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
			throws HibernateException, SQLException {
		if ( settable[0] ) {
			nullSafeSet( st, value, index, session );
		}
	}

	@Override
	public String getName() {
		return "guid-binary";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}

	@Override
	public String objectToSQLString(UUID value, Dialect dialect) throws Exception {
		return value.toString();
	}
}