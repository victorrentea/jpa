package victor.training.jpa;

import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.sql.SQLException;

public class StartDatabase {
	public static void main(String[] args) throws SQLException {
		deletePreviousDBContents();

		System.out.println("Started DB...");
		//hsqldb does not support Nested Transactions (REQUIRES_NEW).
//		org.hsqldb.server.Server.main("--database.0 mem:test --dbname.0 test".split(" "));

		// H2 does :)
		org.h2.tools.Server.createTcpServer("-ifNotExists").start();

	}

	private static void deletePreviousDBContents() {
		File userHomeFolder = new File(System.getProperty("user.home"));
		if (!userHomeFolder.isDirectory()) {
			throw new IllegalArgumentException("Could not locate userHome");
		}
		File databaseFile = new File(userHomeFolder, "test.mv.db");
		System.out.println("Database file: " + databaseFile.getAbsolutePath());
		if (databaseFile.isFile()) {
			System.out.println("Deleting previous db contents...");
			boolean ok = databaseFile.delete();
			if (!ok) {
				System.err.println("Could not delete database file ");
			} else {
				System.out.println("SUCCESS");
			}
		}
	}
}
