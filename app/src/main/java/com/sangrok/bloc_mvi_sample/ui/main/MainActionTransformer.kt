package com.sangrok.bloc_mvi_sample.ui.main

import com.sangrok.bloc_mvi_sample.bloc.ActionTransformer
import com.sangrok.bloc_mvi_sample.repository.MockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class MainActionTransformer(
    private val memberRepository: MockRepository,
) : ActionTransformer<MainAction> {
    override suspend fun transformActions(action: MainAction): Flow<MainAction> {
        return when (action) {
            is MainAction.ClickToggle -> toggleAction(action)
            else -> flow { emit(action) }
        }
    }

    private fun toggleAction(action: MainAction.ClickToggle): Flow<MainAction> = flow {
        // MainAction.SetSetMemberState로 toggle 변경된 상태 Action 전달
        runCatching {
            emit(
                MainAction.SetMemberState(
                    action.member.copy(liked = action.member.liked.not()),
                ),
            )
            memberRepository.like(action.member)
        }.onFailure {
            // MainAction.SetSetMemberState로 변경되기 전 상태 Action 전달
            emit(
                MainAction.SetMemberState(
                    member = action.member,
                ),
            )
        }
    }
}
