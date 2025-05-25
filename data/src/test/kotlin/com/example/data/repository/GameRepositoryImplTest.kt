
import com.example.data.general.dao.UserGeneralDao
import com.example.data.general.entities.UserGeneralEntity
import com.example.data.local.dao.UserLocalDao
import com.example.data.repository.GameRepositoryImpl
import com.example.domain.models.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class GameRepositoryImplTest {

    private val userGeneralDao: UserGeneralDao = mock()
    private val userLocalDao: UserLocalDao = mock()
    private val dummyEntity = UserGeneralEntity(
        name = "test_user",
        wins = 5,
        losses = 3,
        fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
        moves = "e2e4/e7e5/g1f3",
        side = true,
        mate = 0
    )
    private lateinit var repository: GameRepositoryImpl

    @Before
    fun setup() {
        whenever(userLocalDao.getUser()).thenReturn(flowOf(null))
        repository = GameRepositoryImpl(userGeneralDao, userLocalDao)
    }

    @Test
    fun `getPlayer returns Player if exists`() = runTest {
        whenever(userGeneralDao.getByName("test")).thenReturn(dummyEntity)

        // При необходимости замокай toPlayerDomain()
        val result = repository.getPlayer(LoginPlayerParam("test"))
        assertNotEquals(Player.Empty, result)
    }

    @Test
    fun `getPlayer returns PlayerEmpty if not exists`() = runTest {
        whenever(userGeneralDao.getByName("test")).thenReturn(null)

        val result = repository.getPlayer(LoginPlayerParam("test"))
        assertEquals(Player.Empty, result)
    }

    @Test
    fun `getAllPlayers returns collection via DAO`() = runTest {
        val dummyList = listOf(dummyEntity)
        whenever(userGeneralDao.getAll()).thenReturn(dummyList)

        val result = repository.getAllPlayers()
        assertEquals(dummyList.size, result.size)
    }

    @Test
    fun `createPlayer inserts to DAO and returns Player`() = runTest {
        val param = RegisterPlayerParam(name = "Vasya")
        val slot = argumentCaptor<UserGeneralEntity>()
        whenever(userGeneralDao.insert(slot.capture())).thenReturn(Unit)

        val result = repository.createPlayer(param)
        assertEquals("Vasya", result.name)
        verify(userGeneralDao).insert(any())
    }

    @Test
    fun `updateCurrentPlayer updates both userGeneralDao and userLocalDao if user exists`() = runTest {
        val param = Player("danil", wins = 1, losses = 2, side = SideColor.White)
        whenever(userGeneralDao.getByName("danil")).thenReturn(dummyEntity)

        repository.updateCurrentPlayer(param)

        verify(userGeneralDao).insert(any())
        verify(userLocalDao).clear()
        verify(userLocalDao).insert(any())
    }
}
