package com.example.glucoguardclient.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ActivityItem(
    val title: String,
    val value: String,
    val unit: String,
    val icon: ImageVector,
    val color: Color
)