package com.example.newsapplication.ui.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun SingleArticleComponent (
    modifier: Modifier = Modifier,
    articleImage: String,
    articleTitle: String,
    articleSource: String,
    articleDescription: String,
    articlePublishedAt: String,
    onFavClick: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }


//    val imageLoader = ImageLoader.Builder(context)
//        .diskCachePolicy(policy = CachePolicy.ENABLED)
//        .memoryCachePolicy(policy = CachePolicy.ENABLED)
//        .memoryCache {
//            MemoryCache.Builder(context)
//                .maxSizePercent(0.25)
//                .build()
//        }
//        .diskCache(
//            diskCache = DiskCache
//                .Builder()
//                .directory(context.cacheDir.resolve("image_cache"))
//                .build()
//        )
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), // Rounded corners
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            // Image loading with Coil
            AsyncImage(
                model = articleImage,
                contentDescription = "Image for article: ${articleTitle}",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // Match top corners
            )

            Column(modifier = modifier.padding(16.dp)) {
                // Source Name
                Text(
                    text = articleSource,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = modifier.height(8.dp))

                // Article Title
                Text(
                    text = articleTitle,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = modifier.height(4.dp))

                // Article Description
                Text(
                    text = articleDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = modifier.height(8.dp))

                Row(modifier = modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = articlePublishedAt, // You might want to format this date
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    IconButton(
                        onClick = {
                            onFavClick(isFavorite)
                            isFavorite = !isFavorite
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


//@Preview (showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewSingleArticle(){
//    val sampleArticle = NewsEntities(
//        // You can leave roomId as default since it's auto-generated
//        content = "This is the full content of the article. It can be a longer piece of text to see how it wraps and fits within the component design.",
//        description = "This is a sample description for a news article to be displayed in the preview.",
//        id = "sample-id-123",
//        image = "https://example.com/image.jpg", // You can use a placeholder URL
//        lang = "en",
//        publishedAt = "2025-11-27T10:00:00Z",
//        source = "Sample News Source",
//        title = "This is a Sample Article Title",
//        url = "https://example.com/sample-article"
//    )
//    SingleArticleComponent(
//        modifier = Modifier,
//        newsEntities = sampleArticle,
//        onFavClick = {},
//        onArticleClick = {}
//    ) {
//
//    }
//}