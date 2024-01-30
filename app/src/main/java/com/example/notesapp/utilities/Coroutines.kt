package com.example.notesapp.utilities

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines {
    fun io(work: suspend (() -> Unit)) {
        CoroutineScope(Dispatchers.IO).launch { work() }
    }
    //Job: nắm giữ thông tin về lifecycle của coroutine
    //
    //Dispatcher: Quyết định thread nào mà coroutine sẽ chạy trên đó. Có các loại dispatcher sau:
    //Dispatchers.IO: chạy trên background thread của thread pool. Thường được dùng khi Read, write files, Database, Networking
}