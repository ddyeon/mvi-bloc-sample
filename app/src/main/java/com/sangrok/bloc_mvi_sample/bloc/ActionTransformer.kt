package com.sangrok.bloc_mvi_sample.bloc

import kotlinx.coroutines.flow.Flow

/**
 * 입력받은 Action을 받아 비동기적으로 각 Action을 다른 Action으로 변환하는 클래스
 */
interface ActionTransformer<ACTION> {
    suspend fun transformActions(action: ACTION): Flow<ACTION>
}