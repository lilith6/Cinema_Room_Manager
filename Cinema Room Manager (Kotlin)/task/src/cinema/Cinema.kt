package cinema

fun main() {

    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seats = readln().toInt()

    val cinemaRoom = CinemaRoom(rows, seats)
    cinemaRoom.serveMenu()

}