package com.example.newsapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapplication.core.viewmodels.DbViewModel
import com.example.newsapplication.core.viewmodels.NewsViewModel
import com.example.newsapplication.core.viewmodels.PermissionsViewModel

@Composable
fun MyViewPager(
    modifier: Modifier = Modifier,
    newsViewModele: NewsViewModel,
    DbViewModele: DbViewModel
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(
        initialPage = 0,
    ) { 3 }

    Column {
        TabRow(
            selectedTabIndex = selectedTab,
        ) {
            for (index in 0 until pagerState.pageCount){
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        selectedTab = index
                    }
                ){ Text(text = "Tab ${index}", modifier.padding(vertical = 8.dp)) }
            }
        }

        HorizontalPager(state = pagerState,) { currentPage->
            when (currentPage){
                0 ->{
//                    Screen1(modifier, currentPage.toString())
                    Screen(newsViewModele, dbViewModel = DbViewModele)
                }
                1 ->{
                    val permissionViewModel: PermissionsViewModel by lazy { PermissionsViewModel() }
                    Screen2(modifier, permissionViewModel)
                }
                2 ->{
                    //Screen1(modifier, currentPage.toString())
                }
                else -> {
                    Text(text = "Page Size Error!")
                }
            }
        }
        LaunchedEffect(key1 = selectedTab, key2 = pagerState.currentPage) {
            pagerState.scrollToPage(selectedTab)
            selectedTab = pagerState.currentPage
        }
//        LaunchedEffect(pagerState.currentPage) {
//            selectedTab = pagerState.currentPage
//        }
    }
}


//@Composable
//fun Screen1(modifier: Modifier = Modifier, pageNumber: String) {
//    Column(
//        modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ){
//        Text(text = "page 1 ${pageNumber}")
//    }
//}
//@Composable
//fun Screen2(modifier: Modifier = Modifier, pageNumber: String) {
//    Column(
//        modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ){
//        Text(text = "page 2 ${pageNumber}")
//    }
//}
//@Composable
//fun Screen3(modifier: Modifier = Modifier, pageNumber: String) {
//    Column(
//        modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ){
//        Text(text = "page 3 ${pageNumber}")
//    }
//}


@Preview
@Composable
private fun MyViewPagerPreview() {
    //MyViewPager(modifier = Modifier)
}