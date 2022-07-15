package com.sample.project.watchbox.data.repositories

import com.sample.project.watchbox.data.database.dao.MoviesDao
import com.sample.project.watchbox.data.database.entities.MovieEntity
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.data.model.mapper.moviemapper.MoviesMapper
import com.sample.project.watchbox.data.network.movies.MoviesService
import com.sample.project.watchbox.utils.RxImmediateSchedulerRule
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MoviesRepositoryTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()
    @Mock
    lateinit var moviesService: MoviesService
    @Mock
    private lateinit var moviesRepository: MoviesRepository
    @Mock
    private lateinit var moviesMapper: MoviesMapper
    @Mock
    private lateinit var moviesDao: MoviesDao

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviesRepository = MoviesRepositoryImpl(moviesService, moviesMapper, moviesDao)
    }

    @Test
    fun `when movies requested from database, should return mapped movies list from database`() {
        val movieResponse =
            listOf(
                MovieEntity(
                    "id123",
                    "movieTitle1",
                    "1999",
                    "yes",
                    "1999",
                    "140",
                    "criminal",
                    "actors list",
                    "poster",
                    "7.0",
                    "2000",
                    "160000$"
                ), MovieEntity(
                    "id1234",
                    "movieTitle2",
                    "1999",
                    "yes",
                    "1999",
                    "140",
                    "criminal",
                    "actors list",
                    "poster",
                    "7.0",
                    "2000",
                    "160000$"
                )
            )

        Mockito.`when`(moviesDao.getAllMovies()).thenReturn(Single.just(movieResponse))
        val result = moviesRepository.getMovies()

        val testObserver = TestObserver<List<DetailedMovie>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        org.junit.Assert.assertEquals(listResult.size, 2)
        org.junit.Assert.assertEquals(listResult[0].title, "movieTitle1")
        org.junit.Assert.assertEquals(listResult[1].title, "movieTitle2")
    }

    @Test
    fun `when movies requested from database, should return mapped empty list from database`() {
        val movieResponse = listOf<MovieEntity>()

        Mockito.`when`(moviesDao.getAllMovies()).thenReturn(Single.just(movieResponse))

        val result = moviesRepository.getMovies()
        val testObserver = TestObserver<List<DetailedMovie>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        val listResult = testObserver.values()[0]
        org.junit.Assert.assertEquals(listResult.size, 0)
    }

}
