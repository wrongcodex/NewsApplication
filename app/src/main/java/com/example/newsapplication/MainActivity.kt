package com.example.newsapplication

import android.Manifest
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.newsapplication.core.apis.newsApi.NetworkResponse
import com.example.newsapplication.core.db.NewsDB.NewsEntities
import com.example.newsapplication.core.models.newsModel.Article
import com.example.newsapplication.core.utils.notifications.NotificationUtils.Companion.browserPendingIntentWithUri
import com.example.newsapplication.core.utils.notifications.NotificationUtils.Companion.launchNotification
import com.example.newsapplication.core.viewmodels.DbViewModel
import com.example.newsapplication.core.viewmodels.NewsViewModel
import com.example.newsapplication.ui.presentation.components.SingleArticleComponent
import com.example.newsapplication.ui.theme.NewsApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val REQUEST_NOTIFICATION_PERMISSION = Manifest.permission.POST_NOTIFICATIONS
    private val REQUEST_NOTIFICATION_CODE: Int = 100

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestRuntimeNotificationPermission(context: Context)
    {
        if (ContextCompat.checkSelfPermission(context, REQUEST_NOTIFICATION_PERMISSION) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "Permission Granted, You can use relative feature now", Toast.LENGTH_SHORT).show()
        }else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)){
            AlertDialog.Builder(this)
                .setTitle("Notification Permission")
                .setMessage("This application requires permission for Notification to work as expected.")
                .setCancelable(false)
                .setPositiveButton("Accept"){dialog, which->
                    ActivityCompat.requestPermissions(this, arrayOf(REQUEST_NOTIFICATION_PERMISSION) ,REQUEST_NOTIFICATION_CODE)
                    dialog.dismiss()
                }
                .setNegativeButton("Decline"){dialog, which->
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()
        }else{
            //ActivityCompat.requestPermissions(this, arrayOf(REQUEST_NOTIFICATION_PERMISSION) ,REQUEST_NOTIFICATION_PERMISSION.toInt())
            showSettingsRedirectDialog()
        }
        //onRequestPermissionsResult(REQUEST_NOTIFICATION_CODE, arrayOf(REQUEST_NOTIFICATION_PERMISSION), grantResults = IntArray, deviceId)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showSettingsRedirectDialog(){
        AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("We need notification permission to send you news updates. You can grant this in the app settings, or by clicking the button below.")
            .setCancelable(false)
            .setPositiveButton("Go to Settings"){dialog,_ ->
                dialog.dismiss()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }
                startActivity(intent)
            }
            .setNeutralButton("Grant Permission"){dialog, _ ->
                ActivityCompat.requestPermissions(this, arrayOf(REQUEST_NOTIFICATION_PERMISSION) ,REQUEST_NOTIFICATION_CODE)
                dialog.dismiss()
            }.show()
    }

    private val viewModel by viewModels<NewsViewModel>()
    private val dbViewModel by viewModels<DbViewModel>()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val cancelIntent = Intent(this, MainActivity::class.java)
            val cancelPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(cancelIntent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }



            val myChannerID = "1"
            
//            val builder = NotificationCompat.Builder(this, myChannerID)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle("FIELD MARSHAL ASIM MUNIR")
//                .setContentText("I AM VERY HAPPY TO SHOW THAT I AM FIELD MARSHAL ASIM MUNIR AT PAKISTAN ARMY")
//                .setPriority(NotificationCompat.PRIORITY_MAX)
//                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
//                .setOngoing(true)
//                .addAction(R.drawable.ic_launcher_background, "Open App", cancelPendingIntent)

            //adding cancel intent

