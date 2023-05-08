package strukturalnie

import kotlin.random.Random

//1. wypisz plansze -Do
//2. pobierz pole od urzytkownika - Do
//3. pobierz pole od komputera  - Do
//4. sprawdzaj wygrana
/*
Gracz -> 1
Computer ->-1
Puste -> 0
 */

fun main(args: Array<String>){

   val board= Array(9){0} // do każdego elementu tablicy wpisz 0
    var champion: String?=null

    while(true){
        drawBorad(board)
        getUserMove(board)

        champion = getWinner(board)
        if(champion !=null){
            println(champion)
            println("--------------")
            drawBorad(board)
            break
        }
        if(isGameEnd(board)){
            println("Remis")
            break
            }
        getComputerMove(board)

        champion = getWinner(board)
        if(champion !=null){
            println(champion)
            println("--------------")
            drawBorad(board)
            break
        }



    }


}

fun getWinner(board: Array<Int>): String? {
    if(checkRows(board) == 3) return "Wygral gracz"
    if(checkRows(board) == -3) return "Wygral komputer"

    if(checkColums(board) == 3) return "Wygral gracz"
    if(checkColums(board) == -3) return "Wygral komputer"

    if(checkDiagonal(board) == 3) return "Wygral gracz"
    if(checkDiagonal(board) == -3) return "Wygral komputer"

    return null
}

fun isGameEnd(board: Array<Int>):Boolean {
    for (i in board){
        if(i==0) return false

    }
    return true

}

fun checkDiagonal(board: Array<Int>):Int? {
    val diag1= board[0]+board[4]+board[8]
    val diag2= board[6]+board[4]+board[2]
    if(diag1==3||diag1==-3) return diag1
    if(diag2==3||diag2==-3) return diag2

    return null
}

fun checkColums(board: Array<Int>): Int? {
for(i in 0..2){
    val sum = board[0+i] + board[3+i]+board[6+i]
    if(sum == 3 || sum == -3) return sum
}
    return null
}

fun checkRows(board: Array<Int>):Int? {
    /*
    0 1 2
    3 4 5
    6 7 8
     */
    var sum = 0
    for (i in 0..8) {
        if (i != 0 && i % 3 == 0)
            sum += board[i]
        if (sum == 3 || sum == -3) return sum
    }
    return null
}

fun getComputerMove(board: Array<Int>) {

    while (true){
        val computerMove= Random.nextInt(0,9) // 9 nie jest brana pod uwage
        if(board[computerMove] !=0 ){
            continue
        }
        board[computerMove] = -1
        break
    }

}

fun getUserMove(board: Array<Int>) {

    while (true){
        print("Podaj nr pola w zakresie od 0-8: ")
        val x= readLine()!!.toInt()
        if(x !in 0..8){ // !in - nie jest z zakresu
            println("Nie ma takiego pola!")
            continue
        }
      if(board[x] != 0){
        println("Pole jest zajete!")
          continue
        }
        board[x]=1
        break
    }

}

fun drawBorad(board: Array<Int>) {

    for(i in board.indices){ // pętla po tablicy
        if(i != 0 && i%3 ==0) println()
        print("${board[i]}|")
    }
        println()
}
