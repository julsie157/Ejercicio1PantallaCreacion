package com.example.personaje

import com.google.cloud.dialogflow.v2.DetectIntentResponse


interface BotReply {
    fun callback(returnResponse: DetectIntentResponse?)
}