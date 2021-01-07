package com.ahmedsaber.protodatastore.other

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.ahmedsaber.protodatastore.Person
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

class MySerializer: Serializer<Person> {
    override fun readFrom(input: InputStream): Person {
         try {
             return Person.parseFrom(input)
        } catch (exception:InvalidProtocolBufferException){
            throw CorruptionException("Can't read Proto",exception)
        }
    }

    override fun writeTo(t: Person, output: OutputStream) {
        t.writeTo(output)
    }
}