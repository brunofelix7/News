package me.brunofelix.news.feature.presentation

sealed class UIEvent {
    data class ShowToast(val message: String): UIEvent()
    data class ShowSnackBar(val message: String): UIEvent()
}