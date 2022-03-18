package com.llw.datastore.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.llw.datastore.PersonPreferences
import java.io.InputStream
import java.io.OutputStream

/**
 * Person 序列化器
 * @author llw
 */
object PersonSerializer : Serializer<PersonPreferences> {

    override val defaultValue: PersonPreferences = PersonPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): PersonPreferences {
        try {
            return PersonPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: PersonPreferences, output: OutputStream) = t.writeTo(output)
}