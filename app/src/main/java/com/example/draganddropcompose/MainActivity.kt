package com.example.draganddropcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.draganddropcompose.ui.theme.DragAndDropComposeTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.draganddropcompose.ui.theme.cardBackgroundColorDashBoard
import com.example.draganddropcompose.ui.theme.colorDashBoardBackGround

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DragAndDropComposeTheme {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ){
                    DragandDrop()
                    Spacer(modifier = Modifier.height(30.dp))
                    EditProgram()
                }
            }
        }
    }
}


@Composable
fun DragandDrop() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 17.dp)
    ) {
        val stateList = rememberLazyListState()
        var ShopItems by remember {
            mutableStateOf(
                mutableStateListOf(
                    "Leg Day 1",
                    "Pull Day 2",
                    "Push Day 3",
                    "Rest Day 4",
                    "Leg Day 5",
                    "Pull Day 6",
                    "Push Day 7"
                )
            )
        }
        val dragItems by remember {
            derivedStateOf {
                ShopItems.size
            }
        }

        var draggedItemIndex by remember { mutableStateOf<Int?>(null) }

        val dragDropState =
            rememberDragDropState(lazyListState = stateList, draggableItemsNum = dragItems,
                onMove = { fromIndex, toIndex ->
                    draggedItemIndex = toIndex
                },
                onDragStart = { index ->
                    draggedItemIndex = index
                },
                onDragStop = { index ->
                    draggedItemIndex = null
                })

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.dragContainer(dragDropState).fillMaxWidth()
                .heightIn(min = 150.dp, max = 10000.dp),
            state = stateList
        ) {
            draggableItems(
                items = ShopItems, dragDropState = dragDropState
            ) { modifier, item, index ->
                val isBeingDragged = draggedItemIndex == index
                val alpha by animateFloatAsState(
                    targetValue = if (isBeingDragged) 0.5f else 1f,
                    animationSpec = tween(durationMillis = 300), label = ""
                )

                DragItem(
                    modifier = modifier.alpha(alpha),
                    WorkoutName = item,
                )
            }
        }
    }
}


@Composable
fun DragItem(
    WorkoutName: String,
    modifier: Modifier = Modifier
) {
    Column {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackgroundColorDashBoard),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardBackgroundColorDashBoard)
                    .padding(horizontal = 21.dp, vertical = 12.dp)
            ) {
                Text(text = WorkoutName, color = Color.White)
            }
        }
    }
}



@Composable
fun EditProgram() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 17.dp)
    ) {
        val day = mutableListOf<String>(
            "MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY",
            "SATURDAY",
            "SUNDAY"
        )
        val stateList = rememberLazyListState()
        var ShopItems by remember {
            mutableStateOf(
                mutableStateListOf(
                    "Leg Day 1",
                    "Pull Day 1",
                    "Push Day 1",
                    "Rest Day 1",
                    "Leg Day 1",
                    "Pull Day 1",
                    "Push Day 1"
                )
            )
        }
        val dragItems by remember {
            derivedStateOf {
                ShopItems.size
            }
        }

        val dragDropState = rememberDragDropState(lazyListState = stateList, draggableItemsNum = dragItems,
            onMove = { fromIndex, toIndex ->
                ShopItems = ShopItems.toMutableStateList().apply { add(toIndex, removeAt(fromIndex)) }
                    .toMutableStateList()
            },
            onDragStart = { index ->


            },
            onDragStop = { index ->

            })

        
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(
            modifier = Modifier.dragContainer(dragDropState).fillMaxWidth()
                .heightIn(min = 150.dp, max = 10000.dp), state = stateList
        ) {
            draggableItems(
                items = ShopItems!!, dragDropState = dragDropState
            ) { modifier, item, index ->
                RowItem(
                    Days = day[index],
                    WorkoutName = item,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun RowItem(
    Days: String,
    WorkoutName: String,
    modifier: Modifier = Modifier
) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = Days, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackgroundColorDashBoard),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardBackgroundColorDashBoard)
                    .padding(horizontal = 21.dp, vertical = 12.dp)
            ) {
                Text(text = WorkoutName, color = Color.White)
            }
        }
    }
}