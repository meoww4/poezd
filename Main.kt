import kotlin.random.Random

data class TrainCar(val capacity: Int, val passengers: Int)

class Train(val route: String) {
    private val cars = mutableListOf<TrainCar>()

    fun addCar(capacity: Int, passengers: Int) {
        cars.add(TrainCar(capacity, passengers))
    }

    fun carDetails(): List<TrainCar> = cars

    fun totalPassengers(): Int = cars.sumOf { it.passengers }

    fun totalCapacity(): Int = cars.sumOf { it.capacity }
}

fun getRandomCities(cities: List<String>): String {
    val city1 = cities[Random.nextInt(cities.size)]
    var city2: String
    do {
        city2 = cities[Random.nextInt(cities.size)]
    } while (city1 == city2)
    return "$city1 - $city2"
}

fun main() {
    val availableCities = listOf(
        "Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург",
        "Нижний Новгород", "Казань", "Челябинск", "Омск",
        "Самара", "Ростов-на-Дону", "Уфа", "Красноярск",
        "Волгоград", "Пермь", "Тюмень"
    )

    val cities = mutableListOf<String>()

    println("Список доступных городов:")
    availableCities.forEach { println(it) }

    println("Введите названия городов через запятую (введите 'END' для завершения ввода):")
    while (cities.size < 2) {
        val input = readLine()?.trim()
        if (input.equals("END", ignoreCase = true)) break

        val enteredCities = input?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: listOf()

        for (city in enteredCities) {
            if (availableCities.contains(city) && !cities.contains(city)) {
                cities.add(city)
            } else {
                println("Город '$city' не найден в списке доступных городов или уже был введен. Пожалуйста, введите корректные названия.")
            }
        }

        if (cities.size < 2) {
            println("Необходимо ввести как минимум два уникальных города.")
        }
    }

    while (true) {
        println("Хотите закончить работу? Введите EXIT для выхода или любую другую клавишу для продолжения.")
        if (readLine()?.trim()?.uppercase() == "EXIT") break

        val route = getRandomCities(cities)
        println("Создано направление: $route")

        val passengers = Random.nextInt(5, 202)
        println("Количество пассажиров, купивших билеты: $passengers")

        val train = Train(route) //создание поезда
        var totalPassengers = passengers

        while (totalPassengers > 0) {
            val capacity = Random.nextInt(5, 26)
            val currentPassengers = minOf(totalPassengers, capacity)
            train.addCar(capacity, currentPassengers)
            totalPassengers -= currentPassengers
        }

        println("Поезд '$route' состоит из ${train.carDetails().size} вагонов.")
        train.carDetails().forEachIndexed { index, car ->
            println("Вагон ${index + 1}: вместимость = ${car.capacity}, пассажиров = ${car.passengers}")
        }

        println("Поезд '$route' отправлен.")
        println()
    }
}
