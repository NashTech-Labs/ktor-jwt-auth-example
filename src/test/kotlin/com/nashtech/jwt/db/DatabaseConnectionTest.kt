import com.nashtech.jwt.db.DatabaseConnection
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DatabaseConnectionTest {

    @Test
    fun testDbConnection() {
        // Arrange
        val expectedUrl = "jdbc:mysql://localhost:3306/ktordb"
        val expectedDriver = "com.mysql.cj.jdbc.Driver"
        val expectedUser = "root"
        val expectedPassword = "password"

        // Act
        val db = DatabaseConnection.db

        // Assert
        assertNotNull(db)
        assertEquals(expectedUrl, db.url)
        assertEquals(expectedDriver, "com.mysql.cj.jdbc.Driver")
        assertEquals(expectedUser, "root")
        assertEquals(expectedPassword, "password")
    }

    @Test
    fun testWrongDbConnectionUrl() {
        // Arrange
        val expectedUrl = "jdbc:mysql://localhost:3306/ktordb"
        val expectedDriver = "com.mysql.cj.jdbc.Driver"
        val expectedUser = "local"
        val expectedPassword = "password"

        // Act
        val db = DatabaseConnection.db

        // Assert
        assertNotNull(db)
        assertEquals(expectedUrl, db.url)
        assertEquals(expectedDriver,  "com.mysql.cj.jdbc.Driver")
        assertEquals(expectedUser, "local")
        assertEquals(expectedPassword, "password")
    }

    @Test
    fun testWrongDbConnectionCredentials() {
        // Arrange
        val expectedUrl = "jdbc:mysql://localhost:3306/ktordb"
        val expectedDriver = "com.mysql.cj.jdbc.Driver"
        val expectedUser = "root"
        val expectedPassword = "wrong_password"

        // Act
        val db = DatabaseConnection.db

        // Assert
        assertNotNull(db)
        assertEquals(expectedUrl, db.url)
        assertEquals(expectedDriver, "com.mysql.cj.jdbc.Driver")
        assertEquals(expectedUser, "root")
        assertEquals(expectedPassword, "wrong_password")
    }

}
