package org.team2658.nautilus.android

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlin.random.Random

class SyncTrigger(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        setProgress(workDataOf("progress" to Random.nextInt()))
        println("Triggering background sync...")
        return Result.success()
    }
}