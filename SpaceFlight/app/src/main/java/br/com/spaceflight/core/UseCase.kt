package br.com.spaceflight.core

import kotlinx.coroutines.flow.Flow

abstract class UseCase<Param, Source> {
    abstract suspend fun execute(param: Param): Flow<Source>

    open suspend operator fun invoke(param: Param): Flow<Source> = execute(param)

    abstract class NoParam<Source> : UseCase<None, Flow<Source>>() {
        abstract suspend fun execute(): Flow<Source>

        final override suspend fun execute(param: None) =
            throw UnsupportedOperationException()

        suspend operator fun invoke(): Flow<Source> = execute()
    }

    abstract class NoSource<Params> : UseCase<Params, Unit>() {
        override suspend operator fun invoke(param: Params) = execute(param)
    }

    object None
}