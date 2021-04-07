package com.adnroidapp.amazingappnasa.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.adnroidapp.amazingappnasa.App

class ListNotesViewModel(application: Application) : AndroidViewModel(application) {

    val liveDataListNotes = App.db.notes().getAllNotes()
}