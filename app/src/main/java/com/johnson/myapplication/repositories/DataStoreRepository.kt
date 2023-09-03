package com.johnson.myapplication.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject


/**
 *created by Ronnie Otieno on 13-Feb-21.
 **/
class DataStoreRepository @Inject constructor(context: Context) {
    private val Context.createDataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")
    private val isDialogShown = booleanPreferencesKey("dialog_shown")
    private val dataStore = context.createDataStore

    suspend fun saveDialogShown() {
        dataStore.edit { settings ->
            settings[isDialogShown] = true
        }
    }

    val isDialogShownFlow: Flow<Boolean?> = dataStore.data
        .map { preferences ->
            preferences[isDialogShown]
        }
}