package com.android.pinchzoom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.android.pinchzoom.ui.theme.PinchZoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinchZoomTheme {

                var scale by remember {
                    mutableStateOf(1f)
                }

                var offset by remember {
                    mutableStateOf(Offset.Zero)
                }

                BoxWithConstraints (
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(894f / 893f)
                ){

                    val  state = rememberTransformableState { zoomChange, panChange, rotationChange ->
                        scale = (scale * zoomChange).coerceIn(1f, 5f)


                        val extraWidth = (scale - 1) * constraints.maxWidth
                        val extraHeight = (scale - 1) * constraints.maxHeight

                        val maxX = extraWidth / 2
                        val maxY = extraHeight / 2

                        offset = Offset(
                            x = (offset.x + panChange.x).coerceIn(-maxX, maxX) ,
                            y = (offset.y + panChange.y).coerceIn(-maxY, maxY)
                        )

                    }

                    Image(
                        painter = painterResource(id = R.drawable.cattt),
                        contentDescription = "Cat",
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
//                                rotationZ = rotation
                                translationX = offset.x
                                translationY = offset.y
                            }
                            .transformable(state)
                    )

                }
            }
        }
    }
}

