package com.example.roomwordsample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/*
WordViewModel that gets the Application as a parameter
and extends AndroidViewModel
 */
class WordViewModel(application: Application) : AndroidViewModel(application)
{
    //Add private member variable to hold a reference
    // to the repository
    private val repository: WordRepository
    //Add a private LiveData member variable to cache
    // the list of words.
    val allWords: LiveData<List<Word>>

    //Create an init block that gets a reference
    // to the WordDao
    //from the WordRoomDatabase and constructs
    // the WordRepository based on it.
    init {
        val wordsDao = WordRoomDatabase.getDatabase(application,viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        //In the init block, initialize the allWords property
        // with the data from repository
        allWords = repository.allWords
    }
//        Create a wrapper insert() method that calls
//        the Repository's insert() method.
//        In this way, the implementation of insert() is
//        completely hidden from the UI. We want the insert()
//        method to be called away from the main thread,
//        so we're launching a new coroutine, based on the
//        coroutine scope defined previously. Because
//        we're doing a database operation,
//        we're using the IO Dispatcher.
        fun insert(word: Word) = viewModelScope.launch() {
        repository.insert(word)
        }

    }