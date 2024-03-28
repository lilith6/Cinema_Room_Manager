package cinema

import java.util.*

class CinemaRoom(private val rowNr: Int, private val seatsInRow: Int) {

    private val totalSeats = rowNr * seatsInRow
    private val cinemaSeats: MutableList<MutableList<Char>> = mutableListOf()
    private val menu = mapOf("Show the seats" to 1, "Buy a ticket" to 2, "Statistics" to 3, "Exit" to 0)
    private val smallRoomPrice = 10
    private val bigRoomPriceFront = 10
    private val bigRoomPriceBack = 8
    private var purchasedTickets = 0
    private var income = 0
    private val totalIncome: Int
    private var formatPercentage: String = "%.2f".format(Locale.US,0.0)

    init {
        var rowSeats = mutableListOf<Char>()
        for (i in 1..rowNr) {
            for (j in 1..seatsInRow) {
                rowSeats.add('S')
            }
            cinemaSeats.add(rowSeats)
            rowSeats = mutableListOf()
        }
    }

    init {
        totalIncome = if (totalSeats <= 60) totalSeats * smallRoomPrice
        else rowNr / 2 * seatsInRow * bigRoomPriceFront + (rowNr / 2 + rowNr % 2) * seatsInRow * bigRoomPriceBack
    }

    private fun printSeats() {
        println("Cinema: ")
        for (i in 0..rowNr) {
            if (i == 0) print(" ")
            else print("$i")
            for (j in 1..seatsInRow) {
                if (i == 0) print(" $j")
                else print(" ${cinemaSeats[i - 1][j - 1]}")
            }
            println()
        }
        println()
    }

    private fun printMenu() {
        println()
        menu.forEach { (key, value) ->
            println("${value}. $key")
        }
        println()
    }

    private fun getQuote(requestedRow: Int): Int {
        return when {
            totalSeats <= 60 -> smallRoomPrice
            else -> if (requestedRow <= rowNr / 2) bigRoomPriceFront else bigRoomPriceBack
        }
    }

    private fun displayPrice(requestedRow: Int) {
        println("Ticket price: $${getQuote(requestedRow)}")
        println()
    }

    private fun readSeat(): Pair<Int, Int> {
        var requestedRow = 0
        var requestedSeat = 0
        while (true) {
            println("Enter a row number:")
            requestedRow = readln().toInt()
            println("Enter a seat number in that row:")
            requestedSeat = readln().toInt()
            when {
                requestedRow !in 1..rowNr -> println("Wrong input!")
                requestedSeat !in 1..seatsInRow -> println("Wrong input!")
                cinemaSeats[requestedRow - 1][requestedSeat - 1] == 'B' -> println("That ticket has already been purchased")
                else -> break
            }
        }
        return Pair(requestedRow, requestedSeat)
    }

    private fun buyTickets() {

        val (requestedRow, requestedSeat) = readSeat()
        displayPrice(requestedRow)
        cinemaSeats[requestedRow - 1][requestedSeat - 1] = 'B'
        purchasedTickets++
        income += getQuote(requestedRow)
        val percentage = purchasedTickets.toDouble() / totalSeats * 100
        formatPercentage = "%.2f".format(Locale.US, percentage)
    }

    private fun printStatistics() {
        print(
            """
        Number of purchased tickets: $purchasedTickets
        Percentage: $formatPercentage%
        Current income: $$income
        Total income: $$totalIncome""".trimIndent()
        )
        println()
    }

    fun serveMenu() {
        do {
            printMenu()
            val selectedItem = readln().toInt()
            when (selectedItem) {
                1 -> printSeats()
                2 -> buyTickets()
                3 -> printStatistics()
            }
        } while (selectedItem != 0)
    }
}
