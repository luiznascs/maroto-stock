import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext
import kotlin.random.Random


fun obterNomeThread(): String {
    return Thread.currentThread().name
}

suspend fun prepararComida(pedido: String) {
    println("Preparando comida em ${obterNomeThread()} com ${coroutineContext[CoroutineName]?.name}")
    val tempoDePreparo = Random.nextLong(1000, 5000)
    delay(tempoDePreparo)
    println("Comida pronta: $pedido em ${obterNomeThread()} com ${coroutineContext[CoroutineName]?.name}")
}


suspend fun fazerPedido(pedido: String) {
    println("Fazendo o pedido em ${obterNomeThread()} com ${coroutineContext[CoroutineName]?.name}")
    prepararComida(pedido)
}

// Função principal para simular o funcionamento do restaurante
fun main() = runBlocking {
    val pedidos = listOf("Pizza", "Hambúrguer", "Salada", "Sopa")

    // Cria um escopo para lançar coroutines com nome e dispatcher específico
    val jobs = mutableListOf<Job>()

    for (pedido in pedidos) {
        // Lança uma coroutine para cada pedido com um nome específico e dispatcher
        val job = launch(Dispatchers.IO + CoroutineName("Pedido - $pedido")) {
            fazerPedido(pedido)
            println("Pedido concluído: $pedido em ${obterNomeThread()} com ${coroutineContext[CoroutineName]?.name}")
        }
        jobs.add(job)
    }

    // Espera todos os pedidos serem processados
    jobs.forEach { it.join() }

    println("Todos os pedidos foram processados!")
}
