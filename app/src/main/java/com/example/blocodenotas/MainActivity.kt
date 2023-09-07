package com.example.blocodenotas

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blocodenotas.data_store.StoreAnotacao
import com.example.blocodenotas.ui.theme.BlocoDeNotasTheme
import com.example.blocodenotas.ui.theme.black
import com.example.blocodenotas.ui.theme.white
import com.example.blocodenotas.ui.theme.yellow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlocoDeNotasTheme {
            LayoutBloco()
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LayoutBloco(){

    val context = LocalContext.current

    val remember = rememberCoroutineScope()

    val salvarAnotacao = StoreAnotacao(context)
    val recuperacaoAnotacao = salvarAnotacao.getAnotacao.collectAsState(initial = "")

    var anotacao by remember{
        mutableStateOf("")
    }

    anotacao = recuperacaoAnotacao.value

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = yellow
            ) {
                Text(
                    text = "Bloco de Notas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = black

                )
            }
        },
        floatingActionButton = {
            androidx.compose.material.FloatingActionButton(
                onClick = {
                    remember.launch {
                        salvarAnotacao.salvarAnotacao(anotacao)
                        Toast.makeText(context, "Anotação salva com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                },
                backgroundColor = yellow,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)

            ){
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_save_24),
                    contentDescription = "Botão de salvar")
            }
        }
    ) {
        Column {
            TextField(
                value = anotacao,
                onValueChange = {
                    anotacao = it
                },
                textStyle = TextStyle(
                    fontSize = 18.sp
                ),
                label = {
                    Text(text = "Digite sua anotação...")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = white,
                    cursorColor = yellow,
                    focusedLabelColor = white
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BlocoDeNotasTheme {
        LayoutBloco()
    }
}