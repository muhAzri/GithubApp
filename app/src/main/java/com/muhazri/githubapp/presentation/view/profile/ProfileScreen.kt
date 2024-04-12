package com.muhazri.githubapp.presentation.view.profile

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.muhazri.githubapp.data.model.FavouriteUserModel
import com.muhazri.githubapp.data.viewmodel.ProfileViewModel
import com.muhazri.githubapp.presentation.component.UserTile
import com.muhazri.githubapp.router.AppRouter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    DelicateCoroutinesApi::class
)
@Composable
fun ProfileScreen(
    navController: NavController
    ,login: String,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    Toast.makeText(
                        context,
                        if (!state.isFavourite) "Added to Favorites" else "Removed from Favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = FavouriteUserModel(
                        id = state.detailUser?.id ?: 0,
                        login = state.detailUser?.login ?: "",
                        avatarUrl = state.detailUser?.avatarUrl ?: ""
                    )

                    viewModel.setIsFavorite(user)
                },
                icon = {
                    Icon(
                        imageVector = if (state.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (state.isFavourite) "Remove from Favorites" else "Add to Favorites",
                        tint = if (state.isFavourite) MaterialTheme.colorScheme.error else LocalContentColor.current
                    )
                },
                text = {
                    Text(
                        text = if (state.isFavourite) "Remove from Favorites" else "Add to Favorites",
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Favourites User",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()  }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                        )
                    }
                },


                )
        },
    ) {

            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            if(state.userDetailLoading){
                ProfileSkeleton()
            }else {
                Row {
                    Image(
                        painter = rememberAsyncImagePainter(state.detailUser?.avatarUrl),
                        contentDescription = "User Image",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(shape = RoundedCornerShape(8.dp)),

                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.widthIn(0.dp, 150.dp)
                    ) {
                        Text(text = state.detailUser?.name ?: "NoName", style = MaterialTheme.typography.titleLarge )
                        Text(text = "@${state.detailUser?.login}", style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth().height(50.dp),

                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = "${state.detailUser?.followers}", style = MaterialTheme.typography.bodyLarge )
                        Text(text = "Followers", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (state.detailUser?.location !== null)Text(text = "Based On ${state.detailUser?.location}" , style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))

//            val tabTitles = listOf("Followers", "Following")
//            val selectedTabIndex = remember { mutableIntStateOf(0) }
//            TabRow(selectedTabIndex = selectedTabIndex.intValue) {
//                tabTitles.forEachIndexed { index, title ->
//                    Tab(
//                        text = { Text(text = title) },
//                        selected = selectedTabIndex.intValue == index,
//                        onClick = { selectedTabIndex.intValue = index }
//                    )
//                }
//            }
//
//            if(state.userFollowersLoading || state.userFollowingLoading){
//                UserListSkeleton()
//            } else{
//                Column(
//                    modifier = modifier.verticalScroll(rememberScrollState())
//                ) {
//                    val users = if (selectedTabIndex.intValue == 0) state.followers else state.following
//                    users.forEach { user ->
//                        UserTile(
//                            imageUrl = user.avatarUrl,
//                            name = user.login,
//                            onClick = {
//                                AppRouter.replaceScreen(navController,"Profile/${user.login}")
//                            }
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                    }
//                }
//            }

            val tabTitles = listOf("Followers", "Following")
            val pagerState = rememberPagerState(pageCount = {tabTitles.size})



            Column {
                TabRow(selectedTabIndex = pagerState.currentPage) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(text = title) },
                            selected = pagerState.currentPage == index,
                            onClick = { GlobalScope.launch {
                                pagerState.scrollToPage(index)
                            } }
                        )
                    }
                }

                if (state.userFollowersLoading || state.userFollowingLoading) {
                    UserListSkeleton()
                } else {
                    HorizontalPager(state = pagerState) { page ->
                        val users = if (page == 0) state.followers else state.following
                        Column(
                            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
                        ) {
                            users.forEach { user ->
                                UserTile(
                                    imageUrl = user.avatarUrl,
                                    name = user.login,
                                    onClick = {
                                        AppRouter.replaceScreen(navController, "Profile/${user.login}")
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }

        }
    }

    LaunchedEffect(login) {
        viewModel.getUserDetail(login)
        viewModel.getUserFollowers(login)
        viewModel.getUserFollowing(login)
        viewModel.getIsFavorite(login)
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            viewModel.resetState()
        }
    }

    errorMessage?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        viewModel.resetErrorData()
    }
}

@Composable
fun ProfileSkeleton() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .size(100.dp)
                .clip(shape = CircleShape)
                .fillMaxWidth(),

            color = Color.LightGray
        ) {}

        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                color = Color.LightGray,
            ) {}

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                color = Color.LightGray
            ) {}
        }

        Column(
            modifier = Modifier
                .padding(start=16.dp,end = 16.dp)
                .align(Alignment.CenterVertically)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                color = Color.LightGray
            ) {}

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                color = Color.LightGray
            ) {}
        }
    }
}
@Composable
fun UserListSkeleton() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        repeat(5) { _ ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(shape = CircleShape),
                    color = Color.LightGray
                ) {}

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                        .padding(start = 16.dp),
                        color = Color.LightGray
                    ) {}


                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController, login = "1234")
}

