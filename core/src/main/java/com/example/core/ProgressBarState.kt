package com.example.core

sealed class ProgressBarState {
    object Idle: ProgressBarState()
    object Loading: ProgressBarState()
}