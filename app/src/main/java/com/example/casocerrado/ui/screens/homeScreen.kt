package com.example.casocerrado.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.casocerrado.R

@Composable
fun HomeScreen(
    detectiveName: String = "Detective",
    onNavigateToList: () -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToClosedCases: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Case Closed",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Your cases, always under control",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_banner),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = "Hello, $detectiveName",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "The truth always leaves a trace",
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MenuCard(
                title = "My cases",
                subtitle = "Review your investigations",
                modifier = Modifier.weight(1f),
                onClick = onNavigateToList
            )
            MenuCard(
                title = "New case",
                subtitle = "Register a new case",
                modifier = Modifier.weight(1f),
                onClick = onNavigateToCreate
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MenuCard(
                title = "Statistics",
                subtitle = "Your activity in numbers",
                modifier = Modifier.weight(1f),
                onClick = onNavigateToStats
            )
            MenuCard(
                title = "Closed cases",
                subtitle = "Review the finished cases",
                modifier = Modifier.weight(1f),
                onClick = onNavigateToClosedCases
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "\"Observe. Analyze. Conclude.\"",
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun MenuCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() } 
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}