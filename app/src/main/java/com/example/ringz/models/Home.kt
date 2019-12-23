package com.example.ringz.models

import android.media.Ringtone

class Home(uuid: String, name: String, ringtone: Ringtone) {
    var uuid: String? = null
    var name: String? = null
    var ringtone: Ringtone? = null

    init {
        this.uuid = uuid
        this.name = name
        this.ringtone = ringtone
    }
}
