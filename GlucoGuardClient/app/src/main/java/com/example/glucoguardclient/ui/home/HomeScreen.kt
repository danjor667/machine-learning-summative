package com.example.glucoguardclient.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.glucoguardclient.data.ActivityItem
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.glucoguardclient.NavigationEvent

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    activities: List<ActivityItem>,
    viewModel: HomeViewModel,
    onNavigateToPredict: (String) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val navigationEvent by viewModel.navigationEvent.collectAsState()

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.NavigateToPredictScreen -> {
                val token = (navigationEvent as NavigationEvent.NavigateToPredictScreen).token
                onNavigateToPredict(token)
                viewModel.onNavigationHandled()
            } else -> { }
        }
    }

    val backgroundColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.surface
    }
    val textColor = Color.White

    BackHandler(enabled = true) {
        // Do nothing, prevent navigation backward
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        TopBar()
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items(activities) { activity ->
                ActivityCard(activity)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Button(
            onClick = { viewModel.navigateToPredict() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            Text("Make Prediction", color = textColor)
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun TopBar() {
    TopAppBar(title = { Text("Daily Activities", color = Color.White) }, navigationIcon = {
            IconButton(onClick = {  }) {
                Icon(Icons.Filled.Face, contentDescription = "Back", tint = Color.White)
            }
        },
        colors = TopAppBarColors(Color.Black, Color.Gray, Color.White, Color.Gray, Color.White)) //todo fix
}

@Composable
fun ActivityCard(activity: ActivityItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.value,
                    color = Color.Black,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${activity.title} (${activity.unit})",
                    color = Color.Black.copy(alpha = 0.5F),
                    fontSize = 18.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(activity.color),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = activity.icon,
                    contentDescription = activity.title,
                    tint = Color.Black
                )
            }
        }
    }
}


@ExperimentalMaterial3Api
@Preview
@Composable
fun ShowPage(){
    val activities = listOf(
        ActivityItem(
            title = "Running Distance",
            value = "7.25",
            unit = "km",
            icon = Icons.Filled.Face,
            color = Color(0xFFFFB3BA)
        ),
        ActivityItem(
            title = "Average Running Time",
            value = "58",
            unit = "minutes",
            icon = Icons.Filled.ThumbUp,
            color = Color(0xFFBAE1FF)
        ),
        ActivityItem(
            title = "Average Blood Sugar",
            value = "107",
            unit = "mg/dL",
            icon = Icons.Filled.Home,
            color = Color(0xFFBFFCC6)
        ),
        ActivityItem(
            title = "Average Blood Sugar",
            value = "107",
            unit = "mg/dL",
            icon = Icons.Filled.Home,
            color = Color(0xFFBFFCC6)
        ),
        ActivityItem(
            title = "Average Blood Sugar",
            value = "107",
            unit = "mg/dL",
            icon = Icons.Filled.Home,
            color = Color(0xFFBFFCC6)
        ),
        ActivityItem(
            title = "Average Blood Sugar",
            value = "107",
            unit = "mg/dL",
            icon = Icons.Filled.Home,
            color = Color(0xFFBFFCC6)
        )
    )

    HomeScreen(activities = activities, viewModel = HomeViewModel(""), {})

}
