package com.example.searchbookapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections

// 유저가 여러 번 눌렀을 때 스택이 쌓이는 것을 방지 하기 위한 함수
fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}