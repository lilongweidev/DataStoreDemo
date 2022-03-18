package com.llw.datastore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.llw.datastore.data.PersonSerializer
import com.llw.datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //创建 DataStore
    val Context.studyDataStore: DataStore<PersonPreferences> by dataStore(
        fileName = "study.pb",
        serializer = PersonSerializer
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //存数据
        binding.btnPut.setOnClickListener {
            EasyDataStore.putData("name", "居家")
        }
        //取数据
        binding.btnGet.setOnClickListener {
            val data = EasyDataStore.getData("name", "办公")
            binding.textView.text = data
            Toast.makeText(this, "name: $data", Toast.LENGTH_SHORT).show()
        }
        //清空数据
        binding.btnClear.setOnClickListener {
            EasyDataStore.clearData()
        }
        //proto 存数据
        binding.btnProtoPut.setOnClickListener {
            runBlocking {
                studyDataStore.updateData {
                    it.toBuilder()
                        .setName("刘爱国")
                        .setAge(11)
                        .build()
                }
            }
        }
        //proto 取数据
        binding.btnProtoGet.setOnClickListener {
            runBlocking {
                val person = studyDataStore.data.first()
                binding.textView.text = "name: ${person.name} , age: ${person.age}"
            }
        }
    }

}