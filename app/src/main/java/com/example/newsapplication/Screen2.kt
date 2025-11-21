package com.example.newsapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.newsapplication.core.viewmodels.DbViewModel
import com.example.newsapplication.ui.theme.myTitleStyle


@Composable
fun Screen2(modifier: Modifier = Modifier, dbViewModele: DbViewModel,) {

    val context = LocalContext.current
    Column (modifier = Modifier.fillMaxSize()){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.Cyan,
                contentDescription = "Arrow Icon",
                modifier = Modifier
                    .size(45.dp)
                    .clickable {

                    }
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "My Screen 2",
                modifier = Modifier,
                style = myTitleStyle(
                    color = Color.Blue,
                    fontSize = 28,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Button(
            onClick = {
                dbViewModele.getAllNews()
            }
        ) {
            Text(text = "Fetch Favorites")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row (modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {

            }) {
                Text(text = "Notification")
            }
        }
    }
}

