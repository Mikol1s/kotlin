package _2_programowanie_obiektowe

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import org.apache.commons.csv.CSVRecord
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter

class Client(
    val name: String,
    val surname: String,
    val pesel: String
)

interface CsvManagerInterface {
    fun readCsv(): List<CSVRecord>
    fun writeCsv(clients: List<Client>)
    fun getClients(csvRecords: List<CSVRecord>): List<Client>
}

class CsvManger(
    private val bufferedReader: BufferedReader,
    private val fileWriter: FileWriter?
) : CsvManagerInterface {

    private val csvParser = CSVParser(bufferedReader, CSVFormat.DEFAULT.withIgnoreHeaderCase())
    private val csvPrinter = CSVPrinter(fileWriter, CSVFormat.DEFAULT)

    override fun readCsv(): List<CSVRecord> {
        try {
            return csvParser.records
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            csvParser.close()
        }

        return emptyList()
    }

    override fun writeCsv(clients: List<Client>) {
        for (client in clients) {
            val data = listOf(client.name, client.surname, client.pesel)
            csvPrinter.printRecord(data)
        }
        csvPrinter.close()
    }

    override fun getClients(csvRecords: List<CSVRecord>): List<Client> {
        val clients = ArrayList<Client>()
        for (record in csvRecords) {
            val name = record.get(0)
            val surname = record.get(1)
            val pesel = record.get(2)

            val client = Client(name, surname, pesel)
            clients.add(client)
        }
        return clients
    }
}


fun main(args: Array<String>) {

    val bufferedReader =
        BufferedReader(FileReader(" *path_to_file* .csv"))
    val fileWriter =
        FileWriter(
            " *path_to_file* .csv",
            true
        )

    val csvManager = CsvManger(bufferedReader, fileWriter)

    val csvRecords = csvManager.readCsv()
    val clients = csvManager.getClients(csvRecords)

    for (client in clients) {
        println("Name: ${client.name}")
    }

    val newClient = Client("Jon", "Smith", "789")
    csvManager.writeCsv(listOf(newClient))

}









