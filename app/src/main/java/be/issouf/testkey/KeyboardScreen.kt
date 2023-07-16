package be.issouf.testkey

import android.inputmethodservice.InputMethodService
import android.text.style.LineHeightSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun KeyboardScreen(){
    val keyMatrix = arrayOf(
        arrayOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
        arrayOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
        arrayOf("\uD822\uDF54\uD81B\uDFE4", "X", "C", "V", "B", "N", "M")
    )
    Column(
        modifier = Modifier
            .background(Color(0xFF9575CD))
            .fillMaxWidth()
    ) {
        keyMatrix.forEach { row ->
            FixedHeightBox(modifier = Modifier.fillMaxWidth(), height = 56.dp){
                Row(Modifier) {
                    row.forEach { key ->
                        KeyboardKey(keyboardKey = key, modifier = Modifier.weight(1f))

                    }

                }
            }
        }

    }
}

@Composable
fun FixedHeightBox(modifier: Modifier, height: Dp, content: @Composable () -> Unit){
    Layout(modifier = modifier, content = content){ measurables, constraints ->
        val placebles = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        val h = height.roundToPx()
        layout(constraints.maxWidth, h){
            placebles.forEach{ placeable ->
                placeable.place(x = 0, y = kotlin.math.min(0, h - placeable.height))
            }
        }
    }
}

@Composable
fun KeyboardKey(
    keyboardKey: String,
    modifier: Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val ctx = LocalContext.current

    Box(
        modifier = modifier.fillMaxHeight(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            keyboardKey,
            Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .border(1.dp, Color.Black)
                .clickable(interactionSource = interactionSource, indication = null) {
                    (ctx as IMEService).currentInputConnection.commitText(
                        keyboardKey,
                        keyboardKey
                            .length
                    )
                }
                .background(Color.White)
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp,
                    bottom = 16.dp
                )
        )
        if(pressed.value){
            Text(
                keyboardKey,
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
                    .background(Color.White)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 48.dp
                    ),
            fontFamily = FontFamily(Font(R.font.test))
            )
        }
    }

}