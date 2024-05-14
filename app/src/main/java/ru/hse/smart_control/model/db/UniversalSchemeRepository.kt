package ru.hse.smart_control.model.db

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.coroutines.flow.Flow
import okio.Path
import okio.Path.Companion.toPath
import ru.hse.smart_control.model.entities.universal.scheme.common.UniversalScheme

class UniversalSchemeRepository(tempFile: Path) {

    private val store: KStore<UniversalScheme> = storeOf(
        file = tempFile,
    )

    suspend fun getScheme(): UniversalScheme? {
        return store.get()
    }

    suspend fun saveScheme(scheme: UniversalScheme) {
        store.set(scheme)
    }

    suspend fun updateScheme(transform: (UniversalScheme?) -> UniversalScheme?) {
        store.update(transform)
    }

    suspend fun deleteScheme() {
        store.delete()
    }

    fun observeScheme(): Flow<UniversalScheme?> {
        return store.updates
    }
}