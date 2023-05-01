package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ProfilePictureUtil {

	private static final String PICTURE_COLUMN_NAME = "picture";

	public static byte[] getProfilePicture(String uuid) throws SQLException {
		byte[] imageBytes = null;
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement ps = connection
						.prepareStatement("SELECT " + PICTURE_COLUMN_NAME + " FROM profile_picture WHERE uuid = ?")) {
			ps.setString(1, uuid);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					imageBytes = rs.getBytes(PICTURE_COLUMN_NAME);
				}
			}
		}
		return imageBytes;
	}

	public static void uploadProfilePicture(String uuid, byte[] profilePicture) throws SQLException {
		Objects.requireNonNull(uuid, "UUID cannot be null");
		Objects.requireNonNull(profilePicture, "Profile picture cannot be null");
		if (profilePicture.length > 10 * 1024 * 1024) {
			throw new IllegalArgumentException("Profile picture is too large");
		}
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement ps = connection
						.prepareStatement("MERGE INTO profile_picture WITH (HOLDLOCK) AS target "
								+ "USING (SELECT ? AS uuid, ? AS " + PICTURE_COLUMN_NAME + ") AS source "
								+ "ON target.uuid = source.uuid " + "WHEN MATCHED THEN " + "  UPDATE SET "
								+ PICTURE_COLUMN_NAME + " = source." + PICTURE_COLUMN_NAME + ", "
								+ "             uploaded_at = CURRENT_TIMESTAMP " + "WHEN NOT MATCHED THEN "
								+ "  INSERT (uuid, " + PICTURE_COLUMN_NAME + ", uploaded_at) "
								+ "  VALUES (source.uuid, source." + PICTURE_COLUMN_NAME + ", CURRENT_TIMESTAMP);")) {
			ps.setString(1, uuid);
			ps.setBytes(2, profilePicture);
			ps.executeUpdate();
		}
	}
}
