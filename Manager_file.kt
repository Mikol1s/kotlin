package zaawansowane_rzeczy.menadzer_plikow_tekstowych

import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import kotlin.system.exitProcess


fun main() {
    val fm = MyFileManager()
    while(true) {
        showMenu()
        performFileOperation(fm = fm, opt = getUserInput())
    }
}

fun performFileOperation(fm: MyFileManager, opt: Int) {
    when (opt) {
        1 -> performOpenFile(fm)
        2 -> performWriteFile(fm)
        3 -> performDeleteFile(fm)
        4 -> exitProcess(0) // zakonczenie procesu

    }
}

fun performDeleteFile(fm: MyFileManager) {
    fm.delete(path = setFilePath(), onFailure = { println(it) },
        onCompleted = { isSuccess ->
            if (isSuccess)
                println("Poprawnie usunieto plik")
            else println("Cos poszlo nie tak")
        })
}

fun performWriteFile(fm: MyFileManager) {
    print("Wprowadz tekst >> ")
    val text = readLine()!!.toString()
    fm.write(path = setFilePath(), text = text)
}

fun performOpenFile(fm: MyFileManager) {
    fm.open(path = setFilePath(),
        onFailure = { println(it) },// wyjatek
        onCompleted = { fm.printFile(it) }) // jesli wyskoczy poprawne odczytanie

}

interface FileManagerInterface {
    fun open(
        path: String, onFailure: (Exception) -> Unit,
        onCompleted: (List<String>) -> Unit
    ) // funkcja która przyjmuje wyjątek i nic nie zwraca

    // security exeption ( czy mamy uprawnienia)
    fun write(path: String, text: String)
    fun delete(
        path: String, onFailure: (Exception) -> Unit,
        onCompleted: (Boolean) -> Unit
    )

    fun printFile(lines: List<String>)

}


class MyFileManager() : FileManagerInterface {


    override fun open(
        path: String, onFailure: (Exception) -> Unit,
        onCompleted: (List<String>) -> Unit
    ) {
        var fileReader: FileReader? = null
        try {
            fileReader = FileReader(path)
            //onCompleted(fileReader.readLines())
            fileReader.readLines()?.let { (onCompleted(it)) }
        } catch (e: Exception) {
            onFailure(e)
        } finally {
            fileReader?.close()
        }


    }

    override fun write(
        path: String,
        text: String
    ) {
        val file = File(path)
        FileOutputStream(file).use { out ->
            out.write(text.toByteArray())
        }
    }

    override fun delete(path: String, onFailure: (Exception) -> Unit, onCompleted: (Boolean) -> Unit) {
        val file = File(path) // uchwyt do pliku po to by usunac
        try {
            val resault = file.delete() // zwraca boola
            onCompleted(resault)

        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override fun printFile(lines: List<String>) {
        for (line in lines) {
            println(lines)
        }
    }
}

fun getUserInput(): Int {
    print("Wybierz opcje >> ")
    while (true) {
        try {
            val input = readLine()!!.toInt()
            if (input !in 1..4) throw IllegalAccessException("Liczba jest poza zakresem!!")// czy jestesmy w zakresie
            return input
        } catch (e: Exception) {
            println(e)
        }
    }
}

fun showMenu() {
    println("Menu")
    println("1. Otworz plik")
    println("2. Zapis do pliki")
    println("3. Usun plik")
    println("4. Wyjscie")
}

fun setFilePath(): String {
    print("Wskaz sciezke do pliku: ")
    return readLine()!!.toString()
}