package com.example.blocodenotascompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blocodenotascompose.datastore.StoreAnotacao
import com.example.blocodenotascompose.ui.theme.BLACK
import com.example.blocodenotascompose.ui.theme.BlocoDeNotasComposeTheme
import com.example.blocodenotascompose.ui.theme.GOLD
import com.example.blocodenotascompose.ui.theme.WHITE
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlocoDeNotasComposeTheme {
                BlocoDeNotasComponents()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlocoDeNotasComponents() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val storeAnotacao = StoreAnotacao(context)
    val anotacaoSalva = storeAnotacao.getAnotacao.collectAsState(initial = "")

    var anotacao by remember {
        mutableStateOf("")
    }

    anotacao = anotacaoSalva.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Bloco de Notas")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GOLD
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        storeAnotacao.salvarAnotacao(anotacao = anotacao)

                        Toast.makeText(context, "Anotação salva com sucesso.", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                containerColor = GOLD,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 4.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = BLACK,
                )
            }
        }
    ) { it ->
        Column(modifier = Modifier.padding(it)) {
            TextField(
                value = anotacao,
                onValueChange = { anotacao = it },
                label = {
                    Text(text = "Digite a sua anotação")
                },
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )
        }
    }
}