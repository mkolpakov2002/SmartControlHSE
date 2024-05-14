import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import okio.Path.Companion.toPath
import ru.hse.smart_control.model.db.UniversalSchemeRepository
import ru.hse.smart_control.model.entities.universal.scheme.common.UniversalScheme
import java.nio.file.Files
import java.nio.file.Path

@OptIn(ExperimentalCoroutinesApi::class)
class UniversalSchemeRepositoryTest {

    private lateinit var tempFile: okio.Path
    private lateinit var repository: UniversalSchemeRepository
    private val json = Json { prettyPrint = true }

    @BeforeEach
    fun setUp() {
        // временный файл для тестирования, чтобы избежать взаимодействия с реальной ФС
        tempFile = "test.json".toPath()

        // Инициализация репозитория
        repository = UniversalSchemeRepository(tempFile)
    }

    @Test
    fun `test getScheme`() = runTest {
        val scheme = repository.getScheme()
        assertEquals(1L, scheme?.id)
        coVerify(exactly = 1) { repository.getScheme() }
    }

    @Test
    fun `test saveScheme`() = runTest {
//        val scheme = UniversalScheme(2, mutableListOf(ROSDeviceObject()))
//
//        repository.saveScheme(scheme)
//
//        coVerify(exactly = 1) { repository.saveScheme(scheme) }
    }

    @Test
    fun `test updateScheme`() = runTest {
//        val scheme = UniversalScheme(3, mutableListOf(ArduinoTypeConnectionObject()))
//
//        repository.updateScheme { scheme }
//
//        coVerify(exactly = 1) { repository.updateScheme(any()) }
    }

    @Test
    fun `test deleteScheme`() = runTest {
        repository.deleteScheme()

        coVerify(exactly = 1) { repository.deleteScheme() }
    }

    @Test
    fun `test observeScheme`() = runTest {
        val schemeFlow = repository.observeScheme().first()

        assertEquals(1L, schemeFlow?.id)
    }
}