package com.netradius.sqlserver.data;

import org.hibernate.internal.util.BytesHelper;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.io.Serializable;


import java.util.UUID;

/**
 * Descriptor for {@link UUID} handling for converting sql server GUID to {@link UUID}
 *
 * @author Abhinav Nahar
 */
public class GuidTypeDescriptor extends AbstractTypeDescriptor<UUID> {
	public static final GuidTypeDescriptor INSTANCE = new GuidTypeDescriptor();

	public GuidTypeDescriptor() {
		super(UUID.class);
	}

	public String toString(UUID value) {
		return ToStringTransformer.INSTANCE.transform(value);
	}

	public UUID fromString(String string) {
		return ToStringTransformer.INSTANCE.parse(string);
	}

	/**
	 * Unwrap an instance of {@link UUID} type into the requested type.
	 * Intended use is during {@link java.sql.PreparedStatement} binding.
	 *
	 * @param value The value to unwrap
	 * @param type The type as which to unwrap
	 * @param options The options
	 * @param <X> The conversion type.
	 *
	 * @return The unwrapped value.
	 */
	@SuppressWarnings({"unchecked"})
	public <X> X unwrap(UUID value, Class<X> type, WrapperOptions options) {
		if (value == null) {
			return null;
		}
		if (UUID.class.isAssignableFrom(type)) {
			return (X) PassThroughTransformer.INSTANCE.transform(value);
		}
		if (String.class.isAssignableFrom(type)) {
			return (X) ToStringTransformer.INSTANCE.transform(value);
		}
		if (byte[].class.isAssignableFrom(type)) {
			return (X) ToBytesTransformer.INSTANCE.transform(value);
		}
		throw unknownUnwrap(type);
	}

	/**
	 * Wrap a value as our handled {@link UUID} type.
	 * Intended use is during {@link java.sql.ResultSet} extraction.
	 *
	 * @param value The value to wrap.
	 * @param options The options
	 * @param <X> The conversion type.
	 *
	 * @return The wrapped value.
	 */
	public <X> UUID wrap(X value, WrapperOptions options) {
		if (value == null) {
			return null;
		}
		if (UUID.class.isInstance(value)) {
			return PassThroughTransformer.INSTANCE.parse(value);
		}
		if (String.class.isInstance(value)) {
			return ToStringTransformer.INSTANCE.parse(value);
		}
		if (byte[].class.isInstance(value)) {
			return ToBytesTransformer.INSTANCE.parse(value);
		}
		throw unknownWrap(value.getClass());
	}

	public static interface ValueTransformer {
		public Serializable transform(UUID uuid);

		public UUID parse(Object value);
	}

	public static class PassThroughTransformer implements ValueTransformer {
		public static final PassThroughTransformer INSTANCE = new PassThroughTransformer();

		public UUID transform(UUID uuid) {
			return uuid;
		}

		public UUID parse(Object value) {
			return (UUID) value;
		}
	}

	public static class ToStringTransformer implements ValueTransformer {
		public static final ToStringTransformer INSTANCE = new ToStringTransformer();

		public String transform(UUID uuid) {
			return uuid.toString();
		}

		public UUID parse(Object value) {
			return UUID.fromString((String) value);
		}
	}

	public static class ToBytesTransformer implements ValueTransformer {
		public static final ToBytesTransformer INSTANCE = new ToBytesTransformer();

		public byte[] transform(UUID uuid) {
			byte[] bytes = new byte[16];
			System.arraycopy(BytesHelper.fromLong(uuid.getMostSignificantBits()), 0, bytes, 0, 8);

			System.arraycopy(BytesHelper.fromLong(uuid.getLeastSignificantBits()), 0, bytes, 8, 8);
			byte b0 = bytes[0], b1 = bytes[1], b2 = bytes[2], b3 = bytes[3], b4 = bytes[4], b5 = bytes[5], b6 = bytes[6], b7 = bytes[7];

			bytes[0] = b3;
			bytes[1] = b2;
			bytes[2] = b1;
			bytes[3] = b0;
			bytes[4] = b5;
			bytes[5] = b4;
			bytes[6] = b7;
			bytes[7] = b6;
			return bytes;
		}

		public UUID parse(Object value) {
			byte[] b = new byte[16];
			System.arraycopy(value, 0, b, 0, 16);
			return fromGuidByteArrayToUuid(b);
		}

		private UUID fromGuidByteArrayToUuid(byte[] bytes) {
			if (bytes != null && bytes.length == 16) {
				long msb = bytes[3] & 0xFF;
				msb = msb << 8 | (bytes[2] & 0xFF);
				msb = msb << 8 | (bytes[1] & 0xFF);
				msb = msb << 8 | (bytes[0] & 0xFF);

				msb = msb << 8 | (bytes[5] & 0xFF);
				msb = msb << 8 | (bytes[4] & 0xFF);

				msb = msb << 8 | (bytes[7] & 0xFF);
				msb = msb << 8 | (bytes[6] & 0xFF);

				long lsb = bytes[8] & 0xFF;
				lsb = lsb << 8 | (bytes[9] & 0xFF);
				lsb = lsb << 8 | (bytes[10] & 0xFF);
				lsb = lsb << 8 | (bytes[11] & 0xFF);
				lsb = lsb << 8 | (bytes[12] & 0xFF);
				lsb = lsb << 8 | (bytes[13] & 0xFF);
				lsb = lsb << 8 | (bytes[14] & 0xFF);
				lsb = lsb << 8 | (bytes[15] & 0xFF);

				return new UUID(msb, lsb);
			} else {
				return null;
			}
		}
	}
}
