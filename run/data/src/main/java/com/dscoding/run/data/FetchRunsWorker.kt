package com.dscoding.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dscoding.core.domain.run.RunRepository
import com.dscoding.core.domain.util.DataError
import com.dscoding.core.domain.util.Result

class FetchRunsWorker(
    context: Context,
    params: WorkerParameters,
    private val runRepository: RunRepository
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if(runAttemptCount >= 5) {
            return Result.failure()
        }
        return when(val result = runRepository.fetchRuns()) {
            is com.dscoding.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }
            is com.dscoding.core.domain.util.Result.Success -> Result.success()
        }
    }
}