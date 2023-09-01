package com.sagrd.facturasapp

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import com.sagrd.facturasapp.Nav.AppNavigation
import com.sagrd.facturasapp.Nav.AppScreens
import com.sagrd.facturasapp.ui.theme.FacturasAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var FacturaDb: FacturaDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FacturasAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FormFacturaScreen(viewModel: FacturaViewModel = hiltViewModel(),
        navController: NavController)
    {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Agregar Facturas") },
                    modifier = Modifier.shadow(16.dp),
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(route = AppScreens.FirstScreen.route) }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            content = {
                FormFactura(navController=navController,viewModel = viewModel)
            }
        )
    }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FormFactura(
    viewModel: FacturaViewModel = hiltViewModel(),
    navController: NavController
) {
    val factura by viewModel.facturas.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            val keyboardController = LocalSoftwareKeyboardController.current

            OutlinedTextField(
                value = viewModel.fecha,
                onValueChange = { viewModel.fecha = it.toString() },
                label = { Text("Ingrese Fecha") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = viewModel.monto.toString(),
                onValueChange = { viewModel.monto = it.toDouble()},
                label = { Text("Ingrese Monto") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = viewModel.NCF.toString(),
                onValueChange = { viewModel.NCF = it.toDouble()},
                label = { Text("Ingrese NCF") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = viewModel.ITBIS.toString(),
                onValueChange = { viewModel.ITBIS = it.toDouble()},
                label = { Text("Ingrese ITBIS") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = viewModel.CDT.toString(),
                onValueChange = { viewModel.CDT = it.toDouble()},
                label = { Text("Ingrese CDT") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = viewModel.ISC.toString(),
                onValueChange = { viewModel.ISC = it.toDouble()},
                label = { Text("Ingrese ISC") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done),
            )

            OutlinedButton(onClick = {
                keyboardController?.hide()
                viewModel.saveFactura()
                viewModel.setMessageShown()
                navController.navigate(route = AppScreens.FirstScreen.route)
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription ="Done" )
            }
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacturasScreen(
    viewModel: FacturaViewModel = hiltViewModel(),
    navController: NavController
) {


    Scaffold (
        topBar = { TopAppBar(title = { Text(text = "Facturas") },
            modifier =Modifier.shadow(8.dp),
            navigationIcon = {
                Icon(imageVector = Icons.Default.DateRange, contentDescription ="" )
            }
        )},
        content = ({
            Facturas(viewModel)
        }),
        bottomBar = {
           Row (horizontalArrangement = Arrangement.End, modifier = Modifier
               .padding(16.dp)
               .fillMaxWidth()){
               FloatingActionButton(onClick = { navController.navigate(route = AppScreens.SecondScreen.route) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription ="Add" )
               }
           }
        }
    )

}

@Composable
fun Facturas(
    viewModel: FacturaViewModel = hiltViewModel()
)
{
    val factura by viewModel.facturas.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp)
    ) {

        items(factura) { factura ->
            Card(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth())
            {
                Text(text = """
                        ID:     ${factura.facturaId}
                        Fecha:  ${factura.fecha}
                        Monto:  ${factura.monto}
                        NCF:    ${factura.NCF}
                        ITBIS:  ${factura.ITBIS}
                        CDT:    ${factura.CDT}
                        ISC:    ${factura.ISC}
                    """.trimIndent(),modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@HiltViewModel
class FacturaViewModel @Inject constructor(
    private val facturaDb: FacturaDb
) : ViewModel() {
    var fecha : String by mutableStateOf("${LocalDate.now()}")
    var monto : Double? by mutableStateOf (0.0)
    var NCF : Double? by mutableStateOf (0.0)
    var ITBIS : Double? by mutableStateOf (0.0)
    var CDT : Double? by mutableStateOf (0.0)
    var ISC : Double? by mutableStateOf(0.0)

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()

    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }

    val facturas: StateFlow<List<Factura>> = facturaDb.facturaDao().getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun saveFactura() {
        viewModelScope.launch {
            val factura = Factura(
                fecha = fecha,
                monto = monto,
                NCF = NCF,
                ITBIS = ITBIS,
                CDT = CDT ,
                ISC = ISC
            )
            facturaDb.facturaDao().save(factura)
            limpiar()
        }
    }
    fun DeleteFactura() {
        viewModelScope.launch {
            val factura = Factura(
                fecha = fecha,
                monto = monto,
                NCF = NCF,
                ITBIS = ITBIS,
                CDT = CDT ,
                ISC = ISC
            )
            facturaDb.facturaDao().delete(factura)
            limpiar()
        }
    }

    private fun limpiar() {
        fecha =("${LocalDate.now()}")
        monto =(0.0)
        NCF = (0.0)
        ITBIS = (0.0)
        CDT =(0.0)
        ISC =(0.0)
    }
}

//region Room Database
@Entity(tableName = "Facturas")
data class Factura(
    @PrimaryKey
    val facturaId: Int?=null ,
    var fecha : String = "",
    var monto : Double? = (0.0),
    var NCF : Double? = (0.0),
    var ITBIS : Double? = (0.0),
    var CDT : Double? = (0.0) ,
    var ISC : Double? = (0.0)
)

@Dao
interface facturaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(factura: Factura)

    @Query(
        """
        SELECT * 
        FROM Facturas 
        WHERE facturaId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): Factura

    @Delete
    suspend fun delete(factura: Factura)

    @Query("SELECT * FROM Facturas")
     fun getAll(): Flow<List<Factura>>
}

@Database(
    entities = [Factura::class],
    version = 3
)
abstract class FacturaDb : RoomDatabase() {
    abstract fun facturaDao(): facturaDao
}
