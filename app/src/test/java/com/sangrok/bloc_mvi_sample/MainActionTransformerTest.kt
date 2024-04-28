package com.sangrok.bloc_mvi_sample

import com.sangrok.bloc_mvi_sample.bloc.ActionTransformer
import com.sangrok.bloc_mvi_sample.repository.MockRepository
import com.sangrok.bloc_mvi_sample.ui.main.MainAction
import com.sangrok.bloc_mvi_sample.ui.main.MainActionTransformer
import com.sangrok.bloc_mvi_sample.ui.main.Member
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActionTransformerTest {

    @Mock
    lateinit var mockRepository: MockRepository

    lateinit var mainActionTransformer: ActionTransformer<MainAction>

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        mainActionTransformer = MainActionTransformer(mockRepository)
    }

    // ClickToggle 액션에 대한 ActionTransformer 테스트 작성하기
    @Test
    fun `GIVEN 멤버 목록에서 WHEN 토글을 클릭했을 때 THEN 해당 멤버 상태를 바꾼다`() = runTest {
        val clickedMember = Member(
            name = "다연",
            liked = false,
        )
        //  whenever(mockRepository.like(clickedMember)).thenReturn()

        val action = MainAction.ClickToggle(clickedMember)
        val actual = mainActionTransformer.transformActions(action).first()

        val expect = MainAction.SetMemberState(member = clickedMember.copy(liked = clickedMember.liked.not()))
        Assert.assertEquals(expect, actual)
    }
}
