package com.example.newsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.newsapplication.core.apis.newsApi.NetworkResponse
import com.example.newsapplication.core.models.newsModel.Article
import com.example.newsapplication.core.viewmodels.DbViewModel
import com.example.newsapplication.core.viewmodels.NewsViewModel
import com.example.newsapplication.ui.theme.NewsApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //lateinit var viewModel: NewsViewModel
    private val viewModel by viewModels<NewsViewModel>()
    private val dbViewModel by viewModels<DbViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //val viewModel: NewsViewModel
            NewsApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen(viewModel,innerPadding, dbViewModel)
                }
            }
        }
    }
}

@Composable
fun Screen(viewModel: NewsViewModel, innerPadding: PaddingValues, dbViewModel: DbViewModel) {
    val newsResult by viewModel.news.collectAsState()
    var city by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
            OutlinedTextField(modifier = Modifier
                    //.weight(1f)
                    .fillMaxWidth(),
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
                    NewsScreen(
                        (newsResult as NetworkResponse.Success).data.articles,
                        dbViewModel = dbViewModel
                    )
                }
            }
        Spacer(modifier = Modifier.height(16.dp))

//        LazyColumn () {
//
//        }
    }

}

//@Composable
//fun Posts(data: List<Article>) {
//    LazyColumn(
//        modifier = Modifier,
//        state = rememberLazyListState(),
//        contentPadding = PaddingValues(10.dp),
//    ) {
////        items(data.articles){
////            data.articles.forEach {article ->
////                SinglePost(article)
////            }
////        }
//        items(data){
//            data.forEach {article ->
//                SinglePost(article)
//            }
//        }
////        itemsIndexed(articles){
////            articles.forEach {
////                SinglePost(it)
////            }
////        }
//    }
//}
//
//@Composable
//fun SinglePost(article: Article) {
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .padding(8.dp)){
//        Card (modifier = Modifier.fillMaxWidth()) {
//            AsyncImage(
//                model = ImageRequest
//                    .Builder(LocalContext.current)
//                    .data(article.image)
//                    .crossfade(enable = true)
//                    .build(),
//                contentDescription = "Loading",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(180.dp)
//                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
//            )
//        }
//        //Text(article.title, fontStyle = MaterialTheme.typography.titleMedium )
//        Text(
//            text = article.title,
//            //fontStyle = MaterialTheme.typography.titleLarge,
//            modifier = Modifier,
//            fontStyle = MaterialTheme.typography.titleMedium.fontStyle
//        )
//    }
//}


//AI Code

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(articles: List<Article>, dbViewModel: DbViewModel) {
    // A Scaffold provides the basic Material Design layout structure
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top Headlines") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),

            )
        }
    ) { innerPadding ->
        // Apply the padding provided by the Scaffold to the content
        Box(modifier = Modifier.padding(innerPadding)) {
            if (articles.isEmpty()) {
                // You can show a loading indicator or an empty state message here
                // For now, we'll just assume the list is passed in
            } else {
                ArticleList(articles = articles, dbViewModel)
            }
        }
    }
}

/**
 * Displays a scrollable list of articles using LazyColumn for performance.
 */
@Composable
fun ArticleList(articles: List<Article>, dbViewModel: DbViewModel) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(articles) { article ->
            ArticleItem(article = article, dbViewModel)
        }
    }
}

/**
 * A modern, attractive composable for displaying a single news article.
 * It uses an ElevatedCard with rounded corners for a clean, Material 3 look.
 */
@Composable
fun ArticleItem(article: Article, dbViewModel: DbViewModel) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }


    val imageLoader = ImageLoader.Builder(context)
        .diskCachePolicy(policy = CachePolicy.ENABLED)
        .memoryCachePolicy(policy = CachePolicy.ENABLED)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), // Rounded corners
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            // Image loading with Coil
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.image)
//                    .memoryCachePolicy(policy = CachePolicy.ENABLED)
//                    .diskCachePolicy(policy = CachePolicy.ENABLED)
                    .crossfade(true)
                    .build(),
                //placeholder = painterResource(R.drawable.placeholder), // Your placeholder drawable
                //error = painterResource(R.drawable.placeholder),       // Your error drawable
                contentDescription = "Image for article: ${article.title}",
                contentScale = ContentScale.Crop,
//                imageLoader = imageLoader
//                    .memoryCachePolicy(policy = CachePolicy.ENABLED)
//                    .memoryCache {
//                        MemoryCache
//                            .Builder(context)
//                            .maxSizePercent(0.1)
//                            .strongReferencesEnabled(enable = true)
//                            .build()
//                    }
//                    .diskCachePolicy(policy = CachePolicy.ENABLED)
//                    .diskCache {
//                        DiskCache.Builder()
//                            .maxSizePercent(0.1)
//                            .build()
//                            //.cleanupDispatcher(dispatcher = Dispatchers.IO)
//                    }
//                    .logger(DebugLogger())
//                    .build(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // Match top corners
            )


            // Content section with padding
            Column(modifier = Modifier.padding(16.dp)) {
                // Source Name
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Article Title
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Article Description
                Text(
                    text = article.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = article.publishedAt, // You might want to format this date
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    IconButton(
                        onClick = {
                            isFavorite = !isFavorite
                            dbViewModel.saveNews(article)
                        }
                    ) {
                        Icon(
                            //imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                            //imageVector = Icons.Outlined.Favorite,
                            contentDescription = if (isFavorite) "Remove from Favorites" else "Add to favorites",
                            tint = if (isFavorite) Color.Red else LocalContentColor.current
                        )
                    }
                }
                // Published Date
            }
        }
    }
}