//            val cancelActionIntent = Intent(this, MainApplication::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            }
//            val cancelPendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, cancelActionIntent,
//                PendingIntent.FLAG_IMMUTABLE)



            var isCanceled by remember { mutableStateOf(false) }
            NewsApplicationTheme {
                Surface {
                    Column (modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                        ){
                        Spacer(modifier = Modifier.height(36.dp))
//                        Button(onClick = {
//                            (requestRuntimeNotificationPermission(this@MainActivity))
////                            NotificationManagerCompat.from(this@MainActivity).apply {
////                                if (isCanceled){
////                                    cancel(0)
////                                    isCanceled = false
////                                }
////                                else{
////                                    isCanceled = true
////                                    notify(0, builder.build())
////                                }
////                            }
//                            launchNotification(this@MainActivity, "1", "New Message", "www.gomovies.com", cancelPendingIntent)
////                            if (isCanceled){
////                                cancel(0)
////                                isCanceled = false
////                            }
////                            else{
////                                isCanceled = true
////                                notify(0, builder.build())
////                            }
//                        }) {
//
//                            Text(text = if (isCanceled) "Hide Notification" else "Show Notification")
//                        }
                    }
                    MyViewPager(
                        modifier = Modifier.padding(top = 36.dp),
                        newsViewModele = viewModel,
                        dbViewModele = dbViewModel,
                        pendingIntent = cancelPendingIntent
                    )
                }
            }
        }
    }
}

@Composable
//fun Screen(viewModel: NewsViewModel, innerPadding: PaddingValues, dbViewModel: DbViewModel) {
fun Screen(viewModel: NewsViewModel, dbViewModel: DbViewModel, pendingIntent: PendingIntent?) {
    val newsResult by viewModel.news.collectAsState()
    //val articlees by dbViewModel.articlees.collectAsStateWithLifecycle()
    var city by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val favoriteArticles = dbViewModel.favorites.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            //verticalAlignment = Alignment.CenterVertically,
            //horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(modifier = Modifier,
                    //.weight(1f)
                    //.fillMaxWidth(),
                    //.height(56.dp),
                value = city,
                onValueChange = { city = it },
                label = { Text("Search for any Location") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            IconButton(onClick = {
                viewModel.getListOfNewsByCountry(city)
                isLoading = true
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search for any Location"
                )
            }
        }
            when(newsResult) {
                is NetworkResponse.Error -> {
                    Text(text = "Error ${(newsResult as NetworkResponse.Error).message}")
                }
                NetworkResponse.Loading -> {
                    if (isLoading){
                        CircularProgressIndicator()
                    }
                }
                is NetworkResponse.Success<*> -> {
                    isLoading = false
                    ShowNews(
                        (newsResult as NetworkResponse.Success).data.articles,
                        dbViewModel = dbViewModel,
                        favoriteArticles.value,
                        pendingIntent = pendingIntent
                    )
                }
            }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowNews(
    articles: List<Article>,
    dbViewModel: DbViewModel,
    favoriteArticles: List<NewsEntities>,
    pendingIntent: PendingIntent?
) {
    // A Scaffold provides the basic Material Design layout structure
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top Headlines") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(18.dp),
                expandedHeight = 18.dp
            )
        }
    ) { innerPadding ->
        // Apply the padding provided by the Scaffold to the content
        Box(modifier = Modifier.padding(innerPadding)) {
            if (articles.isEmpty()) {
                // You can show a loading indicator or an empty state message here
                // For now, we'll just assume the list is passed in
                Text("No Article Yet")
            } else {
                ArticleList(articles = articles, dbViewModel, favoriteArticles, pendingIntent = pendingIntent)
            }
        }
    }
}

/**
 * Displays a scrollable list of articles using LazyColumn for performance.
 */
@Composable
fun ArticleList(
    articles: List<Article>,
    dbViewModel: DbViewModel,
    favoriteArticles: List<NewsEntities>,
    pendingIntent: PendingIntent?
//    cancelPendingIntent: PendingIntent?
) {
    //val isFav by remember { mutableStateOf(false) }
    //val favoriteArticles by dbViewModel.articlees.collectAsStateWithLifecycle()
    val isFavorite by remember { mutableStateOf(false) }
    val context: Context = LocalContext.current
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(articles) { article ->
            val isFav = favoriteArticles.any { it.title == article.title }
            SingleArticleComponent(
                modifier = Modifier.padding(16.dp),
                article.image,
                article.title,
                article.source.name,
                article.description,
                article.publishedAt,
                isFavorite = isFav,
                onFavClick = {
                    !isFav
                    dbViewModel.saveNews(article)
                    Log.d("abcbd", "ArticleList Value of Fav in Article List2: ${isFav} ")
                    launchNotification(context, "1", "New Message", "www.gomovies.com", browserPendingIntentWithUri(context,article.image))
                },
            )
        }
    }
}
