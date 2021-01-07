package com.ahmedsaber.protodatastore.repo

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.*
import com.ahmedsaber.protodatastore.other.MySerializer
import com.ahmedsaber.protodatastore.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "my_preference"
class DataStoreRepository(context: Context) {
    /**
     * this block below to use pref data store
     */
    private object PreferenceKeys {
        val firstName = preferencesKey<String>("first_name")
        val lastName = preferencesKey<String>("last_name")
        val age = preferencesKey<Int>("age")

    }
    private val preferenceDataStore: DataStore<Preferences> = context.createDataStore(
            name = PREFERENCE_NAME
    )
    suspend fun saveToPrefDataStore(firstName: String,lastName: String,age: Int){
        preferenceDataStore.edit { preference ->
            with(preference){
            this[PreferenceKeys.firstName] = firstName
            this[PreferenceKeys.lastName] = lastName
            this[PreferenceKeys.age] = age
            }
        }
    }

    val readFromPrefDataStore: Flow<List<String>> = preferenceDataStore.data
            .catch { exception ->
                if(exception is IOException){
                    Log.d("DataStore", exception.message.toString())
                    emit(emptyPreferences())
                }else {
                    throw exception
                }
            }
            .map { preference ->
                val firstName = preference[PreferenceKeys.firstName] ?: "none"
                val lastName = preference[PreferenceKeys.lastName] ?: "none"
                val age = preference[PreferenceKeys.age] ?: "none"
                listOf(firstName,lastName, age.toString())
            }

    /**
     * this block below to use proto data store
     */

    private val protoDataStore:DataStore<Person> = context.createDataStore(
        fileName = "my_data",
        serializer = MySerializer()
    )
    val readProto : Flow<Person> = protoDataStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("Error", exception.message.toString())
                emit(Person.getDefaultInstance())
            }else{
                throw exception
            }
        }
    suspend fun saveToProtoDataStore(firstName:String, lastName:String, age:Int){
        protoDataStore.updateData { preferences ->
            with(preferences.toBuilder()){
            setFirstName(firstName)
            setLastName(lastName)
            setAge(age)
            }.build()
        }
    }
}